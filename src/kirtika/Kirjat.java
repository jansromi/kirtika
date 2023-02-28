package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author Jansromi
 * TODO:
 *		- Parseri tiedostonlukua varten.
 *
 */
public class Kirjat {
	private static final int MAX_KIRJAT		= 500;
	private int 				lkm			= 0;
	private String        tiedostonNimi     = "kirjat.dat";
	private Kirja             alkiot[]      = new Kirja[MAX_KIRJAT];
	
	public Kirjat() {
		try {
			alustaKirjat();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			   if (i == 0) {
				   i++;
				   continue;
			   }
			   
			   alkiot[i-1] = new Kirja(line);
			   i++;
			   lkm++;
			}
		scan.close();
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
	 * kirjat.getLkm() === 0;
	 * kirjat.lisaa(ody);
	 * kirjat.getLkm() === 1;
	 * </pre>
	 */
	public void lisaa(Kirja kirja) {
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
	
	//
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
	
	
	public static void main(String[] args) {
		Kirjat kirjat = new Kirjat();
		//Kirja ody = new Kirja();
		//Kirja ody2 = new Kirja();
		//Kirja ody3 = new Kirja();
		//ody.setOdysseia(); ody2.setOdysseia(); ody3.setOdysseia();
		
		
		System.out.println(kirjat.getLkm());
		
		
	}

}
