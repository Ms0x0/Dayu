package org.secbug.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.secbug.conf.Context;
import org.secbug.vo.Result;

public class GetResultUtil {

	public static void getResult() {

		List<String> strList = new ArrayList<String>();

		System.out.println("============================================================================");
		System.out.println("============================================================================");
		System.out.println("当前任务指纹识别结果：");
		// 自定义模式
		if (Context.requestUrl.size() != 0 && Context.requestUrl != null) {
			if (Context.results == null || Context.results.size() == 0) {
				System.out.println("任务指纹识别为空！");
			} else {
				Set<String> keySet = Context.resultHashMap.keySet();
				if (!Context.outputPath.equals("")) {
					for (String key : keySet) {
						String str = "Recognition Url：" + key + "  Recognition Context："
								+ Context.resultHashMap.get(key);
						strList.add(str);
					}
					FileOutputUtil.outPutFile(strList);
					System.out.println("任务指纹识别结果已输出到目标文件，请查看。");
				} else {
					for (String key : keySet) {
						System.out.println(
								"Recognition Url：" + key + "  Recognition Context：" + Context.resultHashMap.get(key));
					}
				}
			}
			ContextUtil.exitPrintln("当前任务指纹识别结束。。");
		}

		// 常规模式
		if (Context.model == 2) {
			List<Integer> featureidList = new ArrayList<Integer>();

			HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
			HashMap<String, Integer> hashMap2 = new HashMap<String, Integer>();
			Set<String> urlKey = new HashSet<String>();
			if (Context.results == null || Context.results.size() == 0) {
				System.out.println("任务指纹识别为空！");
			} else {
				for (Result result : Context.results) {
					String str = result.getProgram_name().toUpperCase();
					hashMap.put(result.getDomainUrl() + " " + str,
							hashMap.get(result.getDomainUrl() + " " + str) == null ? 1
									: hashMap.get(result.getDomainUrl() + " " + str) + 1);
					hashMap2.put(str, result.getFeatureid());
					urlKey.add(result.getDomainUrl());
				}
				int maxValue = 0;
				String name = "";
				Set<String> keySet = hashMap.keySet();
				for (String ukey : urlKey) {
					for (String string : keySet) {
						String array[] = string.split(" ");
						String domainUrl = array[0];
						String program_name = array[1];
						if (ukey.equals(domainUrl)) {
							if (hashMap.get(string) > maxValue) {
								maxValue = hashMap.get(string);
								name = program_name;
							}
						}
					}
					Set<String> keysSet2 = hashMap2.keySet();
					for (String string : keysSet2) {
						if (name.equals(string)) {
							featureidList.add(hashMap2.get(name));
						}
					}
					maxValue = 0;
					name = "";
				}
				if (!Context.outputPath.equals("")) {
					for (int i = 0; i < featureidList.size(); i++) {
						for (Result result : Context.results) {
							if (featureidList.get(i).equals(result.getFeatureid())) {
								String str = "Program Name：" + result.getProgram_name() + " recognition Url："
										+ result.getRecognUrl() + " manufacturer Address："
										+ result.getManufacturerUrl();
								strList.add(str);
							}
						}
					}
					FileOutputUtil.outPutFile(strList);
					System.out.println("任务指纹识别结果已输出到目标文件，请查看。");
				} else {
					for (int i = 0; i < featureidList.size(); i++) {
						for (Result result : Context.results) {
							if (featureidList.get(i).equals(result.getFeatureid())) {
								System.out.println("Program Name：" + result.getProgram_name() + " recognition Url："
										+ result.getRecognUrl() + " manufacturer Address："
										+ result.getManufacturerUrl());
							}
						}
					}
				}
			}
			ContextUtil.exitPrintln("当前任务指纹识别结束。。");
		} else if (Context.model == 1 || Context.model == 3) {
			if (Context.results == null || Context.results.size() == 0) {
				System.out.println("任务指纹识别为空！");
			} else {
				if (!Context.outputPath.equals("")) {
					for (Result result : Context.results) {
						String str = "Program Name：" + result.getProgram_name() + " recognition Url："
								+ result.getRecognUrl() + " manufacturer Address：" + result.getManufacturerUrl();
						strList.add(str);
					}
					FileOutputUtil.outPutFile(strList);
					System.out.println("任务指纹识别结果已输出到目标文件，请查看。");
				} else {
					for (Result result : Context.results) {
						System.out.println("Program Name：" + result.getProgram_name() + " recognition Url："
								+ result.getRecognUrl() + " manufacturer Address：" + result.getManufacturerUrl());
					}
				}
			}
		}
		ContextUtil.exitPrintln("当前任务指纹识别结束。。");
	}

}
