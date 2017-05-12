package mctees.beans;

import java.util.GregorianCalendar;

public class TemaBean
{
	private String codice;
	private String nome; 
	private double prezzo;
	private GregorianCalendar dataAggiunta;
	private boolean edizioneLimitata;
	//Mancano CatalogoBean e ScontoBean
	
	public TemaBean()
	{
		
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public GregorianCalendar getDataAggiunta() {
		return dataAggiunta;
	}

	public void setDataAggiunta(GregorianCalendar dataAggiunta) {
		this.dataAggiunta = dataAggiunta;
	}

	public boolean isEdizioneLimitata() {
		return edizioneLimitata;
	}

	public void setEdizioneLimitata(boolean edizioneLimitata) {
		this.edizioneLimitata = edizioneLimitata;
	}
}
