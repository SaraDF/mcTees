package mctees.controlServlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mctees.beans.VoceBean;
import mctees.modelClasses.CarrelloModel;

@WebServlet
(
	name="CarrelloControl",
	urlPatterns={"/carrello"}
)
public class CarrelloControl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public void addToCarrello(String codiceArticolo, int quantità, HttpServletRequest request, HttpServletResponse response)
	{
		CarrelloModel cm=new CarrelloModel();
		VoceBean voce=cm.creaVoce(codiceArticolo, quantità);
		
		HttpSession session=request.getSession();
		ArrayList<VoceBean> listaVoci=(ArrayList<VoceBean>) session.getAttribute("listaVoci");
		if(listaVoci==null)
		{
			listaVoci=new ArrayList<VoceBean>();
			session.setAttribute("listaVoci", listaVoci);
		}
		listaVoci.add(voce);
		//Manca la creazione/modifica di un ordine non confermato nel DB
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//Capire se add, remove, o show semplice...
		String action=request.getParameter("action");
		if(action.equals("add"))
		{
			//Ricezione articolo coinvolto
			String codiceArticolo=request.getParameter("codiceArticolo");
			int quantità=Integer.parseInt(request.getParameter("quantità"));
			addToCarrello(codiceArticolo, quantità, request, response);
			
			//Redirect ad Articolo che mostra solo un messaggio di avvenuta aggiunta
			String codiceTema=request.getParameter("codiceTema");
			response.sendRedirect(request.getContextPath() + "/articolo?codiceTema=" + codiceTema);
		}
		else
			if(action.equals("show"))
			{
				//Redirect a CarrelloView passandogli la lista delle voci prese dalla sezione
				HttpSession session=request.getSession();
				ArrayList<VoceBean> listaVoci=(ArrayList<VoceBean>) session.getAttribute("listaVoci");
				request.setAttribute("listaVoci", listaVoci);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/CarrelloView.jsp");
				dispatcher.forward(request, response);
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}