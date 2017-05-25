package org.secbug.crack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;
import org.secbug.conf.Context;
import org.secbug.util.MyX509TrustManager;

public class PrintCrack {

	private static Logger logger = Logger.getLogger(PrintCrack.class);

	public synchronized static String getResponseInfo(int recognid, String url) {
		String responseInfo = "";
		try {
			if (url.indexOf("https") != -1) {
				responseInfo = PrintCrack.httpsGetInfo(recognid, url);
			} else {
				responseInfo = PrintCrack.httpGetInfo(recognid, url);
			}
			logger.info("当前jobid：" + Context.jobid + "  " + responseInfo);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return responseInfo;
	}

	// HTTP GET方式
	private static String httpGetInfo(int recognid, String path) throws IOException {
		String responseInfo = "";
		String filetype = "";
		int num = path.lastIndexOf(".");
		if (num != -1) {
			filetype = path.substring(num, path.length());
		}
		URL url = new URL(path);
		URLConnection urlConnection = url.openConnection();
		HttpURLConnection connection = (HttpURLConnection) urlConnection;

		try {
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "google_user_agent");
			connection.setConnectTimeout(1000);
			connection.setReadTimeout(2000);
			connection.setDoInput(true);
			connection.connect();
		} catch (ConnectException e) {
			logger.error(e.toString());
		}
		try {
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				// 从服务器返回一个输入流
				InputStream inputStream = connection.getInputStream();
				responseInfo = PrintCrack.getRecognWay(responseCode, recognid, filetype, inputStream, connection);
			} else if (responseCode == 401) {
				InputStream inputStream = null;
				responseInfo = PrintCrack.getRecognWay(responseCode, recognid, filetype, inputStream, connection);
			} else if (responseCode == 500 || responseCode == 403 || responseCode == 502) {
				InputStream inputStream = connection.getErrorStream();
				responseInfo = PrintCrack.getRecognWay(responseCode, recognid, filetype, inputStream, connection);
			}
		} catch (Exception e) {
			logger.error("当前jobid：" + Context.jobid + "错误信息：" + e.toString());
			responseInfo = "";
		} finally {
			connection.disconnect();
		}

