package org.secbug.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.secbug.conf.Context;

public class JobCommand {

	private Logger logger = Logger.getLogger(JobCommand.class);

	public void init() {
		this.setState();
		this.setHelp();
		this.setCheckParam();
		this.setProtocol();
		this.setPort();
		this.setThread();
		this.setOutput();
		this.setReadFile();
		this.setUrl();
		this.setCustom();
		this.setJob();
		this.setModel();
	}

	/**
	 * jar说明
	 */
	private void setState() {
		if (Context.args.length <= 0) {
			System.out.println("Options:");
			System.out.println("    -u A Domain or IP,or more Domain or IP use','separate.");
			System.out.println("    -r The normal Domain or IP file path.");
			System.out.println("    -t Enter a number of threads, The default threads is 50.");
			System.out.println("    -p Enter a http-proxy port, The default port is 80.");
			System.out.println("    -s Set the access protocol, The default protocol is http.(http、https)");
			System.out.println("    -h See To help illustrate.");
			System.out.println("    -o Output String to The normal local file path.");
			System.out.println(
					"    -m Change the probe model, The default model is 1.(1: get fast probe to result 2: get high hit rate fingerprint to result 3: get all fingerprints to results)");
			System.out.println("    --http-request Set the custom request url path.");
			System.out.println("    --http-response Set the custom response Context word.");
			System.out.println("Example: java -jar Dayu.jar -u www.discuz.net -t 80");
			System.out.println("Example: java -jar Dayu.jar -u www.discuz.net -s https -p 8080");
			System.out.println("Example: java -jar Dayu.jar -r d:\\ip.txt -m 3 ");
			System.out.println(
					"Example: java -jar Dayu.jar -u www.discuz.net --http-request /robots.txt --http-response Discuz");
			ContextUtil.exitPrintln("");
		}
	}

	/**
	 * 设置url
	 */
	private void setUrl() {
		if (ArgsUtil.existsKey(Context.args, "-u")) {
			String urlStr = ArgsUtil.getArgsKeyToValue(Context.args, "-u");
			if (urlStr.indexOf(",") != -1) {
				String[] urls = urlStr.split(",");
				for (int i = 0; i < urls.length; i++) {
					boolean falg = CheckAddressUtil.getMatchResult(urls[i]);
					if (falg) {
						String currurl = Context.protocol + "://" + urls[i] + ":" + String.valueOf(Context.port);
						Context.urls.add(currurl);
					} else {
						ContextUtil.exitPrintln("Exception:Please enter the normal domain names or IPs.");
					}
				}
			} else {
				boolean falg = CheckAddressUtil.getMatchResult(urlStr);
				if (falg) {
					String currurl = Context.protocol + "://" + urlStr + ":" + String.valueOf(Context.port);
					Context.urls.add(currurl);
				} else {
					ContextUtil.exitPrintln("Exception:Please enter a the normal domain name or IP.");
				}
			}
		} else if (!ArgsUtil.existsKey(Context.args, "-r")) {
			ContextUtil.exitPrintln("Exception:Please enter a domain name or IP.");
		}
	}

