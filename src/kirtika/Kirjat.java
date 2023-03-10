package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import kirtika.SailoException.TaulukkoTaysiException;

/**
 * 
 * @author Jansromi
 * TODO:
 *		- Parseri tiedostonlukua varten.
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
	 * Alustetaan kirjat tiedostosta
	 * @throws FileNotFoundException jos genret.dat ei löydy
	 */
	private void alustaKirjat() throws FileNotFoundException {
		File f = new File("C:/kurssit/ohj2/kirtika/src/data/" + tiedostonNimi);
		Scanner scan = new Scanner(f);
		
		int i = 0;
		
		while (scan.hasNextLine()) {
			   String line = scan.nextLine();
			   
			   // Skipataan eka rivi
			   if (i == 0) {
				   i++;
				   continue;
			   }
			   // Jos alkiot täynnä, luodaan uusi taulukko
			   if (lkm == maxKirjat) uusiTaulukko();
			   alkiot[i-1] = new Kirja(line);
			   i++;
			   lkm++;
			}
		scan.close();
	}
	
	/**
	 * Jos vanha alkiot-taulukko täyttyy,
	 * tehdään uusi taulukko joka on 2 kertaa suurempi kuin vanha.
	 * Siirretään viitteet uuteen taulukkoon.
	 */
	public void uusiTaulukko() {
		maxKirjat = maxKirjat * 2;
		Kirja[] uudetAlkiot = new Kirja[maxKirjat];
		for (int i = 0; i < alkiot.length; i++) {
			uudetAlkiot[i] = alkiot[i];
		}
		this.alkiot = uudetAlkiot;
	}
	
	
	
	
	/**
	 * Palauttaa tiedostonimen
	 * @return tiedoston nimi
	 */
	public String getFileName() {
		return this.tiedostonNimi;
	}
	
	
	/**
	 * 
	 * @return Kirjojen lukumäärä
	 */
	public int getLkm() {
		return this.lkm;
	}
	
	/**
	 * Kirjan lisäys rekisteriin.
	 * Kasvatetaan lkm-yhdellä
	 * @param kirja lisättävä kirja
	 * 
	 * TODO: Poikkeuskäsittely
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
		if (lkm >= maxKirjat) uusiTaulukko();
		alkiot[lkm] = kirja;
		lkm++;
	}
	
	/**
	 * Palauttaa viitteen kirjaolioon
	 * @param i
	 * @return viite kirjaolioon, jolla on index i
	 * @throws IndexOutOfBoundsException
	 * 
	 */
	public Kirja anna(int i) throws IndexOutOfBoundsException{
		if (i < 0 || lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		
		return alkiot[i];
	}
	
	/**
	 * 
	 * @param kId kirja-id
	 * @return kirja-idllä löytyvän kirjan nimi.
	 * 		   "" jos ei löytynyt
	 */
	public String annaKirjanNimi(int kId) {
		for (Kirja kirja : alkiot) {
			if (kirja.oletkoTamaId(kId)) return kirja.getKirjanNimi();
		}
		return "";
	}
	
	
	/**
	 * Palauttaa kirjan kenttätiedot taulukossa
	 * @param i
	 * @return
	 */
	public String[] annaKirjanTiedot(int i) {
		return alkiot[i].annaKirjanTiedot();
	}
	
}
