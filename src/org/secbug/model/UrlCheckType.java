package org.secbug.model;

import org.apache.log4j.Logger;
import org.secbug.conf.Context;

public class UrlCheckType implements CheckType {

	private Logger logger = Logger.getLogger(UrlCheckType.class);

	@Override
	public boolean check(String url, String recogninfo, String responseInfo) {
		boolean falg = false;
		if (responseInfo.equals("true")) {
			falg = true;
			return falg;
		}
		logger.info("当前jobid：" + Context.jobid + " 检验状态：" + falg + " 识别内容：" + recogninfo + " URL：" + url);
		return falg;
	}

}
