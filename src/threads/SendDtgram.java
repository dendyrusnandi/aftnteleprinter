package threads;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendDtgram {
	static String data="";
	
	public SendDtgram(String dt) {
		data=dt;
		Send();
	}
	
	public static void Send() {
		try {
			DatagramSocket s = new DatagramSocket();
			byte[] line = new byte[10000];
			InetAddress dest = InetAddress.getByName("127.0.0.1");
			line = data.getBytes();
			int len = line.length;
              
			System.out.println(data + "\nlength:" + len);
			DatagramPacket pkt = new DatagramPacket(line, len, dest, 101);
			s.send(pkt);

			s.close();
		} catch (Exception err) {
			System.out.println("err" + err);
		}
	}
	
	
}
