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
		<%
			ArrayList<VoceBean> listaVoci=(ArrayList<VoceBean>) request.getAttribute("listaVoci");
			int n=listaVoci.size();
			for(int i=0; i<n; i++)
			{
				VoceBean voce=listaVoci.get(i);
		%>		
				Nome: <%=voce.getArticolo().getTema().getNome()%>
				Sesso: <%=voce.getArticolo().getMaglietta().getSesso()%>
				Tipo: <%=voce.getArticolo().getMaglietta().getTipo()%>
				Taglia: <%=voce.getArticolo().getMaglietta().getTaglia()%>
				Colore: <%=voce.getArticolo().getMaglietta().getColore()%>
				//Sistemare il prezzo: va ricalcolato
				Prezzo: <%=voce.getPrezzo()%>
		<%
			}
		%>
		</div>
		
		<%@ include file="fragments/footer.html"%> 
	</body>
</html>