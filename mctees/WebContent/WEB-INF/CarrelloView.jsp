<%@ page
		language="java"
		contentType="text/html; charset=ISO-8859-1"
    	pageEncoding="ISO-8859-1"
    	import="java.util.ArrayList, mctees.beans.VoceBean"
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="src/styles/general.css">
		<link rel="stylesheet" href="src/styles/header.css">
		<link rel="stylesheet" href="src/styles/footer.css">
		<link rel="stylesheet" href="src/styles/carrello.css">
		<link rel="shortcut icon" href="src/images/logo2.png">
		<title>Carrello - MCTees</title>
	</head>
	<body>
		<%@ include file="fragments/header.html"%> 
		
		<div id="mainCarrello">
		<%	ArrayList<VoceBean> listaVoci=(ArrayList<VoceBean>) request.getAttribute("listaVoci");
			if(listaVoci!=null)
			{%>
				<div id="upperCarrello">
					<span id="itemSpan">Item</span>
					<span id="prezzoSpan">Prezzo</span>
					<span id="quantitàSpan">Quantità</span>
					<span id="totaleSpan">Totale</span>
				</div>
				
				<div id="centralCarrello">
				<%	int n=listaVoci.size();
					double totale=0;
					for(int i=0; i<n; i++)
					{
						VoceBean voce=listaVoci.get(i);
						totale+=voce.getPrezzo()*voce.getQuantità();
				%>		<div class="rowCarrello">
							<section class="fotoVoce">
								<img src="src/images/magliagiochi.png">
							</section>
							<section class="infoArticoloVoce">
								Nome: <%=voce.getArticolo().getTema().getNome()%><br>
								Sesso: <%=voce.getArticolo().getMaglietta().getSesso()%><br>
								Tipo: <%=voce.getArticolo().getMaglietta().getTipo()%><br>
								Taglia: <%=voce.getArticolo().getMaglietta().getTaglia()%><br>
								Colore: <%=voce.getArticolo().getMaglietta().getColore()%>
							</section>
							<section>
								<%=voce.getPrezzo()%> &euro;
							</section>
							<section>
								<form method="post" action="carrello?action=show">
									<button type="submit" formaction="carrello?action=decrease&codiceArticolo=<%=voce.getArticolo().getCodice()%>">-</button>
									&nbsp;<%=voce.getQuantità()%>&nbsp;
									<button type="submit" formaction="carrello?action=increase&codiceArticolo=<%=voce.getArticolo().getCodice()%>">+</button>
								</form>
							</section>
							<section>
								<%=voce.getPrezzo()*voce.getQuantità()%> &euro;
							</section>
							<section>
								<form method="post" action="carrello?action=remove&codiceArticolo=<%=voce.getArticolo().getCodice()%>">
									<button type="submit">Rimuovi</button>
								</form>
							</section>
						</div>	
				<%	} %>
				</div>
			
				<div id="lowerCarrello">
					<span><%=totale %> &euro;</span>
					<form method="post" action="checkout">
						<button type="submit">Checkout</button>
					</form>
				</div>
		<% }%>
		</div>

		<%@ include file="fragments/footer.html"%> 
	</body>
</html>