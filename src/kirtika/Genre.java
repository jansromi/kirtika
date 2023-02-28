package kirtika;

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
	
	/**
	 * Asetetaan YKL:n mukainen luokitus
	 * @param id
	 */
	public void setClassId(String id) {
		this.genreId = id;
	}
	
	/**
	 * @return genren kuvaus
	 */
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
	
}
