package com.xwkj.shopping.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.directwebremoting.WebContextFactory;

import com.xwkj.common.util.FileTool;
import com.xwkj.shopping.bean.TypeBean;
import com.xwkj.shopping.domain.Icon;
import com.xwkj.shopping.domain.Type;
import com.xwkj.shopping.service.TypeManager;
import com.xwkj.shopping.service.util.ManagerTemplate;
import com.xwkj.shopping.servlet.PhotoServlet;

public class TypeManagerImpl extends ManagerTemplate implements TypeManager {

	@Override
	public TypeBean getType(String tid) {
		Type type=typeDao.get(tid);
		if(type==null)
			return null;
		return new TypeBean(type);
	}

	@Override
	public String addType(String tname) {
		Type type=new Type();
		type.setTname(tname);
		type.setCreateDate(new Date());
		type.setCategories(0);
		return typeDao.save(type);
	}

	@Override
	public void modifyType(String tid, String tname) {
		Type type=typeDao.get(tid);
		type.setTname(tname);
		typeDao.update(type);
	}

	@Override
	public boolean deleteType(String tid) {
		Type type=typeDao.get(tid);
		Icon icon=type.getIcon();
		if(type.getCategories()>0)
			return false;
		typeDao.delete(type);
		String rootPath=WebContextFactory.get().getServletContext().getRealPath("/");
		String path=rootPath+PhotoServlet.PHOTO_FOLDER+"/"+tid;
		FileTool.delAllFile(path);
		FileTool.delFolder(path);
		iconDao.delete(icon);
		return true;
	}

	@Override
	public List<TypeBean> getAll(boolean enable) {
		List<TypeBean> types=new ArrayList<>();
		for(Type type: typeDao.findAll(enable)) 
			types.add(new TypeBean(type));
		return types;
	}

	@Override
	public void updateCreateDate(String tid) {
		Type type=typeDao.get(tid);
		type.setCreateDate(new Date());
		typeDao.update(type);
	}

	@Override
	public void enable(String cid, boolean enable) {
		Type type=typeDao.get(cid);
		type.setEnable(enable);
		typeDao.update(type);
	}

}
