/**
 * 
 */
package com.sudheer.assignment3;

public class Navigation {

	private String id;
	private String label;

	/**
	 * @param id
	 * @param label
	 */
	public Navigation(String id, String label) {
		this.id = id;
		this.label = label;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

}
