package en.tedu.chat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends ChatProtocol {

	public Client() {
		super();
	}

	public Client(Socket socket) throws IOException {
		super(socket);
	}

	public static void main(String[] args) throws IOException {
		// demo01();
		demo02();
	}

	public static void demo01() throws IOException {
		Socket socket = new Socket("localhost", 40000);

		Client client = new Client(socket);

		Scanner sc = new Scanner(System.in);
		System.out.println("���������ݣ�");
		while (true) {
			String message = sc.nextLine();
			client.sendMessage(message);
			if ("�ݰ�".equals(message)) {
				break;
			}
		}
		String str = client.receiveMessage();
		System.out.println(str);

		sc.close();
		client.close();
		socket.close();
	}

	// �����߳���ǰ̨�̣߳������߳��Ǻ�̨�̡߳�
	public static void demo02() throws IOException {
		Client client = new Client();
		client.init();
	}

	public void init() throws IOException {
		// ����socket
		Socket socket = new Socket("localhost", 40000);
		open(socket);
		
		Sender sender = new Sender();
		Receive receive = new Receive();
		receive.setDaemon(true);
		
		sender.start();
		receive.start();
//		socket.close();
	}

	// �������ݵ��߳���
	private class Sender extends Thread {
		@Override
		public void run() {
			// �û��ӿ���̨�������ݣ�����
			Scanner sc = new Scanner(System.in);
			System.out.println("��ʼ���죺");
			try {
				while (true) {
					String message = sc.nextLine();
					sendMessage(message);
					if ("�����".equals(message)) {
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				sc.close();
			}
		}
	}

	private class Receive extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					String str = receiveMessage();
					System.out.println(str);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
