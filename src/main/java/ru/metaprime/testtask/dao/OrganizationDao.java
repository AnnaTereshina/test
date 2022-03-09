package ru.metaprime.testtask.dao;

import java.util.ArrayList;

import ru.metaprime.testtask.DataParser;

public interface OrganizationDao {
	public void importTableToDB(String tableName, DataParser dataParser);

	public ArrayList<String> receiveNamesOrganizationsFromDB(String connectionProperties);
}
