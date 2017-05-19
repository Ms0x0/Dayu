package org.secbug.model;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.secbug.conf.Context;
import org.secbug.util.Md5Utils;

public class MD5CheckType implements CheckType {

	private Logger logger = Logger.getLogger(MD5CheckType.class);

	@Override
	public boolean check(String url, String recogninfo, String responseInfo) {
		boolean falg = false;
		try {
			responseInfo = responseInfo.replace("\\", "\\\\");
			if (responseInfo.indexOf("D:\\") != -1) {
				String md5value = Md5Utils.getMd5ByFile(new File(responseInfo));
				if ((recogninfo.toUpperCase()).equals(md5value.toUpperCase())) {
					falg = true;
				}
				logger.info("当前jobid：" + Context.jobid + " 文件MD5值：" + md5value.toUpperCase() + " 识别内容："
						+ recogninfo.toUpperCase() + " URL：" + url);
				responseInfo = responseInfo.replace("\\\\", "\\");
				File file = new File(responseInfo);
				if (file.exists()) {
					file.delete();
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("当前jobid：" + Context.jobid + " 错误信息：" + e.toString());
		}
		return falg;
	}

}
