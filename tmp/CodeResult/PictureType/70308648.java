package com.tutoring.action;


import java.io.File;
import java.util.Map;
import com.tutoring.util.StaticUtil;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tutoring.biz.TopicBiz;
import com.tutoring.entity.User;

public class LaunchTopicAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private File picture;
	private File file;
	private String pictureFileName;
	private String fileFileName;
	private String pictureContentType;
	private String fileContentType;
	private String type;
	private String title;
	
	TopicBiz topicBiz;
	
	public void setTopicBiz(TopicBiz topicBiz) {
		this.topicBiz = topicBiz;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	private String content;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public File getPicture() {
		return picture;
	}
	public void setPicture(File picture) {
		this.picture = picture;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getPictureFileName() {
		return pictureFileName;
	}
	public void setPictureFileName(String pictureFileName) {
		this.pictureFileName = pictureFileName;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getPictureContentType() {
		return pictureContentType;
	}
	public void setPictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	
	public String execute() throws Exception{
		//System.out.println(fileFileName+","+pictureFileName+","+type);
		ActionContext ac = ActionContext.getContext();
		Map<String, Object> session = ac.getSession();
		String pic_sn = null;
		String attach_sn = null;
		System.out.println("title length:"+title.length());
		
		if(title.length()>30||title.length()==0||content.length()>1000||type==null)
			return ERROR;
		
		
		if(picture!=null)
		{
			String pictureType = pictureFileName.substring(pictureFileName.indexOf('.'));
			if(pictureType.equals(".jpg")||pictureType.equals(".jpeg")||pictureType.equals(".png")||pictureType.equals(".bmp"))
			{
				String filetype = pictureFileName.substring(pictureFileName.indexOf('.'));
				pic_sn = StaticUtil.generateRandomString(10)+filetype;
				//System.out.println(filetype);
				String storePath = ServletActionContext.getServletContext().getRealPath( "/images" )+"/"+pic_sn;
				File storeFile = new File(storePath);
				StaticUtil.copy(picture,storeFile);
			}
			else
				return ERROR;
		}
		
		
		
		if(file!=null)
		{
			
			String filetype = fileFileName.substring(fileFileName.indexOf('.'));
			attach_sn = StaticUtil.generateRandomString(10)+filetype;
			String storePath = ServletActionContext.getServletContext().getRealPath( "/download" )+"/"+attach_sn;
			File storeFile = new File(storePath);
			StaticUtil.copy(file,storeFile);
			
		}
		topicBiz.publishQuestion(((User)session.get("user")).getEmail(), title, content, type,pic_sn,attach_sn,fileFileName);
		return SUCCESS;
	}
	
	 
	

}
