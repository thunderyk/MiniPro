package com.javaex.ex01;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {

	public static void main(String[]args) throws IOException{
		
		ServerSocket sSocket = new ServerSocket();
		sSocket.bind(new InetSocketAddress("192.168.35.52",1051));
		
		System.out.println("<서버 시작>");
		System.out.println("===================================");
		System.out.println("연결을 기다리고 있습니다.");
		
		Socket socket = sSocket.accept();
		System.out.println("클라이언트가 연결되었습니다.");
		
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is,"UTF-8");
		BufferedReader br = new BufferedReader(isr);
		
		OutputStream os = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		
		OutputStream os2 = socket.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os2,"UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		
		InputStream is2 = socket.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is2);
		
		ReadWriteDB rw = new ReadWriteDB();
		List<Person> lPerson = rw.Read();
		
		while(true) {
			String msg = br.readLine();
			
			if(msg==null) {
				System.out.println("서버를 종료합니다.");
				break;
			}else {
				if(msg.equals("1")||msg.equals("4")) {
					oos.writeObject(lPerson);
					oos.reset();
				}else if(msg.equals("2")){
					try {
						lPerson.add((Person)(ois.readObject()));
						rw.Write(lPerson);
						
						bw.write("[등록되었습니다.]");
						bw.newLine();
						bw.flush();
					}catch(Exception e) {
						e.toString();
					}
				}else if(msg.equals("3")){
					try {
						int reNum = (int)(ois.readObject());
						if(reNum<=0||reNum>lPerson.size()) {
							bw.write("[잘못입력하셨습니다.]");
							bw.newLine();
							bw.flush();
						}else {
							lPerson.remove(reNum-1);
							rw.Write(lPerson);
							bw.write("[삭제되었습니다.]");
							bw.newLine();
							bw.flush();
						}
					}catch(Exception e){
						e.toString();
					}
				}
			}
		}
		//자원종료
				br.close();		
				bw.close();
	}
}
