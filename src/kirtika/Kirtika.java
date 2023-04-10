package kirtika;

import java.time.LocalDate;

/**
 * Kirtika-luokka joka sisältää välittäjämetodeja
 * @author Jansromi
 * @version 0.9, 9.4.2023
 * 
 * 
 */
public class Kirtika {
	private final Books books;
	private final Genret genret;
	private final Loans loans;
	
	/**
	 * Default constructor
	 */
	public Kirtika() {
		this.books = new Books();
		this.genret = new Genret();
		this.loans = new Loans();
		setLoanedBookNames();

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
	 */
	public String[] getBookInfo(int bId) {
	    String[] s = books.getBookInfo(bId);

	    // Replaces YKL classification with description
	    s[4] = genret.etsiYklKuvaus(s[4]);
	    return s;
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
		Loan loan = loans.getActiveLaina(bId);
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
	 * Sets the active loans return date, 
	 * and sets the loan as closed.
	 * Finally sets the bookLoaned-property as closed.
	 * @throws SailoException if the given bId had no accociated loan ID. 
	 *         Possible if one messes with the data.
	 */
	public void closeLoan(int bId, LocalDate returnDate) throws SailoException {
		Loan loan = loans.getActiveLaina(bId);
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
	        loans.setLoanedBookName(loanedBookId, bookName);
	    }
	}
	
	/**
	 * A mediator method for saving.
	 *
	 * @throws SailoException if saving fails
	 */
	public void save() throws SailoException {
	    books.save();
	}

	/**
	 * Saves all data.
	 *
	 * @throws SailoException if saving fails
	 */
	public void saveAll() throws SailoException {
	    books.save();
	    loans.saveBookLoans();
	}

	/**
	 * Saves loan data.
	 *
	 * @throws SailoException if saving fails
	 */
	public void saveBookLoans() throws SailoException {
	    loans.saveBookLoans();
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
	 * Returns the file name where books are saved.
	 *
	 * TODO: Is this needed?
	 * @return file name
	 */
	public String getBooksFileName() {
		return books.getFileName();
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
		Loan loan = loans.getActiveLaina(bId);
		if (loan == null) return;
		loan.setLoanStartDate(d);;
	}
	
	/**
	 * Sets the date when book is supposed to be returned.
	 * @param bId
	 */
	public void setReturnDate(int bId, LocalDate d) {
		Loan loan = loans.getActiveLaina(bId);
		if (loan == null) return;
		loan.setLoanReturnDate(d);
	}

	
	/**
	 * 
	 * @param bId
	 * @return The associated loan with given book ID.
	 * 		   Null if there was no loan with given ID.
	 */
	public Loan getActiveLoan(int bId) {
		return loans.getActiveLaina(bId);
	}

}
