package model;

public class Clients {

	private int amountPoint;
	private boolean finish;
	private String id;
	public String type = "Clients";

	private transient Game game;

	public Clients(int amountPoint, boolean finish) {
		this.amountPoint = amountPoint;
		this.finish = finish;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAmountPoint() {
		return this.amountPoint;
	}

	public void setAmountPoint(int amountPoint) {
		this.amountPoint = amountPoint;
	}

	public boolean isFinish() {
		return this.finish;
	}

	public boolean getFinish() {
		return this.finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

}