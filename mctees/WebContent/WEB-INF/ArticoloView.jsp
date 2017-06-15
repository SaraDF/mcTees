<%@ page
		language="java"
		contentType="text/html; charset=ISO-8859-1"
    	pageEncoding="ISO-8859-1"
    	import="mctees.beans.TemaBean"
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<script src="src/scripts/jquery-3.2.1.js"></script>
		<script>
			$.urlParam = function(name)
			{
				var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
				if (results==null)
					return null;
				else
				 	return decodeURI(results[1]) || 0;
			}
	
			function nonDisponibile()
			{
				codiceArticolo=null;
				
				$("#disponibilità").html("Non disponibile");
				
				var img = $('<img src="src/images/nondisponibile.png"></img>');
				$("#logoDisponibilità").empty();
				$("#logoDisponibilità").append(img);
				
				$("#prezzoId").html("N/A");
				
				$("#quantitàId").empty();
				var option=$('<option value="0">N/A</option>');
				$("#quantitàId").append(option);
			}
	
			function disponibile(prezzo, stock)
			{
				$("#disponibilità").html("Disponibile");
				
				var img = $('<img src="src/images/disponibile.png"></img>');
				$("#logoDisponibilità").empty();
				$("#logoDisponibilità").append(img);
				
				//L'ideale sarebbe visualizzare anche il prezzo di partenza
				$("#prezzoId").html(prezzo);
				
				$("#quantitàId").empty();
				var i=1;
				while((i<=stock)&&(i<=10))
				{
					var option=$('<option value="'+i+'">'+i+'</option>');
					$("#quantitàId").append(option);
					i++;
				}
			}
			function callback(data, statusText, xhr)
			{
				codiceArticolo=data.codiceArticolo;
				var prezzo=data.prezzo;
				var stock=data.stock;
				disponibile(prezzo, stock);
			}
			function checkDisponibilità()
			{
				var codiceTema=$.urlParam("codiceTema");
				var sesso=$("#sessoId").val();
				var tipo=$("#tipoId").val();
				var taglia=$("#tagliaId").val();
				var colore=$("#coloreId").val();
				var dataObject=
				{
					"codiceTema" : codiceTema,
					"sesso" : sesso,
					"tipo": tipo,
					"taglia" : taglia,
					"colore" : colore
				};
				var dataJSON=JSON.stringify(dataObject);
					
				$.ajaxSetup(
				{
					error: function (xhr, ajaxOptions, thrownError)
					{
					    nonDisponibile();
					}
				});
				$.post("articoloAjax", "data="+dataJSON, callback);
			}
			function validateForm()
			{
				if(codiceArticolo==null)
				{
					alert("Articolo non disponibile :(");
					return false;
				}
				//Alla fine mando alla servlet del carrello solo il codiceArticolo.
				$("#infoArticolo").attr("action", $("#infoArticolo").attr("action")+"&codiceArticolo="+codiceArticolo);
				return true;
			}
			function init()
			{
				//Metto anche il codiceTema per la conservazione della pagina (vedi servlet del Carrello)
				$("#infoArticolo").attr("action", "carrello?codiceTema="+$.urlParam("codiceTema")+"&action=add");
				checkDisponibilità();
				$(".selectAjax").change(checkDisponibilità);
			}
			var codiceArticolo=null;
			$(document).ready(function(){
		      init();
		 	});
		</script>
		<link rel="stylesheet" href="src/styles/general.css">
		<link rel="stylesheet" href="src/styles/header.css">
		<link rel="stylesheet" href="src/styles/footer.css">
		<link rel="stylesheet" href="src/styles/articolo.css">
		<link rel="shortcut icon" href="src/images/logo2.png">
		<title>Articolo - MCTees</title>
	</head>
	<body>
		<%TemaBean tema=(TemaBean) request.getAttribute("tema"); %>
		<%@ include file="fragments/header.html"%> 
		
		<div id="mainArticolo">
			<section id="upperArticolo">
				<section id="fotoArticolo">
					<img src="src/images/magliagiochi.png">
				</section>
				<form action="carrello" method="post" id="infoArticolo" onsubmit="return validateForm()">
					<span><strong><%=tema.getNome() %></strong></span>
				<%	if(tema.getSconto()!=null)
					{	%>
						<br><span><strong> scontato del <%=tema.getSconto().getPercentuale() %>%</strong></span>
				<%	}	%>
					<table>
						<tr>
							<td>Sesso</td>
							<td>
								<select name="sesso" id="sessoId" class="selectAjax">
									<option value="M">Maschio</option>
									<option value="F">Femmina</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Tipo</td>
							<td>
								<select name="tipo" id="tipoId" class="selectAjax">
									<option value="T-shirt">T-shirt</option>
									<option value="Felpa">Felpa</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Taglia</td>
							<td>
								<select name="taglia" id="tagliaId" class="selectAjax">
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
								<select name="colore" id="coloreId" class="selectAjax">
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
								<select name="quantità" id="quantitàId">
								</select>
							</td>
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
	</body>
</html>