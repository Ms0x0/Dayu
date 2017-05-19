package org.secbug.model;

public interface CheckType {

	/**
	 * 识别方式
	 */
	public abstract boolean check(String url,String recogninfo,String responseInfo);

}
