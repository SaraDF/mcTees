package mctees.modelClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import mctees.beans.ArticoloBean;
import mctees.beans.MagliettaBean;
import mctees.beans.ScontoBean;
import mctees.beans.TemaBean;

public class ArticoloModel extends Model
{
	public ArticoloModel()
	{
		super();
	}
	
	public TemaBean selectTema(String codiceTema)
	{
		try
		{
			Connection connection=Model.ds.getConnection();
			//Recuperare il nome del Tema e un'eventuale sconto
			PreparedStatement st=connection.prepareStatement("select t.codice as codiceTema, t.nome as nomeTema, t.prezzo as prezzoTema, t.dataAggiunta as dataAggiuntaTema, "
				+ "s.codice as codiceSconto, s.dataInizio as dataInizioSconto, s.dataFine as dataFineSconto, s.percentuale as percentualeSconto "
				+ "from tema t left join sconto s on t.sconto=s.codice "
				+ "where t.codice=?;");
			st.setString(1, codiceTema);
			ResultSet rs=st.executeQuery();
			TemaBean tema=null;
			if(rs.next())
			{
				tema=new TemaBean();
				tema.setCodice(rs.getString("codiceTema"));
				tema.setNome(rs.getString("nomeTema"));
				tema.setPrezzo(rs.getDouble("prezzoTema"));
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(rs.getDate("dataAggiuntaTema"));
				tema.setDataAggiunta(data);
				
				ScontoBean sconto=null;
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
			}
			rs.close();
			st.close();
			connection.close();
			return tema;
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			return null;
		}
	}
	
	public ArticoloBean selectArticolo(String codiceTema, String sesso, String tipo, String taglia, String colore)
	{
		try
		{
			Connection connection=Model.ds.getConnection();
			//Recuperare i dati da Articolo e Maglietta joinati dato un Tema. Con eventuale sconto sul tema.
			PreparedStatement st=connection.prepareStatement("select a.codice as codiceArticolo, a.stock as stockArticolo, a.dataAggiunta as dataAggiuntaArticolo, "
				+ "m.codice as codiceMaglietta, m.prezzo as prezzoMaglietta, m.tipo as tipoMaglietta, m.sesso as sessoMaglietta, "
				+ "m.taglia as tagliaMaglietta, m.colore as coloreMaglietta, "
				+ "t.codice as codiceTema, t.nome as nomeTema, t.prezzo as prezzoTema, t.dataAggiunta as dataAggiuntaTema, "
				+ "s.codice as codiceSconto, s.dataInizio as dataInizioSconto, s.dataFine as dataFineSconto, s.percentuale as percentualeSconto "
				+ "from ((articolo a join maglietta m on a.maglietta=m.codice) join tema t on a.tema=t.codice) left join sconto s on t.sconto=s.codice "
				+ "where a.tema=? and m.sesso=? and m.tipo=? and m.taglia=? and m.colore=?;");
			st.setString(1, codiceTema);
			st.setString(2, sesso);
			st.setString(3, tipo);
			st.setString(4, taglia);
			st.setString(5, colore);
			ResultSet rs=st.executeQuery();
			ArticoloBean articolo=null;
			if(rs.next())
			{
				MagliettaBean maglietta=new MagliettaBean();
				maglietta.setCodice(rs.getString("codiceMaglietta"));
				maglietta.setPrezzo(rs.getDouble("prezzoMaglietta"));
				maglietta.setTipo(rs.getString("tipoMaglietta"));
				maglietta.setSesso(rs.getString("sessoMaglietta"));
				maglietta.setTaglia(rs.getString("tagliaMaglietta"));
				maglietta.setColore(rs.getString("coloreMaglietta"));
				
				TemaBean tema=new TemaBean();
				tema.setCodice(rs.getString("codiceTema"));
				tema.setNome(rs.getString("nomeTema"));
				tema.setPrezzo(rs.getDouble("prezzoTema"));
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(rs.getDate("dataAggiuntaTema"));
				tema.setDataAggiunta(data);
				
				ScontoBean sconto=null;
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
				
				articolo=new ArticoloBean();
				articolo.setCodice(rs.getString("codiceArticolo"));
				articolo.setStock(rs.getInt("stockArticolo"));
				data=new GregorianCalendar();
				data.setTime(rs.getDate("dataAggiuntaArticolo"));
				articolo.setDataAggiunta(data);
				articolo.setMaglietta(maglietta);
				articolo.setTema(tema);
				
			}
			rs.close();
			st.close();
			connection.close();
			return articolo;
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<ArticoloBean> selectAllArticoli(String codiceTema)
	{	
		try
		{
			Connection connection=Model.ds.getConnection();
			//Recuperare i dati da Articolo e Maglietta joinati dato un Tema. Con eventuale sconto sul tema.
			PreparedStatement st=connection.prepareStatement("select a.codice as codiceArticolo, a.stock as stockArticolo, a.dataAggiunta as dataAggiuntaArticolo, "
				+ "m.codice as codiceMaglietta, m.prezzo as prezzoMaglietta, m.tipo as tipoMaglietta, m.sesso as sessoMaglietta, "
				+ "m.taglia as tagliaMaglietta, m.colore as coloreMaglietta, "
				+ "t.codice as codiceTema, t.nome as nomeTema, t.prezzo as prezzoTema, t.dataAggiunta as dataAggiuntaTema, "
				+ "s.codice as codiceSconto, s.dataInizio as dataInizioSconto, s.dataFine as dataFineSconto, s.percentuale as percentualeSconto "
				+ "from ((articolo a join maglietta m on a.maglietta=m.codice) join tema t on a.tema=t.codice) left join sconto s on t.sconto=s.codice "
				+ "where a.tema=?;");
			st.setString(1, codiceTema);
			ResultSet rs=st.executeQuery();
			ArrayList<ArticoloBean> list=new ArrayList<ArticoloBean>();
			while(rs.next())
			{
				MagliettaBean maglietta=new MagliettaBean();
				maglietta.setCodice(rs.getString("codiceMaglietta"));
				maglietta.setPrezzo(rs.getDouble("prezzoMaglietta"));
				maglietta.setTipo(rs.getString("tipoMaglietta"));
				maglietta.setSesso(rs.getString("sessoMaglietta"));
				maglietta.setTaglia(rs.getString("tagliaMaglietta"));
				maglietta.setColore(rs.getString("coloreMaglietta"));
				
				TemaBean tema=new TemaBean();
				tema.setCodice(rs.getString("codiceTema"));
				tema.setNome(rs.getString("nomeTema"));
				tema.setPrezzo(rs.getDouble("prezzoTema"));
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(rs.getDate("dataAggiuntaTema"));
				tema.setDataAggiunta(data);
				
				ScontoBean sconto=null;
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
				
				ArticoloBean articolo=new ArticoloBean();
				articolo.setCodice(rs.getString("codiceArticolo"));
				articolo.setStock(rs.getInt("stockArticolo"));
				data=new GregorianCalendar();
				data.setTime(rs.getDate("dataAggiuntaArticolo"));
				articolo.setDataAggiunta(data);
				articolo.setMaglietta(maglietta);
				articolo.setTema(tema);
				
				list.add(articolo);
			}
			rs.close();
			st.close();
			connection.close();
			return list;
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			return null;
		}
	}
}