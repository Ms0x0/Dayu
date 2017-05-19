package org.secbug.util;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.secbug.conf.Context;

public class FileOutputUtil {

	private static Logger logger = Logger.getLogger(FileOutputUtil.class);

	public static void outPutFile(List<String> strList) {
		File file = new File(Context.outputPath);
		try {
			if (!file.isFile()) {
				file.createNewFile();
			}
			FileWriter writer = new FileWriter(file);
			for (String str : strList) {
				writer.write(str + "\n");
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			logger.error("jobid:" + Context.jobid + " " + e.getMessage());
		}
	}

}
