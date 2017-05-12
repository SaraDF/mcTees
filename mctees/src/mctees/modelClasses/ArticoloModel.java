package mctees.modelClasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import mctees.beans.ArticoloBean;
import mctees.beans.MagliettaBean;

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
			//Recuperare i dati da Articolo e Maglietta joinati dato un tema
			PreparedStatement st=getConnection().prepareStatement("select * from articolo a join maglietta m on a.maglietta=m.codice" + " where tema=?;");
			st.setString(1, codiceTema);
			ResultSet rs=st.executeQuery();
			ArrayList<ArticoloBean> list=new ArrayList<ArticoloBean>();
			while(rs.next())
			{
				MagliettaBean mb=new MagliettaBean();
				mb.setCodice(rs.getString("a.Maglietta"));
				mb.setPrezzo(rs.getDouble("m.Prezzo"));
				mb.setTipo(rs.getString("m.Tipo"));
				mb.setSesso(rs.getString("m.Sesso"));
				mb.setTaglia(rs.getString("m.Taglia"));
				mb.setColore(rs.getString("m.Colore"));
				
				ArticoloBean ab=new ArticoloBean();
				ab.setCodice(rs.getString("a.Codice"));
				ab.setStock(rs.getInt("a.Stock"));
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(rs.getDate("a.DataAggiunta"));
				ab.setDataAggiunta(data);
				ab.setMaglietta(mb);
				ab.setTema(null);	//non serve il tema in questo contesto
				
				list.add(ab);
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