/**
 * 
 */
package com.sudheer.assignment3;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 *
 */
@PersistenceCapable
public class UserJDO {

	@PrimaryKey
	@Persistent
	private Key userID;

	@Persistent(mappedBy = "userJDO")
	private ArrayList<DirectoryJDO> directoriesList;

	/**
	 * @param userID
	 */
	public UserJDO(Key userID) {
		this.userID = userID;
	}

	/**
	 * @return the userID
	 */
	public Key getUserID() {
		return userID;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(Key userID) {
		this.userID = userID;
	}

	/**
	 * @return the directoriesList
	 */
	public ArrayList<DirectoryJDO> getDirectoriesList() {
		return directoriesList;
	}

	/**
	 * @param directoriesList
	 *            the directoriesList to set
	 */
	public void setDirectoriesList(ArrayList<DirectoryJDO> directoriesList) {
		this.directoriesList = directoriesList;
	}

	public void addDirectory(DirectoryJDO directory) {
		if (directoriesList == null)
			directoriesList = new ArrayList<DirectoryJDO>();
		directoriesList.add(directory);
	}
}
