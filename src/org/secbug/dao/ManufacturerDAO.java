package org.secbug.dao;

import java.util.List;

import org.secbug.vo.Manufacturer;

public interface ManufacturerDAO {

	/**
	 * 根据id查询
	 * 
	 * @return
	 */
	public Manufacturer findManufacturerById(int manuid);

	/**
	 * 查询所有厂商
	 * 
	 * @return
	 */
	public List<Manufacturer> findAll();
	

	/**
	 * 根据name查询
	 */
	public Manufacturer findManufacturerByName(String name);
	
	/**
	 * 根据url查询 
	 * 
	 */
	public Manufacturer findManufacturerByUrl(String manuUrl);
	
	/**
	 * 添加厂商
	 * 
	 */
	public int insertManufacturer(Manufacturer manufacturer);

}
