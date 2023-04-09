package kirtika;

import java.time.LocalDate;

import fi.jyu.mit.ohj2.Mjonot;
import javafx.beans.property.SimpleStringProperty;



/**
 * TODO: Laina-id:t järkeväksi ratkaisuksi.
 * Nyt lukee ne tiedostosta.
 * Refaktorointi Laina-luokaksi, koska taulukko ei niinkään keskity kirjoihin?
 * @author Roba
 *
 */
public class Laina {
	
	private int lainaId;
	private int lainattuKirjaId;
	private SimpleStringProperty kirjanNimi, lainaajanNimi;
	private LocalDate lainaPvm, palautusPvm;
	private boolean closed;
	private static int seuraavaLainaId = 1;
	
	
	/**
	 * Peruskonstruktori testikäyttöön
	 * @param kirjanNimi
	 * @param lainaHlo
	 * @param lainaAlkupvm
	 * @param lainaLoppupvm
	 */
	public Laina(String kirjanNimi, String lainaHlo, LocalDate lainaAlkupvm, LocalDate lainaLoppupvm) {
		setLainaId();
		this.kirjanNimi = new SimpleStringProperty(kirjanNimi);
		this.lainaajanNimi = new SimpleStringProperty(lainaHlo);
		this.lainaPvm = lainaAlkupvm;
		this.palautusPvm = lainaLoppupvm;
	}
	
	/**
	 * Yhden string-rivin konstruktori
	 */
	public Laina(String line) {
		StringBuilder sb = new StringBuilder(line);
		this.setLainaId(Integer.parseInt(Mjonot.erota(sb, '|')));
		this.lainattuKirjaId = Integer.parseInt(Mjonot.erota(sb, '|'));
		this.lainaajanNimi = new SimpleStringProperty(Mjonot.erota(sb, '|'));
		this.lainaPvm = asetaAika(Mjonot.erota(sb, '|'));
		this.palautusPvm = asetaAika(Mjonot.erota(sb, '|'));
		this.closed = Boolean.parseBoolean(sb.toString());
	}
	
	/**
	 * Constructs a loan from a Kirja-object.
	 * Set the loans start date to current date,
	 * and the LoppuPvm to null
	 * @param kirja
	 */
	public Laina(Kirja kirja, String loaner) {
		setLainaId();
		this.lainattuKirjaId = kirja.getKirjaId();
		this.kirjanNimi = new SimpleStringProperty(kirja.getKirjanNimi());
		this.lainaajanNimi = new SimpleStringProperty(loaner);
		this.lainaPvm = LocalDate.now();
		this.palautusPvm = null;
		this.closed = false;
		
	}
	
	private void setLainaId() {
		this.lainaId = seuraavaLainaId;
		seuraavaLainaId++;
	}
	
	private void setLainaId(int id) {
		this.lainaId = id;
		if (this.lainaId >= seuraavaLainaId) seuraavaLainaId = id + 1;
	}

	public void setLainatutKirjanNimi(String kirjanNimi) {
		this.kirjanNimi = new SimpleStringProperty(kirjanNimi);
	}
	
	/**
	 * Gettereitä tarvitaan taulukkoa varten
	 * @return
	 */
	public LocalDate getLainaPvm() {
		return lainaPvm;
	}

	public LocalDate getPalautusPvm() {
		return palautusPvm;
	}

	public int getLainattuKirjaId() {
		return lainattuKirjaId;
	}

	public int getLainaId() {
		return lainaId;
	}

	public String getKirjanNimi() {
		return kirjanNimi.get();
	}

	public String getLainaajanNimi() {
		return lainaajanNimi.get();
	}
	
	public boolean getClosed() {
		return closed;
	}
	
	public void setClosed(boolean b) {
		closed = b;
	}
	
	public void setPalautuspvm(LocalDate d) {
		this.palautusPvm = d;
	}
	
	public void setLainaPvm(LocalDate d) {
		this.lainaPvm = d;
	}
	
	/**
	 * 
	 * @param lkId lainattu kirja id
	 * @return Onko tällä oliolla tämä id
	 */
	public boolean oletkoTamaKirja(int lkId) {
		return this.lainattuKirjaId == lkId;
	}
	
	/**
	 * Returns the LainattuKirja-object as bar-format
	 * 
	 * TODO: tests
	 */
	public String toString() {
		return lainaId + "|" + lainattuKirjaId + "|" + lainaajanNimi.get() + "|" + lainaPvm + "|" + palautusPvm + "|" + closed;
	}
	
	/**
	 * Builds a LocalDate-object from a string.
	 * @param s Date as String (YYYY-MM-DD)
	 * @return LocalDate-object
	 * 
	 * @example
	 * <pre name="test">
	 * #import java.time.LocalDate;
	 * LocalDate a1 = asetaAika("2022-01-01");
	 * a1.getYear() === 2022;
	 * a1.getMonth() === 01;
	 * </pre>
	 */
	public LocalDate asetaAika(String s) {
		if (s.equals("null")) return null;
		StringBuilder sb = new StringBuilder(s);
		int vuosi = Integer.parseInt(Mjonot.erota(sb, '-'));
		int kuukausi = Integer.parseInt(Mjonot.erota(sb, '-'));
		int paiva = Integer.parseInt(sb.toString());
		return LocalDate.of(vuosi, kuukausi, paiva);
	}
	
	
}
