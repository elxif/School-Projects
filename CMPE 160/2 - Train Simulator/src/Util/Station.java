package Util;

import java.io.PrintStream;
import java.util.Queue;
import java.util.LinkedList;
import CargoTrain.*;

public class Station {
	private int id;
	private Queue<Cargo> cargoQueue;
	private PrintStream printStream;

	public Station(int id, PrintStream printStream) {
		this.id = id;
		this.printStream = printStream;
		this.cargoQueue = new LinkedList<Cargo>();
	}

	public void process(Train train) {

		train.unload(this.cargoQueue);

		Queue<Cargo> temp = new LinkedList<Cargo>();
		temp.addAll(this.cargoQueue);

		while (!temp.isEmpty()) {

			Cargo c = temp.remove();

			if (c.getTarget().getID() == this.id) {
				printStream.println(c.toString());
				cargoQueue.remove(c);
			}

		}
		train.load(cargoQueue);
		printStream.println(this.id + " " + train.getLength());
	}

	public int getID() {
		return this.id;
	}

	public Queue<Cargo> getCargos() {
		return this.cargoQueue;
	}

	public void add(Cargo c) {
		this.cargoQueue.add(c);

	}

}