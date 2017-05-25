<%@ page
		language="java"
		contentType="text/html; charset=ISO-8859-1"
    	pageEncoding="ISO-8859-1"
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="src/styles/general.css">
		<link rel="stylesheet" href="src/styles/header.css">
		<link rel="stylesheet" href="src/styles/footer.css">
		<link rel="stylesheet" href="src/styles/home.css">
		<link rel="shortcut icon" href="src/images/logo2.png">
		<title>MCTees</title>
	</head>
	<body>
		<%@ include file="fragments/header.html"%> 
		
		<div id="mainIndex">
			Slider immagini<br><br><br><br><br><br>
			<section>
				<p class="leftsection">
					<a href="catalogo?categoria=filmecartoni">Film e Cartoni</a><br>
					Pulp Fiction, Harry Potter...<br><br><br><br><br><br>
				</p>
				<img src="src/images/magliagiochi.png" class="right">
			</section>
				
			<section>
				<img src="src/images/magliagiochi.png" class="left">
				<p class="rightsection">
					<a href="catalogo?categoria=serietv">Serie TV</a><br>
					Breaking Bad, The Flash...<br><br><br><br><br><br>
				</p>
			</section>
			
			<section>
				<p class="leftsection">
					<a href="catalogo?categoria=giochi">Giochi</a><br>
					Final Fantasy, Borderlands...<br><br><br><br><br>
				</p>
				<img src="src/images/magliagiochi.png" class="right">
			</section>
			
			<section>
				<img src="src/images/magliagiochi.png" class="left">
				<p class="rightsection">
					<a href="catalogo?categoria=altro">Altro...</a><br><br><br><br><br><br>
				</p>
			</section>
		</div>
		
		<%@ include file="fragments/footer.html"%> 
	</body>
</html>