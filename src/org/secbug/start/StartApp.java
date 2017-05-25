package org.secbug.start;

import java.io.IOException;

import org.secbug.conf.Context;
import org.secbug.core.RecognManager;
import org.secbug.util.GetResultUtil;

public class StartApp {

	public static void main(String[] args) throws IOException {

		// 获取键入参数
		Context.args = args;

		// 系统初始化
		Context.INIT();

		// 任务开启识别工作
		RecognManager.recognPrint();

		// 获取指纹识别结果
		GetResultUtil.getResult();
	}

}
