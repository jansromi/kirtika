package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;


/**
 * 
 * @author Jansromi
 *
 */
public class Books {
	private static int       maxItems		= 10;
	private int 				amt			= 0;
	private String        booksFilePath     = "src/data/kirjat.dat";
	private Book             items[]      = new Book[maxItems];
	
	public Books() {
		try {
			initBooks();
		} catch (FileNotFoundException e) {
			System.err.println("Tiedostoa kirjat.dat ei löytynyt");
			try {
	            File newFile = new File(booksFilePath);
	            newFile.createNewFile();
	            System.out.println("Created new file: " + booksFilePath);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		}
	}
	
	/**
	 * Test constructor
	 * @param b
	 */
	public Books(boolean b){
		
	}
	
	/**
	 * Initialize the collection from a file.
	 * 
	 * @throws FileNotFoundException if the file cannot be found.
	 * */
	private void initBooks() throws FileNotFoundException {
		File f = new File(booksFilePath);
		Scanner scan = new Scanner(f);
		
		int i = 0;
		while (scan.hasNextLine()) {
			   String line = scan.nextLine();
			   // If the array is full, create a new array.
			   if (amt == maxItems) resizeArray();
			   items[i] = new Book(line);
			   i++;
			   amt++;

			}
		scan.close();
	}
	
	/**
	 * If the old items array is full,
	 * create a new array that is twice as large as the old one.
	 * Copy the references to the new array.
	 * 
	 * @example
	 * <pre name="test">
	 * Books books = new Books(true);
	 * Book book = new Book();
	 * book.setOdysseia();
	 * books.getItemsLength() === 10;
	 * books.addItems(book, 10);
	 * books.getAmt() === 10;
	 * books.getItemsLength() === 10;
	 * books.addBook(book);
	 * books.getItemsLength() === 20;
	 * </pre>
	 * */
	public void resizeArray() {
		maxItems = maxItems * 2;
		Book[] newItems = new Book[maxItems];
		for (int i = 0; i < items.length; i++) {
			newItems[i] = items[i];
		}
		this.items = newItems;
	}
	
	/**
	 * Removes a book from the list and shifts all subsequent elements one index back
	 * @param book to be removed
	 * 
	 * @example
	 * <pre name="test">
	 * Books books = new Books(true);
	 * Book book1 = new Book();
	 * book1.setOdysseia();
	 * books.addItems(book1, 3);
	 * Book book2 = new Book();
	 * book2.setOdysseia();
	 * books.addBook(book2);
	 * books.get(3) === book2;
	 * books.addItems(book1, 3);
	 * books.deleteBook(book2);
	 * books.get(3) === book1;
	 * </pre>
	 */
	public void deleteBook(Book book) {
		deleteBookNotes(book);
		for (int i = 0; i < this.getAmt(); i++) {
			if (this.get(i).matchesId(book.getBookId())) {
				int j = i;
				while (j < this.getAmt() - 1) {
					items[j] = items[j + 1];
					j++;
				}
				items[j] = null;
				amt--;
			}
		}
	}
	
	/**
	 * Retrieves the given books associated note text file.
	 * If file is not found, creates a new one.
	 * @param book
	 * @return Book note text file as a string
	 * 
	 * @example
	 * <pre name="test">
	 * #THROWS IOException
	 * #import java.io.IOException;
	 * 
	 * Books books = new Books(true);
	 * Book book = new Book();
	 * book.setOdysseia();
	 * books.addBook(book);
	 * books.getBookNotes(book) === "";
	 * books.saveBookNotes(book, "Nymfit najadit Zeun tyttäret!");
	 * books.getBookNotes(book) === "Nymfit najadit Zeun tyttäret!";
	 * books.deleteBookNotes(book);
	 * </pre>
	 */
	public String getBookNotes(Book book) {
		String noteFilePath = book.getFilePath();
	    StringBuilder sb = new StringBuilder();

	    try (Scanner scanner = new Scanner(new File(noteFilePath))) {
	        while (scanner.hasNextLine()) {
	            sb.append(scanner.nextLine());
	        }
	    } catch (FileNotFoundException e) {
	        try {
	            File newFile = new File(noteFilePath);
	            newFile.createNewFile();
	            System.out.println("Created new file: " + noteFilePath);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	    
	    return sb.toString();
	}
	
	/**
	 * Removes the associated Book note txt-file
	 * from file path
	 * 
	 * @example
	 * <pre name="test">
	 * Books books = new Books();
	 * </pre>
	 */
	public void deleteBookNotes(Book book) {
		 File file = new File(book.getFilePath());
		 boolean b = file.delete();
	        if (b) {
	            System.out.println("File deleted successfully.");
	        } else {
	            System.out.println("Failed to delete file.");
	        }
		
	}
	
	/**
	 * Overwrites the books associated note file with given string.
	 * @param book
	 * @param notes String to replace contents in text file
	 * @throws IOException If writing failed
	 */
	public void saveBookNotes(Book book, String notes) throws IOException {
	    String filePath = book.getFilePath();

	    try (FileWriter writer = new FileWriter(filePath)) {
	        writer.write(notes);
	        System.out.println("Successfully saved notes to file: " + filePath);
	    }
	}

	/**
	 * Saves the books to a file
	 * @throws SailoException  if saving fails
	 */
	public void save() throws SailoException {
		File ftied = new File("C:/kurssit/ohj2/kirtika/src/data/" + booksFilePath);
		try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
			for (int i = 0; i < this.getAmt(); i++) {
				Book book = this.get(i);
				fo.println(book.toString());
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("tiedosto: " + ftied.getAbsolutePath() + " ei aukea");
		}
	}
	
	/**
	 * Adds a book to the registry.
	 * Increases amt by one.
	 * @param book (book) to be added
	 * 
	 * TODO: Exception handling
	 * @example
	 * <pre name="test">
	 * Books kirjat = new Books();
	 * Book ody = new Book();
	 * int x = kirjat.getAmt();
	 * kirjat.addBook(ody);
	 * kirjat.getAmt() === x + 1;
	 * </pre>
	 */
	public void addBook(Book book){
		if (amt >= maxItems) resizeArray();
		items[amt] = book;
		amt++;
		
	}
	
	/**
	 * Sets the value for bookLoaned.
	 * @param bId Book id
	 * @param b Boolean
	 */
	public void setBookLoaned(int bId, boolean b) {
		for (Book book : items) {
			if (book.matchesId(bId)) {
				book.setBookLoaned(b);
				return;
			}
		}
	}
	/**
	 * Iterates the items and returns the boolean bookLoaned of the matching book.
	 * @param bId
	 * @return boolean bookLoaned
	 */
	public boolean getBookLoaned(int bId) {
		for (Book book : items) {
			if (book.matchesId(bId)) return book.bookLoaned();
		}
		return false;
	}
	
	/**
	 * Returns the file name
	 * @return file name
	 */
	public String getFileName() {
		return this.booksFilePath;
	}
	
	/**
	 * Returns a reference to a book object
	 * @param i
	 * @return reference to the book object with index i
	 * @throws IndexOutOfBoundsException
	 */
	public Book get(int i) throws IndexOutOfBoundsException{
		if (i < 0 || amt <= i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return items[i];
	}
	
	/**
	 * @param bId book ID
	 * @return the name of the book found with the book ID.
	 * 		   null if not found
	 */
	public String getBookName(int bId) {
		for (Book book : items) {
			if (book.matchesId(bId)) return book.getBookName();
		}
		return null;
	}
	
	/**
	 * Returns the book's field information as an array
	 * @param i
	 * @return
	 */
	public String[] getBookInfo(int bId) {
		for (Book book : items) {
			if (book.matchesId(bId)) return book.getBookInfo();
		}
		return null;
	}
	
	// TEST METHODS
	
	/**
	 * @return the length of items-array
	 */
	public int getItemsLength() {
		return items.length;
	}
	
	/**
	 * Add n-amount of given book into items-array
	 * @param book
	 * @param n
	 */
	public void addItems(Book book, int n) {
		for (int i = 0; i < n; i++) {
			addBook(book);
		}
	}
	
	/**
	 * @return Amount of books
	 */
	public int getAmt() {
		return this.amt;
	}
}
