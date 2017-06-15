package mctees.ajaxServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import mctees.beans.ArticoloBean;
import mctees.modelClasses.ArticoloModel;

@WebServlet
(
	name="ArticoloAjax",
	urlPatterns={"/articoloAjax"}
)
public class ArticoloAjax extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if(request.getMethod().equals("GET"))
			response.sendRedirect(request.getContextPath() + "/catalogo");	
		else
		{
			try
			{
				String dataJSON=request.getParameter("data");
				JSONObject json=new JSONObject(dataJSON);
				String codiceTema=json.getString("codiceTema");
				String sesso=json.getString("sesso");
				String tipo=json.getString("tipo");
				String taglia=json.getString("taglia");
				String colore=json.getString("colore");
				
				ArticoloModel am=new ArticoloModel();
				ArticoloBean articolo=am.selectArticolo(codiceTema, sesso, tipo, taglia, colore);
				if(articolo==null)
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				else
				{
					//Mando come JSON il codice dell'articolo, il prezzo finale (con sconto) e lo stock
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json, charset=UTF-8");
					String codiceArticolo=articolo.getCodice();
					double prezzo=articolo.getMaglietta().getPrezzo() + articolo.getTema().getPrezzo();
					if(articolo.getTema().getSconto()!=null)
						prezzo=prezzo - (articolo.getTema().getPrezzo() * articolo.getTema().getSconto().getPercentuale() / 100);		
					int stock=articolo.getStock();
					
					JSONObject articoloJSON=new JSONObject();
					articoloJSON.put("codiceArticolo", codiceArticolo);
					articoloJSON.put("prezzo", prezzo);
					articoloJSON.put("stock", stock);
					response.getWriter().append(articoloJSON.toString());			 
				}
			}
			catch(JSONException jsone)
			{
				response.sendRedirect(request.getContextPath() + "/catalogo");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}