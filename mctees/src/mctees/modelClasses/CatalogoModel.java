package mctees.modelClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import mctees.beans.CategoriaBean;
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
			Connection connection=Model.ds.getConnection();
			PreparedStatement st=connection.prepareStatement("select t.codice, t.nome, t.prezzo,"
				+ " t.dataaggiunta, t.edizionelimitata,"
				+ " c.codice, c.nome"
				+ " from tema t join categoria c"
				+ " on t.categoria=c.codice;");
			ResultSet rs=st.executeQuery();
			
			ArrayList<TemaBean> listaTemi=new ArrayList<TemaBean>();
			while(rs.next())
			{
				CategoriaBean categoria=new CategoriaBean();
				categoria.setCodice(rs.getString("c.codice"));
				categoria.setNome(rs.getString("c.nome"));
				
				TemaBean tema=new TemaBean();
				tema.setCodice(rs.getString("t.codice"));
				tema.setNome(rs.getString("t.nome"));
				tema.setPrezzo(rs.getDouble("t.prezzo"));
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(rs.getDate("t.dataaggiunta"));
				tema.setDataAggiunta(data);
				tema.setEdizioneLimitata(rs.getBoolean("t.edizionelimitata"));
				tema.setCategoria(categoria);
				
				listaTemi.add(tema);
			}
			rs.close();
			st.close();
			connection.close();
			return listaTemi;
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<TemaBean> selectTema(String nomeCategoria)
	{	
		try
		{
			Connection connection=Model.ds.getConnection();
			PreparedStatement st=connection.prepareStatement("select t.codice, t.nome, t.prezzo,"
				+ " t.dataaggiunta, t.edizionelimitata,"
				+ " c.codice, c.nome"
				+ " from tema t join categoria c on t.categoria=c.codice"
				+ " where c.nome=?;");
			st.setString(1, nomeCategoria);
			ResultSet rs=st.executeQuery();
			ArrayList<TemaBean> listaTemi=new ArrayList<TemaBean>();
			while(rs.next())
			{
				CategoriaBean categoria=new CategoriaBean();
				categoria.setCodice(rs.getString("c.codice"));
				categoria.setNome(rs.getString("c.nome"));
				
				TemaBean tema=new TemaBean();
				tema.setCodice(rs.getString("t.codice"));
				tema.setNome(rs.getString("t.nome"));
				tema.setPrezzo(rs.getDouble("t.prezzo"));
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(rs.getDate("t.dataaggiunta"));
				tema.setDataAggiunta(data);
				tema.setEdizioneLimitata(rs.getBoolean("t.edizionelimitata"));
				tema.setCategoria(categoria);
				
				listaTemi.add(tema);
			}
			rs.close();
			st.close();
			connection.close();
			return listaTemi;
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			return null;
		}
	}
}