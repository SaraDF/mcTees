package mctees.controlServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet
(
	name="CarrelloControl",
	urlPatterns={"/carrello"}
)
public class CarrelloControl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public void addToCarrello(String codiceTema, HttpServletRequest request, HttpServletResponse response)
	{
		String sesso=request.getParameter("sesso");
		String tipo=request.getParameter("tipo");
		String taglia=request.getParameter("taglia");
		String colore=request.getParameter("colore");
		int quantità=Integer.parseInt(request.getParameter("quantità"));
		
		//Si ricevono tutti i dati della request
		//Se la sessione non esiste, la si crea
		//Questi dati si inviano al CarrelloModel, invocandogli il metodo di CreaVoce
			//Si crea una voce (tramite VoceBean)
			//La si aggiunge al DB
			//Si ritorna la VoceBean
		//Si aggiunge alla sessione questa VoceBean ritornata
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String codiceTema=request.getParameter("codiceTema");
		
		//Ricevere l'articolo selezionato
		
		//Capire se add, remove, o show semplice...
		String action=request.getParameter("action");
		if(action.equals("add"))
		{
			addToCarrello(codiceTema, request, response);
			//Redirect ad Articolo che mostra solo un messaggio di avvenuta aggiunta
			response.sendRedirect(request.getContextPath() + "/articolo?codiceTema=" + codiceTema);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}