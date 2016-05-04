/**
 * 
 */
package com.sudheer.assignment3;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
@PersistenceCapable
public class FileJDO {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key fileID;
	@Persistent
	private String dirID;
	@Persistent
	private String fileName;
	@Persistent
	private Blob fileContent;
	
	@Persistent
	private String id;
	
	/*@Persistent
	private DirectoryJDO directory;*/
	
	/**
	 * @param fileID
	 * @param parentDirID
	 * @param fileName
	 * @param fileContent
	 */
	/*public FileJDO(Long fileID, Long parentDirID, String fileName,
			Blob fileContent) {
		this.fileID = fileID;
		this.parentDirID = parentDirID;
		this.fileName = fileName;
		this.fileContent = fileContent;
	}*/

	/**
	 * @return the fileID
	 */
	public Key getFileID() {
		return fileID;
	}

	/**
	 * @param fileID
	 *            the fileID to set
	 */
	public void setFileID(Key fileID) {
		this.fileID = fileID;
	}

	/**
	 * @return the parentDirID
	 */
	public String getDirID() {
		return dirID;
	}

	/**
	 * @param parentDirID
	 *            the parentDirID to set
	 */
	public void setDirID(String dirID) {
		this.dirID = dirID;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileContent
	 */
	public Blob getFileContent() {
		return fileContent;
	}

	/**
	 * @param fileContent
	 *            the fileContent to set
	 */
	public void setFileContent(Blob fileContent) {
		this.fileContent = fileContent;
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

	/**
	 * @return the directory
	 *//*
	public DirectoryJDO getDirectory() {
		return directory;
	}

	*//**
	 * @param directory the directory to set
	 *//*
	public void setDirectory(DirectoryJDO directory) {
		this.directory = directory;
	}*/

	
}
