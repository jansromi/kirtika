package kirtika;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * 
 * @author Jansromi
 * @version 20.2.2023
 *
 */
public class Kirja {
	
	private int kirjaId;
	private String isbn = "";
	private String kirjailija = "";
	private String kirjanNimi = "";
	private String kirjanKieli = "";
	private String julkaisija = "";
	private int julkaisuvuosi;
	private String luokitus = "";
	private boolean lainassa, luettu;
	private String infoPath = "";
	
	private static int seuraavaId = 1;
	
	/**
	 * Testikonstruktiori
	 * @param kirjaId
	 * @param isbn
	 * @param kirjailija
	 * @param kirjanNimi
	 * @param kirjanKieli
	 * @param julkaisija
	 * @param julkaisuvuosi
	 * @param luokitus
	 * @param luettu
	 * @param infoPath
	 */
	public Kirja(int kirjaId, String isbn, String kirjailija, String kirjanNimi, String kirjanKieli, String julkaisija,
			int julkaisuvuosi, String luokitus, boolean luettu, String infoPath) {
		
		this.kirjaId = kirjaId;
		this.isbn = isbn;
		this.kirjailija = kirjailija;
		this.kirjanNimi = kirjanNimi;
		this.kirjanKieli = kirjanKieli;
		this.julkaisija = julkaisija;
		this.julkaisuvuosi = julkaisuvuosi;
		this.luokitus = luokitus;
		this.luettu = luettu;
		this.infoPath = infoPath;
	}
	
	public Kirja(String line) {
		StringBuilder sb = new StringBuilder(line);
		this.kirjaId = Integer.parseInt(Mjonot.erota(sb, '|'));
		this.isbn = Mjonot.erota(sb, '|');
		this.kirjailija = Mjonot.erota(sb, '|');
		this.kirjanNimi = Mjonot.erota(sb, '|');
		this.kirjanKieli = Mjonot.erota(sb, '|');
		this.julkaisija = Mjonot.erota(sb, '|');
		this.julkaisuvuosi = Integer.parseInt(Mjonot.erota(sb, '|'));
		this.luokitus = Mjonot.erota(sb, '|');
		this.lainassa = Boolean.getBoolean(Mjonot.erota(sb, '|'));
		this.luettu = Boolean.getBoolean(Mjonot.erota(sb, '|'));
		this.infoPath = Mjonot.erota(sb, '|');
	}
	
	private int getRnd() {
		Random rnd = new Random();
		return rnd.nextInt(99);
	}
	
	public Kirja() {
		
	}
	
	
	/**
	 * Asetetaan testiarvoiksi odysseian tiedot
	 */
	public void setOdysseia() {
		this.isbn = "9789511318866";
		this.kirjailija = "Homeros; Saarikoski, Pentti";
		this.kirjanNimi = "Odysseia" + " " + getRnd();
		this.kirjanKieli = "Suomi";
		this.julkaisija = "Kustannusosakeyhtiö Otava";
		this.julkaisuvuosi = 2017;
		this.luokitus = "81.4";
		this.luettu = true;
		this.infoPath =  "...\\kirtika\\text\\1_info.txt";
	}
	
	/**
	 * @return Kirja-Id
	 
	 */
	public int getKirjaId() {
		return this.kirjaId;
	}
	
	public String getKirjanNimi() {
		return this.kirjanNimi;
	}
	
	/**
	 * Onko kirjan id i
	 * @param i
	 * @return
	 */
	public boolean oletkoTamaId(int i) {
		return this.kirjaId == i;
	}
	
	/**
	 * 
	 * @return Isbn string
	 * @example
	 * <pre name="test">
	 * Kirja kirja1 = new Kirja();
	 * kirja1.setOdysseia();
	 * kirja1.getIsbn() === "9789511318866";
	 * </pre>
	 */
	public String getIsbn() {
		return this.isbn;
	}
	
	/**
	 * Asetaan kirjalle id
	 * @return kirjalle annettu id
	 * @example
	 * <pre name="test">
	 * Kirja kirja1 = new Kirja();
	 * Kirja kirja2 = new Kirja();
	 * int n1 = kirja1.setKirjaId();
	 * int n2 = kirja2.setKirjaId();
	 * n1 === n2 - 1;
	 * </pre>
	 */
	public int setKirjaId() {
		this.kirjaId = seuraavaId;
		seuraavaId++;
		return this.kirjaId;
	}
	
	/**
	 * Tulostaa kirjan tiedot
	 * @param out tulostustietovirta
	 */
	public void printBook(PrintStream out) {
		out.println("Kirjan ID: " + this.kirjaId);
		out.println("Kirjan ISBN: " + this.isbn);
		out.println("Kirjan nimi: " + this.kirjanNimi);
		out.println("Kirjailija: " + this.kirjailija);
		out.println("Kirjan kieli: " + this.kirjanKieli);
		out.println("Kirjan julkaisija: " + this.julkaisija);
		out.println("Kirjan julkaisuvuosi: " + this.julkaisuvuosi);
		out.println("Kirjan luokitus: " + this.luokitus);
	}
	
	/**
	 * 
	 * @param os
	 */
	public void printBook(OutputStream os) {
		printBook(new PrintStream(os));
	}
	
	public String[] annaKirjanTiedot() {
		String[] s = new String[6];
		s[0] = this.kirjailija;
		s[1] = this.kirjanKieli;
		s[2] = this.julkaisija;
		s[3] = Integer.toString(this.julkaisuvuosi);
		s[4] = this.luokitus;
		s[5] = this.isbn;
		return s;
	}
	
	
	
	public static void main(String[] args) {
	}
	
}
