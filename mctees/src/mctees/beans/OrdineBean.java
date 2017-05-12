package mctees.beans;

public class OrdineBean {

	private String codice;
	private ClienteBean cliente;
	
	public OrdineBean(){
		
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public ClienteBean getCliente() {
		return cliente;
	}

	public void setCliente(ClienteBean cliente) {
		this.cliente = cliente;
	}
	
	
	
}
