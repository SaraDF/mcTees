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
		<script src="src/scripts/jquery-3.2.1.js"></script>
		<script>
			function callback(data, statusText, xhr)
			{
				var prezzoSpedizione=data.prezzoSpedizione;
				$("#prezzoSpedizione").html(prezzoSpedizione);
				$("#prezzoTotale").html(parseFloat($("#totaleArticoli").html()) + prezzoSpedizione);	
			}
			
			function updatePrezzi()
			{
				//Aggiorna i prezzi con i nuovi dati giunti dal server per la spedizione scelta
				var codiceSpedizione=$("input[name='spedizione']:checked").val();	//Ottenuto dal valore del radio selezionato
				var dataObject=
				{
					"codiceSpedizione" : codiceSpedizione
				};
				var dataJSON=JSON.stringify(dataObject);
					
				$.ajaxSetup(
				{
					error: function (xhr, ajaxOptions, thrownError)
					{
					    //Può capitare solo se quella spedizione sparisce in quell'istante: difficile
					}
				});
				$.post("checkoutAjax", "data="+dataJSON, callback);
			}
			
			function validateForm()
			{
				var cognome=$("input[name='cognome']").val();
				var nome=$("input[name='nome']").val();
				var indirizzo=$("input[name='indirizzo']").val();
				var cellulare=$("input[name='cellulare']").val();
				var città=$("input[name='città']").val();
				var provincia=$("input[name='provincia']").val();
				var cap=$("input[name='cap']").val();
				
				var radioSpedizioni=$("input[name='spedizione']");
				var i=0;
				var n=radioSpedizioni.length;
				while(i<n && !radioSpedizioni[i].checked)
					i++;
				if(i>=n)
				{
					alert("Seleziona un metodo di spedizione");
					return false;
				}
				
				var numeroCarta=$("input[name='numeroCarta']").val();
				var proprietarioCarta=d$("input[name='proprietarioCarta']").val();
				var scadenzaCarta=$("input[name='scadenzaCarta']").val();
				var cvcCarta=$("input[name='cvcCarta']").val();
				
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
			
			function init()
			{
				$("input[name='spedizione']").click(updatePrezzi);
				
				//Qui, quando ci sarà un utente loggato, si immetono si dati suoi di default, altrimenti vuoto
				//Per adesso metto alcuni dati segnaposto
				var cognome="Iannone";
				var nome="Emanuele";
				var indirizzo="Via Enea, 7";
				var cellulare="3290364256";
				var città="Pontecagnano Faiano";
				var provincia="Salerno";
				var cap="84098";
				var numeroCarta="0000 0000 0000 0000";
				var proprietarioCarta="Iannone Emanuele";
				var scadenzaCarta="12/50";
				var cvcCarta="000";
				
				$("input[name='cognome']").val(cognome);
				$("input[name='nome']").val(nome);
				$("input[name='indirizzo']").val(indirizzo);
				$("input[name='cellulare']").val(cellulare);
				$("input[name='città']").val(città);
				$("input[name='provincia']").val(provincia);
				$("input[name='cap']").val(cap);
				$("input[name='numeroCarta']").val(numeroCarta);
				$("input[name='proprietarioCarta']").val(proprietarioCarta);
				$("input[name='scadenzaCarta']").val(scadenzaCarta);
				$("input[name='cvcCarta']").val(cvcCarta);
			}
			
			$(document).ready(function(){
			      init();
			});
		</script>
		<title>Checkout - MCTees</title>
	</head>
	<body>
		<%@ include file="fragments/header.html"%>
		
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
								<td><input type="radio" name="spedizione" value="<%=spedizione.getCodice() %>"></td>
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
					double totale=0;
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
						<span>Totale articoli: <span id="totaleArticoli"><%=totale %></span> &euro;</span><br>
						+<br>
						<span>Prezzo spedizione: <span id="prezzoSpedizione">0.00</span> &euro;</span><br>
						=<br>
						<span>Totale ordine: <span id="prezzoTotale">0.00</span> &euro;</span>
					</div>	
				</div>		
		<% }%>
		</div>
		
		<%@ include file="fragments/footer.html"%>
	</body>
</html>