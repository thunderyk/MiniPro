package com.javaex.ex01;

import java.io.Serializable;

public class Person implements Serializable{
	private String name;
	private String hp;
	private String company;
	
	public Person(String name, String hp, String company) {
		this.name = name;
		this.hp = hp;
		this.company = company;
	}
	
	public void show(int i) {
		System.out.println(i+". "+name+"     "+hp+"     "+company);
	}

	public String getName() {
		return name;
	}

	public String getHp() {
		return hp;
	}

	public String getCompany() {
		return company;
	}
}
