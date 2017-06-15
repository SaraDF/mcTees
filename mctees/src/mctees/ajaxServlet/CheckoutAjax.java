package mctees.ajaxServlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mctees.beans.ArticoloBean;
import mctees.beans.SpedizioneBean;
import mctees.modelClasses.ArticoloModel;
import mctees.modelClasses.CheckoutModel;

@WebServlet
(
	name="CheckoutControlAjax",
	urlPatterns={"/checkoutAjax"}
)
public class CheckoutAjax extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if(request.getMethod().equals("GET"))
			response.sendRedirect(request.getContextPath() + "/checkout");
		else
		{
			//Estrae codice spedizione e ne ricerca il prezzo e lo ritorna come json
			try
			{
				String dataJSON=request.getParameter("data");
				JSONObject json=new JSONObject(dataJSON);
				String codiceSpedizione=json.getString("codiceSpedizione");
				
				CheckoutModel cm=new CheckoutModel();
				SpedizioneBean spedizione=cm.selectSpedizione(codiceSpedizione);
				if(spedizione==null)
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);	//Difficile
				else
				{
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json, charset=UTF-8");
					double prezzoSpedizione=spedizione.getPrezzo();
					
					JSONObject articoloJSON=new JSONObject();
					articoloJSON.put("prezzoSpedizione", prezzoSpedizione);
					response.getWriter().append(articoloJSON.toString());
				}
			}
			catch(JSONException jsone)
			{
				response.sendRedirect(request.getContextPath() + "/checkout");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}