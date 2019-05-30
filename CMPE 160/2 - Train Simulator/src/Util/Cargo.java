package Util;

public class Cargo {
	private int id;
	private int size;
	private Station loadingStation;
	private Station targetStation;

	public Cargo(int id, int size, Station loadingStation, Station targetStation) {
		this.id = id;
		this.size = size;
		this.loadingStation = loadingStation;
		this.targetStation = targetStation;
	}

	public String toString() {
		return this.id + " " + this.loadingStation.getID() + " " + this.targetStation.getID() + " " + this.size;
	}

	public int getID() {
		return this.id;
	}

	public int getSize() {
		return this.size;
	}

	public Station getTarget() {
		return this.targetStation;
	}
}
