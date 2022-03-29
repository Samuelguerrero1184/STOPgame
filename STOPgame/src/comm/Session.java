package comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import com.google.gson.Gson;
import comm.Reception.OnMessageListener;
import model.Clients;

public class Session {

	private Clients player;
	private Socket socket;
	private Transmiter trans;
	private Reception recep;
	private BufferedReader reader;
	private BufferedWriter writer;
	private boolean onGame;
	
	public Session(Socket socket) {
		try {
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		recep = new Reception(this, reader);
		recep.setListener(TCPconnection.getInstance());
		recep.start();
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		trans = new Transmiter(writer);
	} catch(IOException ex) {
		ex.printStackTrace();
	}
	}
	/*
	public String readUser() throws IOException {
		boolean recieved = false;
		String user = "Nothing";
		while(recieved==false) {
			System.out.println("Waiting for message...");
			user = reader.readLine();
			System.out.println(user);
			if (user != null) {
				recieved = true;
				System.out.println("out");
			}
		}
		return user;
	}

	
	public void sendMessage(char c) {
		
		new Thread(() -> {

			try {
				//send.messageSend(line);
				writer.write(c + "\n");

				//System.out.println(line);
				writer.flush();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		;
	}
	*/
	public boolean isOnGame() {
		return this.onGame;
	}

	public boolean getOnGame() {
		return this.onGame;
	}

	public void setOnGame(boolean onGame) {
		this.onGame = onGame;
	}
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public Transmiter getTrans() {
		return trans;
	}
	public Reception getRecep() {
		return recep;
	}
	public Clients getClient() {
		return player;
	}

	public void setPlayer(Clients player) {
		this.player = player;
	}
}
