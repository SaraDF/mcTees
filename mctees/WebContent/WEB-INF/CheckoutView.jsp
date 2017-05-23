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
		<link rel="stylesheet" href="src/styles/checkout.css">
		<link rel="shortcut icon" href="src/images/logo2.png">
		<title>Checkout - MCTees</title>
	</head>
	<body>
		<%@ include file="fragments/header.html"%>
		
		<div id="mainCheckout">
		<%	ArrayList<VoceBean> listaVoci=(ArrayList<VoceBean>) request.getAttribute("listaVoci");
			if(listaVoci!=null)
			{%>
				<form method="post" action="conferma" id="modalitàCheckout">
					<table>
						<tr><td><input type="radio" name="spedizione" checked>Prioritaria 3.00 &euro;</td></tr>
						<tr><td><input type="radio" name="spedizione">Raccomandata 5.00 &euro;</td></tr>
						<tr><td><button type="submit">Conferma ordine</button></td></tr>
					</table>
				</form>
				<div id="recapCheckout">
				<%	int n=listaVoci.size();
					double totale=0;
					for(int i=0; i<n; i++)
					{
						VoceBean voce=listaVoci.get(i);
						totale+=voce.getPrezzo()*voce.getQuantità();
				%>		<div class="rowRecap">
							<section class="fotoVoce">
								<img src="src/images/magliagiochi.png">
							</section>
							<section class="infoVoce">
								<strong><%=voce.getArticolo().getTema().getNome()%></strong><br>
								Sesso: <%=voce.getArticolo().getMaglietta().getSesso()%><br>
								Tipo: <%=voce.getArticolo().getMaglietta().getTipo()%><br>
								Taglia: <%=voce.getArticolo().getMaglietta().getTaglia()%><br>
								Colore: <%=voce.getArticolo().getMaglietta().getColore()%><br>
								Quantità: <%=voce.getQuantità()%><br>
								Prezzo: <%=voce.getPrezzo()*voce.getQuantità()%> &euro;
							</section>
						</div>
				<%	} %>
				
					<div id="lowerRecap">
						Totale ordine: <%=totale %> &euro;
					</div>	
				</div>		
			<% }%>
			</div>
		<%@ include file="fragments/footer.html"%> 
	</body>
</html>