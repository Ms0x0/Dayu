package org.secbug.vo;

public class Result {

	private int id;
	private int jobid;
	private String recognUrl;
	private int fingerPrint_id;
	private String creatime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getJobid() {
		return jobid;
	}

	public void setJobid(int jobid) {
		this.jobid = jobid;
	}

	public String getRecognUrl() {
		return recognUrl;
	}

	public void setRecognUrl(String recognUrl) {
		this.recognUrl = recognUrl;
	}

	public int getFingerPrint_id() {
		return fingerPrint_id;
	}

	public void setFingerPrint_id(int fingerPrint_id) {
		this.fingerPrint_id = fingerPrint_id;
	}

	public String getCreatime() {
		return creatime;
	}

	public void setCreatime(String creatime) {
		this.creatime = creatime;
	}

}
