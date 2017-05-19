package org.secbug.dao.impl;

import java.util.List;

import org.secbug.dao.ResultDAO;
import org.secbug.util.DataBase;
import org.secbug.vo.Result;

public class ResultDAOImpl implements ResultDAO {

	private DataBase dbc = new DataBase();

	@Override
	public List<Result> findAll() {

		return this.dbc.findByList(Result.class);
	}

	@Override
	public int insertResult(Result result) {

		int flag = this.dbc.insert(result);
		if (flag != -1) {
			return flag;
		}
		return -1;
	}

	@Override
	public List<Result> findResultById(int jobid) {

		return this.dbc.findByList(Result.class, "jobid = " + jobid);
	}

	@Override
	public Result getResultById(int resultid) {
		
		return this.dbc.findByID(Result.class, resultid);
	}
}
