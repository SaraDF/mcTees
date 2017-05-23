package mctees.beans;

import java.io.Serializable;

public class VoceBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String codice;
	private ArticoloBean articolo;
	private int quantit�;
	private double prezzo;
	
	public VoceBean(){
		
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public ArticoloBean getArticolo() {
		return articolo;
	}

	public void setArticolo(ArticoloBean articolo) {
		this.articolo = articolo;
	}

	public int getQuantit�() {
		return quantit�;
	}

	public void setQuantit�(int quantit�) {
		this.quantit� = quantit�;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
}
