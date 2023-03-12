package kirtika;

/**
 * Kirtika-luokka joka sisältää välittäjämetodeja
 * @author Jansromi
 * @version 0.6, 12.3.2023
 * 
 * TODO: Mietitään, miten saadaan throwaukset järkevästi, että niille voidaan tehdä
 * jotain käyttöliittymästä
 * TODO: annaKirja-metodi käyttämään kirja-objektia listausindeksin sijaan. Listausindeksi ei välttämätt
 * ole sama kuin kirja-id
 * 
 * 
 */
public class Kirtika {
	private final Kirjat kirjat;
	private final Genret genret;
	private final Lainatut lainatut;
	
	/**
	 * Oletusmuodostaja
	 */
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
	 * Välittäjämetodi tallennukseen.
	 * 
	 * TODO: Pitäisikö exception throwata, jotta se voidaan näyttää käyttöliittymässä?
	 */
	public void tallenna() {
		try {
			kirjat.tallenna();
		} catch (SailoException e) {
			System.err.println("Ei voitu tallentaa: " + e.getMessage());
		}
	}
	
	/**
	 * Välittäjämetodi kirjan poistoon
	 */
	public void poista(Kirja kirja) {
		kirjat.poista(kirja);
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
	
	/**
	 * Palauttaa kirjojen tallennuspaikan tiedostonimen.
	 * 
	 * TODO: Tarvitaanko?
	 * @return
	 */
	public String kirjatName() {
		return kirjat.getFileName();
	}
	
	/**
	 * Välittäjämetodi joka palauttaa tällä idllä kirjan.
	 * @param i indeksi
	 * @return indeksin kirja
	 * @throws IndexOutOfBoundsException
	 * 
	 * TODO: Vaihdetaan parametri kirja-objektiksi
	 */
	public Kirja annaKirja(int i) throws IndexOutOfBoundsException{
		return kirjat.anna(i);
	}
	
	/**
	 * @param i
	 * @return viite lainattuun kirjaan
	 */
	public LainattuKirja annaLainattuKirja(int i) {
		return lainatut.annaLainattuKirja(i);
	}
	
	/**
	 * Palauttaa lainattujen kirjojen lukumäärän
	 * @return
	 */
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
