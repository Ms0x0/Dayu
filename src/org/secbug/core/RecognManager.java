package org.secbug.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.secbug.conf.Context;
import org.secbug.inter.IntruderCheck;
import org.secbug.inter.IntruderCrack;
import org.secbug.inter.ProCheck;
import org.secbug.inter.ProCrack;
import org.secbug.vo.Feature;

public class RecognManager {

	private static Logger logger = Logger.getLogger(RecognManager.class);

	/**
	 * 识别url指纹
	 * 
	 * 识别模式：1：自定义模式 2：常规模式
	 * 
	 * 任务状态：1：开启 2：自定义模式 3：常规模式 4：结束
	 */
	public static boolean recognPrint() {
		int state = 0;
		boolean falg = false;
		ReadCheck readCheck = new ReadCheck();

		List<String> urladds = RecognManager.getUrlProtocol(Context.urls);
		System.out.println("正在访问url,建立连接。。。");
		// 检测域名或ip是否建立连接或读取
		int i = 0;
		IntruderCheck intruderCheck = new IntruderCheck();
		for (String urlPath : urladds) {
			++i;
			ProCheck proCheck = new ProCheck(urlPath, urladds);
			intruderCheck.start(proCheck);
			if (i == 1000) {
				try {
					Thread.sleep(Context.THREADCACHE);
				} catch (InterruptedException e) {
					logger.error("当前jobid：" + Context.jobid + " 信息：" + e.toString());
				}
				intruderCheck.clearCache();
				i = 0;
			}
		}
		intruderCheck.clearCache();
		intruderCheck.shutdown();

		urladds = RecognManager.getUrlProtocol(Context.urls);
		// 自定义模式
		if (Context.requestUrl.size() != 0 && Context.requestUrl != null) {
			state = 2;

			RecognManager.getPercentByJob(null, urladds);
			RecognManager.start(null, urladds, Context.jobid, state, 1);

			falg = true;
			return falg;
		}
		// 常规模式
		@SuppressWarnings("static-access")
		List<Feature> features = readCheck.getCheckData();
		List<String> urladds1 = RecognManager.getUrlProtocol(Context.urls);
		state = 3;

		RecognManager.getPercentByJob(features, urladds1);
		RecognManager.start(features, urladds1, Context.jobid, state, 2); // 精准模式

		falg = true;
		return falg;
	}

	/**
	 * 执行 :1.自定义模式 2.常规模式
	 */
	private static void start(List<Feature> features, List<String> urladds, int jobid, int state, int count) {
		int i = 0;
		IntruderCrack intruderCrack = new IntruderCrack();
		if (count == 1) {
			for (String url : urladds) {
				for (String requestUrl : Context.requestUrl) {
					for (String reponseStr : Context.responseStr) {
						++i;
						String urlPath = url + requestUrl;
						ProCrack crack = new ProCrack(url, urlPath, 2, reponseStr, null, null, 0, null);
						intruderCrack.start(crack);
						if (i == Context.THREADCACHE) {
							try {
								Thread.sleep(Context.THREADCACHE);
							} catch (InterruptedException e) {
								logger.error("当前jobid：" + Context.jobid + " 信息：" + e.toString());
							}
							intruderCrack.clearCache();
							i = 0;
						}
					}
				}
			}
			intruderCrack.clearCache();
		} else if (count == 2) {
			for (String url : urladds) {
				for (Feature feature : features) {
					++i;
					String urlPath = url + feature.getUrl();

					ProCrack crack = new ProCrack(url, urlPath, feature.getRecognitionType_id(),
							feature.getRecognition_content(), feature.getProgram_name(), feature.getManufacturerName(),
							feature.getId(), feature.getManufacturerUrl());
					intruderCrack.start(crack);
					if (i == Context.THREADCACHE) {
						try {
							Thread.sleep(Context.THREADCACHE);
						} catch (InterruptedException e) {
							logger.error("当前jobid：" + Context.jobid + " 信息：" + e.toString());
						}
						intruderCrack.clearCache();
						i = 0;
					}
				}
			}
			intruderCrack.clearCache();
		}
		intruderCrack.shutdown();
	}

	// 改成多个url
	private static List<String> getUrlProtocol(List<String> urls) {
		List<String> urladds = new ArrayList<String>();
		for (String url : urls) {
			StringBuffer urlPath = new StringBuffer();
			if (url.indexOf("http") == -1 && url.indexOf("https") == -1) { // 默认为http
				urlPath.append("http://");
				urlPath.append(url);
			} else {
				urlPath.append(url);
			}
			String urlstr = urlPath.toString();
			if (urlstr.endsWith("/")) {
				urlstr = urlstr.substring(0, urlstr.length() - 1);
			}
			urladds.add(urlstr);
		}
		return urladds;
	}

	public static void getPercentByJob(List<Feature> features, List<String> urladds) {
		Context.features = features;
		Context.urladds = urladds;
		new Thread() {
			public void run() {
				while (true) {
					String res = Context.getPercent();
					if (res.equals("100.00%")) {
						break;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						logger.error("当前jobid：" + Context.jobid + " 信息：" + e.toString());
					}
				}
			};
		}.start();
	}

}
