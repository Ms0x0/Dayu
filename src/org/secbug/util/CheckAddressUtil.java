package org.secbug.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckAddressUtil {

	public static boolean getMatchResult(String str) {
		boolean falg = false;
		Pattern p = Pattern.compile(MatchUtil.MATCHDNS);
		Matcher m = p.matcher(str);
		if (m.find()) {
			falg = true;
		} else {
			Pattern p1 = Pattern.compile(MatchUtil.MATCHIP);
			Matcher m1 = p1.matcher(str);
			if (m1.find()) {
				falg = true;
			}
		}
		return falg;
	}

}
