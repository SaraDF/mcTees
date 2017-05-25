<%@ page
		language="java"
		contentType="text/html"
    	pageEncoding="ISO-8859-1"
    	import="java.util.ArrayList, mctees.beans.TemaBean"
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="src/styles/general.css">
		<link rel="stylesheet" href="src/styles/header.css">
		<link rel="stylesheet" href="src/styles/footer.css">
		<link rel="stylesheet" href="src/styles/catalogo.css">
		<link rel="shortcut icon" href="src/images/logo2.png">
		<title>Catalogo - MCTees</title>
	</head>
	<body>
		<%@ include file="fragments/header.html"%> 
	
		<div id="mainCatalogo">
			<section id="sideCatalogo">
				<table id="toptable">
					<tr><td><a href="catalogo">Tutte</a></td></tr>
					<tr><td><a href="catalogo?categoria=filmecartoni">Film</a></td></tr>
					<tr><td><a href="catalogo?categoria=serietv">Serie TV</a></td></tr>
					<tr><td><a href="catalogo?categoria=giochi">Giochi</a></td></tr>
					<tr><td><a href="catalogo?categoria=altro">Altro</a></td></tr>
				</table>
				<table>
					<tr><td>Ordina per +</td></tr>
					<tr><td>Tipo +</td></tr>
					<tr><td>Taglia +</td></tr>
					<tr><td>Colore +</td></tr>
					<tr><td>Sesso +</td></tr>
				</table>
			</section>
			
			<section id="articoliCatalogo">
			<% 
				ArrayList<TemaBean> listaTemi=(ArrayList<TemaBean>) request.getAttribute("listaTemi");
				int i=0, n=listaTemi.size();
				while(i<n)
				{
					TemaBean tema=listaTemi.get(i);
					if(i%3==0)
					{%>
						<div class="temaRow">
				<%	}%>
							<div class="tema">
								<section class="fotoTema">
									<img src="src/images/magliagiochi.png">
								</section>
								<a href="articolo?codiceTema=<%=tema.getCodice()%>"><%= tema.getNome() %></a><br>
								&euro; <%= tema.getPrezzo()%>
							</div>
				<%	if(i%3==2)
					{%>
						</div>
				<%	}
					i++;
				}
				
				if(i%3>0)
				{%>
						</div>
			<%	}%>
			</section>
		</div>
	<%@ include file="fragments/footer.html"%> 
	</body>
</html>