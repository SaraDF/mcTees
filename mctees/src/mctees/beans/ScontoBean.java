package mctees.beans;

import java.util.GregorianCalendar;

public class ScontoBean {
	
	private String codice;
	private GregorianCalendar dataInizio;
	private GregorianCalendar dataFine;
	private double percentuale;
	
	public ScontoBean(){
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public GregorianCalendar getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(GregorianCalendar dataInizio) {
		this.dataInizio = dataInizio;
	}

	public GregorianCalendar getDataFine() {
		return dataFine;
	}

	public void setDataFine(GregorianCalendar dataFine) {
		this.dataFine = dataFine;
	}

	public double getPercentuale() {
		return percentuale;
	}

	public void setPercentuale(double percentuale) {
		this.percentuale = percentuale;
	}
	
	
	
}
