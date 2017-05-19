package org.secbug.dao;

import java.util.List;

import org.secbug.vo.Result;

public interface ResultDAO {

	/**
	 * 查询结果
	 * 
	 * @return list
	 */
	public List<Result> findAll();
	
	/**
	 * 添加结果 
	 * 
	 * @return
	 */
	public int insertResult(Result result);
	
	/**
	 * 根据jobid查询
	 * 
	 * @return 
	 */
	public List<Result> findResultById(int jobid);
	
	/**
	 * 根据resultid查询
	 * 
	 * @return
	 */
	public Result getResultById(int resultid);

}
