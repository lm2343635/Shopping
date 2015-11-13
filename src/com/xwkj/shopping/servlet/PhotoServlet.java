package com.xwkj.shopping.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xwkj.common.util.FileTool;
import com.xwkj.common.util.ImageTool;
import com.xwkj.shopping.domain.Category;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Icon;
import com.xwkj.shopping.domain.Photo;
import com.xwkj.shopping.domain.Type;
import com.xwkj.shopping.service.util.ManagerTemplate;

import net.sf.json.JSONObject;

@WebServlet("/PhotoServlet")
public class PhotoServlet extends HttpServlet 
{
	//压缩宽度
	public static final int THUMBNAIL_WIDTH=800;
	//压缩高度
	public static final int THUMBNAIL_HEIGHT=600;
	//图标宽度
	public static final int ICON_WIDTH=100;
	///图标高度
	public static final int ICON_HEIGHT=100;
	//压缩格式
	public static final String THUMBNAIL_FORMAT="jpg";
	//上传路径
	public static final String PHOTO_FOLDER="upload";
	//上传文件大小限制
	private static final int FILE_MAX_SIZE=512*1024*1024;
	private static final long serialVersionUID = 1L;
	
	private String task;
       
    public PhotoServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "download":
			download(request,response);
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "uploadTypeIcon":
			uploadTypeIcon(request, response);
			break;
		case "uploadCategoryIcon":
			uploadCategoryIcon(request, response);
			break;
		case "uploadGoodPhoto": 
			uploadGoodPhoto(request, response);
			break;
		default:
			break;
		}
	}

	private void uploadTypeIcon(HttpServletRequest request,HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		JSONObject data=new JSONObject();
		String tid=request.getParameter("tid");
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ManagerTemplate template=(ManagerTemplate)context.getBean("managerTemplate");
		Type type=template.getTypeDao().get(tid);
		//如果之前有icon，删除
		if(type.getIcon()!=null) {
			Icon old=type.getIcon();
			type.setIcon(null);
			template.getTypeDao().update(type);
			String rootPath=getServletConfig().getServletContext().getRealPath("/");
			File file=new File(rootPath+"/"+PHOTO_FOLDER+"/"+type.getTid()+"/"+old.getFilename());
			file.delete();
			template.getIconDao().delete(old);
		}
		String filepath=createUploadPhotoDirectory(tid);
		String fileName=upload(request, filepath);
		//压缩图片
		if(!ImageTool.createThumbnail(filepath+"/"+fileName,ICON_WIDTH, ICON_HEIGHT)) {
			data.put("thumbnail", false);
			File file=new File(filepath+"/"+fileName);
			file.delete();
			response.getWriter().print(data.toString());
			return;
		}
		Icon icon=new Icon();
		icon.setFilename(UUID.randomUUID().toString()+"."+THUMBNAIL_FORMAT);
		icon.setUpload(new Date());
		String iid=template.getIconDao().save(icon);
		//保存成功
		if(iid!=null) {
			//更改名称
			FileTool.modifyFileName(filepath, fileName, icon.getFilename());
			data.put("filename", icon.getFilename());
			data.put("iid", icon.getIid());
			//为type更新icon
			type.setIcon(icon);
			template.getTypeDao().update(type);
		} else {
			//保存失败，删除图片
			File file=new File(filepath+"/"+fileName);
			file.delete();
			data.put("save", false);
		}
		response.getWriter().print(data.toString());
	}

	private void uploadCategoryIcon(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		JSONObject data=new JSONObject();
		String cid=request.getParameter("cid");
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ManagerTemplate template=(ManagerTemplate)context.getBean("managerTemplate");
		Category category=template.getCategoryDao().get(cid);
		if(category.getIcon()!=null) {
			Icon old=category.getIcon();
			category.setIcon(null);
			template.getCategoryDao().update(category);
			String rootPath=getServletConfig().getServletContext().getRealPath("/");
			File file=new File(rootPath+"/"+PHOTO_FOLDER+"/"+category.getType().getTid()+"/"+old.getFilename());
			file.delete();
			template.getIconDao().delete(old);
		}
		String filepath=createUploadPhotoDirectory(category.getType().getTid());
		String fileName=upload(request, filepath);
		//压缩图片
		if(!ImageTool.createThumbnail(filepath+"/"+fileName,ICON_WIDTH, ICON_HEIGHT)) {
			data.put("thumbnail", false);
			File file=new File(filepath+"/"+fileName);
			file.delete();
			response.getWriter().print(data.toString());
			return;
		}
		Icon icon=new Icon();
		icon.setFilename(UUID.randomUUID().toString()+"."+THUMBNAIL_FORMAT);
		icon.setUpload(new Date());
		String iid=template.getIconDao().save(icon);
		//保存成功
		if(iid!=null) {
			//更改名称
			FileTool.modifyFileName(filepath, fileName, icon.getFilename());
			data.put("filename", icon.getFilename());
			data.put("iid", icon.getIid());
			//更新icon
			category.setIcon(icon);
			template.getCategoryDao().update(category);
		} else {
			//保存失败，删除图片
			File file=new File(filepath+"/"+fileName);
			file.delete();
			data.put("save", false);
		}
		response.getWriter().print(data.toString());
	}

	private void uploadGoodPhoto(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		JSONObject data=new JSONObject();
		String gid=request.getParameter("gid");
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ManagerTemplate template=(ManagerTemplate)context.getBean("managerTemplate");
		Good good=template.getGoodDao().get(gid);
		String filepath=createUploadPhotoDirectory(good.getCategory().getType().getTid());
		String fileName=upload(request, filepath);
		//压缩图片
		if(!ImageTool.createThumbnail(filepath+"/"+fileName,THUMBNAIL_WIDTH,THUMBNAIL_HEIGHT)) {
			data.put("thumbnail", false);
			File file=new File(filepath+"/"+fileName);
			file.delete();
			response.getWriter().print(data.toString());
			return;
		}
		Photo photo=new Photo();
		photo.setGood(good);
		photo.setFilename(UUID.randomUUID().toString()+"."+THUMBNAIL_FORMAT);
		photo.setUpload(new Date());
		String pid=template.getPhotoDao().save(photo);
		//保存成功
		if(pid!=null) {
			//更改名称
			FileTool.modifyFileName(filepath, fileName, photo.getFilename());
			data.put("filename", photo.getFilename());
			data.put("pid", photo.getPid());
		} else {
			//保存失败，删除图片
			File file=new File(filepath+"/"+fileName);
			file.delete();
			data.put("save", false);
		}
		response.getWriter().print(data.toString());
	}

	/**
	 * 如果需要的照片上传路径不存在，则创建
	 * @param id
	 * @return 上传文件路径
	 */
	private String createUploadPhotoDirectory(String id) {
		String rootPath=getServletConfig().getServletContext().getRealPath("/");
		String filepath=rootPath+"/"+PHOTO_FOLDER+"/"+id;
		//如果不存文件夹，新建文件夹
		FileTool.createDirectoryIfNotExsit(filepath);
		return filepath;
	}
	
	/**
	 * 指定路径上传文件
	 * @param request HttpServletRequest
	 * @param filepath 文件路径
	 * @return 文件名
	 */
	@SuppressWarnings("unchecked")
	private String upload(HttpServletRequest request,String filepath) {
		String fileName=null;
		DiskFileItemFactory factory = new DiskFileItemFactory();//为文件对象产生工厂对象。
		factory.setSizeThreshold(1024*4); //设置缓冲区的大小，此处为4kb
		factory.setRepository(new File(filepath)); //设置上传文件的目的地
		ServletFileUpload upload = new ServletFileUpload(factory);//产生servlet上传对象
		upload.setSizeMax(FILE_MAX_SIZE);  //设置上传文件的大小
		try {
			List<FileItem> list=upload.parseRequest(request); //取得所有的上传文件信息
			Iterator<FileItem> it=list.iterator();
			while(it.hasNext()) {
			    FileItem item=it.next();
			    if(item.isFormField()==false) { 
				    fileName=item.getName();   //文件名
				    //取文件名  
				    fileName=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());               
				    if(!fileName.equals("")&&!(fileName==null)) {
				    	//如果fileName为null，即没有上传文件  
				    	File uploadedFile=new File(filepath,fileName);  
				        try  {
				        	item.write(uploadedFile);
				        } catch (Exception e) {
				        	e.printStackTrace();
				        }  
				    }            
			    }
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	private void download(HttpServletRequest request,HttpServletResponse response) throws IOException {
		int did=Integer.parseInt(request.getParameter("did"));
	
		String rootPath=getServletConfig().getServletContext().getRealPath("/");
		String filePath=rootPath+"document\\"+did;
		String fileName="";//此处设置文件名
		download(filePath, fileName, response);
	}
	
	/**
	 * 下载指定文件
	 * @param filePath 文件路径
	 * @param fileName
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	private void download(String filePath,String fileName,HttpServletResponse response) throws UnsupportedEncodingException 
	{
		FileInputStream in=null;
		ServletOutputStream out=null;
		response.setContentType("application/x-msdownload; charset=UTF-8");
		response.setHeader("Content-disposition","attachment; filename="+new String(fileName.getBytes("UTF-8"),"iso8859-1"));
		try
		{
			in=new FileInputStream(filePath+"\\"+fileName);
			out=response.getOutputStream();
			out.flush();
			int aRead=0;
			while((aRead=in.read())!=-1&in!=null)
				out.write(aRead);
			out.flush();
			in.close();
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}