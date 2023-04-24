package kirtika;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.jsoup.nodes.Document;

import webscraping.FinnaHaku;
import webscraping.FinnaHaku.BookNotFoundException;
import webscraping.YKLQuery;

/**
 * Main class Kirtika consists mostly of mediator methods.
 * 
 * TODO: Search
 * TODO: If custom genre and "real" genre is set, override custom genre
 * @author Jansromi
 * @version 1, 10.4.2023
 * 
 */
public class Kirtika {
	private final Books books;
	private final Genres genres;
	private final Loans loans;
	
	/**
	 * Default constructor
	 */
	public Kirtika() {
		this.books = new Books();
		this.genres = new Genres();
		this.loans = new Loans();
		if (books.getAmt() > 0) setLoanedBookNames();
		
	}
	
	
	/**
	 * Adds a book to the registry
	 * @param book to be added
	 * @throws SailoException if adding fails
	 */
	public void addBook(Book book) throws SailoException {
		books.addBook(book);
	}
	
	/**
	 * Adds a new loan.
	 *
	 * @param book the loaned book
	 * @param loaner the name of the person who borrowed the book
	 */
	public void addBookLoan(Book book, String loaner) {
	    loans.addBookLoan(book, loaner);
	}
	
	/**
	 * Adds a user defined genre
	 * @param input where genre formatted as "id + desc", like
	 * "84.2 Suomenkielinen kaunokirjallisuus" or
	 * "9999 random"
	 * 
	 */
	public void addGenre(String input) {
		genres.addGenre(input);
	}
	
	/**
	 * A mediator method for deleting a book.
	 *
	 * @param book the book to be deleted
	 */
	public void deleteBook(Book book) {
	    books.deleteBook(book);
	}
	
	/**
	 * A mediator method for deleting a books 
	 * associated note text file.
	 * @param book the books which notes are deleted
	 * @return true if deletion was successful, false otherwise
	 */
	public boolean deleteBookNotes(Book book) {
		return books.deleteBookNotes(book);
	}


	/**
	 * Returns an array of relevant book information
	 * to be shown in GUI
	 * @param bId book id
	 * @return String[6] where
	 * s[0] = writers;
	 * s[1] = bookLanguage;
	 * s[2] = bookPublisher;
	 * s[3] = Integer.toString(bookRelaseYear);
	 * s[4] = bookClassification;
	 * s[5] = isbn;
	 * 
	 * @example
	 * <pre name="test">
	 * Kirtika kirtika = new Kirtika();
	 * String[] s = kirtika.getBookInfo(1);
	 * s[1] === "suomi";
	 * </pre>
	 */
	public String[] getBookInfo(int bId) {
	    String[] s = books.getBookInfo(bId);

	    // If genre description is found, 
	    // replace it with ykl description. Otherwise
	    // set as default.
	    // TODO: if-clause seems shitty. Genres should manage this,
	    // and set the desc
	    if (!genres.getYklDesc(s[5]).equals("Unknown")) {
	    	s[4] = genres.getYklDesc(s[5]);
	    }
	    
	    return s;
	}
	
	/**
	 * Fetches data from Finna API.
	 * @param isbn
	 * @return ArrayList of book data, where
	 * [0] == books title
	 * [1] == books main writer
	 * [2] == books language
	 * [3] == books publisher
	 * [4] == books release year
	 * [5] == books isbn
	 * [6] == YKL classification id
	 * @throws BookNotFoundException when book is not found
	 * 
	 * @example
	 * <pre name="test">
	 * #THROWS BookNotFoundException
	 * #import java.util.ArrayList;
	 * Kirtika kirtika = new Kirtika();
	 * ArrayList list = kirtika.fetchFinnaData("9789524953856");
	 * list.get(0) === "Talous ja moraali";
	 * list.get(1) === "Korkman, Sixten, 1948- kirjoittaja; ";
	 * list.get(2) === "fin; ";
	 * </pre>
	 */
	public ArrayList<String> fetchFinnaData(String isbn) throws BookNotFoundException{
		FinnaHaku fh = new FinnaHaku(isbn);
		fh.fetchBookData();
		ArrayList<String> data = new ArrayList<String>();
		data.add(fh.getBookTitle());
		data.add(fh.getBookWriter());
		data.add(fh.getBookLanguages());
		data.add(fh.getPublisher());
		data.add(fh.getPublicationDates());
		data.add(isbn);
		data.add(fh.getBookYKLClasses());
		return data;
	}
	
	/**
	 * Fetches all availible YKL-genres using web scraping.
	 * Iterates over YKL IDs from 0 to 99 and uses the yklQuery method to retrieve the HTML document for each main categories (0-99).
	 * Then it derives all subclasses from that main category.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void fetchFintoGenres() throws IOException, InterruptedException {
		Document doc;
			// hardcoded 100, because ykl-genres end in 99
			for (int i = 0; i < 100; i++) {
				doc = YKLQuery.yklQuery(String.valueOf(i));
				ArrayList<String> subGenres = YKLQuery.parseSubGenres(doc);
				for (String subgenre : subGenres) {
					genres.addGenre(subgenre);
				}
			}
	}
	
	/**
	 * Fetches the loan information on selected book id
	 * to be shown on GUI
	 * obj[0] = boolean bookLoaned
	 * obj[1] = String loanerName
	 * obj[2] = LocalDate when the loan was given
	 * obj[3] = LocalDate when the loan is supposed to be returned
	 * @param bId book id
	 * @return
	 */
	public Object[] getBookLoanInfo(int bId) {
		Object[] obj = new Object[4];
		Loan loan = loans.getActiveLoan(bId);
		if (loan != null) {
			// should always return true
			obj[0] = books.getBookLoaned(bId);
			obj[1] = loan.getLoanerName();
			obj[2] = loan.getLoanStartDate();
			obj[3] = loan.getLoanEndDate();
		} else {
			obj[0] = false;
			obj[1] = "";
			obj[2] = null;
			obj[3] = null;
		}
		return obj;
	}
	
