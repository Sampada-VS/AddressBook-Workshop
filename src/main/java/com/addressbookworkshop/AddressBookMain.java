package com.addressbookworkshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBookMain {
	public enum IOService {
		CONSOLE_IO
	}
	List<AddressBookData> addressBookList;
	public AddressBookMain(List<AddressBookData> addressBookList) {
		this.addressBookList=addressBookList;
	}

	public static void main(String[] args) {
		System.out.println("Welcome to Addressbook workshop problem.");
		
		List<AddressBookData> addressBookList = new ArrayList<>();
		AddressBookMain addressBookMain = new AddressBookMain(addressBookList);
		Scanner consoleInputReader = new Scanner(System.in);
		addressBookMain.readAddressBookData(consoleInputReader);
		addressBookMain.writeAddressBookData(IOService.CONSOLE_IO);
	}

	private void writeAddressBookData(IOService ioService) {
		if (ioService.equals(IOService.CONSOLE_IO))
			System.out.println("Writing Person details to console \n" + addressBookList);	
	}

	private void readAddressBookData(Scanner consoleInputReader) {
		System.out.println("Enter Person details to add in addressbook ==");
		System.out.println("Enter First name : ");
		String firstName = consoleInputReader.next();

		System.out.println("Enter Last name : ");
		String lastName = consoleInputReader.next();

		System.out.println("Enter Address : ");
		String address = consoleInputReader.next();

		System.out.println("Enter City : ");
		String city = consoleInputReader.next();

		System.out.println("Enter State : ");
		String state = consoleInputReader.next();

		System.out.println("Enter Zip : ");
		String zip = consoleInputReader.next();

		System.out.println("Enter Phone number : ");
		String phone = consoleInputReader.next();

		System.out.println("Enter Email Id : ");
		String email = consoleInputReader.next();
		addressBookList.add(new AddressBookData(firstName, lastName, address, city, state, zip, phone, email));
	}
}
