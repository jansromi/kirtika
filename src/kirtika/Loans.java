package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * This class represents a collection of loans.
 * 
 * Each loan is associated with a unique book and loan id.
 * 
 * The class is responsible for handling loan objects and 
 * reading and writing the loan data from/to a file.
 * The class provides methods for adding, deleting, and modifying loans in the loan collection.
 * @author Jansromi
 *
 */
public class Loans {
	
	private String loansFilePath = "src/data/lainat.dat";
	private ArrayList<Loan> items = new ArrayList<>();
	
	/**
	 * Default constructor.
	 * Initialized loan data from a file defined in loansFilePath
	 * If file is not found, generates a new file there
	 */
	public Loans() {
		try {
			initLoans();
		} catch (FileNotFoundException e) {
			System.err.println("Tiedostoa ei löytynyt: " + loansFilePath);
			try {
				createNewFile();
			} catch (IOException e1) {
				System.err.println("Tiedoston luonti epäonnistui " + e1.getMessage());
			}
		}
	}
	
	/**
	 * Test constructor
	 * @param test
	 */
	public Loans(boolean test) {
		
	}
	
	/**
	 * Test constructor with setting custom filepath.
	 * @param test
	 */
	public Loans(boolean test, String filepath) {
		loansFilePath = filepath;
	}
	
	/**
	 * Test method.
	 * Creates a new lainat.dat-file to loansFilePath
	 * @throws IOException 
	 */
	public boolean createNewFile() throws IOException {
        File newFile = new File(loansFilePath);
        if (newFile.createNewFile()) {
        	System.out.println("Luotiin uusi tiedosto: " + loansFilePath);
        	return true;
        } else {
        	return false;
        }
	}
	
	/**
	 * Test method.
	 * Deletes the loans-file
	 * @return boolean whether deletion was successful
	 */
	public boolean deleteLoansFile() {
		File file = new File(loansFilePath);
		if (file.delete()) {
			System.out.println(file.getName() + " poistettiin");
			return true;
		}
		
		System.out.println("Tiedostoa " + file.getName() + " ei voitu poistaa.");
		return false;
	}
	
	/**
	 * Initializes loans from file
	 * @throws FileNotFoundException if file cannot be opened
	 */
	private void initLoans() throws FileNotFoundException {
		File f = new File(loansFilePath);
		Scanner scan = new Scanner(f);
		
		while (scan.hasNextLine()) {
			   String line = scan.nextLine();
			   items.add(new Loan(line));
			}
		scan.close();
	}

	/**
	 * Sets the name of the borrowed book
	 * @param lbId the id of the borrowed book. NOTE: Same as book-id
	 * @param s The name to set
	 */
	public void setLoanedBookName(int lbId, String s) {
		for (Loan loan : items) {
			if (loan.matchesBookId(lbId)) loan.setLoanedBookName(s);
		}
	}
	
	/**
	 * Sets the date when book was loaned
	 * @param bId Id of the lent book
	 * @param d Date, when loan was given
	 */
	public void setLoanDate(int bId, LocalDate d) {
		Loan loan = getActiveLoan(bId);
		if (loan == null) return;
		loan.setLoanStartDate(d);;
	}
	
	/**
	 * Sets the return date of a loan
	 * @param bId Id of the lent book
	 * @param d Date, when loan is returned
	 */
	public void setReturnDate(int bId, LocalDate d) {
		Loan loan = getActiveLoan(bId);
		if (loan == null) return;
		loan.setLoanReturnDate(d);
	}
	
	/**
	 * Adds a new loan to arraylist.
	 * 
	 * @param book Book to be adder
	 * @param loaner Name of the books lender
	 */
	public void addBookLoan(Book book, String loaner) {
		items.add(new Loan(book, loaner));
	}
	
	/**
	 * Deletes the given loan from the ArrayList.
	 * @param lId loan ID
	 * 
	 * @example
	 * <pre name="test">
	 * Loans lo = new Loans(true);
	 * Book book1 = new Book();
	 * book1.setOdysseia();
	 * Book book2 = new Book();
	 * book2.setOdysseia();
	 * Book book3 = new Book();
	 * book3.setOdysseia();
	 * 
	 * lo.addBookLoan(book1, "Matti");
	 * lo.addBookLoan(book2, "Maija");
	 * lo.addBookLoan(book3, "Mikko");
	 * int size = lo.getLoansAmt();
	 * Loan l = lo.getLoan(size-1);
	 * int id = l.getLoanId();
	 * lo.deleteLoan(id);
	 * lo.getLoansAmt() === size-1;
	 * </pre>
	 */
	public void deleteLoan(int lId) {
	    Iterator<Loan> iterator = items.iterator();
	    while (iterator.hasNext()) {
	        Loan loan = iterator.next();
	        if (loan.getLoanId() == lId) {
	            iterator.remove();
	            break;
	        }
	    }
	}
	
	
	/**
	 * Saves the contents of ArrayList items to a file.
	 * 
	 * @throws FileNotFoundException if file is not found
	 * 
	 * @example
	 * <pre name="test">
	 * #THROWS FileNotFoundException, IOException
	 * #import java.io.IOException;
	 * #import java.io.FileNotFoundException;
	 * #import java.io.File;
	 * 
	 * Loans loans = new Loans(true, "src/data/testlainat.dat");
	 * loans.createNewFile();
	 * Book book1 = new Book();
	 * Book book2 = new Book();
	 * book1.setOdysseia();
	 * book2.setOdysseia();
	 * loans.addBookLoan(book1, "Matti");
	 * loans.addBookLoan(book2, "Maija");
	 * loans.saveBookLoans();
	 * loans.deleteLoansFile();
	 * </pre>
	 */
	public void saveBookLoans() throws FileNotFoundException {
		File file = new File(loansFilePath);
		try (PrintStream fo = new PrintStream(new FileOutputStream(file, false))) {
			for (Loan loan : items) {
				fo.println(loan.toString());
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Tiedosto: " + file.getAbsolutePath() + " ei aukea");
		}
	}
	
	/**
	 * Returns a reference to the Loan object
	 * @param i the index of the Loan in items arraylist
	 * @return reference i in arraylist items
	 */
	public Loan getLoan(int i) {
		return items.get(i);
	}
	
	
	/**
	 * @return The number of loans
	 */
	public int getLoansAmt() {
		return items.size();
	}
	
	/**
	 * Returns an active loan based on the bookId.
	 * 
	 * @return
	 */
	public Loan getActiveLoan(int bId) {
		for (Loan loan : items) {
			if (loan.matchesBookId(bId) && !loan.isLoanActive()) return loan;
		}
		return null;
	}
	
	/**
	 * Returns the loaner of selected book
	 * @param bId book id
	 * @return name of loaner
	 */
	public String getLoaner(int bId) {
		int i = items.size() - 1;
		while (i >= 0) {
			if (items.get(i).matchesBookId(bId)) return items.get(i).getLoanerName();
			i--;
		}
		return null;
	}

	public Loan getLoanedBook(int i) {
		return items.get(i);
	}

}
