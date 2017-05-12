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
	name="ArticoloControl",
	urlPatterns={"/articolo"}
)
public class ArticoloControl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//Invoca i metodi di model per avere una lista di ArticoloBean da passare al View
		
		//Mostra la view dell'Articolo
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/ArticoloView.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}