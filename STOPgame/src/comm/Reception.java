package comm;

import java.io.BufferedReader;
import java.io.IOException;

public class Reception extends Thread{

	private BufferedReader br;
	private OnMessageListener listener;
	
	private Session session;
	
	public Reception(Session session, BufferedReader br) {
		this.session = session;
		this.br = br;
	}
	
	@Override
	public void run() {
		readMessage();
	}
	
	public void setListener(OnMessageListener listener) {
		this.listener = listener;
	}


	public interface OnMessageListener{
		void onMessage(Session session, String msg);
	}
	
	public String readMessage() {
		String msg = "";
		try {
			while (true) {
				msg = br.readLine();
				if (msg == null) {
					break;
				}
				listener.onMessage(session, msg);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg;
	}
}
