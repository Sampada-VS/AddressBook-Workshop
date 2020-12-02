package com.addressbookworkshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBookMain {
	public enum IOService {
		CONSOLE_IO
	}

	List<AddressBookData> addressBookList= new ArrayList<AddressBookData>();;

	public AddressBookMain(List<AddressBookData> addressBookList) {
		this.addressBookList = addressBookList;
	}

	public static void main(String[] args) {
		System.out.println("Welcome to Addressbook workshop problem.");

		List<AddressBookData> addressBookList = new ArrayList<>();
		AddressBookMain addressBookMain = new AddressBookMain(addressBookList);
		Scanner consoleInputReader = new Scanner(System.in);
		System.out.println("Enter 1.Add 2.Display 3.Edit 4.Delete 5.Exit : ");
		int choice=consoleInputReader.nextInt();
		do {
			switch(choice) {
				case 1:	addressBookMain.readAddressBookData(consoleInputReader);
					break;
				case 2: addressBookMain.writeAddressBookData(IOService.CONSOLE_IO);
					break;
				case 3:	addressBookMain.editAddressBookData(consoleInputReader);
					break;
				case 4:	addressBookMain.deleteAddressBookData(consoleInputReader);
				case 5:	System.out.println("You exit.");
				default:	System.out.println("Wrong input .");
			}
		}while(choice !=4);
	}

	private void deleteAddressBookData(Scanner consoleInputReader) {
		System.out.println("Enter person first name to delete :");
		String name=consoleInputReader.next();
		for (int i = 0; i < addressBookList.size(); i++) {
			AddressBookData p = (AddressBookData) addressBookList.get(i);
			if (name.equalsIgnoreCase(p.getName()))
				addressBookList.remove(i);
		}
		System.out.println(addressBookList);	
	}

	private void editAddressBookData(Scanner consoleInputReader) {
		System.out.println("Enter Person's first name :");
		String firstName = consoleInputReader.next();
		for (int i = 0; i < addressBookList.size(); i++) {
			AddressBookData p = (AddressBookData) addressBookList.get(i);
			if (firstName.equalsIgnoreCase(p.getName()))
				addressBookList.remove(i);
		}
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
			addressBookList.add(new AddressBookData(firstName,lastName,address, city, state, zip, phone, email));
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
