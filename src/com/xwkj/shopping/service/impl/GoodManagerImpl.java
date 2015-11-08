package com.xwkj.shopping.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.directwebremoting.WebContextFactory;

import com.xwkj.shopping.bean.GoodBean;
import com.xwkj.shopping.domain.Category;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Photo;
import com.xwkj.shopping.domain.Type;
import com.xwkj.shopping.service.GoodManager;
import com.xwkj.shopping.service.util.ManagerTemplate;
import com.xwkj.shopping.servlet.PhotoServlet;

public class GoodManagerImpl extends ManagerTemplate implements GoodManager {

	@Override
	public GoodBean getGood(String gid) {
		Good good=goodDao.get(gid);
		if(good==null)
			return null;
		return new GoodBean(good);
	}

	@Override
	public String addGood(String cid, String gname, double price, int number, String descriptor) {
		Good good=new Good();
		Category category=categoryDao.get(cid);
		good.setCategory(category);
		good.setGname(gname);
		good.setPrice(price);
		good.setNumber(number);
		good.setDescriptor(descriptor);
		good.setCreateDate(new Date());
		good.setSold(0);
		String gid=goodDao.save(good);
		if(gid!=null) {
			category.setGoods(category.getGoods()+1);
			categoryDao.update(category);
			return gid;
		}
		return null;
	}

	@Override
	public void modifyGood(String gid, String gname, double price, int number, String descriptor) {
		Good good=goodDao.get(gid);
		good.setGname(gname);;
		good.setPrice(price);
		good.setNumber(number);
		good.setDescriptor(descriptor);
		goodDao.update(good);
	}

	@Override
	public int addGoodNumber(String gid, int dnumber) {
		Good good=goodDao.get(gid);
		good.setNumber(good.getNumber()+dnumber);
		goodDao.update(good);
		return good.getNumber();
	}

	@Override
	public boolean removeGood(String gid) {
		Good good=goodDao.get(gid);
		Category category=good.getCategory();
		if(good.getSold()>0)
			return false;
		String rootPath=WebContextFactory.get().getServletContext().getRealPath("/");
		String path=rootPath+PhotoServlet.PHOTO_FOLDER+"/"+good.getCategory().getType().getTid();
		for(Photo photo: photoDao.findByGood(good)) {
			File file=new File(path+"/"+photo.getFilename());
			file.delete();
		}
		goodDao.delete(good);
		category.setGoods(category.getGoods()-1);
		categoryDao.update(category);
		return true;
	}

	@Override
	public List<GoodBean> getGoodsByCid(String cid) {
		List<GoodBean> goods=new ArrayList<>();
		Category category=categoryDao.get(cid);
		for(Good good: goodDao.findByCategory(category)) 
			goods.add(new GoodBean(good));
		return goods;
	}

	@Override
	public List<GoodBean> getGoodsByTidWithLimit(String tid, int limit) {
		List<GoodBean> goods=new ArrayList<>();
		Type type=typeDao.get(tid);
		for(Good good: goodDao.findByTypeWithLimit(type, limit))
			goods.add(new GoodBean(good));
		return goods;
	}

	@Override
	public List<GoodBean> getHotestGoodsWithLimit(int limit) {
		List<GoodBean> goods=new ArrayList<>();
		for(Good good: goodDao.findHotestWithLimit(limit))
			goods.add(new GoodBean(good));
		return goods;
	}

	@Override
	public List<GoodBean> searchGoods(String gname) {
		List<GoodBean> goods=new ArrayList<>();
		for(Good good: goodDao.findByGname(gname))
			goods.add(new GoodBean(good));
		return goods;
	}

}
