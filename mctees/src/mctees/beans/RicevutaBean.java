package mctees.beans;

public class RicevutaBean {

	private String codice;
	private OrdineBean ordine;
	private String descrizione;
	
	public RicevutaBean(){
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public OrdineBean getOrdine() {
		return ordine;
	}

	public void setOrdine(OrdineBean ordine) {
		this.ordine = ordine;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}	
	
	
}
