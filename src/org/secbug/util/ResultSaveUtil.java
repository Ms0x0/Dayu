package org.secbug.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.secbug.dao.ResultDAO;
import org.secbug.dao.impl.ResultDAOImpl;
import org.secbug.vo.Result;

public class ResultSaveUtil {

	public static int SaveResult(int jobid, int fingerPrintId, String urlPath) {
		ResultDAO resultDao = new ResultDAOImpl();

		Result result = new Result();
		result.setJobid(jobid);
		result.setFingerPrint_id(fingerPrintId);
		result.setRecognUrl(urlPath);
		String creatime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		result.setCreatime(creatime);

		int currid = resultDao.insertResult(result);

		return currid;
	}

}
