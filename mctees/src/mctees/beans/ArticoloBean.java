package mctees.beans;

import java.util.GregorianCalendar;

public class ArticoloBean {
	
	private String codice;
	private int stock;
	private GregorianCalendar dataAggiunta;
	private TemaBean tema;
	private MagliettaBean maglietta;
	
	public ArticoloBean(){
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public GregorianCalendar getDataAggiunta() {
		return dataAggiunta;
	}

	public void setDataAggiunta(GregorianCalendar dataAggiunta) {
		this.dataAggiunta = dataAggiunta;
	}

	public TemaBean getTema() {
		return tema;
	}

	public void setTema(TemaBean tema) {
		this.tema = tema;
	}

	public MagliettaBean getMaglietta() {
		return maglietta;
	}

	public void setMaglietta(MagliettaBean maglietta) {
		this.maglietta = maglietta;
	}
	
	
	
}
