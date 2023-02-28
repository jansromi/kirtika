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
public class LainattuKirja {
	
	private int lainaId;
	private int lainattuKirjaId;
	private SimpleStringProperty kirjanNimi, lainaajanNimi;
	private LocalDate lainaPvm, palautusPvm;

	private static int seuraavaLainaId = 1;
	
	
	/**
	 * Peruskonstruktori testikäyttöön
	 * @param kirjanNimi
	 * @param lainaHlo
	 * @param lainaAlkupvm
	 * @param lainaLoppupvm
	 */
	public LainattuKirja(String kirjanNimi, String lainaHlo, LocalDate lainaAlkupvm, LocalDate lainaLoppupvm) {
		setLainaId();
		this.kirjanNimi = new SimpleStringProperty(kirjanNimi);
		this.lainaajanNimi = new SimpleStringProperty(lainaHlo);
		this.lainaPvm = lainaAlkupvm;
		this.palautusPvm = lainaLoppupvm;
	}
	
	/**
	 * Yhden string-rivin konstruktori
	 */
	public LainattuKirja(String line) {
		StringBuilder sb = new StringBuilder(line);
		this.lainaId = Integer.parseInt(Mjonot.erota(sb, '|'));
		this.lainattuKirjaId = Integer.parseInt(Mjonot.erota(sb, '|'));
		this.lainaajanNimi = new SimpleStringProperty(Mjonot.erota(sb, '|'));
		this.lainaPvm = asetaAika(Mjonot.erota(sb, '|'));
		if (sb.isEmpty()) {
			this.palautusPvm = null;
		} else {
			this.palautusPvm = asetaAika(sb.toString());
		}
	}
	
	private void setLainaId() {
		this.lainaId = seuraavaLainaId;
		seuraavaLainaId++;
	}

	public void setLainatutKirjanNimi(String kirjanNimi) {
		this.kirjanNimi = new SimpleStringProperty(kirjanNimi);
	}
	
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
	
	/**
	 * 
	 * @param lkId lainattu kirja id
	 * @return Onko tällä oliolla tämä id
	 */
	public boolean oletkoTamaKirja(int lkId) {
		return this.lainattuKirjaId == lkId;
	}
	
	/**
	 * 
	 * @param s
	 * @return
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
		StringBuilder sb = new StringBuilder(s);
		int vuosi = Integer.parseInt(Mjonot.erota(sb, '-'));
		int kuukausi = Integer.parseInt(Mjonot.erota(sb, '-'));
		int paiva = Integer.parseInt(sb.toString());
		return LocalDate.of(vuosi, kuukausi, paiva);
	}
	
	
}
