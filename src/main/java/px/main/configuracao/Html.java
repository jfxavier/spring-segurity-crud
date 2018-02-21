package px.main.configuracao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

public class Html {	
	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println("HTML Iniciado...");
		System.out.println("HTML BUILDING: Args->" + args[0]);
		System.out.println("HTML BUILDING: Classe->" +NomeQualificado(args[0]));

		Class c = Class.forName(NomeQualificado(args[0]));
		Field[] fields = c.getDeclaredFields();

		
		String diretorio="src\\main\\resources\\templates\\";
		String acao="@{/usuario/salvar}";
		
		
		
		String texto = "";
		for (Field f : fields) {
			texto = texto + "<div class=\"form-group\">\r\n" + "				<label for=\"" + f.getName() + "\">" + f.getName() + "</label> <input type=\"text\" id=\"" + f.getName() + "\"\r\n"
					+ "					class=\"form-control\" th:field=\"*{" + f.getName() + "}\" />\r\n" + "			</div>" + "\r\n";
		}

		String textoQueSeraEscrito = "<html xmlns=\"http://www.w3.org/1999/xhtml\"\r\n" + "	xmlns:th=\"http://www.thymeleaf.org\">\r\n" + "<head>\r\n" + "<meta charset=\"utf-8\" />\r\n"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\r\n" + "\r\n" + "<link rel=\"stylesheet\"\r\n" + "	href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css\"\r\n"
				+ "	integrity=\"sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb\"\r\n" + "	crossorigin=\"anonymous\" />\r\n" + "\r\n" + "<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\"\r\n"
				+ "	integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\"\r\n" + "	crossorigin=\"anonymous\"></script>\r\n" + "\r\n" + "<script\r\n"
				+ "	src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js\"\r\n" + "	integrity=\"sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh\"\r\n"
				+ "	crossorigin=\"anonymous\"></script>\r\n" + "<script\r\n" + "	src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js\"\r\n"
				+ "	integrity=\"sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ\"\r\n" + "	crossorigin=\"anonymous\"></script>\r\n" + "\r\n <title>" + c.getSimpleName() + "</title>\r\n" + "</head>\r\n"
				+ "<body>\r\n" + "	<div th:replace=\"fragmentos/menu\"></div>\r\n" + "	\r\n" + "	\r\n" + "	<h1>Class Building</h1>\r\n" + "	<h2>" + c.getSimpleName() + "</h2>\r\n"
				+ "<form method=\"POST\" th:object=\"${view}\" th:action=\""+ acao +"\"> " +

				texto +

				"\r\n <div class=\"form-group\">\r\n" + "<button type=\"submit\" class=\"btn btn-primary\" id=\"salvar\">Salvar</button>\r\n" + "			</div></form></body>\r\n" + "</html>";
		FileWriter arquivo;
		try {
			arquivo = new FileWriter(new File(diretorio + c.getSimpleName() + ".html"));
			arquivo.write(textoQueSeraEscrito);
			arquivo.close();
			System.out.println("HTML BUILDING: File->"+diretorio + c.getSimpleName() + ".html\"");
			System.out.println("HTML BUILDING: END");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String NomeQualificado(String text) {
		String path[] = text.split("\\\\");
		String p = "";
		for (int i = path.length - 1; i >= 0; i--) {
			if (path[i].contentEquals("java")) {
				break;
			} else {
				if (path[i].contains("java")) {
					p = path[i].split("\\.")[0];
				}else {
				p = path[i] + "." + p;
			}}
		}
		return p;

	}

}
