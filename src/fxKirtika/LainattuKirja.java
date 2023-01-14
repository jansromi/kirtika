package fxKirtika;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;

public class LainattuKirja {
	private SimpleStringProperty kirjanNimi, lainaajanNimi;
	private LocalDate lainaPvm, palautusPvm;
	
	public LainattuKirja(String kirjanNimi, String lainaajanNimi, LocalDate lainaPvm, LocalDate palautusPvm) {
		this.setKirjanNimi(new SimpleStringProperty(kirjanNimi));
		this.setLainaajanNimi(new SimpleStringProperty(lainaajanNimi));
		this.setLainaPvm(lainaPvm);
		this.setPalautusPvm(palautusPvm);
	}

	public String getKirjanNimi() {
		return kirjanNimi.get();
	}

	public void setKirjanNimi(SimpleStringProperty kirjanNimi) {
		this.kirjanNimi = kirjanNimi;
	}
;
	public String getLainaajanNimi() {
		return lainaajanNimi.get();
	}

	public void setLainaajanNimi(SimpleStringProperty lainaajanNimi) {
		this.lainaajanNimi = lainaajanNimi;
	}

	public LocalDate getLainaPvm() {
		return lainaPvm;
	}

	public void setLainaPvm(LocalDate lainaPvm) {
		this.lainaPvm = lainaPvm;
	}

	public LocalDate getPalautusPvm() {
		return palautusPvm;
	}

	public void setPalautusPvm(LocalDate palautusPvm) {
		this.palautusPvm = palautusPvm;
	}

}
