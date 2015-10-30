package com.xwkj.shopping.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.directwebremoting.WebContextFactory;

import com.xwkj.shopping.bean.CategoryBean;
import com.xwkj.shopping.domain.Category;
import com.xwkj.shopping.domain.Icon;
import com.xwkj.shopping.domain.Type;
import com.xwkj.shopping.service.CategoryManager;
import com.xwkj.shopping.service.util.ManagerTemplate;
import com.xwkj.shopping.servlet.PhotoServlet;

public class CategoryManagerImpl extends ManagerTemplate implements CategoryManager {

	@Override
	public CategoryBean getCategory(String cid) {
		Category category=categoryDao.get(cid);
		if(category==null)
			return null;
		return new CategoryBean(category);
	}

	@Override
	public String addCategory(String tid, String cname) {
		Category category=new Category();
		Type type=typeDao.get(tid);
		category.setType(type);
		category.setCname(cname);
		category.setCreateDate(new Date());
		category.setGoods(0);
		String cid=categoryDao.save(category);
		if(cid!=null) {
			type.setCategories(type.getCategories()+1);
			typeDao.update(type);
			return cid;
		}
		return null;
	}

	@Override
	public void modifyCategory(String cid, String cname) {
		Category category=categoryDao.get(cid);
		category.setCname(cname);
		categoryDao.update(category);
	}

	@Override
	public boolean removeCategory(String cid) {
		Category category=categoryDao.get(cid);
		if(category.getGoods()>0)
			return false;
		Type type=category.getType();
		Icon icon=category.getIcon();
		categoryDao.delete(category);
		//删除图标
		String rootPath=WebContextFactory.get().getServletContext().getRealPath("/");
		String path=rootPath+PhotoServlet.PHOTO_FOLDER+"/"+type.getTid();
		File file=new File(path+"/"+icon.getFilename());
		file.delete();
		iconDao.delete(icon);
		//更新一级分类
		type.setCategories(type.getCategories()-1);
		typeDao.update(type);
		return true;
	}

	@Override
	public List<CategoryBean> getCategoriesByTid(String tid) {
		List<CategoryBean> categories=new ArrayList<>();
		Type type=typeDao.get(tid);
		for(Category category: categoryDao.findByType(type))
			categories.add(new CategoryBean(category));
		return categories;
	}

}
