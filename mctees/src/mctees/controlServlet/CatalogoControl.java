package mctees.controlServlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mctees.beans.TemaBean;
import mctees.modelClasses.CatalogoModel;

@WebServlet
(
	name="CatalogoControl",
	urlPatterns={"/catalogo"}
)
public class CatalogoControl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//Accesso al DB: query per ottenere i temi, per adesso ritorniamoli tutti
		CatalogoModel cm=new CatalogoModel();
		//Criterio di ricerca scelto (nella request)
		ArrayList<TemaBean> list=cm.selectAllTemi();
		
		request.setAttribute("list", list);
		//Mostra la view del Catalogo
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/CatalogoView.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}