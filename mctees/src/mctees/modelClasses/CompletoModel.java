package mctees.modelClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mctees.beans.SpedizioneBean;

public class CompletoModel extends Model
{
	public CompletoModel()
	{
		super();
	}
	
	public SpedizioneBean selectSpedizione(String codiceSpedizione)
	{
		try
		{
			Connection connection=Model.ds.getConnection();
			//Recuperare la spedizione dato il codice.
			PreparedStatement st=connection.prepareStatement("select codice, nome, prezzo, giorni, descrizione"
				+ " from spedizione where codice=?;");
			st.setString(1, codiceSpedizione);
			ResultSet rs=st.executeQuery();
			
			SpedizioneBean spedizione=new SpedizioneBean();
			if(rs.next())
			{
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
}