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
	
	public void addToCarrello(ArrayList<VoceBean> listaVoci, String codiceArticolo, int quantità, HttpServletRequest request)
	{
		CarrelloModel cm=new CarrelloModel();
		
		//Scorro alla ricerca di una voce dello stesso articolo
		int i=0;
		int n=listaVoci.size();
		while((i<n)&&(!listaVoci.get(i).getArticolo().getCodice().equals(codiceArticolo)))
			i++;
			
		//Voce con quell'articolo già esistente: aggiorno
		if(i<n)
		{
			try
			{
				cm.updateVoce(listaVoci, i, quantità);
			}
			catch(RuntimeException rte)
			{
				//Tipo boh, mostrare qualcosa all'utente tipo un boh
				rte.printStackTrace();
			}
		}
		//Voce con quell'articolo non esistente: creo nuova
		else
		{
			VoceBean voce=cm.creaVoce(codiceArticolo, quantità);
			listaVoci.add(voce);
		}
		
		//Manca la creazione/modifica di un ordine non confermato nel DB
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session=request.getSession();
		ArrayList<VoceBean> listaVoci=(ArrayList<VoceBean>) session.getAttribute("listaVoci");
		//Capire se add, show...
		String action=request.getParameter("action");
		if(action.equals("add"))
		{
			if(listaVoci==null)	//Se non esiste già la lista delle voci nella sessione la si crea e si assegna
			{
				listaVoci=new ArrayList<VoceBean>();
				session.setAttribute("listaVoci", listaVoci);
			}
			
			//Ricezione articolo coinvolto
			String codiceArticolo=request.getParameter("codiceArticolo");
			int quantità=Integer.parseInt(request.getParameter("quantità"));
			addToCarrello(listaVoci, codiceArticolo, quantità, request);
			
			//Redirect ad Articolo che mostra solo un messaggio di avvenuta aggiunta
			String codiceTema=request.getParameter("codiceTema");
			response.sendRedirect(request.getContextPath() + "/articolo?codiceTema=" + codiceTema);
		}
		if(action.equals("show"))
		{
			//Se Carrello non vuoto
			if(listaVoci!=null)
			{
				//Aggiornare i prezzi se sono stati applicati gli sconti e controlla lo stock
				CarrelloModel cm=new CarrelloModel();
				cm.updatePrezzi(listaVoci);
				if(cm.updateQuantità(listaVoci))	//Lo esegue e vede l'esito
				{
					//Boh avvisa l'utente che è stata fatta sta modifica
				}
			}
			
			//Redirect a CarrelloView passandogli la lista delle voci prese dalla sessione
			request.setAttribute("listaVoci", listaVoci);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/CarrelloView.jsp");
			dispatcher.forward(request, response);
		}
		if(action.equals("decrease") || action.equals("increase"))
		{
			if(listaVoci!=null)
			{
				String codiceArticolo=request.getParameter("codiceArticolo");
				//Ricerco la voce e le cambio la quantità
				int i=0;
				int n=listaVoci.size();
				while((i<n)&&(!listaVoci.get(i).getArticolo().getCodice().equals(codiceArticolo)))
					i++;
				if(i<n)
				{
					if(action.equals("decrease") && listaVoci.get(i).getQuantità()>1)
						listaVoci.get(i).setQuantità(listaVoci.get(i).getQuantità()-1);
					if(action.equals("increase"))
						listaVoci.get(i).setQuantità(listaVoci.get(i).getQuantità()+1);
				}
			}
			
			//Poi mostra regolarmente il carrello con le modifiche avvenute
			response.sendRedirect(request.getContextPath() + "/carrello?action=show");
		}
		if(action.equals("remove"))
		{
			if(listaVoci!=null)
			{
				String codiceArticolo=request.getParameter("codiceArticolo");
				//Ricerco la voce e la elimino
				int i=0;
				int n=listaVoci.size();
				while((i<n)&&(!listaVoci.get(i).getArticolo().getCodice().equals(codiceArticolo)))
					i++;
				if(i<n)
				{
					listaVoci.remove(i);
					if(listaVoci.size()==0)
						session.removeAttribute("listaVoci");
				}
			}
			
			//Poi mostra regolarmente il carrello con le modifiche avvenute
			response.sendRedirect(request.getContextPath() + "/carrello?action=show");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}