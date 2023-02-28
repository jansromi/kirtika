package kirtika;

import java.time.LocalDate;

import fi.jyu.mit.ohj2.Mjonot;
import javafx.beans.property.SimpleStringProperty;



/**
 * TODO: Laina-id:t järkeväksi ratkaisuksi.
 * Nyt lukee ne tiedostosta.
 * @author Roba
 *
 */
public class LainattuKirja {
	
	private int lainaId;
	private int kirjaId;
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
		this.kirjaId = Integer.parseInt(Mjonot.erota(sb, '|'));
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

	public LocalDate getLainaPvm() {
		return lainaPvm;
	}

	public LocalDate getPalautusPvm() {
		return palautusPvm;
	}

	public int getKirjaId() {
		return kirjaId;
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
	 * @param s
	 * @return
	 */
	public LocalDate asetaAika(String s) {
		StringBuilder sb = new StringBuilder(s);
		int vuosi = Integer.parseInt(Mjonot.erota(sb, '-'));
		int kuukausi = Integer.parseInt(Mjonot.erota(sb, '-'));
		int paiva = Integer.parseInt(sb.toString());
		return LocalDate.of(vuosi, kuukausi, paiva);
	}
	
	public static void main(String[] args) {
		String s = "1|1|Matti Meikäläinen|2022-01-01|";
		LainattuKirja testi = new LainattuKirja(s);
	}
	
}
