/**
 * 
 */
package com.sudheer.assignment3;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class DirectoryJDO {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key dirID;
	@Persistent
	private String dirName;
	@Persistent
	private Long parentDirID;
	/*@Persistent
	private String userID;*/
	@Persistent(mappedBy="directory")
	private List<FileJDO> fileList=new ArrayList<FileJDO>();
	@Persistent
	private UserJDO userJDO;
	@Persistent
	private String id;
	
	/**
	 * @param dirID
	 * @param dirName
	 * @param parentDirID
	 */
	public DirectoryJDO( String dirName, Long parentDirID) {
		//this.dirID = dirID;
		this.dirName = dirName;
		this.parentDirID = parentDirID;
		//this.userID=userID;
	}
	/**
	 * @return the dirID
	 */
	public Key getDirID() {
		return dirID;
	}
	/**
	 * @param dirID the dirID to set
	 */
	public void setDirID(Key dirID) {
		this.dirID = dirID;
	}
	/**
	 * @return the dirName
	 */
	public String getDirName() {
		return dirName;
	}
	/**
	 * @param dirName the dirName to set
	 */
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	/**
	 * @return the parentDirID
	 */
	public Long getParentDirID() {
		return parentDirID;
	}
	/**
	 * @param parentDirID the parentDirID to set
	 */
	public void setParentDirID(Long parentDirID) {
		this.parentDirID = parentDirID;
	}
	/**
	 * @return the userID
	 *//*
	public String getUserID() {
		return userID;
	}
	*//**
	 * @param userID the userID to set
	 *//*
	public void setUserID(String userID) {
		this.userID = userID;
	}*/
	/**
	 * @return the fileList
	 */
	public List<FileJDO> getFileList() {
		return fileList;
	}
	/**
	 * @param fileList the fileList to set
	 */
	public void setFileList(List<FileJDO> fileList) {
		this.fileList = fileList;
	}
	/**
	 * @return the userJDO
	 */
	public UserJDO getUserJDO() {
		return userJDO;
	}
	/**
	 * @param userJDO the userJDO to set
	 */
	public void setUserJDO(UserJDO userJDO) {
		this.userJDO = userJDO;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	

}
