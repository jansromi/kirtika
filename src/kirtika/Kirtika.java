package kirtika;

/**
 * Kirtika-luokka joka sisältää välittäjämetodeja
 * @author Jansromi
 * @version 0.1, 19.2.2023
 */
public class Kirtika {
	private final Kirjat kirjat;
	private final Genret genret;
	
	public Kirtika() {
		this.kirjat = new Kirjat();
		this.genret = new Genret();
	}
	
	/**
	 * @return kirjojen määrä rekisterissä
	 */
	public int getKirjat() {
		return kirjat.getLkm();
	}
	
	/**
	 * Lisää kirjan rekisteriin
	 * @param kirja joka lisätään
	 * @throws SailoException jos lisäys ei onnistunut
	 */
	public void lisaa(Kirja kirja) throws SailoException {
		kirjat.lisaa(kirja);
	}
	
	public String kirjatName() {
		return kirjat.getFileName();
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
	
	public String[] annaKirjanTiedot(int i) {
		return kirjat.annaKirjanTiedot(i);
	}
	
	public static void main(String[] args) {
		Kirtika kirtika = new Kirtika();
		
		Kirja ody = new Kirja();
		ody.setOdysseia();
		Kirja ody2 = new Kirja();
		ody2.setOdysseia();
		Kirja ody3 = new Kirja();
		ody3.setOdysseia();
		
		System.out.println(kirtika.kirjat.getFileName());
		try {
			kirtika.lisaa(ody);
			kirtika.lisaa(ody2);
			kirtika.lisaa(ody3);
		} catch (SailoException e) {
			//e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		kirtika.annaKirja(2).printBook(System.out);
	}

}
