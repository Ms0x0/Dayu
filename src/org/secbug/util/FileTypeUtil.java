package org.secbug.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class FileTypeUtil {
	
	/**
	 * ªÒ»°Context-Type
	 */
	public static String getFileType(String contextType) {

		Properties properties = new Properties();
		String currpath = FileTypeUtil.class.getResource("/").getPath();
		String filepath = (currpath.replace("/classes/", "/" + "filetype.properties")).replace("%20", " ")
				.replaceFirst("/", "");
		String type = "";
		try {
			InputStream input = new FileInputStream(filepath);
			properties.load(input);

			Set<Object> sets = properties.keySet();
			for (Object obj : sets) {
				if (obj.equals(contextType)) {
					type = properties.getProperty(obj.toString());
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return type;
	}
	
	

}
