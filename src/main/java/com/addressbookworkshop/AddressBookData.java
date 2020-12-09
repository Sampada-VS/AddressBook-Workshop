package com.addressbookworkshop;

import java.time.LocalDate;

public class AddressBookData {
	public int id;
	public String firstName;
	public String lastName;
	public String address;
	public String city;
	public String state;
	public String zip;
	public String phone;
	public String email;
	public LocalDate dateAdded;

	public AddressBookData(String firstName, String lastName, String address, String city, String state,
						   String zip, String phone, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
	}
	
	public AddressBookData(int id, String firstName, String lastName, String address, String city, String state,
			String zip, String phone, String email) {
		this(firstName,lastName,address,city,state,zip,phone,email);
		this.id=id;
	}
	
	public AddressBookData(int id, String firstName, String lastName, String address, String city, String state,
			String zip, String phone, String email,LocalDate dateAdded) {
		this(id,firstName,lastName,address,city,state,zip,phone,email);
		this.dateAdded=dateAdded;
	}

	public String toString() {
		return "First Name :" + firstName + ", Last Name : " + lastName + ", Address : " + address+ ", City : " + city+ ", State : " + state
				+ ", Zip Code : " + zip+ ", Phone Number : " + phone+ ", Email Id : " + email;
	}

	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public String getName() {
		return firstName+" "+lastName;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AddressBookData that = (AddressBookData) o;
		return id == that.id && firstName.equals(that.firstName) && lastName.equals(that.lastName)	
				&& address.equals(that.address) && city.equals(that.city) && state.equals(that.state)
				&& zip.equals(that.zip) && phone.equals(that.phone) && email.equals(that.email);
	}
}
