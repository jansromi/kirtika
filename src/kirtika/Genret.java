package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Genret {
	private String tiedostonNimi = "genret.dat";
	private ArrayList<Genre> alkiot = new ArrayList<>();
	
	public Genret() {
		//TODO: alustus tiedostosta
		try {
			initGenret();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Alustetaan genret tiedostosta
	 * @throws FileNotFoundException jos genret.dat ei löydy
	 */
	private void initGenret() throws FileNotFoundException {
		File f = new File("C:/kurssit/ohj2/kirtika/bin/data/genret.dat");
		Scanner scan = new Scanner(f);
		
		while (scan.hasNextLine()) {
			   String line = scan.nextLine();
			   if (line.equals("genre_id|genre_info")) continue;
			   alkiot.add(new Genre(line));
			}
		scan.close();
	}

	public String getGenreDesc(Genre genre) {
		return genre.getGenreDesc();
	}
	
	/**
	 * TODO: Toteuta
	 * Etsii alkioista YKL-luokitusta vastaavan kuvauksen.
	 * TODO: Jos ei löydy, niin haetaan netistä.
	 * @param s
	 * @return
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
