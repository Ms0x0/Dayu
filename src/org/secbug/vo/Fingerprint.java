package org.secbug.vo;

public class Fingerprint {

	private int id;
	private int userid;
	private String program_name;
	private int programType_id;
	private String version;
	private String url;
	private int recognitionType_id;
	private String recognition_content;
	private int manufacturer_id;
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

	public String getProgram_name() {
		return program_name;
	}

	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}

	public int getProgramType_id() {
		return programType_id;
	}

	public void setProgramType_id(int programType_id) {
		this.programType_id = programType_id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getRecognitionType_id() {
		return recognitionType_id;
	}

	public void setRecognitionType_id(int recognitionType_id) {
		this.recognitionType_id = recognitionType_id;
	}

	public String getRecognition_content() {
		return recognition_content;
	}

	public void setRecognition_content(String recognition_content) {
		this.recognition_content = recognition_content;
	}

	public int getManufacturer_id() {
		return manufacturer_id;
	}

	public void setManufacturer_id(int manufacturer_id) {
		this.manufacturer_id = manufacturer_id;
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
