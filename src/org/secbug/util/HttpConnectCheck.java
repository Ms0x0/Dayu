package org.secbug.util;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;
import org.secbug.conf.Context;

public class HttpConnectCheck {
	
	private static Logger logger = Logger.getLogger(HttpConnectCheck.class);
	
	public static boolean getCheckInfo(String urlPath){
		boolean falg = false;
		try {
			if (urlPath.indexOf("https") != -1) {
				falg = HttpConnectCheck.httpsGetInfo(urlPath);
			} else {
				falg = HttpConnectCheck.httpGetInfo(urlPath);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return falg;
	}

	private static boolean httpGetInfo(String urlPath) throws IOException {
		boolean falg = false;
		URL url = new URL(urlPath);
		URLConnection urlConnection = url.openConnection();
		HttpURLConnection connection = (HttpURLConnection) urlConnection;

		try {
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(3000);
			connection.setDoInput(true);
			connection.connect();
			falg = true;
		} catch (ConnectException e) {
			logger.error("当前jobid：" + Context.jobid + "错误信息：" + e.toString());
			falg = false;
		}
		return falg;
	}

	private static boolean httpsGetInfo(String urlPath) throws IOException {
		boolean falg = false;
		SSLSocketFactory socketFactory = null;
		try {
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 获取SSLSocketFactory对象
			socketFactory = sslContext.getSocketFactory();
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			logger.error("当前jobid：" + Context.jobid + "错误信息：" + e.toString());
		} catch (KeyManagementException e) {
			logger.error("当前jobid：" + Context.jobid + "错误信息：" + e.toString());
		}

		URL url = new URL(urlPath);
		URLConnection urlConnection = url.openConnection();
		HttpsURLConnection connection = (HttpsURLConnection) urlConnection;
		connection.setSSLSocketFactory(socketFactory);

		try {
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(3000);
			connection.setDoInput(true);
			connection.connect();
			falg = true;
		} catch (ConnectException e) {
			logger.error("当前jobid：" + Context.jobid + "错误信息：" + e.toString());
			falg = false;
		}
		return falg;
	}

}
