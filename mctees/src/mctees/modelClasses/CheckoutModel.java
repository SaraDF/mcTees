package mctees.modelClasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mctees.beans.SpedizioneBean;

public class CheckoutModel extends Model
{
	public CheckoutModel()
	{
		super();
	}
	
	public ArrayList<SpedizioneBean> selectAllSpedizioni()
	{
		try
		{
			//Recuperare i tutti i dati Spedizione.
			PreparedStatement st=getConnection().prepareStatement("select codice, nome, prezzo, giorni, descrizione"
					+ " from spedizione;");
			ResultSet rs=st.executeQuery();
			ArrayList<SpedizioneBean> list=new ArrayList<SpedizioneBean>();
			while(rs.next())
			{
				SpedizioneBean spedizione=new SpedizioneBean();
				spedizione.setCodice(rs.getString("codice"));
				spedizione.setNome(rs.getString("nome"));
				spedizione.setPrezzo(rs.getDouble("prezzo"));
				spedizione.setGiorni(rs.getInt("giorni"));
				spedizione.setDescrizione(rs.getString("descrizione"));
				
				list.add(spedizione);
			}
			
			return list;
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			return null;
		}
	}
}