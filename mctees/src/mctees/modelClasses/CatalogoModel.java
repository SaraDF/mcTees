package mctees.modelClasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import mctees.beans.TemaBean;

public class CatalogoModel extends Model
{	
	public CatalogoModel()
	{
		super();
	}
	
	public ArrayList<TemaBean> selectAllTemi()
	{	
		try
		{
			PreparedStatement st=getConnection().prepareStatement("select * from " + "tema" + ";");
			ResultSet rs=st.executeQuery();
			ArrayList<TemaBean> list=new ArrayList<TemaBean>();
			while(rs.next())
			{
				TemaBean tema=new TemaBean();
				tema.setCodice(rs.getString("Codice"));
				tema.setNome(rs.getString("Nome"));
				tema.setPrezzo(rs.getDouble("Prezzo"));
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(rs.getDate("DataAggiunta"));
				tema.setDataAggiunta(data);
				tema.setEdizioneLimitata(rs.getBoolean("EdizioneLimitata"));
				list.add(tema);
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