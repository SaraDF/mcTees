package mctees.modelClasses;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class Model
{
	public static DataSource ds;
	static
	{
		try
		{
			Context initCtx=new InitialContext();
			Context envCtx=(Context) initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mctees");
		}
		catch(NamingException ne)
		{
			ne.printStackTrace();
		}
	}
}