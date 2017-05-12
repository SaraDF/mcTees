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
				<form action="carrello?codiceTema=<%=request.getParameter("codiceTema")%>&action=add" method="post" id="infoArticolo">
					<table>
						<tr>
							<td>Sesso</td>
							<td>
								<select name="sesso" id="sessoId" onChange="selectedArticolo=checkDisponibilit�()">
									<option value="M">Maschio</option>
									<option value="F">Femmina</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Tipo</td>
							<td>
								<select name="tipo" id="tipoId" onChange="selectedArticolo=checkDisponibilit�()">
									<option value="T-shirt">T-shirt</option>
									<option value="Felpa">Felpa</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Taglia</td>
							<td>
								<select name="taglia" id="tagliaId" onChange="selectedArticolo=checkDisponibilit�()">
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
								<select name="colore" id="coloreId" onChange="selectedArticolo=checkDisponibilit�()">
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
							<td id="disponibilit�"></td>
							<td id="logoDisponibilit�"></td>
						</tr>
						<tr>
							<td>Prezzo</td>
							<td id="prezzoId"></td>
						</tr>
						<tr>
							<td>Quantit�</td>
							<td>
								<select name="quantit�" id="quantit�Id"></select>
							</td>
						</tr>
						<tr><td><input type="submit" value="Aggiungi al carrello" onClick="return validateForm()"></td></tr>
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
			function nonDisponibile()
			{
				document.getElementById("disponibilit�").innerHTML="Non disponibile";
				
				var img = document.createElement("img");
				img.src="src/images/nondisponibile.png";
				if(document.getElementById("logoDisponibilit�").childNodes.length==0)
					document.getElementById("logoDisponibilit�").appendChild(img);
				else
					document.getElementById("logoDisponibilit�").replaceChild(img, document.getElementById("logoDisponibilit�").childNodes[0]);
				
				document.getElementById("prezzoId").innerHTML="N/A";
				
				while (document.getElementById("quantit�Id").firstChild)
					document.getElementById("quantit�Id").removeChild(document.getElementById("quantit�Id").firstChild);
				var option = document.createElement("option");
				option.value="0";
				option.innerHTML="N/A";
				document.getElementById("quantit�Id").appendChild(option);
			}
			function disponibile(x)
			{
				document.getElementById("disponibilit�").innerHTML="Disponibile";
				
				var img = document.createElement("img");
				img.src="src/images/disponibile.png";
				if(document.getElementById("logoDisponibilit�").childNodes.length==0)
					document.getElementById("logoDisponibilit�").appendChild(img);
				else
					document.getElementById("logoDisponibilit�").replaceChild(img, document.getElementById("logoDisponibilit�").childNodes[0]);
				
				document.getElementById("prezzoId").innerHTML=x.prezzo;
				
				while (document.getElementById("quantit�Id").firstChild)
					document.getElementById("quantit�Id").removeChild(document.getElementById("quantit�Id").firstChild);
				
				var i=1;
				while((i<=x.stock)&&(i<=5))
				{
					var option = document.createElement("option");
					option.value=i+"";
					option.innerHTML=i+"";
					document.getElementById("quantit�Id").appendChild(option);
					
					i++;
				}
			}
			function checkDisponibilit�()
			{
				var sesso=document.getElementById("sessoId").value;
				var tipo=document.getElementById("tipoId").value;
				var taglia=document.getElementById("tagliaId").value;
				var colore=document.getElementById("coloreId").value;
				
				var i=0;
				var n=articoli.length;
				var found=false;
				while((i<n) && (!found))
				{
					if	((articoli[i].sesso==sesso)&&(articoli[i].tipo==tipo)&&(articoli[i].taglia==taglia)&&(articoli[i].colore==colore))
						found=true;
					else
						i++;
				}
				if(found)
				{
					disponibile(articoli[i]);
					return articoli[i];
				}
				else
				{
					nonDisponibile();
					return null;
				}
			}
			function validateForm()
			{
				if(selectedArticolo==null)
				{
					alert("Articolo non disponibile :(");
					return false;
				}
				return true;
			}
			function init()
			{
				var x, list=[];
				<% 
					ArrayList<ArticoloBean> list=(ArrayList<ArticoloBean>) request.getAttribute("list");
					int i=0, n=list.size();
					while(i<n)
					{
						ArticoloBean ab=list.get(i);
				%>
						x=new Object();
						x.codice="<%=ab.getCodice()%>";
						x.sesso="<%=ab.getMaglietta().getSesso()%>";
						x.tipo="<%=ab.getMaglietta().getTipo()%>";
						x.taglia="<%=ab.getMaglietta().getTaglia()%>";
						x.colore="<%=ab.getMaglietta().getColore()%>";
						x.prezzo=<%=ab.getMaglietta().getPrezzo()%>;
						x.stock=<%=ab.getStock()%>;
						list.push(x);
				<% 	i++;
					}
				%>
				return list;
			}
			var articoli=[], selectedArticolo;
	
			articoli=init();
			selectedArticolo=checkDisponibilit�();
		</script>
	</body>
</html>