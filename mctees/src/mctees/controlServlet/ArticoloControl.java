package mctees.controlServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mctees.beans.TemaBean;
import mctees.modelClasses.ArticoloModel;

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
		String codiceTema=request.getParameter("codiceTema");
		if(codiceTema==null)
			response.sendRedirect(request.getContextPath() + "/catalogo");
		else
		{
			//Mando alla view il tema selezionato
			ArticoloModel tm=new ArticoloModel();
			TemaBean tema=tm.selectTema(codiceTema);
			
			request.setAttribute("tema", tema);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/ArticoloView.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}