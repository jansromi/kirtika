package kirtika;

import java.io.IOException;
import java.util.ArrayList;

public class Genret {
	private String tiedostonNimi = "";
	private ArrayList<Genre> alkiot = new ArrayList<>();
	
	public Genret() {
		//TODO: alustus tiedostosta
	}
	
	/**
	 * TODO: Toteuta
	 * Etsii alkioista YKL-luokitusta vastaavan kuvauksen.
	 * TODO: Jos ei löydy, niin haetaan netistä.
	 * @param s
	 * @return
	 */
	public String etsiYklKuvaus(String s) {
		return s;
	}
	
	
	public void setUnknownClassDesc(String s) throws IOException, InterruptedException {
		//this.genreDesc = webscraping.Queries.yklQuery(s);
		// TODO: Tallenna genrekuvaus tiedostoon
	}
}
