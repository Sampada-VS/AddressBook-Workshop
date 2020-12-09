package com.addressbookworkshop;

import java.util.List;

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
}
