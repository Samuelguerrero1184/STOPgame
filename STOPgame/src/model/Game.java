package model;

import java.io.IOException;
import java.util.Random;

import com.google.gson.Gson;

import comm.Session;
import comm.TCPconnection;

public class Game {
	
    private char letter;
    private Clients[] players;
    private Clients win;
	private Session a;
	private Session b;
	private TCPconnection tcp;

	public Game (Session a, Session b) {
		System.out.println("Starting game");
		this.a = a;
		this.b = b;
		startGame(a,b);
	}
	
	public void startGame(Session a,Session b) {
		Message msg = new Message("sendPlayer");
		Gson gson = new Gson();
		String message = gson.toJson(msg);
		a.getTrans().sendMessage(message);
		String playerA = a.getRecep().readMessage();
		b.getTrans().sendMessage(message);
		String playerB = b.getRecep().readMessage();
		Random r = new Random();
		char c = (char)(r.nextInt(26) + 'a');
		a.getTrans().sendMessage(playerB + "//" + c);
		b.getTrans().sendMessage(playerA + "//" + c);
		
	new Thread(() -> {

		String x = a.getRecep().readMessage();

		b.getTrans().sendMessage(x);
		String y = b.getRecep().readMessage();
		a.getTrans().sendMessage(y);
		b.getTrans().sendMessage("");

		a.getRecep().readMessage();
		b.getRecep().readMessage();

	}).start();

	new Thread(() -> {

		String y = b.getRecep().readMessage();
		a.getTrans().sendMessage(y);
		String x = a.getRecep().readMessage();
		b.getTrans().sendMessage(x);
		b.getRecep().readMessage();

	}).start();
	
	}
	 public char getLetter() {
	        return this.letter;
	    }

	    public void setLetter(char letter) {
	        this.letter = letter;
	    }

	    public Clients[] getplayers() {
	        return this.players;
	    }

	    public void setplayers(Clients[] players) {
	        this.players = players;
	    }

	    public Clients getWin() {
	        return this.win;
	    }

	    public void setWin(Clients win) {
	        this.win = win;
	    }
		public Session findSession(Clients player) {
			Session temp = tcp.findSession(player);
			return temp;
		}
	}

