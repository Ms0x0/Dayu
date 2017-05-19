package org.secbug.dao.impl;

import java.util.List;

import org.secbug.dao.ManufacturerDAO;
import org.secbug.util.DataBase;
import org.secbug.vo.Manufacturer;

public class ManufacturerDAOImpl implements ManufacturerDAO {

	private DataBase dbc = new DataBase();

	@Override
	public Manufacturer findManufacturerById(int manuid) {

		return this.dbc.findByID(Manufacturer.class, manuid);
	}

	@Override
	public List<Manufacturer> findAll() {

		return this.dbc.findByList(Manufacturer.class);
	}

	@Override
	public Manufacturer findManufacturerByName(String name) {

		return this.dbc.findByList(Manufacturer.class, "name = '" + name + "'").get(0);
	}

	@Override
	public Manufacturer findManufacturerByUrl(String manuUrl) {

		List<Manufacturer> manuList = this.dbc.findByList(Manufacturer.class, "url = '" + manuUrl + "'");
		if (manuList.isEmpty()) {
			return null;
		}
		return manuList.get(0);
	}

	@Override
	public int insertManufacturer(Manufacturer manufacturer) {

		int flag = this.dbc.insert(manufacturer);
		if (flag != -1) {
			return flag;
		}
		return -1;
	}

}
