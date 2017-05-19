package org.secbug.conf;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.secbug.util.ContextUtil;
import org.secbug.util.DataBase;
import org.secbug.vo.Fingerprint;

public class Context {

	public static String[] args;
	public static DataBase dataBase = null;

	public static String currpath = "D:\\FingerCheck.txt";
	public static List<String> urls = new ArrayList<String>();
	public static int jobid = 0;

	// public static boolean falg = false;
	public static int i = 0;
	public static StringBuffer UrlMatch = new StringBuffer();
	public static List<String> SUREURLS = new ArrayList<String>(); // 指纹识别成功的url

	public static List<String> urladds = new ArrayList<String>();
	public static List<Fingerprint> fingerprints = new ArrayList<Fingerprint>();

	public static int THREADCACHE = 5000; // 线程间隔，每执行1万次对象清除一下系统缓存，调用System.gc();
	public static int THREADNUM = 50; // 线程数
	public static int TASKCOUNT = 0; // 总任务数
	public static int CURRENTNUM = 0; // 已完成任务总数
	public static long STARTTIME = 0; // 系统初始化时间

	public static int port = 80; // 端口
	public static String protocol = "http"; // 协议
	public static int model = 1; // 识别模式(1。快速模式 2.精准模式 3.人工判断 )
	public static String outputPath = "";  // 结果输出到文件下
	public static List<String> fastProbeList = new ArrayList<String>(); // 快速模式（单个域名或ip只出现一次）
	public static List<String> requestUrl = new ArrayList<String>(); // 自定义
																		// --设置请求url
	public static List<String> responseStr = new ArrayList<String>(); // 自定义
																		// --设置响应内容关键字

	public static List<Integer> resultIds = new ArrayList<Integer>();
	public static HashMap<Integer, String> resultHashMap = new HashMap<Integer, String>(); // 自定义模式
																							// 获取结果

	/***
	 * 更新进度
	 */
	public static synchronized void updatePercent() {
		Context.CURRENTNUM++;
	}

	public static synchronized int getTaskCount() {
		int urlCount = urladds.size();
		if (fingerprints == null) {
			int requestUrlCount = requestUrl.size();
			int responseStrCount = responseStr.size();
			return urlCount * requestUrlCount * responseStrCount;
		} else {
			int fingerCount = fingerprints.size();
			return urlCount * fingerCount;
		}
	}

	/**
	 * 进度条类
	 * 
	 * @return 100.00%
	 */
	public static synchronized String getPercent() {
		double count = Context.getTaskCount();
		double curent = Context.CURRENTNUM;
		System.out.print("count=" + count + " current=" + curent);
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位
		String ret = format.format(curent / count);// 打印计算结果
		System.out.print(" " + ret + "\r\n");
		return ret;
	}

	/**
	 * 设置初始化设置
	 * 
	 */
	public static void INIT() {

		// 注册数据库驱动
		ContextUtil.regDriver();
		// 打开数据库连接
		ContextUtil.getDataSource();

		// 设置用户变量
		ContextUtil.setJobOption();

		// 程序开始运行时间
		Context.STARTTIME = System.currentTimeMillis();

		// 虚拟机退出时的hook
		ContextUtil.doShutDownWork();

	}

	public static void END() {

		// 关闭数据库连接
		ContextUtil.closeDataSource();
	}
}
