package CargoTrain;

import Util.*;
import java.util.Queue;

public class Train {
	private int carCapacity;
	private int length;
	private Carriage head;
	private Carriage tail;

	public Train(int length, int carCapacity) {
		this.length = length;
		this.carCapacity = carCapacity;

		this.head = new Carriage(carCapacity);

		if (length > 2) {

			Carriage current = head;
			for (int i = 0; i < length - 2; i++) {
				Carriage n = new Carriage(carCapacity);
				current.setNext(n);
				current.getNext().setPrev(current);
				current = current.getNext();
			}

			this.tail = new Carriage(carCapacity);
			tail.setPrev(current);
			current.setNext(tail);
		} else if (length == 2) {
			this.head = new Carriage(carCapacity);
			this.tail = new Carriage(carCapacity);
			this.head.setNext(tail);
			this.tail.setPrev(head);
		} else if (length == 1) {
			this.head = this.tail = new Carriage(carCapacity);
		}

	}

	public void load(Queue<Cargo> cargos) {

		this.head = this.tail = new Carriage(this.carCapacity);
		this.length = 1;

		while (!cargos.isEmpty()) {

			Cargo pack = cargos.remove();
			if (this.head.getEmpty() >= pack.getSize()) {
				this.head.push(pack);
			} else {

				Carriage current = this.head.getNext();
				while (current != null && current.getEmpty() < pack.getSize()) {
					current = current.getNext();
				}
				if (current != null) {
					current.push(pack);
				} else {
					Carriage newc = new Carriage(carCapacity);

					this.tail.setNext(newc);
					newc.setPrev(tail);
					tail = newc;
					this.length++;

					if (tail.getEmpty() >= pack.getSize()) {
						tail.push(pack);
					}

				}
			}
		}

		if (head.isEmpty()) {
			head = tail = null;
			this.length = 0;
		}
	}

	public void unload(Queue<Cargo> cargos) {
		Carriage current = this.head;
		while (current != null) {

			// cargos.addAll(current.getCargos());

			while (!current.isEmpty()) {
				cargos.add(current.pop());
			}

			current = current.getNext();

		}
	}

	public int getLength() {
		return this.length;
	}

}
