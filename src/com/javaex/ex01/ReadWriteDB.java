package com.javaex.ex01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteDB{

	List<Person> lPerson = new ArrayList<Person>();
	
	
	public List<Person> Read() throws IOException {
		InputStream dbIs = new FileInputStream("C:\\Users\\김영관\\Desktop\\웹프로그래밍\\20201210(JAVA)\\PhoneDB.txt");
		InputStreamReader dbIsr = new InputStreamReader(dbIs, "UTF-8");
		BufferedReader dbBr = new BufferedReader(dbIsr);
		
		while(true) {
			String str = dbBr.readLine(); //한줄씩 읽어온다. 줄바꿈기호는 제외
			if(str == null) {
				break;
			}
			
			String person[] = str.split(",");
			lPerson.add(new Person(person[0],person[1],person[2]));
		}
		
		return lPerson;
	}
	public void Write(List<Person> lPerson) throws IOException{
		this.lPerson = lPerson;
		
		OutputStream out = new FileOutputStream("C:\\Users\\김영관\\Desktop\\웹프로그래밍\\20201210(JAVA)\\PhoneDB.txt");
		OutputStreamWriter osw2 = new OutputStreamWriter(out,"UTF-8");
		BufferedWriter bw = new BufferedWriter(osw2);
		
		for(int i=0;i<lPerson.size();i++) {
			bw.write(lPerson.get(i).getName()+","+lPerson.get(i).getHp()+","+lPerson.get(i).getCompany());
			bw.newLine();
		}
		bw.close();
	}
}
