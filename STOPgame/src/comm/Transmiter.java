package comm;

import java.io.BufferedWriter;
import java.io.IOException;

public class Transmiter {

	private BufferedWriter bw;
	
	
	public Transmiter(BufferedWriter bw) {
		this.bw = bw;
	}
	
	
	public void sendMessage(String msg) {
		new Thread(
				() -> {
					try {
						bw.write(msg + "\n");
						bw.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}).start();
	}
}
