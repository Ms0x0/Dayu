package org.secbug.util;

import org.secbug.conf.Context;
import org.secbug.dao.JobDAO;
import org.secbug.dao.impl.JobDAOImpl;
import org.secbug.vo.Job;

public class JobStateUtil {
	
	private static JobDAO dao = new JobDAOImpl();

	// 任务结束后  更新Job
	public static void EndJobState() {
		int status = 0;

		Job job1 = new Job();
		job1.setId(Context.jobid);
		status = 4;
		job1.setStatus(status);
		dao.updateJobById(job1);

		Job job2 = new Job();
		job2.setId(Context.jobid);
		job2.setTwoperent("100.00%");
		dao.updateJobWithTwo(job2);
	}
	
	// 选择模式状态 更新Job
	public static void ChangeJobState(int state){
		Job job = new Job();
		job.setId(Context.jobid);
		job.setStatus(state);
		dao.updateJobById(job);
	}
	
	// 建立链接无效 更新状态
	public synchronized static void InvalidJobState(){
		Job jobCheck = new Job();
		jobCheck.setId(Context.jobid);
		jobCheck.setOneperent("100.00%");
		dao.updateJobWithOne(jobCheck);
		jobCheck.setTwoperent("100.00%");
		dao.updateJobWithTwo(jobCheck);
		jobCheck.setStatus(4);
		dao.updateJobById(jobCheck);
	}

}
