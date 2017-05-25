package org.secbug.util;

import org.secbug.conf.Context;

public class ContextUtil {

	/**
	 * 虚拟机退出时调用
	 */
	public static void doShutDownWork() {
		Runtime run = Runtime.getRuntime();// 当前 Java 应用程序相关的运行时对象。
		run.addShutdownHook(new Thread() { // 注册新的虚拟机来关闭钩子
			@Override
			public void run() {

				System.out.println("指纹识别工作所需时间：" + ContextUtil.getRunTime());

			}
		});
	}

	/**
	 * 获取程序运行时间
	 * 
	 * @return
	 */
	public static String getRunTime() {
		long startTime = Context.STARTTIME; // 获取开始时间
		long endTime = System.currentTimeMillis(); // 获取结束时间
		double time = ((double) (endTime - startTime)) / 1000;
		return time + "秒";
	}

	public static void exitPrintln(Object str) {
		System.out.println(str);
		System.exit(-1);
	}

	/**
	 * 设置全局的任务配置信息，例如url和任务号
	 */
	public static void setJobOption() {
		JobCommand job = new JobCommand();
		job.init();
	}

}
