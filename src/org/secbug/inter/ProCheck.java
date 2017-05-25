package org.secbug.inter;

import java.util.List;
import java.util.concurrent.Callable;

import org.secbug.conf.Context;
import org.secbug.util.HttpConnectCheck;

public class ProCheck extends Thread implements Callable<String>  {
	
	private String urlPath;
	private List<String> urladds;
	
	public ProCheck(String urlPath,List<String> urladds){
		this.urlPath = urlPath;
		this.urladds = urladds;
	}
	
	public void run(){
		boolean falg1 = HttpConnectCheck.getCheckInfo(urlPath);
		if (!falg1) {
			if (urladds.size() - 1 > 0) {
				Context.urls.remove(urlPath);
				System.out.println("URL:" + urlPath + " 访问异常，已删除.");
			} else {
				System.out.println("URL:" + urlPath + " 访问异常，已删除.");
			}
		}
	}

	@Override
	public String call() throws Exception {
		this.run();
		return "";
	}
	
	

}
