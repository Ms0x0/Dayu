package org.secbug.dao;

import org.secbug.vo.Job;

public interface JobDAO {

	/**
	 * 修改Job
	 * 
	 * @return
	 */
	public int updateJobById(Job job);

	/**
	 * 添加 Job
	 * 
	 * @return
	 */
	public int insertJob(Job job);
	
	/**
	 * 根据jobid查询已经结束的
	 * 
	 * @return 
	 */
	public Job findJobByIdWithStatus(int jobid);
	
	/**
	 * 修改粗略模式进度 
	 * 
	 * @return
	 */
	public int updateJobWithOne(Job job);
	
	/**
	 * 修改精准模式进度
	 * 
	 * @return
	 */
	public int updateJobWithTwo(Job job);
	
	/**
	 * 根据id查询 
	 */
	public Job findJobById(int jobid);

}
