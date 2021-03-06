package com.addressbookworkshop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.addressbookworkshop.AddressBookService.IOService;

public class AddressBookServiceTest {
	static AddressBookService addressBookService;
	List<AddressBookData> addressBookData;

	@BeforeClass
	public static void createObj() {
		addressBookService = new AddressBookService();
	}

	@AfterClass
	public static void nullObj() {
		addressBookService = null;
	}
	
	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	private Response addContactToJsonServer(AddressBookData addressBookData) {
		String contactJson = new Gson().toJson(addressBookData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactJson);
		return request.post("/addressbook");
	}
	
	private AddressBookData[] getAddressBookList() {
		Response response = RestAssured.get("/addressbook");
		System.out.println("Adddressbook entries in JsonServer :\n" + response.asString());
		AddressBookData[] arrayOfPerson = new Gson().fromJson(response.asString(), AddressBookData[].class);
		return arrayOfPerson;
	}
	
	@Test
	public void given2Contacts_WhenWrittenToFile_ShouldMatchTotalEntries() {
		AddressBookData[] arrayOfPerson = {
				new AddressBookData("Bill", "G", "Dadar", "Mumbai", "Maha", "400000", "9876541230", "bg@gm.com"),
				new AddressBookData("Terrisa", "M", "CST", "Mumbai", "Maha", "400500", "7886541230", "tm@gm.com") };
		addressBookService = new AddressBookService(Arrays.asList(arrayOfPerson));
		addressBookService.writeAddressBookData(IOService.FILE_IO);
		addressBookService.printAddressBookData(IOService.FILE_IO);
		long entries = addressBookService.countEntries(IOService.FILE_IO);
		assertEquals(2, entries);
	}

	@Test
	public void givenFile_WhenReadFromFile_ShouldReturnPersonCount() {
		System.out.println("After reading from file :");
		int entries = addressBookService.readAddressBookData(IOService.FILE_IO);
		assertEquals(2, entries);
	}
	
	@Test
	public void givenAddressbookInDB_WhenRetrieved_ShouldMatchTotalPersonCount() {
		addressBookData = addressBookService.readAddressBookDataFromDB(IOService.DB_IO);
		assertEquals(4, addressBookData.size());
		System.out.println("Total person in addressbook :" + addressBookData.size());
	}
	
	@Test
	public void givenPhoneNumber_WhenUpdated_ShouldSyncWithDB() {
		addressBookService.updateContactNumber("Terrisa", "9876543285");
		boolean result = addressBookService.checkAddressBookSyncWithDB("Terrisa");
		assertTrue(result);
		System.out.println("Contact number got updated for Terrisa.");
	}
	
	@Test
	public void givenDateRange_WhenRetrieved_ShouldMatchPersonCount() {
		LocalDate dateAdded = LocalDate.of(2018, 01, 01);
		LocalDate dateNow = LocalDate.now();
		addressBookData = addressBookService.readAddressBookForDateRange(IOService.DB_IO, dateAdded,dateNow);
		assertEquals(3, addressBookData.size());
		System.out.println("Person count match for given date range.");
	}
	
	@Test
	public void givenCity_WhenContactsRetrieved_ShouldMatchPersonCount() {
		String city = "Mumbai";
		addressBookData = addressBookService.readAddressBookForCity(IOService.DB_IO, city);
		assertEquals(2, addressBookData.size());
		System.out.println("Person count match for given city.");
	}

	@Test
	public void givenState_WhenContactsRetrieved_ShouldMatchPersonCount() {
		String state = "Maharashtra";
		addressBookData = addressBookService.readAddressBookForState(IOService.DB_IO, state);
		assertEquals(3, addressBookData.size());
		System.out.println("Person count match for given state.");
	}
	
	@Test
	public void givenNewPerson_WhenAdded_ShouldSyncWithDB() {
		addressBookService.addPersonToAddressBook("Gunjan", "T", "K", "Kerala", "Kerala", "498792", "9876543213",
				"gt@gm.com", LocalDate.now());
		boolean result = addressBookService.checkAddressBookSyncWithDB("Gunjan");
		assertTrue(result);
		System.out.println("Person added in addressbook .");
	}
	
