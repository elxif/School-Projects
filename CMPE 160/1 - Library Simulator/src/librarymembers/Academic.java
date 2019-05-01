package librarymembers;

import java.util.ArrayList;

import books.Book;

/**
 * @author Elif Nur Akalin
 * 
 *         Represents an academic registered as a member in a library. An
 *         academic can read the handwritten books in a library, in addition to
 *         the things other members can do.
 * 
 * 
 *         A subclass of the LibraryMember class.
 */
public class Academic extends LibraryMember {

	/**
	 * Creates a new academic with the given id, calling the superclass. An
	 * academic's maximum number of books that can be borrowed a the same time is
	 * 20, their maximum number of ticks that a book can be borrowed for is 50, and
	 * their member type is A, standing for academic.
	 * 
	 * @param id is the id number of the member.
	 */
	public Academic(int id) {
		super(id, 20, 50, "A");
	}

	/**
	 * Gets the history of this academic.
	 * 
	 * @return this academic's history of all read books.
	 */
	public ArrayList<Book> getTheHistory() {
		return this.history;
	}

	/**
	 * Adds a book to this member's history of all read books. This book can be
	 * handwritten or printed. Implements the abstract method of the class
	 * LibraryMember.
	 * 
	 * @param book is the book that is added to this academic's history.
	 */
	public void addToHistory(Book book) {
		this.history.add(book);
	}

}