package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Loans {
	
	private String loansFilePath = "src/data/lainat.dat";
	private ArrayList<Loan> items = new ArrayList<>();
	
	/**
	 * Default constructor
	 */
	public Loans() {
		try {
			initLoans();
		} catch (FileNotFoundException e) {
			System.err.println("Tiedostoa lainat.dat ei löytynyt");
			try {
	            File newFile = new File(loansFilePath);
	            newFile.createNewFile();
	            System.out.println("Created new file: " + loansFilePath);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		}
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
	 * Saves the contents of ArrayList items to a file.
	 * 
	 * @throws SailoException if file is not found
	 */
	public void saveBookLoans() throws SailoException {
		File file = new File(loansFilePath);
		try (PrintStream fo = new PrintStream(new FileOutputStream(file, false))) {
			for (Loan loan : items) {
				fo.println(loan.toString());
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("Tiedosto: " + file.getAbsolutePath() + " ei aukea");
		}
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
	 * Adds a new loan to arraylist.
	 * Saves the loans to file after addition.
	 * @param book
	 * @param loaner
	 */
	public void addBookLoan(Book book, String loaner) {
		items.add(new Loan(book, loaner));
	}
	
	/**
	 * Deletes the given loan from arraylist.
	 * @param laina
	 */
	public void deleteLoan(int lId) {
	    Iterator<Loan> iterator = items.iterator();
	    while (iterator.hasNext()) {
	        Loan loan = iterator.next();
	        if (loan.getLoanId() == lId) {
	            iterator.remove();
	        }
	    }
	}
	
	
	/**
	 * Returns a reference to the LainattuKirja object
	 * @param i the index of the book in the list
	 * @return
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
