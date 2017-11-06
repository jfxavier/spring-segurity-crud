package px.main.controle;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import px.main.seguranca.modelos.Grupo;
import px.main.seguranca.modelos.Usuario;
import px.main.seguranca.modelos.UsuarioRegra;
import px.main.seguranca.repository.UsuarioRegraRepository;
import px.main.seguranca.repository.UsuarioRepository;
import px.main.seguranca.views.Alterarsenha;
import px.main.seguranca.views.UsuarioView;

@Controller
@RequestMapping("/usuario")
public class ControleUsuarios {

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	UsuarioRegraRepository usuarioRegraRepository;

	@RequestMapping(value = "/lista")
	public ModelAndView lista() {
		ModelAndView model = new ModelAndView("usuario/lista");
		model.addObject("lista", usuarioRepository.findAll());
		return model;
	}

	@RequestMapping("")
	public ModelAndView novo(UsuarioView view) {
		ModelAndView model = new ModelAndView("usuario/formulario");
		model.addObject("view", view);
		return model;
	}

	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid UsuarioView view, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(view);
		}
		Usuario usuario;

		if (view.getId() != 0) {
			usuario = usuarioRepository.findOne(view.getId());
			usuario.Set(view);
		} else {
			usuario = new Usuario(view);
			usuario.setSenha(view.getLogin());
		}
		usuarioRepository.save(usuario);

		ModelAndView model = new ModelAndView("redirect:lista");
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso.");
		return model;
	}

	@RequestMapping(value = "/gerarADM")
	public ModelAndView criarADM() {

		if (usuarioRepository.findAll() == null) {

			Usuario usuario = new Usuario(0, "adm", Usuario.MD5("adm"), "Administrador", "", "", true, new ArrayList<UsuarioRegra>(), new ArrayList<Grupo>());
			usuario.criarADM();

			System.out.println(usuario);
			usuarioRepository.save(usuario);

			ModelAndView model = new ModelAndView("usuario/adm");
			model.addObject("usuario", usuario);

			return model;
		}
		return lista();
	}

	@RequestMapping(value = "/salvarsenha", method = RequestMethod.POST)
	public ModelAndView alterar(@Valid Alterarsenha view) {

		if (view.CompararSenhas()) {
			Usuario user = usuarioRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
			if (user.getSenha().equals(Usuario.MD5(view.getSenha()))) {

				user.setSenha(view.getSenha());

				ModelAndView model = new ModelAndView("usuario/alterarsenha");
				model.addObject("view", new Alterarsenha());
				model.addObject("m1", "Senha alterada com sucesso.");
				return model;
			} else {
				ModelAndView model = new ModelAndView("usuario/alterarsenha");
				model.addObject("view", new Alterarsenha());
				model.addObject("m2", "Senha incorreta.");
				return model;
			}
		}
		ModelAndView model = new ModelAndView("usuario/alterarsenha");
		model.addObject("m2", "Nova Senha informada não confere.");
		model.addObject("view", new Alterarsenha());
		return model;
	}

	@RequestMapping(value = "/alterarsenha", method = RequestMethod.GET)
	public ModelAndView alterar() {
		ModelAndView model = new ModelAndView("usuario/alterarsenha");
		model.addObject("view", new Alterarsenha());
		return model;

	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable Integer id) {
		if (usuarioRepository.findOne(id) != null)
			usuarioRepository.delete(id);
		return lista();

	}

	@RequestMapping(value = "/qdelete/{id}", method = RequestMethod.GET)
	public ModelAndView qdelete(@PathVariable Integer id) {
		if (usuarioRepository.findOne(id) != null) {
			ModelAndView model = new ModelAndView("usuario/excluir");
			model.addObject("usuario", usuarioRepository.findOne(id));
			return model;
		}
		return lista();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView usuario(@PathVariable Integer id) {
		Usuario usuario = usuarioRepository.findOne(id);
		System.out.println(usuario);
		return novo(new UsuarioView(usuario));
	}

	public static String UsuarioLogado() {
		Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (usuarioLogado == null) {
			return "";
		}
		String username;
		if (usuarioLogado instanceof UserDetails) {
			username = ((UserDetails) usuarioLogado).getUsername();
		} else {
			username = usuarioLogado.toString();
		}
		return username;
	}

	@RequestMapping(value = "/existe/{login}", method = RequestMethod.GET)
	public @ResponseBody String LoginExiste(@PathVariable String login) {
		Usuario user = usuarioRepository.findByLogin(login);
		if (user != null) {
			return "not";
		} else {
			return "ok";
		}
	}

}