	/**
	 * 设置文件读取
	 * 
	 * @throws IOException
	 */
	private void setReadFile() {
		if (ArgsUtil.existsKey(Context.args, "-r")) {
			String filePath = ArgsUtil.getArgsKeyToValue(Context.args, "-r");
			File file = new File(filePath);
			if (file.isFile()) {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(file));
					String line;
					while ((line = reader.readLine()) != null) {
						/*
						 * boolean falg = CheckAddressUtil.getMatchResult(line);
						 * if (falg) {
						 */
						String currurl = Context.protocol + "://" + line + ":" + String.valueOf(Context.port);
						Context.urls.add(currurl);
						/*
						 * } else { ContextUtil.exitPrintln(
						 * "Exception:Please check domain or IP in the file.");
						 * }
						 */
					}
				} catch (IOException e) {
					logger.error("异常信息：文件读取异常！" + e.getMessage());
				} finally {
					try {
						reader.close();
					} catch (IOException e) {
						logger.error("异常信息：文件读取异常！" + e.getMessage());
					}
				}
			} else {
				ContextUtil.exitPrintln("Exception:Please enter the normal file path.");
			}
		} else if (!ArgsUtil.existsKey(Context.args, "-u")) {
			ContextUtil.exitPrintln("Exception:Please enter a domain name or IP.");
		}
	}

	/**
	 * 设置线程
	 */
	private void setThread() {
		if (ArgsUtil.existsKey(Context.args, "-t")) {
			String thread = ArgsUtil.getArgsKeyToValue(Context.args, "-t");
			Context.THREADNUM = Integer.parseInt(thread);
		}
	}

	/**
	 * 设置端口
	 */
	private void setPort() {
		if (ArgsUtil.existsKey(Context.args, "-p")) {
			String port = ArgsUtil.getArgsKeyToValue(Context.args, "-p");
			if (Integer.parseInt(port) > 65535 && Integer.parseInt(port) < 1) {
				ContextUtil.exitPrintln("Exception:Please enter a http-proxy port.(1-65535)");
			}
			Context.port = Integer.parseInt(port);
		}
	}

	/**
	 * 设置协议
	 */
	private void setProtocol() {
		if (ArgsUtil.existsKey(Context.args, "-s")) {
			String protocol = ArgsUtil.getArgsKeyToValue(Context.args, "-s");
			if (!protocol.equals("http") && !protocol.equals("https")) {
				ContextUtil.exitPrintln("Exception:Please enter a access protocol.(http、https)");
			}
			Context.protocol = protocol;
		}
	}

	/**
	 * 设置帮助
	 */
	private void setHelp() {
		if (ArgsUtil.existsKey(Context.args, "-h")) {
			System.out.println("Options:");
			System.out.println("    -u A Domain or IP,or more Domain or IP use','separate.");
			System.out.println("    -r The normal Domain or IP file path.");
			System.out.println("    -t Enter a number of threads, The default threads is 50.");
			System.out.println("    -p Enter a http-proxy port, The default port is 80.");
			System.out.println("    -s Set the access protocol, The default protocol is http.(http、https)");
			System.out.println("    -h See To help illustrate.");
			System.out.println("    -o Output String to The normal local file path.");
			System.out.println(
					"    -m Change the probe model, The default model is 1.(1: get fast probe to result 2: get high hit rate fingerprint to result 3: get all fingerprints to results)");
			System.out.println("    --http-request Set the custom request url path.");
			System.out.println("    --http-response Set the custom response Context word.");
			System.out.println("Example: java -jar Dayu.jar -u www.discuz.net -t 80");
			System.out.println("Example: java -jar Dayu.jar -u www.discuz.net -s https -p 8080");
			System.out.println("Example: java -jar Dayu.jar -r d:\\ip.txt -m 3 ");
			System.out.println(
					"Example: java -jar Dayu.jar -u www.discuz.net --http-request /robots.txt --http-response Discuz");
			ContextUtil.exitPrintln("");
		}
	}

	/**
	 * 设置模式
	 */
	private void setModel() {
		if (ArgsUtil.existsKey(Context.args, "-m")) {
			String model = ArgsUtil.getArgsKeyToValue(Context.args, "-m");
			if (Integer.parseInt(model) != 1 && Integer.parseInt(model) != 2 && Integer.parseInt(model) != 3) {
				ContextUtil.exitPrintln(
						"Exception:Please enter a probe model.(1: get single fingerprint to result 2: get all fingerprints to results)");
			}
			Context.model = Integer.parseInt(model);
		}
	}

	/**
	 * 设置自定义识别
	 */
	private void setCustom() {
		if (ArgsUtil.existsKey(Context.args, "--http-request") && ArgsUtil.existsKey(Context.args, "--http-response")) {
			if (ArgsUtil.existsKey(Context.args, "--http-request")) {
				String requestUrl = ArgsUtil.getArgsKeyToValue(Context.args, "--http-request");
				if (requestUrl.indexOf(",") != -1) {
					String str[] = requestUrl.split(",");
					for (int i = 0; i < str.length; i++) {
						if (str[i].startsWith("/")) {
							Context.requestUrl.add(str[i]);
						} else {
							ContextUtil.exitPrintln("Exception:Please enter the normal url path.");
						}
					}
				} else {
					if (requestUrl.startsWith("/")) {
						Context.requestUrl.add(requestUrl);
					} else {
						ContextUtil.exitPrintln("Exception:Please enter the normal url path.");
					}
				}
			}

			if (ArgsUtil.existsKey(Context.args, "--http-response")) {
				String responseStr = ArgsUtil.getArgsKeyToValue(Context.args, "--http-response");
				if (responseStr.indexOf(",") != -1) {
					String str[] = responseStr.split(",");
					for (int i = 0; i < str.length; i++) {
						Context.responseStr.add(str[i]);
					}
				} else {
					Context.responseStr.add(responseStr);
				}
			}
		} else if (ArgsUtil.existsKey(Context.args, "--http-request")
				&& !ArgsUtil.existsKey(Context.args, "--http-response")) {
			ContextUtil.exitPrintln("Exception:Please enter two param to make.(--http-requset、--http-response)");
		} else if (!ArgsUtil.existsKey(Context.args, "--http-request")
				&& ArgsUtil.existsKey(Context.args, "--http-response")) {
			ContextUtil.exitPrintln("Exception:Please enter two param to make.(--http-requset、--http-response)");
		}
	}

	/**
	 * 检查模式参数
	 */
	private void setCheckParam() {
		if (ArgsUtil.existsKey(Context.args, "-m") && (ArgsUtil.existsKey(Context.args, "--http-request")
				|| ArgsUtil.existsKey(Context.args, "--http-response"))) {
			ContextUtil.exitPrintln(
					"Exception:Don't support -m and --http-request or --http-response params to make.(Convention model and Custom model)");
		}
	}

	/**
	 * 输出结果到路径下
	 */
	private void setOutput() {
		if (ArgsUtil.existsKey(Context.args, "-o")) {
			String output = ArgsUtil.getArgsKeyToValue(Context.args, "-o");
			output = output.replace("\\", "\\\\");
			if (output.indexOf("\\\\") == -1 && output.indexOf("/") == -1) {
				ContextUtil.exitPrintln("Exception:Please set the normal local file path.");
			}
			Context.outputPath = output;
		}
	}

	/**
	 * 设置job
	 */
	private void setJob() {
		Context.jobid = 1;
	}
}
