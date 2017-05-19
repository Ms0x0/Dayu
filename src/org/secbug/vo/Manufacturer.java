package org.secbug.vo;

public class Manufacturer {

	private int id;
	private int userid;
	private String name;
	private String url;
	private String creatime;
	private int status;
	private int isCheck;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreatime() {
		return creatime;
	}

	public void setCreatime(String creatime) {
		this.creatime = creatime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

}
