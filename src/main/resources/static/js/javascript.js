function checarlogin(texto) {
	console.log($(texto).val());
	$('#result').css("margin-left", "20px");
	var username = $(texto).val();
	if (username != '') {
		$.get("/usuario/existe/" + username, function(result) {

			if (result == 'ok') {
				console.log("ok");
				$('#result').html(username + ' disponível!');
				$('#result').css("color", "green");
				$('#result').css("padding", "10 0 0 0");
				$('#result').css("margin-left", "100");
				$("#salvar").prop("disabled", false);
			} else {
				console.log("Not");
				$('#result').html(username + ' já utilizado!');
				$('#result').css("color", "red");
				$('#result').css("margin-left", "100");
				$('#result').css("padding", "10 0 0 0");
				$("#salvar").prop("disabled", true);
			}
		}).fail(function() {
			alert("Ocorreu um erro, tente novamente mais tarde.");
		});
	} else {
		$('#result').html("Informar um login é obrigatório.");
		$('#result').css("color", "red");
		$('#result').css("padding", "10 0 0 0");
		$('#result').css("margin-left", "50");
		$("#salvar").prop("disabled", true);
	}
}