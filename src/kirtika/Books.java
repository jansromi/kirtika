package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
	private String        fileName     = "kirjat.dat";
	private Book             items[]      = new Book[maxItems];
	
	public Books() {
		try {
			initBooks();
		} catch (FileNotFoundException e) {
			System.err.println("Tiedostoa kirjat.dat ei löytynyt");
			// TODO: uusi tiedosto
		}
	}
	
	/**
	 * Initialize the collection from a file.
	 * 
	 * @throws FileNotFoundException if the file cannot be found.
	 * */
	private void initBooks() throws FileNotFoundException {
		File f = new File("C:/kurssit/ohj2/kirtika/src/data/" + fileName);
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
	 * TODO: write tests for this method
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
	 * Removes a book from the list and shifts all subsequent elements one index back..
	 * @param book to be removed
	 */
	public void deleteBook(Book book) {
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
	 * Saves the books to a file
	 * @throws SailoException  if saving fails
	 */
	public void save() throws SailoException {
		File ftied = new File("C:/kurssit/ohj2/kirtika/src/data/" + fileName);
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

	//
	// =========== Getters / Setters ==========
	//
	
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
		return this.fileName;
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
	
	/**
	 * @return Amount of books
	 */
	public int getAmt() {
		return this.amt;
	}
	
	
}
