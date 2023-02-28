package kirtika;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;

public class LainattuKirja {
	
	private int lainaId;
	private int kirjaId;
	private SimpleStringProperty kirjanNimi, lainaajanNimi;
	private LocalDate lainaPvm, palautusPvm;

	private static int seuraavaLainaId = 1;
	
	public LainattuKirja() {
		super();
	}
	
	public LainattuKirja(String kirjanNimi, String lainaHlo, LocalDate lainaAlkupvm, LocalDate lainaLoppupvm) {
		setLainaId();
		this.kirjanNimi = new SimpleStringProperty(kirjanNimi);
		this.lainaajanNimi = new SimpleStringProperty(lainaHlo);
		this.lainaPvm = lainaAlkupvm;
		this.palautusPvm = lainaLoppupvm;
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
	
	
	
}
