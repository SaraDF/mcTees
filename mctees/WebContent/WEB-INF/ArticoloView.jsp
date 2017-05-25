<%@ page
		language="java"
		contentType="text/html; charset=ISO-8859-1"
    	pageEncoding="ISO-8859-1"
    	import="java.util.ArrayList, mctees.beans.ArticoloBean"
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="src/styles/general.css">
		<link rel="stylesheet" href="src/styles/header.css">
		<link rel="stylesheet" href="src/styles/footer.css">
		<link rel="stylesheet" href="src/styles/articolo.css">
		<link rel="shortcut icon" href="src/images/logo2.png">
		<title>Articolo - MCTees</title>
	</head>
	<body>
		<%@ include file="fragments/header.html"%> 
		
		<div id="mainArticolo">
			<section id="upperArticolo">
				<section id="fotoArticolo">
					<img src="src/images/magliagiochi.png">
				</section>
				<form action="carrello?codiceTema=<%=request.getParameter("codiceTema")%>&action=add" method="post" id="infoArticolo" onsubmit="return validateForm()">
					<table>
						<tr>
							<td>Sesso</td>
							<td>
								<select name="sesso" id="sessoId" onChange="selectedArticolo=checkDisponibilità()">
									<option value="M">Maschio</option>
									<option value="F">Femmina</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Tipo</td>
							<td>
								<select name="tipo" id="tipoId" onChange="selectedArticolo=checkDisponibilità()">
									<option value="T-shirt">T-shirt</option>
									<option value="Felpa">Felpa</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Taglia</td>
							<td>
								<select name="taglia" id="tagliaId" onChange="selectedArticolo=checkDisponibilità()">
									<option value="S">S</option>
									<option value="M">M</option>
									<option value="L">L</option>
									<option value="XL">XL</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Colore</td>
							<td>
								<select name="colore" id="coloreId" onChange="selectedArticolo=checkDisponibilità()">
									<option value="Nera">Nera</option>
									<option value="Bianca">Bianca</option>
									<option value="Grigia">Grigia</option>
									<option value="Rossa">Rossa</option>
									<option value="Blu">Blu</option>
									<option value="Verde">Verde</option>
									<option value="Gialla">Gialla</option>
								</select>
							</td>
						</tr>
						<tr>
							<td id="disponibilità"></td>
							<td id="logoDisponibilità"></td>
						</tr>
						<tr>
							<td>Prezzo cad.</td>
							<td id="prezzoId"></td>
							<td>&euro;</td>
						</tr>
						<tr>
							<td>Quantità</td>
							<td>
								<select name="quantità" id="quantitàId" onChange="calcolaTotale()"></select>
							</td>
						</tr>
						<tr>
							<td>Prezzo totale</td>
							<td id="prezzoTotaleId"></td>
							<td>&euro;</td>
						</tr>
						<tr><td><input type="submit" value="Aggiungi al carrello"></td></tr>
					</table>
				</form>
			</section>
			<section id="lowerArticolo">
				Forse potrebbe interessanti anche...
				<br><br><br><br><br><br><br><br><br><br><br><br><br><br>
			</section>
		</div>
		
		<%@ include file="fragments/footer.html"%>
		
		<script>
			function creaListaArticoli()
			{
				var x, list=[];
				<% 
					ArrayList<ArticoloBean> listaArticoli=(ArrayList<ArticoloBean>) request.getAttribute("listaArticoli");
					int i=0, n=listaArticoli.size();
					while(i<n)
					{
						ArticoloBean articolo=listaArticoli.get(i);
				%>
						x=new Object();
						x.codice="<%=articolo.getCodice()%>";
						x.sesso="<%=articolo.getMaglietta().getSesso()%>";
						x.tipo="<%=articolo.getMaglietta().getTipo()%>";
						x.taglia="<%=articolo.getMaglietta().getTaglia()%>";
						x.colore="<%=articolo.getMaglietta().getColore()%>";
						x.prezzoMaglietta=<%=articolo.getMaglietta().getPrezzo()%>;
						x.prezzoTema=<%=articolo.getTema().getPrezzo()%>;
					<%	if(articolo.getTema().getSconto()==null)
						{
					%>		x.percentualeSconto=0.0;
					<%	}
						else
						{
					%>		x.percentualeSconto=<%=articolo.getTema().getSconto().getPercentuale()%>;
					<%	}
					%>	x.stock=<%=articolo.getStock()%>;
						
						list.push(x);
				<% 	i++;
					}
				%>
				return list;
			}
			function nonDisponibile()
			{
				document.getElementById("disponibilità").innerHTML="Non disponibile";
				
				var img = document.createElement("img");
				img.src="src/images/nondisponibile.png";
				if(document.getElementById("logoDisponibilità").childNodes.length==0)
					document.getElementById("logoDisponibilità").appendChild(img);
				else
					document.getElementById("logoDisponibilità").replaceChild(img, document.getElementById("logoDisponibilità").childNodes[0]);
				
				document.getElementById("prezzoId").innerHTML="N/A";
				document.getElementById("prezzoTotaleId").innerHTML="N/A";
				
				while (document.getElementById("quantitàId").firstChild)
					document.getElementById("quantitàId").removeChild(document.getElementById("quantitàId").firstChild);
				var option = document.createElement("option");
				option.value="0";
				option.innerHTML="N/A";
				document.getElementById("quantitàId").appendChild(option);
			}
			function disponibile(x)
			{
				document.getElementById("disponibilità").innerHTML="Disponibile";
				
				var img = document.createElement("img");
				img.src="src/images/disponibile.png";
				if(document.getElementById("logoDisponibilità").childNodes.length==0)
					document.getElementById("logoDisponibilità").appendChild(img);
				else
					document.getElementById("logoDisponibilità").replaceChild(img, document.getElementById("logoDisponibilità").childNodes[0]);
				
				var prezzo=x.prezzoMaglietta + x.prezzoTema;
				if(x.percentualeSconto>0)
					prezzo=prezzo - (x.prezzoTema * x.percentualeSconto / 100);
					
				document.getElementById("prezzoId").innerHTML=prezzo;
				document.getElementById("prezzoTotaleId").innerHTML=prezzo;
				//Passare il value del Prezzo calcolato al server non serve: se lo ricalcola perché potrebbe
				//variare ancora MENTRE la voce sta nel carrello
				
				while (document.getElementById("quantitàId").firstChild)
					document.getElementById("quantitàId").removeChild(document.getElementById("quantitàId").firstChild);
				
				var i=1;
				while((i<=x.stock)&&(i<=5))
				{
					var option = document.createElement("option");
					option.value=i+"";
					option.innerHTML=i+"";
					document.getElementById("quantitàId").appendChild(option);
					
					i++;
				}
			}
			function checkDisponibilità()
			{
				var sesso=document.getElementById("sessoId").value;
				var tipo=document.getElementById("tipoId").value;
				var taglia=document.getElementById("tagliaId").value;
				var colore=document.getElementById("coloreId").value;
				
				var i=0;
				var n=listaArticoli.length;
				var found=false;
				while((i<n) && (!found))
					if	((listaArticoli[i].sesso==sesso)&&(listaArticoli[i].tipo==tipo)&&(listaArticoli[i].taglia==taglia)&&(listaArticoli[i].colore==colore))
						found=true;
					else
						i++;
				if(found)
				{
					disponibile(listaArticoli[i]);
					return listaArticoli[i];
				}
				else
				{
					nonDisponibile();
					return null;
				}
			}
			function calcolaTotale()
			{
				document.getElementById("prezzoTotaleId").innerHTML=document.getElementById("prezzoId").innerHTML * document.getElementById("quantitàId").value;
			}
			function validateForm()
			{
				if(selectedArticolo==null)
				{
					alert("Articolo non disponibile :(");
					return false;
				}
				var form=document.getElementById("infoArticolo");
				form.action=form.action + "&codiceArticolo=" + selectedArticolo.codice;
				return true;
			}
			function init()
			{
				listaArticoli=creaListaArticoli();
				selectedArticolo=checkDisponibilità();
			}
			var selectedArticolo;
			var listaArticoli=[];	//mi servono globale per comodità
			window.onload=init;
		</script>
	</body>
</html>