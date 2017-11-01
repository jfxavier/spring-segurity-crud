package px.main.seguranca.views;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import px.main.seguranca.modelos.Usuario;

@Data
public class UsuarioView {

	private Integer id;

	@NotBlank(message = "Informe o login para acesso ao sistema.")
	private String login;

	@NotBlank(message = "Informe seu nome.")
	private String nome;

	private String senha, setor, obs;
	private boolean enabled;

	private boolean usuario;
	private boolean adm;
	private boolean visitante;

	private List<String> textoRegras;

	public UsuarioView() {
		this.id = 0;
		textoRegras = new ArrayList<String>();
		textoRegras.add("Cadastrar e alterar ocorrências.");
		textoRegras.add("Adicionar e excluir usuarios.");
		textoRegras.add("Apenas visualizar as ocorrências.");

	}

	public UsuarioView(Usuario usuario) {
		this.id = usuario.getId();
		this.login = usuario.getLogin();
		this.nome = usuario.getNome();
		this.senha = usuario.getSenha();
		this.setor = usuario.getSetor();
		this.obs = usuario.getObs();
		this.enabled=usuario.isEnabled();

		if (usuario.findRegrasInt("ROLE_USER") != 0) {
			this.usuario = true;
		}
		if (usuario.findRegrasInt("ROLE_ADM") != 0) {
			this.adm = true;
		}
		if (usuario.findRegrasInt("ROLE_VISITANTE") != 0) {
			this.visitante = true;
		}

		textoRegras = new ArrayList<String>();
		textoRegras.add("Cadastrar e alterar ocorrências.");
		textoRegras.add("Adicionar e excluir usuarios.");
		textoRegras.add("Apenas visualizar as ocorrências.");
	}

}