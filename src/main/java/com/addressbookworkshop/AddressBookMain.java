package com.addressbookworkshop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import java.util.Scanner;

public class AddressBookMain {
	public enum IOService {
		CONSOLE_IO
	}

	static Map<String, List<AddressBookData>> addressBookName = new HashMap<String, List<AddressBookData>>();
	static LinkedHashMap<String, String> cityPerson = new LinkedHashMap<String, String>();
	static LinkedHashMap<String, String> statePerson = new LinkedHashMap<String, String>();

	public static void main(String[] args) {
		System.out.println("Welcome to Addressbook workshop problem.");

		List<AddressBookData> addressBookList = new ArrayList<>();
		Scanner consoleInputReader = new Scanner(System.in);
		int choice;
		do {
			System.out.println("Enter 1.Add addressbook 2.Display 3.Edit 4.Delete 5.Search By City"
					+ " 6.Search By State 7.Count person by city 8.Count person by state 9.Sort By Name 10.Exit : ");
			choice = consoleInputReader.nextInt();
			switch (choice) {
			case 1:
				createNewAddressBook(consoleInputReader);
				break;
			case 2:
				writeAddressBookData(IOService.CONSOLE_IO);
				break;
			case 3:
				editAddressBookData(consoleInputReader);
				break;
			case 4:
				deleteAddressBookData(consoleInputReader);
				break;
			case 5:
				searchByCity(consoleInputReader);
				break;
			case 6:
				searchByState(consoleInputReader);
				break;
			case 7:
				countByCity(consoleInputReader);
				break;
			case 8:
				countByState(consoleInputReader);
				break;
			case 9:
				sortEntriesByName(consoleInputReader);
				break;
			case 10:
				System.out.println("You exit.");
				break;
			default:
				System.out.println("Wrong input .");
			}
		} while (choice != 10);
}
	private static void sortEntriesByName(Scanner consoleInputReader) {
		for (Entry<String, List<AddressBookData>> contactList : addressBookName.entrySet()) {
			String bookName = contactList.getKey();
			List<AddressBookData> data = contactList.getValue();
			System.out.println(bookName+" addressbook sorted by name == ");
			data = data.stream().sorted(Comparator.comparing(AddressBookData::getFirstName))
					.collect(Collectors.toList());
			data.forEach(System.out::println);
		}
	}

	private static void countByState(Scanner consoleInputReader) {
		System.out.println("Enter state :");
		String state = consoleInputReader.next();
		long totalStatePerson=0;
		for (Entry<String, List<AddressBookData>> contactList : addressBookName.entrySet()) {
			List<AddressBookData> data = contactList.getValue();
			long statePersonCount = data.stream().filter(p -> state.equalsIgnoreCase(p.getState())).count();
			totalStatePerson+=statePersonCount;
		}
		System.out.println("Total Persons in '" + state + "' state are: "+totalStatePerson);		
	}

	private static void countByCity(Scanner consoleInputReader) {
		System.out.println("Enter city :");
		String city = consoleInputReader.next();
		long totalCityPerson=0;
		for (Entry<String, List<AddressBookData>> contactList : addressBookName.entrySet()) {
			List<AddressBookData> data = contactList.getValue();
			long cityPersonCount = data.stream().filter(p -> city.equalsIgnoreCase(p.getCity())).count();
			totalCityPerson+=cityPersonCount;
		}
		System.out.println("Total Persons in '" + city + "' city are: "+totalCityPerson);
	}

	private static void searchByState(Scanner consoleInputReader) {
		System.out.println("Search by state :");
		String state = consoleInputReader.next();
		System.out.println("Persons in " + state + " state are: ");
		for (Entry<String, List<AddressBookData>> contactList : addressBookName.entrySet()) {
			List<AddressBookData> data = contactList.getValue();
			statePerson = data.stream().filter((p) -> state.equalsIgnoreCase(p.getState()))
					.collect(Collectors.toMap(AddressBookData::getName, AddressBookData::getState, (x, y) -> x + ", " + y, LinkedHashMap::new));
			statePerson.forEach((x, y) -> System.out.println(x));
		}
	}

	private static void searchByCity(Scanner consoleInputReader) {
		System.out.println("Search by city :");
		String city = consoleInputReader.next();
		System.out.println("Persons in " + city + " city are: ");
		for (Entry<String, List<AddressBookData>> contactList : addressBookName.entrySet()) {
			List<AddressBookData> data = contactList.getValue();
			cityPerson = data.stream().filter((p) -> city.equalsIgnoreCase(p.getCity()))
					.collect(Collectors.toMap(AddressBookData::getName, AddressBookData::getCity, (x, y) -> x + ", " + y, LinkedHashMap::new));
			cityPerson.forEach((x, y) -> System.out.println(x));
		}
	}

