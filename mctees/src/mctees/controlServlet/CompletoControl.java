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
import mctees.modelClasses.CompletoModel;

@WebServlet
(
	name="CompletoControl",
	urlPatterns={"/completo"}
)
public class CompletoControl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session=request.getSession();
		ArrayList<VoceBean> listaVoci=(ArrayList<VoceBean>) session.getAttribute("listaVoci");
		
		String cognome=request.getParameter("cognome");
		
		//Se uno dei due è null, significa che non si ha usato il form del checkout per accedere qui
		if(cognome!=null && listaVoci!=null)
		{
			String nome=request.getParameter("nome");
			String indirizzo=request.getParameter("indirizzo");
			String cellulare=request.getParameter("cellulare");
			String città=request.getParameter("città");
			String provincia=request.getParameter("provincia");
			String cap=request.getParameter("cap");
			String numeroCarta=request.getParameter("numeroCarta");
			String proprietarioCarta=request.getParameter("proprietarioCarta");
			
			//Accesso alla lista delle spedizioni e selezione di quella scelta
			String codiceSpedizione=request.getParameter("spedizione");
			CompletoModel cm=new CompletoModel();
			SpedizioneBean spedizione=cm.selectSpedizione(codiceSpedizione);
			
			//Accedo al carrello per avere il totale
			int n=listaVoci.size();
			double totale=0;
			for(int i=0; i<n; i++)
			{
				VoceBean voce=listaVoci.get(i);
				totale+=voce.getPrezzo()*voce.getQuantità();
			}
			totale+=spedizione.getPrezzo();
			
			//Mettere nella request e inoltrare al view
			request.setAttribute("cognome", cognome);
			request.setAttribute("nome", nome);
			request.setAttribute("indirizzo", indirizzo);
			request.setAttribute("città", città);
			request.setAttribute("provincia", provincia);
			request.setAttribute("cap", cap);
			request.setAttribute("spedizione", spedizione);
			request.setAttribute("totale", totale);
			request.setAttribute("listaVoci", listaVoci);
			
			//Svuota carrello
			session.removeAttribute("listaVoci");
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/CompletoView.jsp");
			dispatcher.forward(request, response);
		}
		else
		{
			//Accesso diretto negato
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}