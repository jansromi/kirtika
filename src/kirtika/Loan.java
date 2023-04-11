package kirtika;

import java.time.LocalDate;

import fi.jyu.mit.ohj2.Mjonot;
import javafx.beans.property.SimpleStringProperty;



/**
 * TODO: Loan-id:t järkeväksi ratkaisuksi.
 * Nyt lukee ne tiedostosta.
 * Refaktorointi Loan-luokaksi, koska taulukko ei niinkään keskity kirjoihin?
 * @author Roba
 *
 */
public class Loan {
	
	private int loanId;
	private int loanedBookId;
	private SimpleStringProperty bookName, loanerName;
	private LocalDate loanStartDate, loanEndDate;
	private boolean loanActive;
	private static int nextLoanId = 1;
	
	/**
	* Basic constructor for testing purposes.
	* @param bookName - the name of the book being loaned.
	* @param loanerName - the name of the person lending the book.
	* @param loanStartDate - the start date of the loan.
	* @param loanEndDate - the end date of the loan.
	*/
	public Loan(String bookName, String lenderName, LocalDate loanStartDate, LocalDate loanEndDate) {
	    setLoanId(); // Sets the unique ID of the loan.
	    this.bookName = new SimpleStringProperty(bookName);
	    this.loanerName = new SimpleStringProperty(lenderName);
	    this.loanStartDate = loanStartDate;
	    this.loanEndDate = loanEndDate;
	}
	
	/**
	 * Line constructor
	 */
	public Loan(String line) {
		StringBuilder sb = new StringBuilder(line);
		this.setLoanId(Integer.parseInt(Mjonot.erota(sb, '|')));
		this.loanedBookId = Integer.parseInt(Mjonot.erota(sb, '|'));
		this.loanerName = new SimpleStringProperty(Mjonot.erota(sb, '|'));
		this.loanStartDate = SetDate.setDate(Mjonot.erota(sb, '|'));
		this.loanEndDate = SetDate.setDate(Mjonot.erota(sb, '|'));
		this.loanActive = Boolean.parseBoolean(sb.toString());
	}
	
	public Loan() {
		
	}
	
	/**
	 * Constructs a loan from a Book-object.
	 * Set the loans start date to current date,
	 * and the LoppuPvm to null
	 * @param book
	 */
	public Loan(Book book, String loaner) {
		setLoanId();
		this.loanedBookId = book.getBookId();
		this.bookName = new SimpleStringProperty(book.getBookName());
		this.loanerName = new SimpleStringProperty(loaner);
		this.loanStartDate = LocalDate.now();
		this.loanEndDate = null;
		this.loanActive = false;
		
	}
	
	/**
	 * Checks if this object has the given book ID.
	 * @param lbId loanedBookId
	 * @return true if this object has the given book ID, false otherwise.
	 */
	public boolean matchesBookId(int lbId) {
		return this.loanedBookId == lbId;
	}
	
	public static class SetDate {
		
		/**
		 * Builds a LocalDate-object from a string.
		 * @param s Date as String (YYYY-MM-DD)
		 * @return LocalDate-object
		 * 
		 * @example
		 * <pre name="test">
		 * #import java.time.LocalDate;
		 * #import kirtika.Loan;
		 * 
		 * Loan loan = new Loan();
		 * LocalDate a1 = SetDate.setDate("2022-01-01");
		 * a1.getYear() === 2022;
		 * a1.getMonthValue() === 1;
		 * </pre>
		 */
		public static LocalDate setDate(String s) {
			if (s.equals("null")) return null;
			StringBuilder sb = new StringBuilder(s);
			int y = Integer.parseInt(Mjonot.erota(sb, '-'));
			int m = Integer.parseInt(Mjonot.erota(sb, '-'));
			int d = Integer.parseInt(sb.toString());
			return LocalDate.of(y, m, d);
		}
	}
	
	/**
	 * Returns the LainattuKirja-object as bar-format
	 * 
	 * TODO: tests
	 */
	public String toString() {
		return loanId + "|" + loanedBookId + "|" + loanerName.get() + "|" + loanStartDate + "|" + loanEndDate + "|" + loanActive;
	}
	
	// ****************
	// GETTERS / SETTERS
	// *****************
	
	/**
	 * Sets unique loan id
	 */
	private void setLoanId() {
		this.loanId = nextLoanId;
		nextLoanId++;
	}
	
	/**
	 * Sets unique loan id.
	 * @param id
	 */
	private void setLoanId(int id) {
		this.loanId = id;
		if (this.loanId >= nextLoanId) nextLoanId = id + 1;
	}

	/**
	 * Sets the loan status as either open or closed.
	 * @param isOpen - true if the loan is still active, false if it has been closed.
	 */
	public void setLoanStatus(boolean isOpen) {
	    loanActive = isOpen;
	}

	/**
	 * Sets the bookName-property
	 * @param s Name of the borrowed book
	 */
	public void setLoanedBookName(String bookName) {
		this.bookName =  new SimpleStringProperty(bookName);
	}
	
	/**
	 * Sets the end date of the loan.
	 * @param endDate - the date on which the loan was returned.
	 */
	public void setLoanReturnDate(LocalDate endDate) {
	    loanEndDate = endDate;
	}

	/**
	 * Sets the start date of the loan.
	 * @param startDate - the date on which the book was borrowed.
	 */
	public void setLoanStartDate(LocalDate startDate) {
	    loanStartDate = startDate;
	}

	/**
	 * Returns the current loan status of the book.
	 * @return true if the book is still being loaned, false if it has been returned.
	 */
	public boolean isLoanActive() {
	    return loanActive;
	}

	/**
	 * Getter for the start date of the loan.
	 * @return the date on which the book was borrowed.
	 */
	public LocalDate getLoanStartDate() {
	    return loanStartDate;
	}

	/**
	 * Getter for the end date of the loan.
	 * @return the date on which the book was returned.
	 */
	public LocalDate getLoanEndDate() {
	    return loanEndDate;
	}

	/**
	 * Getter for the ID of the loaned book.
	 * @return the ID of the book that was loaned.
	 */
	public int getLoanedBookId() {
	    return loanedBookId;
	}

	/**
	 * Getter for the ID of the loan.
	 * @return the ID of the loan.
	 */
	public int getLoanId() {
	    return loanId;
	}

	/**
	 * Getter for the name of the loaned book.
	 * @return the name of the loaned book.
	 */
	public String getBookName() {
	    return bookName.get();
	}

	/**
	 * Getter for the name of the person who loaned the book.
	 * @return the name of the person who loaned the book.
	 */
	public String getLoanerName() {
	    return loanerName.get();
	}
	
}
