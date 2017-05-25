package org.secbug.vo;

public class Feature {

	private int id;
	private String program_name;
	private String url;
	private int recognitionType_id;
	private String recognition_content;
	private String manufacturerName;
	private String manufacturerUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProgram_name() {
		return program_name;
	}

	public void setProgram_name(String program_name) {
		this.program_name = program_name;
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

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getManufacturerUrl() {
		return manufacturerUrl;
	}

	public void setManufacturerUrl(String manufacturerUrl) {
		this.manufacturerUrl = manufacturerUrl;
	}

}
