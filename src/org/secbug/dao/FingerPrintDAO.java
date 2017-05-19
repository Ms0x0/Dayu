package org.secbug.dao;

import java.util.List;

import org.secbug.vo.Fingerprint;

public interface FingerPrintDAO {

	/**
	 * 查询所有指纹
	 * 
	 * @return
	 */
	public List<Fingerprint> findAll(String urlPath);

	/**
	 * 查询默认指纹
	 * 
	 * @return
	 */
	public List<Fingerprint> findPrintByDefault(String def);

	/**
	 * 根据id查询
	 * 
	 * @return
	 */
	public Fingerprint findPrintById(int id);

	/**
	 * 添加指纹
	 * 
	 * @return
	 */
	public int insertPrint(Fingerprint fingerprint);

	/**
	 * 按照url和识别内容查询
	 * 
	 * @return
	 */
	public Fingerprint findPrintByUrlAndContext(String url, String context);

	/**
	 * 根据userid查询指纹的状态
	 * 
	 * @return
	 */
	public List<Fingerprint> findPrintByUserid(int userid);

}
