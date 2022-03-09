package ru.metaprime.testtask.entity;

public class Organization {
	private String name;
	private String area;
	private String address;
	private String phone;

	public Organization(String name, String ares, String address, String phone) {
		this.name = name;
		this.area = ares;
		this.address = address;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String ares) {
		this.area = ares;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
