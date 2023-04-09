package kirtika;

import java.time.LocalDate;

/**
 * Kirtika-luokka joka sisältää välittäjämetodeja
 * @author Jansromi
 * @version 0.6, 12.3.2023
 * 
 * TODO: Mietitään, miten saadaan throwaukset järkevästi, että niille voidaan tehdä
 * jotain käyttöliittymästä
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
	 * @throws SailoException 
	 */
	public void tallenna() throws SailoException {
			kirjat.tallenna();
	}
	
	public void saveAll() throws SailoException {
		kirjat.tallenna();
		lainatut.saveBookLoans();
	}
	
	public void saveBookLoans() throws SailoException {
		lainatut.saveBookLoans();
	}
	
	/**
	 * Adds a new loan
	 * 
	 * @param kirja
	 * @param b
	 */
	public void addBookLoan(Kirja kirja, String loaner) {
		lainatut.addBookLoan(kirja, loaner);
	}
	
	/**
	 * Välittäjämetodi kirjan poistoon
	 */
	public void poista(Kirja kirja) {
		kirjat.poista(kirja);
	}
	
	/**
	 * Deletes the loan 
	 * @param laina
	 */
	public void deleteLoan(int lId) {
		lainatut.deleteLoan(lId);
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
	public Laina annaLainattuKirja(int i) {
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
	 * @param bId book id
	 * @return
	 */
	public String[] annaKirjanTiedot(int bId) {
		String[] s = kirjat.annaKirjanTiedot(bId);
		
		// Vaihdetaan ykl-luokitus kuvaukseen
		s[4] = genret.etsiYklKuvaus(s[4]);
		return s;
	}
	
	/**
	 * Sets the given books lainattu-status
	 * @param bId
	 */
	public void setLainattu(int bId, boolean b) {
		kirjat.setLainattu(bId, b);
	}
	
	/**
	 * Sets the date when book was loaned
	 * @param bId
	 */
	public void setLoanDate(int bId, LocalDate d) {
		Laina laina = lainatut.getActiveLaina(bId);
		if (laina == null) return;
		laina.setLainaPvm(d);
	}
	
	/**
	 * Sets the date when book was loaned
	 * @param bId
	 */
	public void setReturnDate(int bId, LocalDate d) {
		Laina laina = lainatut.getActiveLaina(bId);
		if (laina == null) return;
		laina.setPalautuspvm(d);
	}
	
	/**
	 * Sets the loans return date
	 * @throws SailoException 
	 */
	public void closeLoan(int bId, LocalDate returnDate) throws SailoException {
		Laina laina = lainatut.getActiveLaina(bId);
		if (laina == null) {
			throw new SailoException("Annetulla kirjalla ei ollut aktiivista lainaa!");
		}
		laina.setPalautuspvm(returnDate);
		laina.setClosed(true);
		setLainattu(bId, false);
	}
	
	public Laina getActiveLoan(int bId) {
		return lainatut.getActiveLaina(bId);
	}
	
	/**
	 * Fetches the loan information on selected book id
	 * to be shown on GUI
	 * obj[0] = boolean lainassa
	 * obj[1] = String lainaaja
	 * obj[2] = date when the loan was given
	 * @param bId book id
	 * @return
	 */
	public Object[] getBookLoanInfo(int bId) {
		Object[] obj = new Object[4];
		Laina laina = lainatut.getActiveLaina(bId);
		if (laina != null) {
			// should always return true
			obj[0] = kirjat.getLainattu(bId);
			obj[1] = laina.getLainaajanNimi();
			obj[2] = laina.getLainaPvm();
			obj[3] = laina.getPalautusPvm();
		} else {
			obj[0] = false;
			obj[1] = "";
			obj[2] = null;
			obj[3] = null;
		}
		return obj;
	}

}
