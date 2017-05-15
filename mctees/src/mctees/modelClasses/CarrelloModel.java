package mctees.modelClasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			
			MagliettaBean maglietta=new MagliettaBean();
			TemaBean tema=new TemaBean();
			ScontoBean sconto=new ScontoBean();
			ArticoloBean articolo=new ArticoloBean();
			VoceBean voce=new VoceBean();
			if(rs.next())
			{
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
				}
				tema.setSconto(sconto);
				
				articolo.setCodice(rs.getString("codiceArticolo"));
				articolo.setStock(rs.getInt("stockArticolo"));
				data=new GregorianCalendar();
				data.setTime(rs.getDate("dataAggiuntaArticolo"));
				articolo.setDataAggiunta(data);
				articolo.setMaglietta(maglietta);
				articolo.setTema(tema);
			}
			
			voce.setCodice("VAXXXX");
			voce.setArticolo(articolo);
			voce.setQuantità(quantità);
			//Questo prezzo è temporaneo, è soggetto a variazione finché non si conferma l'ordine con questa voce
			double prezzo;
			double prezzoMaglietta=voce.getArticolo().getMaglietta().getPrezzo();
			double prezzoTema=voce.getArticolo().getTema().getPrezzo();
			if(sconto==null)
				prezzo=quantità * (prezzoMaglietta + prezzoTema);
			else
				prezzo=quantità * (prezzoMaglietta + (prezzoTema - (prezzoTema * voce.getArticolo().getTema().getSconto().getPercentuale() / 100)));
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
}