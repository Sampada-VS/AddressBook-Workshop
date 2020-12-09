package com.addressbookworkshop;

import java.util.List;

public class AddressBookService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private List<AddressBookData> addressBookList;
	private int lines;

	public AddressBookService() {
	}

	public AddressBookService(List<AddressBookData> addressBookList) {
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
}
