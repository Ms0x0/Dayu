package org.secbug.util;

public class ArgsUtil {
	public static String getArgsKeyToValue(String[] args, String key) {
		for (int i = 0; i < args.length; i++) {
			if (key.equals(args[i])) {
				return args[(++i)];
			}
		}

		return null;
	}

	public static boolean existsKey(String[] args, String key) {
		if (args == null) {
			return false;
		}
		String[] arrayOfString = args;
		int j = args.length;
		for (int i = 0; i < j; i++) {
			String string = arrayOfString[i].trim();
			if (key.equals(string)) {
				return true;
			}
		}
		return false;
	}
}