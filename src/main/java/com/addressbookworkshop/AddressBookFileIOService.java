package com.addressbookworkshop;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class AddressBookFileIOService {
	public static String ADDRESSBOOK_TXT_FILE = "addressbook-file.txt";
	public static String ADDRESSBOOK_CSV_FILE = "addressbook-file1.csv";

	public void writeData(List<AddressBookData> addressBookList) {

		try (Writer writer = Files.newBufferedWriter(Paths.get(ADDRESSBOOK_CSV_FILE));) {
			StatefulBeanToCsv<AddressBookData> beanToCsv = new StatefulBeanToCsvBuilder(writer)
					.withSeparator(CSVWriter.DEFAULT_SEPARATOR)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
					.build();
			beanToCsv.write(addressBookList);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		}
	}

	public void printData() {
		try {
			Files.lines(new File(ADDRESSBOOK_CSV_FILE).toPath()).skip(1).forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long countEntries() {
		long entries = 0;
		try {
			entries = Files.lines(new File(ADDRESSBOOK_CSV_FILE).toPath()).skip(1).count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entries;
	}

	public int readData() {
		int entries = 0;
		try (Reader reader = Files.newBufferedReader(Paths.get(ADDRESSBOOK_CSV_FILE));) {
			CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
			String[] nextRecord;
			while ((nextRecord = csvReader.readNext()) != null) {
				System.out.println("First Name : " + nextRecord[3]);
				System.out.println("Last Name : " + nextRecord[4]);
				System.out.println("Address : " + nextRecord[0]);
				System.out.println("City : " + nextRecord[1]);
				System.out.println("State : " + nextRecord[6]);
				System.out.println("Zip code : " + nextRecord[7]);
				System.out.println("PhoneNo : " + nextRecord[5]);
				System.out.println("Email : " + nextRecord[2]);
				System.out.println("==========================");
				entries++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entries;
	}
}
