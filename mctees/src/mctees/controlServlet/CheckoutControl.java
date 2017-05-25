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

import mctees.beans.SpedizioneBean;
import mctees.beans.VoceBean;
import mctees.modelClasses.CheckoutModel;

@WebServlet
(
	name="CheckoutControl",
	urlPatterns={"/checkout"}
)
public class CheckoutControl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session=request.getSession();
		ArrayList<VoceBean> listaVoci=(ArrayList<VoceBean>) session.getAttribute("listaVoci");
		
		//Recuperare dati sulle spedizioni, sulle carte e sul cliene
		//Per adesso solo dati sulle spedizioni
		CheckoutModel cm=new CheckoutModel();
		ArrayList<SpedizioneBean> listaSpedizioni=cm.selectAllSpedizioni();
		
		request.setAttribute("listaVoci", listaVoci);
		request.setAttribute("listaSpedizioni", listaSpedizioni);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/CheckoutView.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}