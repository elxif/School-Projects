package books;

import librarymembers.*;

/**
 * @author Elif Nur Akalin
 * 
 *         Represents the books in a library. Extended by Printed and
 *         Handwritten classes, which are the two types of books.
 * 
 *         Every book can be read in the library and be returned.
 *
 */
public abstract class Book {
	/**
	 * The ID number of this book.
	 */
	protected int bookID;
	/**
	 * The type of this book, indicates it is whether printed or handwritten.
	 */
	protected String bookType;
	/**
	 * Indicates if this book is available. If a book is borrowed or is being read
	 * in the library, it is taken.
	 */
	protected boolean isTaken;
	/**
	 * Indicates the member that is holding this book. If this book is available to
	 * read, this parameter is null.
	 */
	protected LibraryMember whoHas;

	/**
	 * Creates a new book with the given ID and the type. Starts with the member
	 * holding it set null, not taken, and not read in library.
	 * 
	 * @param bookID   is the ID number specific to this book.
	 * @param bookType is the type determining if this book is printed or
	 *                 handwritten.
	 */
	public Book(int bookID, String bookType) {
		this.bookID = bookID;
		this.bookType = bookType;
		this.isTaken = false;
		this.whoHas = null;
	}

	/**
	 * Returns the taken book to the library. It is abstract, and it is implemented
	 * in the subclasses.
	 * 
	 * @param member is the specific library member who was reading this book.
	 */
	public abstract void returnBook(LibraryMember member);

	/**
	 * Gets the ID of this book.
	 * 
	 * @return this book's ID number.
	 */
	public int getID() {
		return this.bookID;
	}

	/**
	 * Gets the type of this book.
	 * 
	 * @return this book's type.
	 */
	public String getBookType() {
		return this.bookType;
	}

	/**
	 * Gets whether this book is available or not.
	 * 
	 * @return this book's unavailability.
	 */
	public boolean ifTaken() {
		return this.isTaken;
	}

	/**
	 * Changes the unavailability of this book.
	 * 
	 * @param taken This book's unavailability.
	 */
	public void setTaken(boolean taken) {
		this.isTaken = taken;
	}

	/**
	 * Gets this book's holder at the moment.
	 * 
	 * @return the holder of this book.
	 */
	public LibraryMember getWhoHas() {
		return this.whoHas;
	}

	/**
	 * Changes this book's holder.
	 * 
	 * @param member The holder of this book. Must be a library member or null.
	 */
	public void setWhoHas(LibraryMember member) {
		this.whoHas = member;
	}

}