package mctees.beans;

public class ClienteBean {
	
	private UtenteBean utente;
	private double saldo;
	private String indirizzoConsegna;
	
	public ClienteBean(){
	}

	public UtenteBean getUtente() {
		return utente;
	}

	public void setUtente(UtenteBean utente) {
		this.utente = utente;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getIndirizzoConsegna() {
		return indirizzoConsegna;
	}

	public void setIndirizzoConsegna(String indirizzoConsegna) {
		this.indirizzoConsegna = indirizzoConsegna;
	}
	
	
	
}
