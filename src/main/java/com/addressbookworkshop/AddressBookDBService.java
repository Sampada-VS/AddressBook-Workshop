package com.addressbookworkshop;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class AddressBookDBService {
	private static AddressBookDBService addressBookDBService;
	private PreparedStatement addressBookDataStatement;

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
		return this.getAddressBookDataUsingDB(sql);
	}

	private List<AddressBookData> getAddressBookDataUsingDB(String sql) {
		List<AddressBookData> addressBookList = new ArrayList<>();
		try (Connection connection = getConnect()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			addressBookList = this.getAddressBookData(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

	private List<AddressBookData> getAddressBookData(ResultSet resultSet) {
		List<AddressBookData> addressBookList = new ArrayList<>();
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
				addressBookList.add(new AddressBookData(id, firstName, lastName, address, city, state, zip, phone, email));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

	public int updateAddressBookData(String firstName, String phone) {
		return this.updateAddressBookDataUsingPreparedStatement(firstName, phone);
	}

	private int updateAddressBookDataUsingPreparedStatement(String firstName, String phone) {
		try (Connection connection = getConnect()) {
			String sql = "UPDATE addressbook SET PhoneNumber=? WHERE FirstName=? ";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, phone);
			preparedStatement.setString(2, firstName);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<AddressBookData> getAddressBookDetails(String firstName) {
		List<AddressBookData> addressBookList = null;
		if (this.addressBookDataStatement == null)
			this.preparedStatementForAddressBookData();
		try {
			addressBookDataStatement.setString(1, firstName);
			ResultSet resultSet = addressBookDataStatement.executeQuery();
			addressBookList = this.getAddressBookData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

	private void preparedStatementForAddressBookData() {
		try {
			Connection connection = getConnect();
			String sql = "SELECT * FROM addressbook WHERE FirstName=?";
			addressBookDataStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public List<AddressBookData> getAddressBookForDateRange(LocalDate dateAdded, LocalDate dateNow) {
		String sql = String.format("SELECT * FROM addressbook WHERE date_added BETWEEN '%s' AND '%s';",
				Date.valueOf(dateAdded), Date.valueOf(dateNow));
		return this.getAddressBookDataUsingDB(sql);
	}

}
