package com.addressbookworkshop;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AddressBookFileIOService {
	public static String ADDRESSBOOK_TXT_FILE = "addressbook-file.txt";

	public void writeData(List<AddressBookData> addressBookList) {
		StringBuffer contactBuffer = new StringBuffer();
		addressBookList.forEach(contact -> {
			String addressBookDataString = contact.toString().concat("\n");
			contactBuffer.append(addressBookDataString);
		});
		try {
			Files.write(Paths.get(ADDRESSBOOK_TXT_FILE), contactBuffer.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printData() {
		try {
			Files.lines(new File(ADDRESSBOOK_TXT_FILE).toPath()).forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long countEntries() {
		long entries = 0;
		try {
			entries = Files.lines(new File(ADDRESSBOOK_TXT_FILE).toPath()).count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entries;
	}

	public List<String> readData() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(ADDRESSBOOK_TXT_FILE));
			for (String line : lines)
				System.out.println(line);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return lines;
	}
}
