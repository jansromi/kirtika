package kirtika;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * 
 * @author Jansromi
 * @version 20.2.2023
 *
 */
public class Book {
	
	private int bookId;
	private String isbn = "";
	private String writers = "";
	private String bookName = "";
	private String bookLanguage = "";
	private String bookPublisher = "";
	private int bookRelaseYear;
	private String bookClassification = "";
	private boolean bookLoaned, bookRead;
	private String bookInfoPath = "";
	
	private static int nextBookId = 1;
	
	/**
	 * Default constructor
	 */
	public Book() {
		
	}
	
	/**
	 * Parsing constructor
	 * @param line
	 */
	public Book(String line) {
		StringBuilder sb = new StringBuilder(line);
		this.setBookId(Integer.parseInt(Mjonot.erota(sb, '|')));
		isbn = Mjonot.erota(sb, '|');
		writers = Mjonot.erota(sb, '|');
		bookName = Mjonot.erota(sb, '|');
		bookLanguage = Mjonot.erota(sb, '|');
		bookPublisher = Mjonot.erota(sb, '|');
		bookRelaseYear = Integer.parseInt(Mjonot.erota(sb, '|'));
		bookClassification = Mjonot.erota(sb, '|');
		bookLoaned = Boolean.parseBoolean(Mjonot.erota(sb, '|'));
		bookRead = Boolean.getBoolean(Mjonot.erota(sb, '|'));
		bookInfoPath = Mjonot.erota(sb, '|');
	}
	
	/**
	 * Override toString-method
	 */
	@Override
	public String toString() {
		return bookId + "|" +
				isbn + "|" +
				writers + "|" +
				bookName + "|" +
				bookLanguage + "|" +
				bookPublisher + "|" +
				bookRelaseYear + "|" +
				bookClassification + "|" +
				bookLoaned + "|" +
				bookRead + "|" +
				bookInfoPath;
		
	}
	
	/**
	 * Test method
	 */
	public void setOdysseia() {
		setBookId();
		isbn = "9789511318866";
		writers = "Homeros; Saarikoski, Pentti";
		bookName = "Odysseia" + " " + getRnd();
		bookLanguage = "Suomi";
		bookPublisher = "Kustannusosakeyhtiö Otava";
		bookRelaseYear = 2017;
		bookClassification = "81.4";
		bookRead = true;
		bookInfoPath =  "...\\kirtika\\text\\1_info.txt";
	}
	
	/**
	 * @return Book-Id
	 */
	public int getBookId() {
		return bookId;
	}
	

	/**
	 * @return Name of the book
	 */
	public String getBookName() {
		return bookName;
	}
	
	/**
	 * Is the book's ID equal to i
	 * @param i
	 * @return true if same, false if not
	 */
	public boolean matchesId(int i) {
		return bookId == i;
	}
	
	/**
	 * @return Isbn as a String
	 * @example
	 * <pre name="test">
	 * Book kirja1 = new Book();
	 * kirja1.setOdysseia();
	 * kirja1.getIsbn() === "9789511318866";
	 * </pre>
	 */
	public String getIsbn() {
		return isbn;
	}
	
	/**
	 * Sets the book's ID and ensures that future IDs are larger.
	 */
	private void setBookId(int id) {
		bookId = id;
		if (bookId >= nextBookId) nextBookId = id + 1;
	}
	
	/**
	 * Asetaan kirjalle id
	 * @return kirjalle annettu id
	 * @example
	 * <pre name="test">
	 * Book book1 = new Book();
	 * Book book2 = new Book();
	 * int id1 = book1.setBookId();
	 * int id2 = book2.setBookId();
	 * id1 === id2 - 1;
	 * </pre>
	 */
	public int setBookId() {
		bookId = nextBookId;
		nextBookId++;
		return bookId;
	}
	
	/**
	 * Sets the bookLoaned-param.
	 * @param b Boolean
	 */
	public void setBookLoaned(boolean b) {
		bookLoaned = b;
	}
	
	/**
	 * Prints the book's information.
	 * @param out The output stream for printing.
	 */
	public void printBook(PrintStream out) {
		out.println("Kirjan ID: " + bookId);
		out.println("Kirjan ISBN: " + isbn);
		out.println("Kirjan nimi: " + bookName);
		out.println("Kirjailija: " + writers);
		out.println("Kirjan kieli: " + bookLanguage);
		out.println("Kirjan bookPublisher: " + bookPublisher);
		out.println("Kirjan bookRelaseYear: " + bookRelaseYear);
		out.println("Kirjan bookClassification: " + bookClassification);
	}
	
	/**
	 * Prints the book's information.
	 * @param os
	 */
	public void printBook(OutputStream os) {
		printBook(new PrintStream(os));
	}
	
	/**
	 * Returns relevant information about the book for textfields.
	 * @return String[6]
	 */
	public String[] getBookInfo() {
		String[] s = new String[6];
		s[0] = writers;
		s[1] = bookLanguage;
		s[2] = bookPublisher;
		s[3] = Integer.toString(bookRelaseYear);
		s[4] = bookClassification;
		s[5] = isbn;
		return s;
	}
	
	/**
	 * @return boolean bookLoaned
	 */
	public boolean bookLoaned() {
		return bookLoaned;
	}
	
	/**
	 * Generates random numbers for tests
	 */
	private int getRnd() {
		Random rnd = new Random();
		return rnd.nextInt(99);
	}
}
