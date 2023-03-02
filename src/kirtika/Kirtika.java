package kirtika;

/**
 * Kirtika-luokka joka sisältää välittäjämetodeja
 * @author Jansromi
 * @version 0.1, 19.2.2023
 */
public class Kirtika {
	private final Kirjat kirjat;
	private final Genret genret;
	private final Lainatut lainatut;
	
	public Kirtika() {
		this.kirjat = new Kirjat();
		this.genret = new Genret();
		this.lainatut = new Lainatut();
		alustaLainattujenNimet();

	}
	
	/**
	 * Alustetaan silmukassa lainattujen kirjojen nimet,
	 * sillä ovat tietokannassa vain id:llä
	 */
	public void alustaLainattujenNimet() {
		// 
		for (int i = 0; i < lainatut.getLainatutLkm(); i++) {
			// Yhdistetään kirja-id kirjan nimeen
			int lkId = lainatut.annaLainattuKirja(i).getLainattuKirjaId();
			String s = kirjat.annaKirjanNimi(lkId);
			lainatut.setLainatunKirjanNimi(lkId, s);
		}
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
	
	
	
	
	/**
	 * 
	 * @param i
	 * @return viite lainattuun kirjaan
	 */
	public LainattuKirja annaLainattuKirja(int i) {
		return lainatut.annaLainattuKirja(i);
	}
	
	public int getLainatutLkm() {
		return lainatut.getLainatutLkm();
	}
	
	/**
	 * Palauttaa kirjan tiedot taulukossa
	 * @param i
	 * @return
	 */
	public String[] annaKirjanTiedot(int i) {
		String[] s = kirjat.annaKirjanTiedot(i);
		
		// Vaihdetaan ykl-luokitus kuvaukseen
		s[4] = genret.etsiYklKuvaus(s[4]);
		return s;
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
