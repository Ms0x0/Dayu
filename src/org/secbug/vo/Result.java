package org.secbug.vo;

public class Result {

	private int featureid;
	private String program_name;
	private String recognUrl;
	private String manufacturerName;
	private String manufacturerUrl;

	private String domainUrl;

	public int getFeatureid() {
		return featureid;
	}

	public void setFeatureid(int featureid) {
		this.featureid = featureid;
	}

	public String getProgram_name() {
		return program_name;
	}

	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}

	public String getRecognUrl() {
		return recognUrl;
	}

	public void setRecognUrl(String recognUrl) {
		this.recognUrl = recognUrl;
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

	public String getDomainUrl() {
		return domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}

	public Result(String program_name, String recognUrl, String manufacturerUrl) {
		super();
		this.program_name = program_name;
		this.recognUrl = recognUrl;
		this.manufacturerUrl = manufacturerUrl;
	}

	public Result(int featureid, String program_name, String recognUrl, String manufacturerUrl, String domainUrl) {
		super();
		this.featureid = featureid;
		this.program_name = program_name;
		this.recognUrl = recognUrl;
		this.manufacturerUrl = manufacturerUrl;
		this.domainUrl = domainUrl;
	}

	public Result() {
		super();
	}

}
