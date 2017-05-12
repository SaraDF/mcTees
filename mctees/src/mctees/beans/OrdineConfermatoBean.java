package mctees.beans;

import java.util.GregorianCalendar;

public class OrdineConfermatoBean {

	private OrdineBean ordine;
	private GregorianCalendar dataConferma;
	private String indirizzoSpedizione;
	private SpedizioneBean spedizione;
	
	public OrdineConfermatoBean(){
		
	}

	public OrdineBean getOrdine() {
		return ordine;
	}

	public void setOrdine(OrdineBean ordine) {
		this.ordine = ordine;
	}

	public GregorianCalendar getDataConferma() {
		return dataConferma;
	}

	public void setDataConferma(GregorianCalendar dataConferma) {
		this.dataConferma = dataConferma;
	}

	public String getIndirizzoSpedizione() {
		return indirizzoSpedizione;
	}

	public void setIndirizzoSpedizione(String indirizzoSpedizione) {
		this.indirizzoSpedizione = indirizzoSpedizione;
	}

	public SpedizioneBean getSpedizione() {
		return spedizione;
	}

	public void setSpedizione(SpedizioneBean spedizione) {
		this.spedizione = spedizione;
	}
	
	
	
}
