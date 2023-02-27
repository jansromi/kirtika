package kirtika;

import java.io.IOException;

import fi.jyu.mit.ohj2.Mjonot;

public class Genre {
	// Ykl-kuvaus
	private String genreDesc;
	// Sama kuin ykl luokitus
	private String genreId;
	
	public Genre() {
		this.genreDesc = "";
		this.genreId = "";
	}
	
	/**
	 * Alustetaan esim. tiedostosta.
	 * @param s Tolpilla eroteltu merkkijono
	 */
	public Genre(String s) {
		StringBuilder sb = new StringBuilder(s);
		this.genreId = Mjonot.erota(sb, '|');
		this.genreDesc = sb.toString();
	}

	public void setUnknownClassDesc(String s) throws IOException, InterruptedException {
		this.genreDesc = webscraping.Queries.yklQuery(s);
		// TODO: Tallenna genrekuvaus tiedostoon
	}
	
	public void setClassId(String id) {
		this.genreId = id;
	}
	
	public String getGenreDesc() {
		return this.genreDesc;
	}
	
	/**
	 * Onko tällä oliolla parametrin id
	 * @param Genre-id (ykl)
	 * @return True jos on
	 */
	public boolean oletkoTamaId(String gid) {
		return this.genreId.equals(gid);
	}
	
	public static void main(String[] args) {
		Genre genre = new Genre();
		try {
			genre.setUnknownClassDesc("84.2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(genre.getGenreDesc());
	}
}
