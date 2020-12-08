package com.addressbookworkshop;

public class AddressBookData {
	public String firstName;
	public String lastName;
	public String address;
	public String city;
	public String state;
	public String zip;
	public String phone;
	public String email;

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
	
	public String toString() {
		return "\nFirst Name :" + firstName + ", Last Name : " + lastName + "\nAddress : " + address+ ", City : " + city+ "\nState : " + state
				+ ", Zip Code : " + zip+ "\nPhone Number : " + phone+ "\nEmail Id : " + email;
	}

	public String getName() {
		return firstName+" "+lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}

}
