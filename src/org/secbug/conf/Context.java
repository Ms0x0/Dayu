package org.secbug.conf;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.secbug.util.ContextUtil;
import org.secbug.vo.Feature;
import org.secbug.vo.Result;

public class Context {

	public static String[] args;

	public static String currpath = System.getProperty("user.dir") + "\\feature.json";
	public static List<String> urls = new ArrayList<String>();
	public static int jobid = 0;

	public static int i = 0;
	public static StringBuffer UrlMatch = new StringBuffer();
	public static List<String> SUREURLS = new ArrayList<String>(); // ָ��ʶ��ɹ���url

	public static List<String> urladds = new ArrayList<String>();
	public static List<Feature> features = new ArrayList<Feature>();

	public static int THREADCACHE = 5000; // �̼߳����ÿִ��1��ζ������һ��ϵͳ���棬����System.gc();
	public static int THREADNUM = 50; // �߳���
	public static int TASKCOUNT = 0; // ��������
	public static int CURRENTNUM = 0; // �������������
	public static long STARTTIME = 0; // ϵͳ��ʼ��ʱ��

	public static int port = 80; // �˿�
	public static String protocol = "http"; // Э��
	public static int model = 1; // ʶ��ģʽ(1������ģʽ 2.��׼ģʽ 3.�˹��ж� )
	public static String outputPath = ""; // ���������ļ���
	public static List<String> fastProbeList = new ArrayList<String>(); // ����ģʽ������������ipֻ����һ�Σ�
	public static List<String> requestUrl = new ArrayList<String>(); // �Զ���
																		// --��������url
	public static List<String> responseStr = new ArrayList<String>(); // �Զ���
																		// --������Ӧ���ݹؼ���

	public static HashMap<String, String> resultHashMap = new HashMap<String, String>(); // �Զ���ģʽ
																							// ��ȡ���

	public static List<Result> results = new ArrayList<Result>(); // �ɹ�ʶ��ָ����ʱ��

	/***
	 * ���½���
	 */
	public static synchronized void updatePercent() {
		Context.CURRENTNUM++;
	}

	public static synchronized int getTaskCount() {
		int urlCount = urladds.size();
		if (features == null) {
			int requestUrlCount = requestUrl.size();
			int responseStrCount = responseStr.size();
			return urlCount * requestUrlCount * responseStrCount;
		} else {
			int fingerCount = features.size();
			return urlCount * fingerCount;
		}
	}

	/**
	 * ��������
	 * 
	 * @return 100.00%
	 */
	public static synchronized String getPercent() {
		double count = Context.getTaskCount();
		double curent = Context.CURRENTNUM;
		System.out.print("count=" + count + " current=" + curent);
		NumberFormat format = NumberFormat.getPercentInstance();// ��ȡ��ʽ����ʵ��
		format.setMinimumFractionDigits(2);// ����С��λ
		String ret = format.format(curent / count);// ��ӡ������
		System.out.print(" " + ret + "\r\n");
		return ret;
	}

	/**
	 * ���ó�ʼ������
	 * 
	 */
	public static void INIT() {

		// �����û�����
		ContextUtil.setJobOption();

		// ����ʼ����ʱ��
		Context.STARTTIME = System.currentTimeMillis();

		// ������˳�ʱ��hook
		ContextUtil.doShutDownWork();

	}
}
