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
public class Kirjat {
	private static int       maxKirjat		= 10;
	private int 				lkm			= 0;
	private String        tiedostonNimi     = "kirjat.dat";
	private Kirja             alkiot[]      = new Kirja[maxKirjat];
	
	public Kirjat() {
		try {
			alustaKirjat();
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
	private void alustaKirjat() throws FileNotFoundException {
		File f = new File("C:/kurssit/ohj2/kirtika/src/data/" + tiedostonNimi);
		Scanner scan = new Scanner(f);
		
		int i = 0;
		
		while (scan.hasNextLine()) {
			   String line = scan.nextLine();
			   // If the array is full, create a new array.
			   if (lkm == maxKirjat) resizeArray();
			   alkiot[i] = new Kirja(line);
			   i++;
			   lkm++;

			}
		scan.close();
	}
	
	/**
	 * If the old alkiot array is full,
	 * create a new array that is twice as large as the old one.
	 * Copy the references to the new array.
	 * 
	 * TODO: write tests for this method
	 * */
	public void resizeArray() {
		maxKirjat = maxKirjat * 2;
		Kirja[] uudetAlkiot = new Kirja[maxKirjat];
		for (int i = 0; i < alkiot.length; i++) {
			uudetAlkiot[i] = alkiot[i];
		}
		this.alkiot = uudetAlkiot;
	}
	
	/**
	 * Poistaa kirjan alkioista.
	 * Siirtää poistetun kirjan jälkeisiä alkioita 
	 * yhden indeksin taaksepäin
	 * @param kirja
	 */
	public void poista(Kirja kirja) {
		for (int i = 0; i < this.getLkm(); i++) {
			if (this.anna(i).oletkoTamaId(kirja.getKirjaId())) {
				int j = i;
				while (j < this.getLkm() - 1) {
					alkiot[j] = alkiot[j + 1];
					j++;
				}
				alkiot[j] = null;
				lkm--;
			}
		}
	}
	
	/**
	 * Saves the books to a file
	 * @throws SailoException  if saving fails
	 */
	public void tallenna() throws SailoException {
		File ftied = new File("C:/kurssit/ohj2/kirtika/src/data/" + tiedostonNimi);
		try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
			for (int i = 0; i < this.getLkm(); i++) {
				Kirja kirja = this.anna(i);
				fo.println(kirja.toString());
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("tiedosto: " + ftied.getAbsolutePath() + " ei aukea");
		}
	}
	
	/**
	 * Adds a book to the registry.
	 * Increases lkm by one.
	 * @param kirja (book) to be added
	 * 
	 * TODO: Exception handling
	 * @example
	 * <pre name="test">
	 * Kirjat kirjat = new Kirjat();
	 * Kirja ody = new Kirja();
	 * int x = kirjat.getLkm();
	 * kirjat.lisaa(ody);
	 * kirjat.getLkm() === x + 1;
	 * </pre>
	 */
	public void lisaa(Kirja kirja){
		if (lkm >= maxKirjat) resizeArray();
		alkiot[lkm] = kirja;
		lkm++;
		
	}
	
	

	//
	// =========== Getters / Setters ==========
	//
	
	/**
	 * Sets the value for lainassa.
	 * @param bId Book id
	 * @param b Boolean
	 */
	public void setLainattu(int bId, boolean b) {
		for (Kirja kirja : alkiot) {
			if (kirja.oletkoTamaId(bId)) {
				kirja.setLainassa(b);
				return;
			}
		}
	}
	
	/**
	 * Returns the file name
	 * @return file name
	 */
	public String getFileName() {
		return this.tiedostonNimi;
	}
	
	/**
	 * Returns a reference to a book object
	 * @param i
	 * @return reference to the book object with index i
	 * @throws IndexOutOfBoundsException
	 */
	public Kirja anna(int i) throws IndexOutOfBoundsException{
		if (i < 0 || lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return alkiot[i];
	}
	
	/**
	 * @param bId book ID
	 * @return the name of the book found with the book ID.
	 * 		   null if not found
	 */
	public String annaKirjanNimi(int bId) {
		for (Kirja kirja : alkiot) {
			if (kirja.oletkoTamaId(bId)) return kirja.getKirjanNimi();
		}
		return null;
	}
	
	
	/**
	 * Returns the book's field information as an array
	 * @param i
	 * @return
	 */
	public String[] annaKirjanTiedot(int bId) {
		for (Kirja kirja : alkiot) {
			if (kirja.oletkoTamaId(bId)) return kirja.annaKirjanTiedot();
		}
		return null;
	}
	
	public boolean getLainattu(int bId) {
		for (Kirja kirja : alkiot) {
			if (kirja.oletkoTamaId(bId)) return kirja.getLainattu();
		}
		return false;
	}
	
	
	/**
	 * @return Amount of books
	 */
	public int getLkm() {
		return this.lkm;
	}
	
	
}
