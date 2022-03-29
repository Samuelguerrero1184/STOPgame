package comm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import com.google.gson.Gson;

import comm.Reception.OnMessageListener;
import model.Clients;
import model.Game;
import model.Generic;
import model.Message;

public class TCPconnection extends Thread implements Reception.OnMessageListener {

	public static final int PORT = 6000;
	private ArrayList<Session> sessions;
	private Queue<Session> sessionList;
	private static TCPconnection instance = null;
	private Socket socket;
	public String id;
	private int port = 6000;
	public OnMessageListener listener;
	private Gson gson;

	private TCPconnection() {
		sessions = new ArrayList<>();
		gson = new Gson();
		sessionList = new LinkedList<>();
		id = UUID.randomUUID().toString();
	}

	public static synchronized TCPconnection getInstance() {
		if (instance == null) {
			instance = new TCPconnection();
		}
		return instance;
	}

	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(PORT);

			while (true) {
				System.out.println("Awaiting conection in port: " + PORT);
				socket = server.accept();
				System.out.println("Connection established!");
				Session session = new Session(socket);
				sessions.add(session);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void organize() {

		new Thread(() -> {

			Session sessionA = null;
			Session sessionB = null;

			boolean out = false;
			for (int i = 0; i < sessions.size() && out == false; i++) {
				Session tmp = sessions.get(i);
				if (tmp.isOnGame() == false) {
					sessionA = tmp;
					sessionA.setOnGame(true);
					System.out.println(sessionA.isOnGame());
					sessions.get(i).setOnGame(true);
					out = true;
				}
			}
			out = false;
			for (int i = 0; i < sessions.size() && out == false; i++) {
				Session tmp = sessions.get(i);
				if (tmp.isOnGame() == false) {

					sessionB = tmp;
					sessionB.setOnGame(true);
					sessions.get(i).setOnGame(true);
					out = true;
				}
			}
			if (sessionA == null || sessionB == null) {
				System.out.println("Alguna sesión es nula");
			}
			if (sessionA != null && sessionB != null) {
				Game game = new Game(sessionA, sessionB);
			}

		}).start();

	}

	public void startGame(Session sessionA, Session sessionB) {
		Game game = new Game(sessionA, sessionB);
	}
	
	@Override
	public void onMessage(Session session, String msg) {
		Generic obj = gson.fromJson(msg, Generic.class);

		switch (obj.getType()) {
		case "User":
			Clients player = gson.fromJson(msg, Clients.class);
			session.setPlayer(player);
			sessionList.add(session);
			organize();
			break;

		case "Game":
			Game gameIn = gson.fromJson(msg, Game.class);
			break;
		case "Message":
			Message message = gson.fromJson(msg, Message.class);
			switch (message.getMessage()) {

			}
			break;

		default:
			break;
		}

	}
	public Session findSession(Clients player) {
		boolean validation = false;
		Session temp = null;

		for (int i = 0; i < sessions.size() && !validation; i++) {
			if (sessions.get(i).getClient().getId().equals(player.getId())) {
				temp = sessions.get(i);
				validation = true;
			}
		}

		return temp;
	}
}
