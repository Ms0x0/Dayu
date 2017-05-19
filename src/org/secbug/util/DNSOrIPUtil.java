package org.secbug.util;

public class DNSOrIPUtil {

	public static String getDnsOrIp(String urlPath) {
		int urlNum = urlPath.indexOf("://");
		String url = urlPath.substring(urlNum + 3, urlPath.length());
		int numDomain = url.indexOf("/");
		String domain = url.substring(0, numDomain); // ”Ú√˚ªÚip
		return domain;
	}

}
