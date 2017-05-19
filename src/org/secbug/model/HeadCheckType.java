package org.secbug.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.secbug.conf.Context;

public class HeadCheckType implements CheckType {

	private Logger logger = Logger.getLogger(HeadCheckType.class);

	@Override
	public boolean check(String url, String recogninfo, String responseInfo) {
		boolean falg = false;
		// 关键字寻找
		try {
			recogninfo = recogninfo.replace("&lt;", "<").replace("&gt;", ">");
			if (responseInfo.indexOf(recogninfo) != -1 || responseInfo.equals(recogninfo)) {
				falg = true;
			} else { // 正则表达式
				Pattern pattern = Pattern.compile(recogninfo);
				Matcher matcher = pattern.matcher(responseInfo);
				if (matcher.find()) {
					falg = true;
				}
			}
			logger.info("当前jobid：" + Context.jobid + " 检验状态：" + falg + " 识别内容：" + recogninfo + " URL：" + url);
		} catch (Exception e) {
			logger.error("当前jobid：" + Context.jobid + " 错误信息：" + e.toString());
		}
		return falg;
	}

}
