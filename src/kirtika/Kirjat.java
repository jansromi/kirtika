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
	
	public int getLkm() {
		return this.lkm;
	}
	
	/**
	 * Kirjan lisäys rekisteriin.
	 * Kasvatetaan lkm-yhdellä
	 * @param kirja lisättävä kirja
	 * 
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
		
		
		for (int i = 0; i < 500; i++) {
			kirjat.lisaa(ody3);
		}
		
		System.out.println(kirjat.getLkm());
		
		//kirjat.anna(499).printBook(System.out);
		/*
		try {
			kirjat.lisaa(ody); kirjat.lisaa(ody2); kirjat.lisaa(ody3);
			
			for (int i = 0; i < kirjat.getLkm(); i++) {
				Kirja kirja = kirjat.anna(i);
				System.out.println("kirja id:" + kirja.getKirjaId());
				kirja.printBook(System.out);
			}
		} catch (Exception e) {
			System.out.println("virhe oli: " + e);
		}
		*/
		
	}

}
