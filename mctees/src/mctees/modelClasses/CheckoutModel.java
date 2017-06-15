package mctees.modelClasses;

import java.sql.Connection;
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
	
	public SpedizioneBean selectSpedizione(String codiceSpedizione)
	{
		try
		{
			Connection connection=Model.ds.getConnection();
			//Recuperare una certa Spedizione.
			PreparedStatement st=connection.prepareStatement("select codice, nome, prezzo, giorni, descrizione"
					+ " from spedizione where codice=?;");
			st.setString(1, codiceSpedizione);
			ResultSet rs=st.executeQuery();
			SpedizioneBean spedizione=null;
			if(rs.next())
			{
				spedizione=new SpedizioneBean();
				spedizione.setCodice(rs.getString("codice"));
				spedizione.setNome(rs.getString("nome"));
				spedizione.setPrezzo(rs.getDouble("prezzo"));
				spedizione.setGiorni(rs.getInt("giorni"));
				spedizione.setDescrizione(rs.getString("descrizione"));
			}
			rs.close();
			st.close();
			connection.close();
			return spedizione;
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<SpedizioneBean> selectAllSpedizioni()
	{
		try
		{
			Connection connection=Model.ds.getConnection();
			//Recuperare i tutti i dati Spedizione.
			PreparedStatement st=connection.prepareStatement("select codice, nome, prezzo, giorni, descrizione"
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