	@Test
	public void givenFourContacts_WhenAdded_ShouldMatchContactEntries() {
		AddressBookData[] arrayOfPersons = {
				new AddressBookData(0, "Bill", "T", "CST", "Mumbai", "Maharashtra", "428792", "9876543213", "bt@gm.com",LocalDate.now()),
				new AddressBookData(0, "Mark", "K", "Dadar", "Mumbai", "Maharashtra", "498892", "9876544213","mt@gm.com", LocalDate.now()),
				new AddressBookData(0, "Terrisa", "T", "Karve", "Pune", "Maharashtra", "491792", "9877543213","tt@gm.com", LocalDate.now()),
				new AddressBookData(0, "Charlie", "K", "S", "New Delhi", "Delhi", "493792", "9879543213", "ck@gm.com",LocalDate.now()) };
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readAddressBookDataFromDB(IOService.DB_IO);
		Instant threadStart = Instant.now();
		addressBookService.addContactToAddressBookUsingThreads(Arrays.asList(arrayOfPersons));
		Instant threadEnd = Instant.now();
		System.out.println("Duration with thread :" + Duration.between(threadStart, threadEnd));
		assertEquals(5, addressBookService.countEntries(IOService.DB_IO));
	}
	
	@Test
	public void givenAddressBookDataInJsonServer_WhenRetrieved_ShouldMatchPersonCount() {
		AddressBookData[] arrayOfPerson = getAddressBookList();
		addressBookService = new AddressBookService(Arrays.asList(arrayOfPerson));
		long entries = addressBookService.countEntries(IOService.REST_IO);
		assertEquals(2, entries);
	}
	
	
	@Test
	public void givenMultiplePerson_WhenAdded_ShouldMatch201ResponseAndCount() {
		AddressBookData[] arrayOfContacts = getAddressBookList();
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
		AddressBookData[] arrayOfPerson = {
				new AddressBookData(0, "Mark", "K", "Dadar", "Mumbai", "Maharashtra", "498892", "9876544213","mt@gm.com"),
				new AddressBookData(0, "Terrisa", "T", "Karve", "Pune", "Maharashtra", "491792", "9877543213","tt@gm.com"),
				new AddressBookData(0, "Charlie", "K", "S", "New Delhi", "Delhi", "493792", "9879543213","ck@gm.com") };

		for (AddressBookData addressBookData : arrayOfPerson) {

			Response response = addContactToJsonServer(addressBookData);
			int statusCode = response.getStatusCode();
			assertEquals(201, statusCode);

			addressBookData = new Gson().fromJson(response.asString(), AddressBookData.class);
			addressBookService.addContactToAddressBook(addressBookData, IOService.REST_IO);
		}
		long entries = addressBookService.countEntries(IOService.REST_IO);
		assertEquals(5, entries);
	}
	
	@Test
	public void givenPhoneNumber_WhenUpdated_ShouldMatch200Response() {
		AddressBookData[] arrayOfContacts = getAddressBookList();
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
		addressBookService.updatePersonContactNo("Bill", "8876543211", IOService.REST_IO);
		AddressBookData addressBookData = addressBookService.getAddressBookData("Bill");

		String addressBookJson = new Gson().toJson(addressBookData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(addressBookJson);
		Response response = request.put("/addressbook/" + addressBookData.id);
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);
	}
	
	@Test
	public void givenContact_WhenDeleted_ShouldMatch200ResponseAndCount() {
		AddressBookData[] arrayOfContacts = getAddressBookList();
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
		AddressBookData addressBookData = addressBookService.getAddressBookData("Terrisa");
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.delete("/addressbook/" + addressBookData.id);
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);

		addressBookService.deleteAddressBookEntry(addressBookData.firstName, IOService.REST_IO);
		long entries = addressBookService.countEntries(IOService.REST_IO);
		assertEquals(4, entries);
	}
}
