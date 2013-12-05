<html>
	<head>
		<title>${espetaculo.nome}</title>
	</head>
	<body>
		<h2>Criar novas sessões</h2>
		<form action="/espetaculo/${espetaculo.id}/sessoes" method="post">
			<label for="inicio">Início</label>
			<input type="text" name="inicio" id="inicio"/>
						
			<label for="fim">Fim</label>
			<input type="text" name="fim" id="fim"/>
			
			<label for="horario">Horário</label>
			<input type="text" name="horario" id="horario"/>
			
			<label for="periodo">Periodicidade</label>
			<select name="periodo" id="periodo">
				<option value="DIARIA">Diária</option>		
				<option value="SEMANAL">Semanal</option>			
			</select>
			
<<<<<<< HEAD
			<input type="submit" value="Criar"/>
=======
			<input type="submit" value="Criar Sessão" />
>>>>>>> b2925d2aa649a2918703bc2218ed040a3304da29
		</form>
	
	</body>
</html>