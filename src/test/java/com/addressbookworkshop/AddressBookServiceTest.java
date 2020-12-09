package com.addressbookworkshop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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
}
