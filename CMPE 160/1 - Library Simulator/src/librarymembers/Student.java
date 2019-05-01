package librarymembers;

import java.util.ArrayList;

import books.*;

/**
 * @author Elif Nur Akalin
 * 
 *         Represents a student registered in a library. A student can read or
 *         borrow only printed books.
 * 
 *         A subclass of the LibraryMember class.
 *
 */
public class Student extends LibraryMember {

	/**
	 * Creates a new student with the given id, calling the superclass. A student's
	 * maximum number of books that can be borrowed a the same time is 10, their
	 * maximum number of ticks that a book can be borrowed for is 20, and their
	 * member type is S, standing for student.
	 * 
	 * @param id is the id number of the member.
	 */
	public Student(int id) {
		super(id, 10, 20, "S");
	}

	/**
	 * Gets the history of this student.
	 * 
	 * @return this student's history of all read books.
	 */
	public ArrayList<Book> getTheHistory() {
		return this.history;
	}

	/**
	 * Adds a book to this member's history of all read books. This book can be
	 * handwritten or printed. Implements the abstract method of the class
	 * LibraryMember.
	 * 
	 * @param book is the book that is added to this student's history.
	 */
	public void addToHistory(Book book) {
		this.history.add(book);
	}

}