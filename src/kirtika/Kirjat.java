package kirtika;

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
	private String        tiedostonNimi     = "";
	private Kirja             alkiot[]      = new Kirja[MAX_KIRJAT];
	
	public Kirjat() {
		
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
	
	
	public static void main(String[] args) {
		Kirjat kirjat = new Kirjat();
		Kirja ody = new Kirja();
		Kirja ody2 = new Kirja();
		Kirja ody3 = new Kirja();
		ody.setOdysseia(); ody2.setOdysseia(); ody3.setOdysseia();
		
		
		System.out.println(kirjat.getLkm());
		
		
	}

}
