package org.secbug.vo;

public class Job {

	private int id;
	private String creatime;
	private int status;
	private String oneperent;
	private String twoperent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getOneperent() {
		return oneperent;
	}

	public void setOneperent(String oneperent) {
		this.oneperent = oneperent;
	}

	public String getTwoperent() {
		return twoperent;
	}

	public void setTwoperent(String twoperent) {
		this.twoperent = twoperent;
	}

}
