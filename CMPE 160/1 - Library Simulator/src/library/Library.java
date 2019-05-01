package library;

import books.*;
import librarymembers.*;
import java.util.Scanner;

/**
 * @author Elif Nur Akalin
 * 
 *         Represents a library. It holds books and members. Checks if the given
 *         operations are valid, if they are, it calls them.
 *
 */
public class Library {

	/**
	 * The amount of fees paid to this library for not returning a book by its
	 * deadline.
	 */
	private int totalFee;
	/**
	 * The list of all books in this library.
	 */
	private Book[] books;
	/**
	 * The list of all members in this library.
	 */
	private LibraryMember[] members;
	/**
	 * The scanner that reads the input for the operations that must be done in this
	 * library.
	 */
	private Scanner scanner;
	/**
	 * Total quantity of books in this library.
	 */
	private int totalBooks;
	/**
	 * Total quantity of members in this library.
	 */
	private int totalMembers;

	/**
	 * Creates a new library with the given scanner.
	 * 
	 * @param scanner
	 */
	public Library(Scanner scanner) {

		this.books = new Book[1000000];
		this.members = new LibraryMember[1000000];
		this.scanner = scanner;
		this.totalBooks = 0;
		this.totalMembers = 0;
		this.totalFee = 0;
	}

	public Book[] getBooks() {
		return this.books;
	}

	public LibraryMember[] getMembers() {
		return this.members;
	}

	/**
	 * Creates a new book. Checks if the total books in this library does not exceed
	 * the limit. Checks if the book to be added is handwritten or printed. Then
	 * increases total book number by 1. Adds the book to the books array.
	 */
	public void addBook() {
		String bookType = this.scanner.next().toUpperCase();
		if (totalBooks < 999999) {
			if (bookType.equals("H")) {
				Handwritten book = new Handwritten(totalBooks + 1);
				books[book.getID() - 1] = book;
				totalBooks++;
			} else if (bookType.equals("P")) {
				Printed book = new Printed(totalBooks + 1);
				books[book.getID() - 1] = book;
				totalBooks++;
			}
		}
	}

	/**
	 * Creates a new member. Checks if the total members in this library does not
	 * exceed the limit. Checks if the member to be added is academic or student.
	 * Then increases total member number by 1. Adds the member to the members
	 * array.
	 */
	public void addMember() {
		String memberType = this.scanner.next().toUpperCase();
		if (totalBooks < 999999) {
			if (memberType.equals("A")) {
				LibraryMember member = new Academic(totalMembers + 1);
				members[member.getID() - 1] = member;
				totalMembers++;
			} else if (memberType.equals("S")) {
				LibraryMember member = new Student(totalMembers + 1);
				members[member.getID() - 1] = member;
				totalMembers++;
			}
		}
	}

	/**
	 * Lets a member borrow a book. Checks if the book is available, if the book and
	 * the member IDs are valid. Checks if the book is handwritten or printed.
	 * Checks if the member that wants to borrow the book has any books that are due
	 * date and not returned.
	 * 
	 * @param tick the time of this event
	 */

	public void borrowBook(int tick) {

		int bookID = this.scanner.nextInt();
		int memID = this.scanner.nextInt();
		Book book = books[bookID - 1];
		LibraryMember member = members[memID - 1];
		if (totalBooks < bookID || totalMembers < memID)
			return;
		boolean canBorrow = true;
		for (int i = 0; i < member.getCurrentlyHolding().size(); i++) {
			Book currentBook = member.getCurrentlyHolding().get(i);
			if (currentBook.getBookType().equals("P")) {
				if (((Printed) currentBook).getDeadLine() < tick) {
					canBorrow = false;
					break;
				}
			}
		}
		if (canBorrow && book.getBookType().equals("P") && !book.ifTaken()
				&& member.getCurrentBooks() < member.getMaxNumberOfBooks()) {
			((Printed) book).borrowBook(member, tick);
		}
	}

	/**
	 * Lets a member return a book to the library. If it is borrowed, checks if the
	 * deadline has passed. If so, takes a fee from the member that returned the
	 * book. Checks if the book is taken, if the book and the member IDs are valid,
	 * if the member returning it is the same member as the one that borrowed it.
	 * 
	 * @param tick the time of this event
	 */
	public void returnBook(int tick) {
		int bookID = this.scanner.nextInt();
		int memID = this.scanner.nextInt();
		if (totalBooks < bookID || totalMembers < memID)
			return;
		LibraryMember member = members[memID - 1];
		if (books[bookID - 1].ifTaken() && books[bookID - 1].getBookType().equals("P")) {
			Printed book = (Printed) books[bookID - 1];
			if (book.getWhoHas() != null && book.getWhoHas() == member) {
				if (!book.ifReadInLibrary()) {
					if (book.getDeadLine() < tick) {
						this.totalFee += tick - book.getDeadLine();
					}
				}
				book.returnBook(member);
			}
		} else if (books[bookID - 1].ifTaken() && books[bookID - 1].getBookType().equals("P")) {
			Handwritten book = (Handwritten) books[bookID - 1];
			if (book.getWhoHas() != null && book.getWhoHas() == member) {
				book.returnBook(member);
			}
		}
	}

	/**
	 * Lets a member to extend a book's deadline if it is not extended already, or
	 * the deadline has not passed. Checks if the book is taken, if it is printed,
	 * if the book and the member IDs are valid.
	 * 
	 * @param tick the time of this event
	 */
	public void extendBook(int tick) {

		int bookID = this.scanner.nextInt();
		int memID = this.scanner.nextInt();
		if (totalBooks < bookID || totalMembers < memID)
			return;
		LibraryMember member = members[memID - 1];
		if (books[bookID - 1].ifTaken() && books[bookID - 1].getBookType().equals("P")) {
			Printed book = (Printed) books[bookID - 1];
			if (book.getWhoHas() == member && !book.ifReadInLibrary() && !book.getIfExtended()
					&& tick <= book.getDeadLine()) {
				book.extend(member, tick);
			}
		}
	}

	/**
	 * Lets a member read a book in the library. Checks if the book is available, if
	 * the book and the member IDs are valid. Checks if the book is handwritten,
	 * then checks if the member that wants to read it is an academic.
	 */
	public void readInLibrary() {

		int bookID = this.scanner.nextInt();
		int memID = this.scanner.nextInt();
		if (totalBooks < bookID || totalMembers < memID)
			return;
		Book book = books[bookID - 1];
		LibraryMember member = members[memID - 1];

		if (!book.ifTaken()) {
			if (book.getBookType().equals("H") && member.getMemberType().equals("A")) {
				((Handwritten) book).readBook(member);
			}
			if (book.getBookType().equals("P")) {
				((Printed) book).readBook(member);
			}
		}

	}

	/**
	 * Gets the total fees paid to this library.
	 * 
	 * @return total fees paid to this library.
	 */
	public int getTotalFee() {
		return this.totalFee;
	}

}