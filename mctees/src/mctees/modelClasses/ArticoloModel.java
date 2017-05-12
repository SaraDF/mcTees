package mctees.modelClasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mctees.beans.ArticoloBean;

public class ArticoloModel extends Model
{
	public ArticoloModel()
	{
		super();
	}
	
	public ArrayList<ArticoloBean> selectAllArticoli(String codiceTema)
	{	
		try
		{
			PreparedStatement st=getConnection().prepareStatement("select * from " + "articolo" + "where tema=?;");
			st.setString(0, codiceTema);
			ResultSet rs=st.executeQuery();
			ArrayList<ArticoloBean> list=new ArrayList<ArticoloBean>();
			while(rs.next())
			{
				//Recuperare i dati da Articolo e Maglietta
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