package org.secbug.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.secbug.conf.Context;
import org.secbug.vo.Feature;

import net.sf.json.JSONArray;

public class ReadCheck {

	private static Logger logger = Logger.getLogger(ReadCheck.class);

	/**
	 * 遍历检测 读取库存（json） 获取匹配指纹 (常规模式)
	 */
	public static List<Feature> getCheckData() {
		List<Feature> features = new ArrayList<Feature>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Context.currpath));
			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			JSONArray array = JSONArray.fromObject(buffer.toString());
			features = array.toList(array, Feature.class);
			if (features == null) {
				logger.info("指纹json文件读取指纹为空！请添加指纹。");
			}
			reader.close();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return features;
	}
}
