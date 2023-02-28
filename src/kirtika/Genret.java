package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Genret {
	private String tiedostonNimi = "genret.dat";
	private ArrayList<Genre> alkiot = new ArrayList<>();
	
	/**
	 * Alustus tiedostosta.
	 * 
	 * TODO: parempi poikkeuksenhallinta
	 */
	public Genret() {
		try {
			initGenret();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * String-rivillinen testikonstruktori
	 * @param s
	 */
	public Genret(String s) {
		alkiot.add(new Genre(s));
	}
	
	/**
	 * Alustetaan genret tiedostosta
	 * @throws FileNotFoundException jos genret.dat ei löydy
	 */
	private void initGenret() throws FileNotFoundException {
		File f = new File("C:/kurssit/ohj2/kirtika/src/data/" + tiedostonNimi);
		Scanner scan = new Scanner(f);
		
		while (scan.hasNextLine()) {
			   String line = scan.nextLine();
			   if (line.equals("genre_id|genre_info")) continue;
			   
			   // Alustaa genren tolppaparserilla
			   alkiot.add(new Genre(line));
			}
		scan.close();
	}

	public String getGenreDesc(Genre genre) {
		return genre.getGenreDesc();
	}
	
	/**
	 * Etsii alkioista YKL-luokitusta vastaavan kuvauksen.
	 * @param s YKL-luokitus, esim "84.2"
	 * @return YKL-kuvaus, esim "Suomalainen kaunokirjallisuus"
	 * 
	 * @example
	 * <pre name="test">
	 * Genret genret = new Genret("84.2|Suomalainen kertomakirjallisuus");
	 * genret.etsiYklKuvaus("84.2") === "Suomalainen kertomakirjallisuus";
	 * genret.etsiYklKuvaus("67") === "Ei tiedetä";
	 * </pre>
	 */
	public String etsiYklKuvaus(String s) {
		for (Genre genre : alkiot) {
			if (genre.oletkoTamaId(s)) return genre.getGenreDesc();
		}
		return "Ei tiedetä";
	}
	
	
	public void setUnknownClassDesc(String s) throws IOException, InterruptedException {
		//this.genreDesc = webscraping.Queries.yklQuery(s);
		// TODO: Tallenna genrekuvaus tiedostoon
	}
	
	public static void main(String[] args) {
		Genret ge = new Genret();
		System.out.println(ge.alkiot.get(1).getGenreDesc());
		System.out.println(ge.alkiot.get(1).oletkoTamaId("86.53"));
		
		System.out.println(ge.etsiYklKuvaus("81.4"));
		
	}
}
