package mctees.controlServlet;

import java.io.IOException;

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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//Si ricevono tutti i dati della request
		//Se la sessione non esiste, la si crea
		//Questi dati si inviano al CarrelloModel, invocandogli il metodo di CreaVoce
			//Si crea una voce (tramite VoceBean)
			//La si aggiunge al DB
			//Si ritorna la VoceBean
		//Si aggiunge alla sessione questa VoceBean ritornata
		
		//Redirect ad Articolo che mostra solo un messaggio di avvenuta aggiunta conferma
		response.sendRedirect("articolo");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}