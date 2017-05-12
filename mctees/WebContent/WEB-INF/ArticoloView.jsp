<%@ page
		language="java"
		contentType="text/html; charset=ISO-8859-1"
    	pageEncoding="ISO-8859-1"
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
				<form action="carrello" method="post" id="infoArticolo">
					<table>
						<tr>
							<td><img src="src/images/disponibile.png">Disponibile</td>
						</tr>
						<tr>
							<td>Sesso</td>
							<td>
								<select>
									<option value="M">Maschio</option>
									<option value="F">Femmina</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Taglia</td>
							<td>
								<select>
									
								</select>
							</td>
						</tr>
						<tr>
							<td>Colore</td>
							<td>
								<select>
									
								</select>
							</td>
						</tr>
						<tr>
							<td>Quantità</td>
							<td>
								<select>
									
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