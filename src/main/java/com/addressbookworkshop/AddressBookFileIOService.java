package com.addressbookworkshop;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class AddressBookFileIOService {
	public static String ADDRESSBOOK_TXT_FILE = "addressbook-file.txt";
	public static String ADDRESSBOOK_CSV_FILE = "addressbook-file1.csv";
	public static String ADDRESSBOOK_JSON_FILE = "addressbook-file2.json";

	public void writeData(List<AddressBookData> addressBookList) {
		try (Writer writer = Files.newBufferedWriter(Paths.get(ADDRESSBOOK_JSON_FILE));) {
			Gson gson = new Gson();
			gson.toJson(addressBookList, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printData() {
		try (Reader reader = Files.newBufferedReader(Paths.get(ADDRESSBOOK_JSON_FILE));) {
			Gson gson = new Gson();
			List<AddressBookData> books = Arrays.asList(gson.fromJson(reader, AddressBookData[].class));
			books.forEach(System.out::println);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long countEntries() {
		List<AddressBookData> books = null;
		try (Reader reader = Files.newBufferedReader(Paths.get(ADDRESSBOOK_JSON_FILE));) {
			Gson gson = new Gson();
			books = Arrays.asList(gson.fromJson(reader, AddressBookData[].class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return books.size();
	}

	public int readData() {
		List<AddressBookData> books = null;
		try (Reader reader = Files.newBufferedReader(Paths.get(ADDRESSBOOK_JSON_FILE));) {
			Gson gson = new Gson();
			books = Arrays.asList(gson.fromJson(reader, AddressBookData[].class));
			books.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return books.size();
	}
}
