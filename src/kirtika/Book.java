package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
//import kirtika.StringUtils.StringReplace;
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
	 * Sets values based on inputs given in the GUI.
	 * 
	 * @param list Values
	 * list[0] = bookName
	 * list[1] = writers
	 * list[2] = bookLanguage
	 * list[3] = bookPublisher
	 * list[4] = isbn
	 * list[5] = bookClassification
	 */
	public void setGuiValues(ArrayList<String> list) {
		bookName = list.get(0);
		writers = list.get(1);
		bookLanguage = list.get(2);
		bookPublisher = list.get(3);
		
		try {
			bookRelaseYear = Integer.parseInt(list.get(4));
		} catch (NumberFormatException e) {
			bookRelaseYear = 0;
		}
		
		isbn = list.get(5);
		bookClassification = list.get(6);
		bookLoaned = false;
		bookRead = false;
		setBookId();
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
	
	public void setFileName() {
		String fName = bookId + bookName;
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
	
	public static void main(String[] args) {
		/*
		try {
		    String filePath = "src/data/book/1odysseia.txt"; // replace with your relative file path
		    File file = new File(filePath);
		    Scanner scanner = new Scanner(file);
		    
		    while (scanner.hasNextLine()) {
		        String line = scanner.nextLine();
		        System.out.println(line);
		    }
		    
		    scanner.close();
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
		*/
		Book b = new Book();
		String s = "h\\irtettyjen kettujen metsä";
		//String c = StringUtils.replaceScandics(s);
	}
}
