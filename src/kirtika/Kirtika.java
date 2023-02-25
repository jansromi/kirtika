package kirtika;

/**
 * Kirtika-luokka joka sisältää välittäjämetodeja
 * @author Jansromi
 * @version 0.1, 19.2.2023
 */
public class Kirtika {
	private final Kirjat kirjat = new Kirjat();

	/**
	 * @return kirjojen määrä rekisterissä
	 */
	public int getKirjat() {
		return kirjat.getLkm();
	}
	
	/**
	 * Lisää kirjan rekisteriin
	 * @param kirja joka lisätään
	 */
	public void lisaa(Kirja kirja) {
		kirjat.lisaa(kirja);
	}
	
	/**
	 * 
	 * @param i indeksi
	 * @return indeksin kirja
	 * @throws IndexOutOfBoundsException
	 */
	public Kirja annaKirja(int i) throws IndexOutOfBoundsException{
		return kirjat.anna(i);
	}
	
	public static void main(String[] args) {
		Kirtika kirtika = new Kirtika();
		
		Kirja ody = new Kirja();
		ody.setOdysseia();
		Kirja ody2 = new Kirja();
		ody2.setOdysseia();
		Kirja ody3 = new Kirja();
		ody3.setOdysseia();
		kirtika.lisaa(ody);
		kirtika.lisaa(ody2);
		kirtika.lisaa(ody3);
		
		kirtika.annaKirja(2).printBook(System.out);
	}

}
