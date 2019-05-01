package books;

import interfaces.*;
import interfaces.ReadInLibrary;
import librarymembers.*;

/**
 * @author Elif Nur Akalin
 * 
 *         Represents the printed books in the library. They can be read in the
 *         library, brought home and extended by students and academic members.
 * 
 *         A subclass of Book, uses the ReadInLibrary and Borrow interfaces.
 *
 */
public class Printed extends Book implements ReadInLibrary, Borrow {
	/**
	 * The deadline of this book, the time until when it must be returned.
	 */
	private int deadLine;
	/**
	 * The indicator whether this book's deadline is extended.
	 */
	private boolean isExtended;
	/**
	 * Indicates if this book is being read in the library at the moment.
	 */
	protected boolean isReadInLibrary;

	/**
	 * Creates a new printed book with the given ID, calling the superclass. Starts
	 * with the deadline set to 0 and not extended.
	 * 
	 * @param bookID is the ID number specific to this book.
	 */
	public Printed(int bookID) {
		super(bookID, "P");
		this.deadLine = 0;
		this.isExtended = false;
	}

	/**
	 * Reads this book in the library, adds it to the member's history if they
	 * haven't read it before, sets it unavailable.
	 * 
	 * @param member is the library member who wants to read this book.
	 */
	public void readBook(LibraryMember member) {
		this.whoHas = member;
		this.isTaken = true;
		if (!member.ifAlreadyRead(this)) {
			member.addToHistory(this);
		}
		isReadInLibrary = true;

	}

	/**
	 * Gets if this book was read in the library.
	 * 
	 * @return if this book was read in the library.
	 */
	public boolean ifReadInLibrary() {
		return this.isReadInLibrary;
	}

	/**
	 * Borrows this book to take home with given member and tick. Sets the book's
	 * holder to the given member, adds it to the member's history if they haven't
	 * read it before, sets it unavailable, adds this book to the list that lists
	 * the books given member is currently holding. Sets this book's deadline to the
	 * ticks after this tick plus the maximum ticks this member type can borrow a
	 * book for. Increments the number of books that are held by the member by 1.
	 * 
	 * @param member is the library member that wants to borrow this book.
	 * @param tick   is a time unit, indicates the tick when this event happens.
	 */
	public void borrowBook(LibraryMember member, int tick) {
		this.whoHas = member;
		if (!member.ifAlreadyRead(this)) {
			member.addToHistory(this);
		}
		member.holdBook(this);
		this.isTaken = true;
		this.deadLine = tick + member.getTimeLimit();
		member.setCurrentBooks(member.getCurrentBooks() + 1);

	}

	/**
	 * Increments this book's deadline date by the member's time limit. Changes this
	 * book's indicator of being whether extended or not to true.
	 * 
	 * @param member is the library member that wants to extend this book.
	 * @param tick   is when this event happens
	 */
	public void extend(LibraryMember member, int tick) {
		this.deadLine += member.getTimeLimit();
		this.isExtended = true;

	}

	/**
	 * Returns this book after reading it in the library, setting it available
	 * again. Changes this book's holder to nobody, sets its deadline to 0, sets if
	 * it's extended to not, if it is read in library, changes it to not. If not,
	 * then it was borrowed, decreases the number of books the member has borrowed
	 * by 1.
	 * 
	 * @param member is the library member that wants to return this book.
	 */
	public void returnBook(LibraryMember member) {

		this.isTaken = false;
		this.whoHas = null;
		this.deadLine = 0;
		this.isExtended = false;
		member.releaseBook(this);
		if (isReadInLibrary) {
			this.isReadInLibrary = false;
		} else
			member.setCurrentBooks(member.getCurrentBooks() - 1);
	}

	/**
	 * Gets this book's deadline, the tick that it must be returned.
	 * 
	 * @return this book's deadline.
	 */
	public int getDeadLine() {
		return this.deadLine;
	}

	/**
	 * Gets if this book is extended or not.
	 * 
	 * @return if this book is extended or not.
	 */
	public boolean getIfExtended() {
		return this.isExtended;
	}

}