<%@ page
		language="java"
		contentType="text/html; charset=ISO-8859-1"
    	pageEncoding="ISO-8859-1"
    	import="java.util.ArrayList, mctees.beans.VoceBean, mctees.beans.SpedizioneBean"
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
		<%! double totale; %>
		
		<div id="mainCheckout">
		<%	ArrayList<VoceBean> listaVoci=(ArrayList<VoceBean>) request.getAttribute("listaVoci");
			if(listaVoci!=null)
			{%>
				<form name="form" method="post" action="completo" id="modalitàCheckout" onsubmit="return validateForm()">
					<table>
						<tr>
							<td colspan="2"><strong>Dati consegna</strong></td>
						</tr>
						<tr>
							<td>Cognome</td>
							<td><input type="text" name="cognome"></td>
						</tr>
						<tr>
							<td>Nome</td>
							<td><input type="text" name="nome"></td>
						</tr>
						<tr>
							<td>Indirizzo</td>
							<td><input type="text" name="indirizzo"></td>
						</tr>
						<tr>
							<td>Cellulare</td>
							<td><input type="text" name="cellulare"></td>
						</tr>
						<tr>
							<td>Città</td>
							<td><input type="text" name="città"></td>
							<td>Provincia</td>
							<td><input type="text" name="provincia"></td>
							<td>CAP</td>
							<td><input type="text" name="cap"></td>
						</tr>
						
						<tr>
							<td colspan="2"><strong>Modalità spedizione</strong></td>
						</tr>
					<% ArrayList<SpedizioneBean> listaSpedizioni=(ArrayList<SpedizioneBean>) request.getAttribute("listaSpedizioni");
						int n=listaSpedizioni.size();
						for(int i=0; i<n; i++)
						{	
							SpedizioneBean spedizione=listaSpedizioni.get(i); %>
							<tr>
								<td><input type="radio" name="spedizione" value="<%=spedizione.getCodice() %>" onClick="updatePrezzi();"></td>
								<td><%=spedizione.getNome()%> (<%=spedizione.getPrezzo()%> &euro;, <%=spedizione.getGiorni()%> Giorni)</td>
							</tr>
					<%	} %>
						
						<tr>
							<td colspan="2"><strong>Modalità pagamento</strong></td>
						</tr>
						<tr>
							<td>Tipo Carta</td>
							<td>
								<select name="tipoCarta">
									<option value="VISA">VISA</option>
									<option value="Master Card">Master Card</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Numero carta</td>
							<td><input type="text" name="numeroCarta"></td>
						</tr>
						<tr>
							<td>Proprietario carta</td>
							<td><input type="text" name="proprietarioCarta"></td>
						</tr>
						<tr>
							<td>Data scadenza carta</td>
							<td><input type="text" name="scadenzaCarta"></td>
						</tr>
						<tr>
							<td>CVC</td>
							<td><input type="text" name="cvcCarta"></td>
						</tr>
						<tr><td><button type="submit">Conferma ordine</button></td></tr>
					</table>
				</form>
				<div id="recapCheckout">
				<%	int m=listaVoci.size();
					totale=0;
					for(int i=0; i<m; i++)
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
						<span>Totale articoli: <%=totale %> &euro;</span><br>
						+<br>
						<span>Prezzo spedizione: <span id="prezzoSpedizione">0.00</span>&euro;</span><br>
						=<br>
						<span>Totale ordine: <span id="prezzoTotale">0.00</span>&euro;</span>
					</div>	
				</div>		
		<% }%>
		</div>
		<%@ include file="fragments/footer.html"%>
		<script>
			function updatePrezzi()
			{
				var radioSpedizioni=document.getElementsByName("spedizione");
				var i=0;
				var n=radioSpedizioni.length;
				while(i<n && !radioSpedizioni[i].checked)
					i++;
				if(i<n)
				{
					document.getElementById("prezzoSpedizione").innerHTML=listaSpedizioni[i].prezzo+"";
					document.getElementById("prezzoTotale").innerHTML=<%=totale%> + listaSpedizioni[i].prezzo;
				}
			}
			
			function init()
			{	
				var x;
			<%	ArrayList<SpedizioneBean> listaSpedizioni=(ArrayList<SpedizioneBean>) request.getAttribute("listaSpedizioni");
				int n=listaSpedizioni.size();
				for(int i=0; i<n; i++)
				{
					SpedizioneBean spedizione=listaSpedizioni.get(i);
			%>		x=new Object();
					x.codice="<%=spedizione.getCodice()%>";
					x.nome="<%=spedizione.getNome()%>";
					x.prezzo=<%=spedizione.getPrezzo()%>;
					x.giorni=<%=spedizione.getGiorni()%>;
					x.descrizione="<%=spedizione.getDescrizione()%>";
					
					listaSpedizioni.push(x);
			<%	} %>
				
				//Qui, quando ci sarà un utente loggato, si immetono si dati suoi di default, altrimenti vuoto
				//Per adesso metto alcuni dati segnaposto
				document.forms["form"]["cognome"].value="Iannone";
				document.forms["form"]["nome"].value="Emanuele";
				document.forms["form"]["indirizzo"].value="Via Enea, 7";
				document.forms["form"]["cellulare"].value="3290364256";
				document.forms["form"]["città"].value="Pontecagnano Faiano";
				document.forms["form"]["provincia"].value="Salerno";
				document.forms["form"]["cap"].value="84098";
				document.forms["form"]["numeroCarta"].value="0000 0000 0000 0000";
				document.forms["form"]["proprietarioCarta"].value="Iannone Emanuele";
				document.forms["form"]["scadenzaCarta"].value="12/50";
				document.forms["form"]["cvcCarta"].value="000";
			
				var radioSpedizioni=document.getElementsByName("spedizione");
				radioSpedizioni[0].checked="checked";
				document.getElementById("prezzoSpedizione").innerHTML=listaSpedizioni[0].prezzo+"";
				document.getElementById("prezzoTotale").innerHTML=<%=totale%> + listaSpedizioni[0].prezzo;
			}
			
			function validateForm()
			{
				
				var cognome=document.forms["form"]["cognome"];
				var nome=document.forms["form"]["nome"];
				var indirizzo=document.forms["form"]["indirizzo"];
				var cellulare=document.forms["form"]["cellulare"];
				var città=document.forms["form"]["città"];
				var provincia=document.forms["form"]["provincia"];
				var cap=document.forms["form"]["cap"];
				
				
				var radioSpedizioni=document.forms["form"]["spedizione"];
				var i=0;
				var n=radioSpedizioni.length;
				while(i<n && !radioSpedizioni[i].checked)
					i++;
				if(i>=n)
					return false;
				
				var numeroCarta=document.forms["form"]["numeroCarta"];
				var proprietarioCarta=document.forms["form"]["proprietarioCarta"];
				var scadenzaCarta=document.forms["form"]["scadenzaCarta"];
				var cvcCarta=document.forms["form"]["cvcCarta"];
				
				//Usare le regular expressions per check validità
				if(cognome.value=="" || nome.value=="" || indirizzo.value=="" || cellulare.value=="" || 
					città.value=="" || provincia.value=="" || cap.value=="" || numeroCarta.value=="" || 
					proprietarioCarta.value=="" || scadenzaCarta.value=="" || cvcCarta.value=="")
					{
						alert("Devi compilare tutti i campi");
						return false;
					}
						
				return true;
			}
			var listaSpedizioni=[];
			window.onload=init;
		</script>
	</body>
</html>