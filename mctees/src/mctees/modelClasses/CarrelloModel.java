package mctees.modelClasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import mctees.beans.ArticoloBean;
import mctees.beans.MagliettaBean;
import mctees.beans.ScontoBean;
import mctees.beans.TemaBean;
import mctees.beans.VoceBean;

public class CarrelloModel extends Model
{
	public CarrelloModel()
	{
		super();
	}
	
	public VoceBean creaVoce(String codiceArticolo, int quantità)
	{	
		try
		{
			//Cerco l'articolo dato il codice + suoi dati associati
			PreparedStatement st=getConnection().prepareStatement("select a.codice as codiceArticolo, a.stock as stockArticolo, a.dataAggiunta as dataAggiuntaArticolo, "
				+ "m.codice as codiceMaglietta, m.prezzo as prezzoMaglietta, m.tipo as tipoMaglietta, m.sesso as sessoMaglietta, "
				+ "m.taglia as tagliaMaglietta, m.colore as coloreMaglietta, "
				+ "t.codice as codiceTema, t.nome as nomeTema, t.prezzo as prezzoTema, t.dataAggiunta as dataAggiuntaTema, "
				+ "s.codice as codiceSconto, s.dataInizio as dataInizioSconto, s.dataFine as dataFineSconto, s.percentuale as percentualeSconto "
				+ "from ((articolo a join maglietta m on a.maglietta=m.codice) join tema t on a.tema=t.codice) left join sconto s on t.sconto=s.codice "
				+ "where a.codice=?;");
			st.setString(1, codiceArticolo);
			ResultSet rs=st.executeQuery();
			rs.next();
			
			MagliettaBean maglietta=new MagliettaBean();
			TemaBean tema=new TemaBean();
			ScontoBean sconto=new ScontoBean();
			ArticoloBean articolo=new ArticoloBean();
			VoceBean voce=new VoceBean();
			
			maglietta.setCodice(rs.getString("codiceMaglietta"));
			maglietta.setPrezzo(rs.getDouble("prezzoMaglietta"));
			maglietta.setTipo(rs.getString("tipoMaglietta"));
			maglietta.setSesso(rs.getString("sessoMaglietta"));
			maglietta.setTaglia(rs.getString("tagliaMaglietta"));
			maglietta.setColore(rs.getString("coloreMaglietta"));
			
			tema.setCodice(rs.getString("codiceTema"));
			tema.setNome(rs.getString("nomeTema"));
			tema.setPrezzo(rs.getDouble("prezzoTema"));
			GregorianCalendar data=new GregorianCalendar();
			data.setTime(rs.getDate("dataAggiuntaTema"));
			tema.setDataAggiunta(data);
			
			sconto=null;
			if(rs.getString("codiceSconto")!=null)
			{
				sconto=new ScontoBean();
				sconto.setCodice(rs.getString("codiceSconto"));
				data=new GregorianCalendar();
				data.setTime(rs.getDate("dataInizioSconto"));
				sconto.setDataInizio(data);
				data=new GregorianCalendar();
				data.setTime(rs.getDate("dataFineSconto"));
				sconto.setDataFine(data);
				sconto.setPercentuale(rs.getDouble("percentualeSconto"));
				
				//Se non è attivo lo sconto su quel tema
				GregorianCalendar today=new GregorianCalendar();
				if (!((today.getTime().after(sconto.getDataInizio().getTime()))
						&& (today.getTime().before(sconto.getDataFine().getTime()))))
							sconto=null;
			}
			tema.setSconto(sconto);
			
			articolo.setCodice(rs.getString("codiceArticolo"));
			articolo.setStock(rs.getInt("stockArticolo"));
			data=new GregorianCalendar();
			data.setTime(rs.getDate("dataAggiuntaArticolo"));
			articolo.setDataAggiunta(data);
			articolo.setMaglietta(maglietta);
			articolo.setTema(tema);
			
			voce.setCodice("VAXXXX");
			voce.setArticolo(articolo);
			voce.setQuantità(quantità);
			
			//Questo prezzo è temporaneo, è soggetto a variazione finché non si conferma l'ordine con questa voce
			double prezzo;
			double prezzoMaglietta=voce.getArticolo().getMaglietta().getPrezzo();
			double prezzoTema=voce.getArticolo().getTema().getPrezzo();
			prezzo=prezzoMaglietta + prezzoTema;
			if(sconto!=null)
				prezzo=prezzo - (prezzoTema * voce.getArticolo().getTema().getSconto().getPercentuale() / 100);
			voce.setPrezzo(prezzo);
			
			//Manca l'aggiunta al DB della voce appena creata
			
			return voce;
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			return null;
		}
	}
	
