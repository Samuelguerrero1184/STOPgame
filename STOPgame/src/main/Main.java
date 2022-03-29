package main;

import comm.TCPconnection;

public class Main {

	public static void main(String[] args) {
		TCPconnection tcp = TCPconnection.getInstance();
		tcp.start();
	}
}
