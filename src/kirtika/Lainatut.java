package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Lainatut {
	
	private String tiedostonNimi = "lainat.dat";
	private ArrayList<Laina> alkiot = new ArrayList<>();
	
	/**
	 * Default constructor
	 */
	public Lainatut() {
		try {
			alusta();
		} catch (FileNotFoundException e) {
			System.err.println("Tiedostoa lainat.dat ei löytynyt");
			/*
			 * TODO:
			 * Luodaan uusi tiedosto?
			 */
		}
	}
	
	/**
	 * Initializes loans from file
	 * @throws FileNotFoundException if file cannot be opened
	 */
	private void alusta() throws FileNotFoundException {
		File f = new File("C:/kurssit/ohj2/kirtika/src/data/" + tiedostonNimi);
		Scanner scan = new Scanner(f);
		
		while (scan.hasNextLine()) {
			   String line = scan.nextLine();
			   alkiot.add(new Laina(line));
			}
		scan.close();
	}
	
	/**
	 * Saves the contents of ArrayList alkiot to a file.
	 * 
	 * @throws SailoException if file is not found
	 */
	public void saveBookLoans() throws SailoException {
		File file = new File("C:/kurssit/ohj2/kirtika/src/data/" + tiedostonNimi);
		try (PrintStream fo = new PrintStream(new FileOutputStream(file, false))) {
			for (Laina laina : alkiot) {
				fo.println(laina.toString());
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("Tiedosto: " + file.getAbsolutePath() + " ei aukea");
		}
	}
	
	/**
	 * Returns a reference to the LainattuKirja object
	 * @param i the index of the book in the list
	 * @return
	 */
	public Laina annaLainattuKirja(int i) {
		return alkiot.get(i);
	}
	
	/**
	 * @return The number of loans
	 */
	public int getLainatutLkm() {
		return alkiot.size();
	}
	
	/**
	 * Returns an active loan based on the bookId.
	 * 
	 * @return
	 */
	public Laina getActiveLaina(int bId) {
		for (Laina laina : alkiot) {
			if (laina.oletkoTamaKirja(bId) && !laina.getClosed()) return laina;
		}
		return null;
	}
	
	/**
	 * Returns the loaner of selected book
	 * @param bId book id
	 * @return name of loaner
	 */
	public String getLoaner(int bId) {
		int i = alkiot.size() - 1;
		while (i >= 0) {
			if (alkiot.get(i).oletkoTamaKirja(bId)) return alkiot.get(i).getLainaajanNimi();
			i--;
		}
		return null;
	}
	
	/**
	 * Sets the name of the borrowed book
	 * @param lbId the id of the borrowed book. NOTE: Same as book-id
	 * @param s The name to set
	 */
	public void setLainatunKirjanNimi(int lbId, String s) {
		for (Laina laina : alkiot) {
			if (laina.oletkoTamaKirja(lbId)) laina.setLainatutKirjanNimi(s);
		}
	}
	
	/**
	 * Adds a new loan to arraylist.
	 * Saves the loans to file after addition.
	 * @param kirja
	 * @param loaner
	 */
	public void addBookLoan(Kirja kirja, String loaner) {
		alkiot.add(new Laina(kirja, loaner));
	}
	
	/**
	 * Deletes the given loan from arraylist.
	 * @param laina
	 */
	public void deleteLoan(int lId) {
	    Iterator<Laina> iterator = alkiot.iterator();
	    while (iterator.hasNext()) {
	        Laina laina = iterator.next();
	        if (laina.getLainaId() == lId) {
	            iterator.remove();
	        }
	    }
	}

}
