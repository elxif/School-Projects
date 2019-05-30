package CargoTrain;

import java.util.Stack;
import Util.*;

public class Carriage {
	private int capacity;
	private int emptySlot;
	private Stack<Cargo> cargos;
	private Carriage prev;
	private Carriage next;

	public Carriage(int capacity) {
		this.capacity = capacity;
		this.emptySlot = capacity;
		this.cargos = new Stack<Cargo>();
	}

	public boolean isFull() {
		return this.emptySlot == 0;
	}

	public boolean isEmpty() {
		return this.emptySlot == this.capacity;
	}

	public Carriage getPrev() {
		return this.prev;
	}

	public Carriage getNext() {
		return this.next;
	}

	public void setPrev(Carriage c) {
		this.prev = c;
	}

	public void setNext(Carriage c) {
		this.next = c;
	}

	public Stack<Cargo> getCargos() {
		return this.cargos;
	}

	public int getEmpty() {
		return this.emptySlot;
	}

	// implement: if the stack is full, add to the other stack
	public void push(Cargo cargo) {

		/*
		 * if (!this.isFull()) { this.cargos.push(cargo); this.emptySlot--; } else { if
		 * (this.next != null) this.next.push(cargo); else { this.next = new
		 * Carriage(this.capacity); }
		 * 
		 * }
		 * 
		 */

		this.cargos.push(cargo);
		this.emptySlot -= cargo.getSize();

	}

	public Cargo pop() {
		Cargo pack = this.cargos.pop();
		this.emptySlot += pack.getSize();
		return pack;

	}

}