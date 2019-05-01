package librarymembers;

import books.*;
import java.util.ArrayList;

/**
 * @author Elif Nur Akalin
 * 
 *         Represents a member that is registered in a library. A member can
 *         read a book in the library, borrow it to take out of the library,
 *         return it back under certain conditions.
 *
 */
public abstract class LibraryMember {
	/**
	 * The id number of this member.
	 */
	protected int id;
	/**
	 * The maximum number of books that can be borrowed a the same time by this
	 * member.
	 */
	protected int maxNumberOfBooks;
	/**
	 * The maximum number of ticks that a book can be borrowed for by this member.
	 */
	protected int timeLimit;
	/**
	 * The list that holds the history of all the books that are read in the library
	 * or borrowed by this member.
	 */
	protected ArrayList<Book> history;
	/**
	 * The list that holds all the books that this member has borrowed and not
	 * returned.
	 */
	protected ArrayList<Book> currentlyHolding;
	/**
	 * The number of all the books that this member has borrowed and not returned.
	 */
	protected int currentBooks;
	/**
	 * The type of this member, "A" for academic or "S" for student.
	 */
	protected String memberType;

	/**
	 * Creates a new member with given parameters. Creates two new ArrayLists for
	 * this member's history and currently borrowed books.
	 * 
	 * @param id               the id number of this member.
	 * @param maxNumberOfBooks is the maximum number of books that can be borrowed a
	 *                         the same time by this member.
	 * @param timeLimit        is the maximum number of ticks that a book can be
	 *                         borrowed for by this member.
	 * @param memberType       is the type of this member, "A" for academic or "S"
	 *                         for student.
	 */
	public LibraryMember(int id, int maxNumberOfBooks, int timeLimit, String memberType) {
		this.id = id;
		this.currentBooks = 0;
		this.currentlyHolding = new ArrayList<Book>();
		this.history = new ArrayList<Book>();

	}

	/**
	 * An abstract method that must be implemented in subclasses. Gets the history
	 * of this member.
	 * 
	 * @return this member's history of all read books.
	 */
	public abstract ArrayList<Book> getTheHistory();

	/**
	 * An abstract method that must be implemented in subclasses. Adds a book to
	 * this member's history of all read books.
	 * 
	 * @param book is the book that is added to this member's history.
	 */
	public abstract void addToHistory(Book book);

	public int getTimeLimit() {
		return this.timeLimit;
	}

	public int getID() {
		return this.id;
	}

	public int getCurrentBooks() {
		return this.currentBooks;
	}

	public int getMaxNumberOfBooks() {
		return this.maxNumberOfBooks;
	}

	public void setCurrentBooks(int currentBooks) {
		this.currentBooks = currentBooks;
	}

	public String getMemberType() {
		return this.memberType;
	}

	/**
	 * Checks if the given book was read by this member.
	 * 
	 * @param book is the book to be checked.
	 * @return if the given book was read by this member.
	 */
	public boolean ifAlreadyRead(Book book) {
		return this.history.contains(book);
	}

	/**
	 * @return
	 */
	public ArrayList<Book> getCurrentlyHolding() {
		return this.currentlyHolding;
	}

	/**
	 * Adds the given book to this member's currently held books arraylist.
	 * 
	 * @param book
	 */
	public void holdBook(Book book) {
		this.currentlyHolding.add(book);
	}

	/**
	 * Removes the given book from this member's currently held books arraylist.
	 * 
	 * @param book is the book to be returned
	 */
	public void releaseBook(Book book) {
		this.currentlyHolding.remove(book);
	}

}