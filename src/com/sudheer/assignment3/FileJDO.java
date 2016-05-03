/**
 * 
 */
package com.sudheer.assignment3;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

@PersistenceCapable
public class FileJDO {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long fileID;
	@Persistent
	private Long parentDirID;
	@Persistent
	private String fileName;
	@Persistent
	private Blob fileContent;
	@Persistent
	private DirectoryJDO directory;
	
	/**
	 * @param fileID
	 * @param parentDirID
	 * @param fileName
	 * @param fileContent
	 */
	public FileJDO(Long fileID, Long parentDirID, String fileName,
			Blob fileContent) {
		this.fileID = fileID;
		this.parentDirID = parentDirID;
		this.fileName = fileName;
		this.fileContent = fileContent;
	}

	/**
	 * @return the fileID
	 */
	public Long getFileID() {
		return fileID;
	}

	/**
	 * @param fileID
	 *            the fileID to set
	 */
	public void setFileID(Long fileID) {
		this.fileID = fileID;
	}

	/**
	 * @return the parentDirID
	 */
	public Long getParentDirID() {
		return parentDirID;
	}

	/**
	 * @param parentDirID
	 *            the parentDirID to set
	 */
	public void setParentDirID(Long parentDirID) {
		this.parentDirID = parentDirID;
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
	 * @return the directory
	 */
	public DirectoryJDO getDirectory() {
		return directory;
	}

	/**
	 * @param directory the directory to set
	 */
	public void setDirectory(DirectoryJDO directory) {
		this.directory = directory;
	}

	
}
