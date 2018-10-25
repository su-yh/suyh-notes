package cn.tedu.net;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	static ServerSocket ss;

	public static void main(String[] args) throws IOException {
		demo02();
	}

	public static void demo01() throws IOException {
		ss = new ServerSocket(10086);
		// ���տͻ��˵��������󣬽�������
		while (true) {
			Socket socket = ss.accept();
			System.out.println("�������ӳɹ���");
			System.out.println(socket);

			// ��������
			byte[] bys = new byte[5];
			InputStream is = socket.getInputStream();
			// is.read(bys, 0, bys.length);
			int len = is.read(bys);
			System.out.println(new String(bys, 0, len));

			is.close();
			socket.close();
		}
	}
	
	public static void demo02() throws IOException {
		ss = new ServerSocket(10086);
		Socket socket = ss.accept();
		System.out.println("�������ӳɹ���");
		System.out.println(socket);
		
		InputStream is = socket.getInputStream();
		DataInputStream dis = new DataInputStream(is);

		// Э��ID
		int type = dis.readInt();
		if (type == 1)
		{
			System.out.println("��ȡ�����ı��ļ���");
		}
		
		// ����
		long len = dis.readLong() + 1;
		byte[] bys = new byte[(int)len];
		
		// ��������
		int length = dis.read(bys);
		System.out.println(new String(bys, 0, bys.length, "UTF-8"));
		
		dis.close();
		is.close();
		socket.close();
		ss.close();
	}
}
