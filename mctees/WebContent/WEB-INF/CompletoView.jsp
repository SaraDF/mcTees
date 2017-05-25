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
		<link rel="stylesheet" href="src/styles/completo.css">
		<link rel="shortcut icon" href="src/images/logo2.png">
		<title>Completo - MCTees</title>
	</head>
	<body>
		<%@ include file="fragments/header.html"%>
		
	<% String cognome=(String)request.getAttribute("cognome");
		String nome=(String)request.getAttribute("nome");
		String indirizzo=(String)request.getAttribute("indirizzo");
		String città=(String)request.getAttribute("città");
		String provincia=(String)request.getAttribute("provincia");
		String cap=(String)request.getAttribute("cap");
		SpedizioneBean spedizione=(SpedizioneBean)request.getAttribute("spedizione");
		ArrayList<VoceBean> listaVoci=(ArrayList<VoceBean>) request.getAttribute("listaVoci");
	%>	<div id="mainCompleto">
		<%	if(listaVoci!=null)
			{
		%>		<p>
					Congratulazioni <%=cognome %> <%=nome %>!
				</p>
				<p>
					Il tuo ordine è stato compleato con successo.<br>
					Gli articoli verranno recapitati in circa <%=spedizione.getGiorni() %> giorni <br>
					in: <%=indirizzo %>, <%=città %>, <%=provincia %>, <%=cap %>.
				</p>
				<a href="home">Torna alla home</a>
		<%	} %>
		</div>
		
		<%@ include file="fragments/footer.html"%>
	</body>
</html>