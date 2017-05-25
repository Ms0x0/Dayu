package org.secbug.inter;

import java.util.concurrent.Callable;

import org.secbug.conf.Context;
import org.secbug.crack.PrintCrack;
import org.secbug.model.CheckType;
import org.secbug.model.ContextCheckType;
import org.secbug.model.HeadCheckType;
import org.secbug.model.MD5CheckType;
import org.secbug.model.UrlCheckType;
import org.secbug.vo.Result;

public class ProCrack implements Callable<String> {

	private String url;
	private String urlPath;
	private int recognid;
	private String recogncontext;
	private String program_name;
	private String manufacturerName;
	private String manufacturerUrl;
	private int featureid;

	public ProCrack(String url, String urlPath, int recognid, String recogncontext, String program_name,
			String manufacturerName, int featureid, String manufacturerUrl) {
		this.url = url;
		this.urlPath = urlPath;
		this.recognid = recognid;
		this.recogncontext = recogncontext;
		this.program_name = program_name;
		this.manufacturerName = manufacturerName;
		this.manufacturerUrl = manufacturerUrl;
		this.featureid = featureid;
	}

	public void run() {
		if (Context.model == 1) {
			if (Context.fastProbeList.size() != 0 && Context.fastProbeList != null) {
				for (String currUrl : Context.fastProbeList) {
					if (url.equals(currUrl)) {
						return;
					}
				}
			}
			String responseInfo = PrintCrack.getResponseInfo(recognid, urlPath);
			if (responseInfo.equals("")) {
				// 空值
			} else {
				boolean falg = ProCrack.getRecognType(urlPath, recognid, recogncontext, responseInfo);
				if (falg) { // 修改成保存结果
					if (Context.fastProbeList.size() != 0 && Context.fastProbeList != null) {
						for (String currUrl : Context.fastProbeList) {
							if (url.equals(currUrl)) {
								return;
							}
						}
					}
					Context.fastProbeList.add(url);
					Result result = new Result(program_name, urlPath, manufacturerUrl);
					Context.results.add(result);
					Context.resultHashMap.put(urlPath, recogncontext);
				}
			}
		} else if (Context.model == 2) {
			String responseInfo = PrintCrack.getResponseInfo(recognid, urlPath);
			if (responseInfo.equals("")) {
				// 空值
			} else {
				boolean falg = ProCrack.getRecognType(urlPath, recognid, recogncontext, responseInfo);
				if (falg) { // 修改成保存结果
					Result result = new Result(featureid, program_name, urlPath, manufacturerUrl, url);
					Context.results.add(result);
				}
			}
		} else if (Context.model == 3) {
			String responseInfo = PrintCrack.getResponseInfo(recognid, urlPath);
			if (responseInfo.equals("")) {
				// 空值
			} else {
				boolean falg = ProCrack.getRecognType(urlPath, recognid, recogncontext, responseInfo);
				if (falg) { // 修改成保存结果
					Context.i++;
					Result result = new Result(program_name, urlPath, manufacturerUrl);
					Context.results.add(result);
				}
			}
		}

	}

	@Override
	public String call() throws Exception {
		this.run();
		Context.updatePercent(); // 进度条更新
		return "";
	}

	// 根据不同识别类型判断
	private static boolean getRecognType(String urlPath, int recognid, String recogncontext, String responseInfo) {
		boolean falg = false;
		CheckType checkType = null;
		// 这里以后增加处理方式
		if (recognid == 1) { // md5识别
			checkType = new MD5CheckType();
			falg = checkType.check(urlPath, recogncontext, responseInfo);
		} else if (recognid == 2) { // response context识别
			checkType = new ContextCheckType();
			falg = checkType.check(urlPath, recogncontext, responseInfo);
		} else if (recognid == 3) { // response head识别
			checkType = new HeadCheckType();
			falg = checkType.check(urlPath, recogncontext, responseInfo);
		} else if (recognid == 4) { // url 识别
			checkType = new UrlCheckType();
			falg = checkType.check(urlPath, recogncontext, responseInfo);
		}
		return falg;
	}

}
