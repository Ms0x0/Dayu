package org.secbug.dao.impl;

import java.util.List;

import org.secbug.dao.FingerPrintDAO;
import org.secbug.util.DataBase;
import org.secbug.vo.Fingerprint;

public class FingerPrintDAOImpl implements FingerPrintDAO {

	private DataBase dbc = new DataBase();

	@Override
	public List<Fingerprint> findAll(String def) {

		String where = "status = 1 and isCheck = 1";
		return this.dbc.findByList(Fingerprint.class, where);
	}

	@Override
	public List<Fingerprint> findPrintByDefault(String def) {

		String where = "url in (" + def + ") and status = 1 and isCheck = 1";
		return this.dbc.findByList(Fingerprint.class, where);
	}

	@Override
	public Fingerprint findPrintById(int id) {

		return this.dbc.findByID(Fingerprint.class, id);
	}

	@Override
	public int insertPrint(Fingerprint fingerprint) {

		int flag = this.dbc.insert(fingerprint);
		if (flag != -1) {
			return flag;
		}
		return -1;
	}

	@Override
	public Fingerprint findPrintByUrlAndContext(String url, String context) {

		List<Fingerprint> printList = this.dbc.findByList(Fingerprint.class,
				"url = '" + url + "' and recognition_content = '" + context + "'");
		if (printList.isEmpty()) {
			return null;
		}
		return printList.get(0);
	}

	@Override
	public List<Fingerprint> findPrintByUserid(int userid) {

		return this.dbc.findByList(Fingerprint.class, "userid = " + userid + " order by creatime desc");
	}
}
