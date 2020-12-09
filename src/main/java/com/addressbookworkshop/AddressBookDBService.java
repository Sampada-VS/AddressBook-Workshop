package com.addressbookworkshop;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AddressBookDBService {
	private static AddressBookDBService addressBookDBService;

	private AddressBookDBService() {
	}

	public static AddressBookDBService getInstance() {
		if (addressBookDBService == null)
			addressBookDBService = new AddressBookDBService();
		return addressBookDBService;
	}

	private static Connection getConnect() throws SQLException {
		Connection connection;
		String[] dbInfo = dbProperties();
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook_service?useSSL=false",
				dbInfo[0], dbInfo[1]);
		return connection;
	}

	private static String[] dbProperties() {
		String[] dbInfo = { "", "" };
		Properties properties = new Properties();
		try (FileReader reader = new FileReader("DB.properties")) {
			properties.load(reader);
			dbInfo[0] = properties.getProperty("username");
			dbInfo[1] = properties.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dbInfo;
	}

	public List<AddressBookData> readData() {
		String sql = "SELECT * FROM addressbook;";
		return this.getAddressbookDataUsingDB(sql);
	}

	private List<AddressBookData> getAddressbookDataUsingDB(String sql) {
		List<AddressBookData> addressbookList = new ArrayList<>();
		try (Connection connection = getConnect()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			addressbookList = this.getAddressbookData(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressbookList;
	}

	private List<AddressBookData> getAddressbookData(ResultSet resultSet) {
		List<AddressBookData> addressbookList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("PersonId");
				String firstName = resultSet.getString("FirstName");
				String lastName = resultSet.getString("LastName");
				String address = resultSet.getString("Address");
				String city = resultSet.getString("City");
				String state = resultSet.getString("State");
				String zip = resultSet.getString("Zip");
				String phone = resultSet.getString("PhoneNumber");
				String email = resultSet.getString("Email");
				addressbookList
						.add(new AddressBookData(id, firstName, lastName, address, city, state, zip, phone, email));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressbookList;
	}

}
