package mctees.beans;

import java.util.GregorianCalendar;

public class AggiuntaBean {

	private WishlistBean wishlist;
	private ArticoloBean articolo;
	private GregorianCalendar dataAggiunta;
	
	public AggiuntaBean(){
		
	}

	public WishlistBean getWishlist() {
		return wishlist;
	}

	public void setWishlist(WishlistBean wishlist) {
		this.wishlist = wishlist;
	}

	public ArticoloBean getArticolo() {
		return articolo;
	}

	public void setArticolo(ArticoloBean articolo) {
		this.articolo = articolo;
	}

	public GregorianCalendar getDataAggiunta() {
		return dataAggiunta;
	}

	public void setDataAggiunta(GregorianCalendar dataAggiunta) {
		this.dataAggiunta = dataAggiunta;
	}
	
	
	
}