	private static List<AddressBookData> addMultipleAddressBookData(Scanner consoleInputReader) {
		System.out.println("How many contacts do you want to add :");
		int num = consoleInputReader.nextInt();
		List<AddressBookData> contact = new ArrayList<AddressBookData>();
		while (num > 0) {
			contact.addAll(new ArrayList<>(readAddressBookData(consoleInputReader)));
			num--;
		}
		return contact;
	}

	private static void createNewAddressBook(Scanner consoleInputReader) {
		System.out.println("Enter name of addressbook :");
		String addressbookName = consoleInputReader.next();
		int found = 0;
		for (Entry<String, List<AddressBookData>> list : addressBookName.entrySet()) {
			String bookName = list.getKey();
			if (addressbookName.equalsIgnoreCase(bookName))
				found = 1;
		}
		if (found == 0) {
			List<AddressBookData> contactList = addMultipleAddressBookData(consoleInputReader);
			addressBookName.put(addressbookName, contactList);
			System.out.println("Addressbooks are :" + addressBookName);
		} else
			System.out.println("Addressbook already exists.");
	}

	private static void deleteAddressBookData(Scanner consoleInputReader) {
		System.out.println("Enter Person's first name :");
		String firstName = consoleInputReader.next();
		List<AddressBookData> data = new ArrayList<>();
		for (Entry<String, List<AddressBookData>> list : addressBookName.entrySet()) {
			String bookName = list.getKey();
			data = list.getValue();
			for (int i = 0; i < data.size(); i++) {
				AddressBookData p = (AddressBookData) data.get(i);
				if (firstName.equalsIgnoreCase(p.getName())) {
					data.remove(i);
					System.out.println("Contact deleted from " + bookName + " addressbook.");
					System.out.println(bookName + " addressbook entries :" + addressBookName.get(bookName));
				}
			}
		}
	}

	private static void editAddressBookData(Scanner consoleInputReader) {
		System.out.println("Enter Person's first name :");
		String firstName = consoleInputReader.next();
		String bookName = null;
		List<AddressBookData> data = new ArrayList<>();
		for (Entry<String, List<AddressBookData>> list : addressBookName.entrySet()) {
			bookName = list.getKey();
			data = list.getValue();
			for (int i = 0; i < data.size(); i++) {
				AddressBookData p = (AddressBookData) data.get(i);
				if (firstName.equalsIgnoreCase(p.getName()))
					data.remove(i);
			}
		}
		System.out.println("Contact is in " + bookName + " addressbook.");
		System.out.print("Enter Last name : ");
		String lastName = consoleInputReader.next();

		System.out.print("Enter Address : ");
		String address = consoleInputReader.next();

		System.out.print("Enter City : ");
		String city = consoleInputReader.next();

		System.out.print("Enter State : ");
		String state = consoleInputReader.next();

		System.out.print("Enter Zip : ");
		String zip = consoleInputReader.next();

		System.out.print("Enter Phone number : ");
		String phone = consoleInputReader.next();

		System.out.print("Enter Email Id : ");
		String email = consoleInputReader.next();
		data.add(new AddressBookData(firstName, lastName, address, city, state, zip, phone, email));
		addressBookName.put(bookName, data);
	}

	private static void writeAddressBookData(IOService ioService) {
		if (ioService.equals(IOService.CONSOLE_IO))
			System.out.println("Writing Person details to console \n" + addressBookName);
	}

	private static List<AddressBookData> readAddressBookData(Scanner consoleInputReader) {
		System.out.println("Enter Person details to add in addressbook ==");
		System.out.print("Enter First name : ");
		String firstName = consoleInputReader.next();
		AddressBookData found = null;
		List<AddressBookData> inputData = new ArrayList<>();
		for (Entry<String, List<AddressBookData>> list : addressBookName.entrySet()) {
			List<AddressBookData> data = list.getValue();
			found = data.stream().filter(p -> firstName.equalsIgnoreCase(p.getName())).findAny().orElse(null);
		}

		if (found != null)
			System.out.println("Can't add person entry because it already exists.");

		else {
			System.out.print("Enter Last name : ");
			String lastName = consoleInputReader.next();

			System.out.print("Enter Address : ");
			String address = consoleInputReader.next();

			System.out.print("Enter City : ");
			String city = consoleInputReader.next();

			System.out.print("Enter State : ");
			String state = consoleInputReader.next();

			System.out.print("Enter Zip : ");
			String zip = consoleInputReader.next();

			System.out.print("Enter Phone number : ");
			String phone = consoleInputReader.next();

			System.out.print("Enter Email Id : ");
			String email = consoleInputReader.next();
			inputData.add(new AddressBookData(firstName, lastName, address, city, state, zip, phone, email));
		}
		return inputData;
	}
}