		return responseInfo;
	}

	// HTTPS GET方式
	private static String httpsGetInfo(int recognid, String path) throws IOException {
		SSLSocketFactory socketFactory = null;
		String responseInfo = "";
		String filetype = "";
		int num = path.lastIndexOf(".");
		if (num != -1) {
			filetype = path.substring(num, path.length());
		}

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

		URL url = new URL(path);
		URLConnection urlConnection = url.openConnection();
		HttpsURLConnection connection = (HttpsURLConnection) urlConnection;
		connection.setSSLSocketFactory(socketFactory);

		try {
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "google_user_agent");
			connection.setConnectTimeout(1000);
			connection.setReadTimeout(2000);
			connection.setDoInput(true);
			connection.connect();
		} catch (ConnectException e) {
			logger.error("当前jobid：" + Context.jobid + "错误信息：" + e.toString());
		}

		try {
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) { // 以后这里还要修改
				// 从服务器返回一个输入流
				InputStream inputStream = connection.getInputStream();
				responseInfo = PrintCrack.getRecognWay(responseCode, recognid, filetype, inputStream, connection);
			} else if (responseCode == 401) {
				InputStream inputStream = null;
				responseInfo = PrintCrack.getRecognWay(responseCode, recognid, filetype, inputStream, connection);
			} else if (responseCode == 500 || responseCode == 403 || responseCode == 502) {
				InputStream inputStream = connection.getErrorStream();
				responseInfo = PrintCrack.getRecognWay(responseCode, recognid, filetype, inputStream, connection);
			}
		} catch (Exception e) {
			logger.error("当前jobid：" + Context.jobid + "错误信息：" + e.toString());
			responseInfo = "";
		} finally {
			connection.disconnect();
		}
		return responseInfo;
	}

	/**
	 * 识别方式选择 可以自定添加识别方式
	 * 
	 * @return
	 */
	private static String getRecognWay(int responseCode, int recognid, String filetype, InputStream inputStream,
			HttpURLConnection connection) {
		String responseInfo = "";
		if (recognid == 1) { // md5 Hash
			responseInfo = PrintCrack.getDownFile(filetype, inputStream);
		} else if (recognid == 2) { // Response context
			responseInfo = PrintCrack.getResponseInfo(inputStream);
		} else if (recognid == 3) { // Response Head
			responseInfo = PrintCrack.getHeadInfo(connection);
		} else if (recognid == 4) { // Url Address
			responseInfo = PrintCrack.getIsUrl(responseCode);
		}
		return responseInfo;
	}

	/**
	 * 根据响应码去判断是否连接访问可通
	 */
	private static String getIsUrl(int responseCode) {
		String responseInfo = "";
		if (String.valueOf(responseCode).startsWith("2")) {
			responseInfo = "true";
		} else {
			responseInfo = "false";
		}
		return responseInfo;
	}

	/**
	 * 获取响应内容 (Response Context match)
	 * 
	 * @return 响应内容
	 */
	private static String getResponseInfo(InputStream input) {
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = input;

		try {
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream); // 设置utf-8
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line = "";
			while ((line = reader.readLine()) != null) { // 返回context信息
				buffer.append(line);
			}
			reader.close();
			inputStreamReader.close();
		} catch (Exception e) {
			logger.error("当前jobid：" + Context.jobid + "错误信息：" + e.toString());
		} finally {
			// 释放资源
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.error("当前jobid：" + Context.jobid + "错误信息：" + e.toString());
			}
			inputStream = null;
		}
		return buffer.toString();
	}

	/**
	 * 下载文件 (md5 match)
	 * 
	 * @return 本地文件地址
	 */
	private static String getDownFile(String fileType, InputStream input) {
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = input;
		try {
			if (fileType.indexOf("/") == -1) { // 图片类型
				File file = new File("D:/testpath/image/");
				if (!file.exists()) {
					file.mkdirs();
				}
				File targetFile = new File(file.getPath(), new Date().getTime() + fileType);
				FileOutputStream outputStream = new FileOutputStream(targetFile);
				byte[] bytes = new byte[1024 * 1024];
				int length = 0;

				while ((length = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, length);
				}
				outputStream.close();
				buffer.append(targetFile.getPath());
			} else { // 文本类型
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream); // 设置utf-8
				BufferedReader reader = new BufferedReader(inputStreamReader);
				String line = "";
				while ((line = reader.readLine()) != null) { // 返回context信息
					buffer.append(line);
				}
				int num = fileType.lastIndexOf("/"); // 127.0.0.1:80/README
				String subStr = fileType.substring(num + 1, fileType.length());
				File file = new File("D:/testpath/text/");
				if (!file.exists()) {
					file.mkdirs();
				}
				File targetFile = new File(file.getPath(), new Date().getTime() + subStr);
				FileOutputStream outputStream = new FileOutputStream(targetFile);
				byte[] bytes = buffer.toString().getBytes("UTF-8");
				outputStream.write(bytes);

				outputStream.close();
				buffer.delete(0, buffer.toString().length()); // 返回图片的本地地址
				buffer.append(targetFile);

				reader.close();
				inputStreamReader.close();
			}
		} catch (Exception e) {
			logger.error("当前jobid：" + Context.jobid + "错误信息：" + e.toString());
		} finally {
			// 释放资源
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.error("当前jobid：" + Context.jobid + "错误信息：" + e.toString());
			}
			inputStream = null;
		}
		return buffer.toString();
	}

	/**
	 * 获取响应头信息 (Response HeadInfo match)
	 * 
	 * @return 响应头
	 */
	private static String getHeadInfo(HttpURLConnection connection) {
		StringBuffer buffer = new StringBuffer();
		Map<String, List<String>> map = connection.getHeaderFields();
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			String head = entry.getKey() + ":" + entry.getValue() + "\n";
			head = head.replace("null:", "");
			buffer.append(head);
		}
		String headInfo = buffer.toString();
		headInfo = headInfo.replace("[", "").replace("]", "").replace("\"", "\\\"");
		return headInfo;
	}

}
