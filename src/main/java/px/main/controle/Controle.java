package px.main.controle;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import px.main.seguranca.modelos.Grupo;
import px.main.seguranca.modelos.Usuario;
import px.main.seguranca.modelos.UsuarioRegra;
import px.main.seguranca.repository.UsuarioRepository;

@Controller
public class Controle {

	@Autowired
	UsuarioRepository usuarioRepository;

	@RequestMapping("/home")
	public String hello() {
		return criarADM();

	}

	@RequestMapping("/olamundo")
	public String olamundo() {
		return "index";
	}

	@RequestMapping("")
	public String index() {
		return  criarADM();
	}

	@RequestMapping("/login")
	public String goLogin() {
		return "login";
	}

	@RequestMapping("/logout")
	public String logout() {
		return "login";
	}
	@RequestMapping("/403")
	public String erro403() {
		return "403";
	}
	@RequestMapping("/404")
	public String erro404() {
		return "404";
	}
	@RequestMapping("/adm")
	public String adm() {
		return "adm";
	}
	
	public String criarADM() {
		if (usuarioRepository.findAll().size()==0) {
			Usuario usuario = new Usuario(0, "adm", Usuario.MD5("adm"), "Administrador", "", "", true, new ArrayList<UsuarioRegra>(), new ArrayList<Grupo>());
			usuario.criarADM();
			usuarioRepository.save(usuario);

			return "adm";
		}
		return "index";
	}
}
