package com.xwkj.shopping.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.directwebremoting.WebContextFactory;

import com.xwkj.shopping.bean.PhotoBean;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Photo;
import com.xwkj.shopping.service.PhotoManager;
import com.xwkj.shopping.service.util.ManagerTemplate;
import com.xwkj.shopping.servlet.PhotoServlet;

public class PhotoManagerImpl extends ManagerTemplate implements PhotoManager {

	@Override
	public void removePhoto(String pid) {
		Photo photo=photoDao.get(pid);
		String rootPath=WebContextFactory.get().getServletContext().getRealPath("/");
		String pathname=rootPath+PhotoServlet.PHOTO_FOLDER+"/"+photo.getGood().getCategory().getType().getTid()+"/"+photo.getFilename();
		File file=new File(pathname);
		file.delete();
		Good good=photo.getGood();
		if(good.getCover()!=null) {
			if(good.getCover().getPid().equals(photo.getPid())) {
				good.setCover(null);
				goodDao.update(good);
			}
		}
		photoDao.delete(photo);
	}

	@Override
	public void setAsGoodCover(String pid) {
		Photo photo=photoDao.get(pid);
		Good good=photo.getGood();
		good.setCover(photo);
		goodDao.update(good);
	}

	@Override
	public List<PhotoBean> getPhotosByGid(String gid) {
		Good good=goodDao.get(gid);
		List<PhotoBean> photos=new ArrayList<>();
		for(Photo photo: photoDao.findByGood(good))
			photos.add(new PhotoBean(photo));
		return photos;
	}
}
