package com.addressbookworkshop;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO 
	}

	private List<AddressBookData> addressBookList;
	private int lines;
	private AddressBookDBService addressBookDBService;

	public AddressBookService() {
		addressBookDBService = AddressBookDBService.getInstance();
	}

	public AddressBookService(List<AddressBookData> addressBookList) {
		this();
		this.addressBookList = addressBookList;
	}

	public void writeAddressBookData(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO))
			new AddressBookFileIOService().writeData(addressBookList);
	}

	public void printAddressBookData(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO))
			new AddressBookFileIOService().printData();
	}

	public long countEntries(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO))
			return new AddressBookFileIOService().countEntries();
		if (ioService.equals(IOService.DB_IO))
			return addressBookList.size();
		if (ioService.equals(IOService.REST_IO))	
			return addressBookList.size();
		return 0;
	}

	public int readAddressBookData(IOService ioService) {
		this.lines = new AddressBookFileIOService().readData();
		return lines;
	}

	public List<AddressBookData> readAddressBookDataFromDB(IOService ioService) {
		this.addressBookList = addressBookDBService.readData();
		return this.addressBookList;
	}

	public void updateContactNumber(String firstName, String phone) {
		int result;
		result = addressBookDBService.updateAddressBookData(firstName, phone);
		if (result == 0)
			return;
		AddressBookData addressBookData = this.getAddressBookData(firstName);
		if (addressBookData != null)
			addressBookData.phone = phone;	
	}

	private AddressBookData getAddressBookData(String firstName) {
		AddressBookData addressBookData;
		addressBookData = this.addressBookList.stream().filter(dataItem -> dataItem.firstName.equals(firstName))
				.findFirst().orElse(null);
		return addressBookData;
	}

	public boolean checkAddressBookSyncWithDB(String firstName) {
		List<AddressBookData> addressBookDataList = addressBookDBService.getAddressBookDetails(firstName);
		return addressBookDataList.get(0).equals(getAddressBookData(firstName));
	}

	public List<AddressBookData> readAddressBookForDateRange(IOService ioService, LocalDate dateAdded, LocalDate dateNow) {
		if (ioService.equals(IOService.DB_IO))
			return addressBookDBService.getAddressBookForDateRange(dateAdded, dateNow);
		return null;
	}

	public List<AddressBookData> readAddressBookForCity(IOService ioService, String city) {
		if (ioService.equals(IOService.DB_IO))
			return addressBookDBService.getAddressBookForGivenCity(city);
		return null;
	}

	public List<AddressBookData> readAddressBookForState(IOService ioService, String state) {
		if (ioService.equals(IOService.DB_IO))
			return addressBookDBService.getAddressBookForGivenState(state);
		return null;
	}

	public void addPersonToAddressBook(String firstName, String lastName, String address, String city, String state,
			String zip, String phone, String email, LocalDate dateAdded) {
		addressBookList.add(addressBookDBService.addPersonToAddressBookDB(firstName, lastName, address, city, state,
				zip, phone, email, dateAdded));
	}

	public void addContactToAddressBookUsingThreads(List<AddressBookData> contactsList) {
		Map<Integer, Boolean> contactAdditionStatus = new HashMap<Integer, Boolean>();
		contactsList.forEach(personData -> {
			Runnable task = () -> {
				contactAdditionStatus.put(personData.hashCode(), false);
				this.addPersonToAddressBook(personData.firstName, personData.lastName, personData.address,
						personData.city, personData.state, personData.zip, personData.phone, personData.email,
						personData.dateAdded);
				contactAdditionStatus.put(personData.hashCode(), true);
			};
			Thread thread = new Thread(task, personData.firstName);
			thread.start();
		});
		while (contactAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

	public void addContactToAddressBook(AddressBookData addressBookData, IOService ioService) {
		if (ioService.equals(IOService.REST_IO))	
			addressBookList.add(addressBookData);
	}
}
