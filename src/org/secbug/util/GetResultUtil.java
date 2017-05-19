package org.secbug.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.secbug.conf.Context;
import org.secbug.dao.FingerPrintDAO;
import org.secbug.dao.ManufacturerDAO;
import org.secbug.dao.ResultDAO;
import org.secbug.dao.impl.FingerPrintDAOImpl;
import org.secbug.dao.impl.ManufacturerDAOImpl;
import org.secbug.dao.impl.ResultDAOImpl;
import org.secbug.vo.Fingerprint;
import org.secbug.vo.Manufacturer;
import org.secbug.vo.Result;

public class GetResultUtil {

	public static void getResult() {

		List<String> strList = new ArrayList<String>();

		FingerPrintDAO fingerPrintDAO = new FingerPrintDAOImpl();
		ManufacturerDAO manufacturerDAO = new ManufacturerDAOImpl();
		ResultDAO resultDao = new ResultDAOImpl();
		System.out.println("============================================================================");
		System.out.println("============================================================================");
		System.out.println("当前任务指纹识别结果：");
		// 自定义模式
		if (Context.requestUrl.size() != 0 && Context.requestUrl != null) {
			if (Context.resultIds == null || Context.resultIds.size() == 0) {
				System.out.println("任务指纹识别为空！");
			} else {
				Set<Integer> keySet = Context.resultHashMap.keySet();
				if (!Context.outputPath.equals("")) {
					for (Integer key : keySet) {
						Result result2 = resultDao.getResultById(key);
						String str = "Recognition Url：" + result2.getRecognUrl() + "  Recognition Context："
								+ Context.resultHashMap.get(key);
						strList.add(str);
					}
					FileOutputUtil.outPutFile(strList);
					System.out.println("任务指纹识别结果已输出到目标文件，请查看。");
				} else {
					for (Integer key : keySet) {
						Result result2 = resultDao.getResultById(key);
						System.out.println("Recognition Url：" + result2.getRecognUrl() + "  Recognition Context："
								+ Context.resultHashMap.get(key));
					}
				}
			}
			ContextUtil.exitPrintln("当前任务指纹识别结束。。");
		}

		// 常规模式
		if (Context.model == 2) {
			HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
			HashMap<String, Integer> hashMap2 = new HashMap<String, Integer>();
			if (Context.resultIds == null || Context.resultIds.size() == 0) {
				System.out.println("任务指纹识别为空！");
			} else {
				for (Integer resid : Context.resultIds) {
					Result result2 = resultDao.getResultById(resid);
					Fingerprint fingerprint = fingerPrintDAO.findPrintById(result2.getFingerPrint_id());
					String str = fingerprint.getProgram_name().toUpperCase();
					hashMap.put(str, hashMap.get(str) == null ? 1 : hashMap.get(str) + 1);
					hashMap2.put(str, resid);
				}
				Set<String> keySet = hashMap.keySet();
				int maxValue = 0;
				String program_Name = "";
				List<String> nameList = new ArrayList<String>();
				for (String string : keySet) {
					if (hashMap.get(string) > maxValue) {
						maxValue = hashMap.get(string);
						program_Name = string;
						nameList.add(program_Name);
					}
				}
				int ResValue = 0;
				Set<String> keySet2 = hashMap2.keySet();
				if (!Context.outputPath.equals("")) {
					for (String string2 : keySet2) {
						for (int i = 0; i < nameList.size(); i++) {
							if (string2.equals(nameList.get(i))) {
								ResValue = hashMap2.get(string2);
							}
						}
						Result result = resultDao.getResultById(ResValue);
						Fingerprint fingerprint = fingerPrintDAO.findPrintById(result.getFingerPrint_id());
						Manufacturer manufacturer = manufacturerDAO
								.findManufacturerById(fingerprint.getManufacturer_id());
						String str = "Program Name：" + fingerprint.getProgram_name() + " recognition Url："
								+ result.getRecognUrl() + " manufacturer Address：" + manufacturer.getUrl(); // 精准识别默认取第一个程序
						strList.add(str);
					}
					FileOutputUtil.outPutFile(strList);
					System.out.println("任务指纹识别结果已输出到目标文件，请查看。");
				} else {
					for (String string2 : keySet2) {
						for (int i = 0; i < nameList.size(); i++) {
							if (string2.equals(nameList.get(i))) {
								ResValue = hashMap2.get(string2);
							}
						}
						Result result = resultDao.getResultById(ResValue);
						Fingerprint fingerprint = fingerPrintDAO.findPrintById(result.getFingerPrint_id());
						Manufacturer manufacturer = manufacturerDAO
								.findManufacturerById(fingerprint.getManufacturer_id());
						System.out.println("Program Name：" + fingerprint.getProgram_name() + " recognition Url："
								+ result.getRecognUrl() + " manufacturer Address：" + manufacturer.getUrl()); // 精准识别默认取第一个程序
					}
				}
			}
			ContextUtil.exitPrintln("当前任务指纹识别结束。。");
		} else if (Context.model == 1 || Context.model == 3) {
			if (Context.resultIds == null || Context.resultIds.size() == 0) {
				System.out.println("任务指纹识别为空！");
			} else {
				if (!Context.outputPath.equals("")) {
					for (Integer resid : Context.resultIds) {
						Result result2 = resultDao.getResultById(resid);
						Fingerprint fingerprint = fingerPrintDAO.findPrintById(result2.getFingerPrint_id());
						Manufacturer manufacturer = manufacturerDAO
								.findManufacturerById(fingerprint.getManufacturer_id());
						String str = "Program Name：" + fingerprint.getProgram_name() + " recognition Url："
								+ result2.getRecognUrl() + " manufacturer Address：" + manufacturer.getUrl();
						strList.add(str);
					}
					FileOutputUtil.outPutFile(strList);
					System.out.println("任务指纹识别结果已输出到目标文件，请查看。");
				} else {
					for (Integer resid : Context.resultIds) {
						Result result2 = resultDao.getResultById(resid);
						Fingerprint fingerprint = fingerPrintDAO.findPrintById(result2.getFingerPrint_id());
						Manufacturer manufacturer = manufacturerDAO
								.findManufacturerById(fingerprint.getManufacturer_id());
						System.out.println("Program Name：" + fingerprint.getProgram_name() + " recognition Url："
								+ result2.getRecognUrl() + " manufacturer Address：" + manufacturer.getUrl());
					}
				}
			}
		}
		ContextUtil.exitPrintln("当前任务指纹识别结束。。");
	}

}
