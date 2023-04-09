package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lainatut {
	
	private String tiedostonNimi = "lainat.dat";
	private ArrayList<LainattuKirja> alkiot = new ArrayList<>();
	
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
			   alkiot.add(new LainattuKirja(line));
			}
		scan.close();
	}
	
	/**
	 * Returns a reference to the LainattuKirja object
	 * @param i the index of the book in the list
	 * @return
	 */
	public LainattuKirja annaLainattuKirja(int i) {
		return alkiot.get(i);
	}
	
	/**
	 * @return The number of loans
	 */
	public int getLainatutLkm() {
		return alkiot.size();
	}
	
	/**
	 * Sets the name of the borrowed book
	 * @param lbId the id of the borrowed book. NOTE: Same as book-id
	 * @param s The name to set
	 */
	public void setLainatunKirjanNimi(int lbId, String s) {
		for (LainattuKirja lainattuKirja : alkiot) {
			if (lainattuKirja.oletkoTamaKirja(lbId)) lainattuKirja.setLainatutKirjanNimi(s);
		}
	}

}