	/**
	 * Gets the note text file associated with given book.
	 * If the text file is not found, it creates a new file
	 * @param book
	 * @return Text file as a string
	 * @throws IOException if note-file couldn't be created
	 */
	public String getBookNotes(Book book) throws IOException {
		return books.getBookNotes(book);
	}
	
	/**
	 * Sets the active loans return date, 
	 * and sets the loan as closed.
	 * Finally sets the bookLoaned-property as closed.
	 * @throws SailoException if the given bId had no accociated loan ID. 
	 *         Possible if one messes with the data.
	 */
	public void closeLoan(int bId, LocalDate returnDate) throws SailoException {
		Loan loan = loans.getActiveLoan(bId);
		if (loan == null) {
			throw new SailoException("Annetulla kirjalla ei ollut aktiivista lainaa!");
		}
		loan.setLoanReturnDate(returnDate);
		loan.setLoanStatus(true);;
		setBookAsLoaned(bId, false);
	}
	
	/**
	 * Deletes a loan from the registry.
	 *
	 * @param lId the ID of the loan to be deleted
	 */
	public void deleteLoan(int lId) {
	    loans.deleteLoan(lId);
	}
	
	/**
	 * Initializes the names of the loaned books in a loop, since they are only stored in the database by ID.
	 */
	public void setLoanedBookNames() {
	    for (int i = 0; i < loans.getLoansAmt(); i++) {
	        // Combines the book ID with its name
	    	Loan loan = loans.getLoan(i);
	        int loanedBookId = loan.getLoanedBookId();
	        String bookName = books.getBookName(loanedBookId);
	        
	        // if book has been deleted, but its still on loan record,
	        // delete the loan.
	        if (bookName == null) {
	        	loans.deleteLoan(loan.getLoanId());
	        	continue;
	        }
	        
	        loans.setLoanedBookName(loanedBookId, bookName);
	    }
	}

	/**
	 * Saves all data.
	 *
	 * @throws FileNotFoundException 
	 */
	public void saveAll() throws FileNotFoundException {
	    books.save();
	    loans.saveBookLoans();
	    genres.saveGenres();
	    
	}
	
	public void sortGenres() throws IOException {
		genres.sortGenres();
	}
	
	/**
	 * A mediator method for saving.
	 *
	 * @throws SailoException if saving fails
	 * @throws FileNotFoundException 
	 */
	public void save() throws FileNotFoundException {
	    books.save();
	}

	/**
	 * Saves loan data.
	 *
	 * @throws SailoException if saving fails
	 * @throws FileNotFoundException 
	 */
	public void saveBookLoans() throws FileNotFoundException {
	    loans.saveBookLoans();
	}
	
	public void saveBookNotes(Book book, String notes) throws IOException {
		books.saveBookNotes(book, notes);
	}
	
	public void saveGenres() throws FileNotFoundException {
		genres.saveGenres();
	}
	
	/**
	 * 
	 * @param yklId
	 * @return
	 */
	public boolean genresContain(String yklId) {
		return genres.genresContain(yklId);
	}
	
	public void deleteGenre(Genre genre) {
		genres.deleteGenre(genre);
	}
	
	/**
	 * A mediator method that returns a book with this index.
	 * @param i index
	 * @return book at this index
	 * @throws IndexOutOfBoundsException
	 */
	public Book getBook(int i) throws IndexOutOfBoundsException{
		return books.get(i);
	}
	
	/**
	 * Returns the number of books in the registry.
	 *
	 * @return the number of books in the registry
	 */
	public int getBooks() {
	    return books.getAmt();
	}
	
	/**
	 * Returns a reference to a loan by index
	 * @return reference to the loan
	 */
	public Loan getLoanedBook(int i) {
	    return loans.getLoanedBook(i);
	}

	/**
	 * @return the number of loaned books
	 */
	public int getLoansAmt() {
	    return loans.getLoansAmt();
	}

	/**
	 * Sets the given books loaned-status
	 * @param bId
	 */
	public void setBookAsLoaned(int bId, boolean b) {
		books.setBookLoaned(bId, b);
	}
	
	/**
	 * Sets the date when book was loaned
	 * @param bId
	 */
	public void setLoanDate(int bId, LocalDate d) {
		loans.setLoanDate(bId, d);
	}
	
	/**
	 * Sets the date when book is supposed to be returned.
	 * @param bId
	 */
	public void setReturnDate(int bId, LocalDate d) {
		loans.setReturnDate(bId, d);
	}

	/**
	 * 
	 * @param bId
	 * @return The associated loan with given book ID.
	 * 		   Null if there was no loan with given ID.
	 */
	public Loan getActiveLoan(int bId) {
		return loans.getActiveLoan(bId);
	}
	
	/**
	 * @return All found genres
	 */
	public ArrayList<Genre> getGenres() {
		return genres.getGenres();
	}

}