	public void updateVoce(ArrayList<VoceBean> listaVoci, int i, int quantità) throws RuntimeException
	{
		//Se non sforo lo stock totale, altrimenti non fa niente
		if(quantità + listaVoci.get(i).getQuantità() <= listaVoci.get(i).getArticolo().getStock())
			listaVoci.get(i).setQuantità(listaVoci.get(i).getQuantità() + quantità);
		else
			throw new RuntimeException("Non è possibile aggiungere tale quantità al carrello");
	}
	
	public void updatePrezzi(ArrayList<VoceBean> listaVoci)
	{
		try
		{
			//Solo elenco dei Temi con sconti associati
			PreparedStatement st=getConnection().prepareStatement("select t.codice as codiceTema, "
				+ "s.codice as codiceSconto, s.datainizio as dataInizioSconto, s.dataFine as dataFineSconto, "
				+ "s.Percentuale as percentualeSconto "
				+ "from tema t join sconto s on t.sconto=s.codice;");
			ResultSet rs=st.executeQuery();
			ArrayList<TemaBean> listaTemi=new ArrayList<TemaBean>();
			while(rs.next())
			{
				TemaBean tema=new TemaBean();
				tema.setCodice(rs.getString("codiceTema"));
				ScontoBean sconto=new ScontoBean();
				sconto.setCodice(rs.getString("codiceSconto"));
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(rs.getDate("dataInizioSconto"));
				sconto.setDataInizio(data);
				data=new GregorianCalendar();
				data.setTime(rs.getDate("dataFineSconto"));
				sconto.setDataFine(data);
				sconto.setPercentuale(rs.getDouble("percentualeSconto"));
				tema.setSconto(sconto);
				
				listaTemi.add(tema);
			}
			
			//Scorro tutte le voci per aggiornare gli sconti
			int n=listaVoci.size();
			for(int i=0; i<n; i++)
			{
				VoceBean voce=listaVoci.get(i);				
				int m=listaTemi.size();
				int j=0;
				double prezzo;
				double prezzoMaglietta=voce.getArticolo().getMaglietta().getPrezzo();
				double prezzoTema=voce.getArticolo().getTema().getPrezzo();
				prezzo=prezzoMaglietta + prezzoTema;
				//Cerco se c'è uno sconto per quel tema dal DB
				while ((j<m) && !(voce.getArticolo().getTema().getCodice().equals(listaTemi.get(j).getCodice())))
					j++;
				if(j<m)	//Trovato uno sconto per quel tema dal DB
				{
					//Se è attivo, glielo assegno sovrascrivendo
					GregorianCalendar today=new GregorianCalendar();
					if ((today.getTime().after(listaTemi.get(j).getSconto().getDataInizio().getTime()))
						&& (today.getTime().before(listaTemi.get(j).getSconto().getDataFine().getTime())))
					{
						voce.getArticolo().getTema().setSconto(listaTemi.get(j).getSconto());
						double percentuale=voce.getArticolo().getTema().getSconto().getPercentuale();
						prezzo=prezzo - (prezzoTema * percentuale / 100);
					}
					//Se non è attivo, glielo tolgo
					else
						voce.getArticolo().getTema().setSconto(null);
				}
				//Sconto per quel tema dal DB NON TROVATO
				else
					voce.getArticolo().getTema().setSconto(null);
				voce.setPrezzo(prezzo);
				
				//Manca aggiornamento questa voce nel DB
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}
	
	public boolean updateQuantità(ArrayList<VoceBean> listaVoci)
	{
		try
		{
			boolean flag=false;	//se true almeno una voce è stata modificata
			//Elenco Articolo-Stock
			PreparedStatement st=getConnection().prepareStatement("SELECT codice, stock FROM articolo");
			ResultSet rs=st.executeQuery();
			while(rs.next())
			{
				String codiceArticolo=rs.getString("codice");
				int stock=rs.getInt("stock");
				
				int i=0;
				int n=listaVoci.size();
				while((i<n)&&(!listaVoci.get(i).getArticolo().getCodice().equals(codiceArticolo)))
					i++;
				//Trovato e la quantità supera lo stock presente
				if((i<n)&&(listaVoci.get(i).getQuantità()>stock))
				{
					listaVoci.get(i).setQuantità(stock);
					flag=true;
				}
			}
			return flag;
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			return false;
		}
	}
}