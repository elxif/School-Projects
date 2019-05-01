package books;

import librarymembers.*;
import interfaces.ReadInLibrary;

/**
 * @author Elif Nur Akalin
 * 
 *         Represents the handwritten books in the library. They can only be
 *         read in the library, and only by academic members.
 * 
 *         A subclass of Book, uses the ReadInLibrary interface.
 *
 */
public class Handwritten extends Book implements ReadInLibrary {
	/**
	 * Creates a new handwritten book with the given ID, calling the superclass.
	 * 
	 * @param bookID is the ID number specific to this book.
	 */
	public Handwritten(int bookID) {
		super(bookID, "H");
	}

	/**
	 * Reads this book in the library, adds it to the member's history if they
	 * haven't read it before, sets it unavailable.
	 * 
	 * @param member is the library member who wants to read this book.
	 */
	public void readBook(LibraryMember member) {

		if (!member.ifAlreadyRead(this)) {
			member.addToHistory(this);
		}
		this.whoHas = member;
		this.isTaken = true;

	}

	/**
	 * Returns this book after reading it in the library, setting it available
	 * again.
	 * 
	 * @param member is the library member that wants to return this book.
	 */
	public void returnBook(LibraryMember member) {
		this.isTaken = false;
		this.whoHas = null;
	}
}