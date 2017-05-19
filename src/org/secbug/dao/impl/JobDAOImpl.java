package org.secbug.dao.impl;

import java.util.List;

import org.secbug.dao.JobDAO;
import org.secbug.util.DataBase;
import org.secbug.vo.Job;

public class JobDAOImpl implements JobDAO {

	private DataBase dbc = new DataBase();

	@Override
	public int updateJobById(Job job) {

		return this.dbc.update(Job.class, "status =" + job.getStatus() + " where id = " + job.getId());
	}

	@Override
	public int insertJob(Job job) {

		int flag = this.dbc.insert(job);
		if (flag != -1) {
			return flag;
		}
		return -1;
	}

	@Override
	public Job findJobByIdWithStatus(int jobid) {
		List<Job> jobList = this.dbc.findByList(Job.class, "id =" + jobid + " and status = 4");
		if (jobList.isEmpty() || jobList == null) {
			return null;
		}
		return jobList.get(0);
	}

	@Override
	public int updateJobWithOne(Job job) {
   
		return this.dbc.update(Job.class, "oneperent = '" + job.getOneperent() + "' where id = " + job.getId());
	}

	@Override
	public int updateJobWithTwo(Job job) {

		return this.dbc.update(Job.class, "twoperent = '" + job.getTwoperent() + "' where id = " + job.getId());
	}

	@Override
	public Job findJobById(int jobid) {
		
		return this.dbc.findByID(Job.class, jobid);
	}

}
