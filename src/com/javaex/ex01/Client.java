package com.javaex.ex01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {//실시간으로 통신을 통한 서버의 전화번호부 관리
	//Thread를 사용하면 여러 사람이 동시에 접근해서 수정 삭제를 하기 때문에
	//데이터의 정확성 무결성 일관성을 고려할 수 없어서 일단은 1대1 통신으로 만듬 추후 고려 후 수정
	public static void main(String[]args) throws IOException{
		
		Socket socket = new Socket();
		List<Person> lPerson = new ArrayList<Person>();
		
		System.out.println("클라이언트 시작");
		System.out.println("===============================");
		System.out.println("서버에 연결을 요청합니다.");
		
		socket.connect(new InetSocketAddress("192.168.35.52",1051));
		
		System.out.println("서버에 연결되었습니다.");
		
		OutputStream os = socket.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		
		InputStream is = socket.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		
		InputStream is2 = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is2,"UTF-8");
		BufferedReader br = new BufferedReader(isr);
		
		OutputStream os2 = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os2);
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("************************************");
		System.out.println("*            전화번호 관리 프로그램           *");
		System.out.println("************************************");
		while(true) {
			System.out.println();
			System.out.println("1.리스트	2.등록	3.삭제	4.검색	5.종료");
			System.out.println("--------------------------------------");
			System.out.print(">메뉴번호: ");
			String s = sc.nextLine();
			if(s.equals("5")) {
				System.out.println("************************************");
				System.out.println("*             감사합니다.             *");
				System.out.println("************************************");
				break;
			}else {
				bw.write(s);
				bw.newLine();
				bw.flush();
				if(s.equals("1")) {
					try {
						lPerson = (ArrayList<Person>)(ois.readObject());
						System.out.println("<1.리스트>");
						for(int i=0;i<lPerson.size();i++) {
							lPerson.get(i).show(i+1);
						}
						ois.reset();
					}catch(Exception e) {
						e.toString();
					}
				}else if(s.equals("2")){
					System.out.println("<2.등록>");
					System.out.print(">이름: ");
					String name = sc.nextLine();
					System.out.print(">휴대전화: ");
					String hp = sc.nextLine();
					System.out.print(">회사전화: ");
					String company = sc.nextLine();
					Person person = new Person(name,hp,company);
					
					oos.writeObject(person);
					String result = br.readLine();
					System.out.println(result);
				}else if(s.equals("3")){
					System.out.println("<3.삭제>");
					System.out.print(">번호: ");
					try {
						int num = sc.nextInt();
						sc.nextLine();
						oos.writeObject(num);
						String result = br.readLine();
						System.out.println(result);
					}catch(Exception e) {
						e.toString();
					}
				}else if(s.equals("4")){
					try {
						lPerson = (ArrayList<Person>)(ois.readObject());
						System.out.println("<4.검색>");
						System.out.print(">이름: ");
						String name = sc.nextLine();
						for(int i=0;i<lPerson.size();i++) {
							if(lPerson.get(i).getName().contains(name)) {
								lPerson.get(i).show(i+1);
							}
						}
						ois.reset();
					}catch(Exception e) {
						e.toString();
					}
				}else {
					System.out.println("[다시 입력해주세요.]");
				}
			}
		}
		//자원종료
		//br.close();		
		bw.close();
		sc.close();
	}
}
