package px.main.controle;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import px.main.ocorrencia.modelos.Envolvido;
import px.main.ocorrencia.repository.EnvolvidoRepository;
import px.main.ocorrencia.views.EnvolvidoListaView;

@Controller
@RequestMapping(value = "/pessoa")
public class ControleEnvolvido {

	private static final int REGISTROPORGAGINA = 10;

	@Autowired
	EnvolvidoRepository envolvidoRepository;

	@RequestMapping("")
	public ModelAndView novo(Envolvido env) {
		ModelAndView model = new ModelAndView("envolvido/formulario");
		model.addObject("view", env);
		return model;
	}

	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public ModelAndView salvar(@ModelAttribute("view") @Valid Envolvido ov, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(ov);
		}
		System.out.print(ov);
		Envolvido envolvido;
		if (ov.getId() == null) {
			envolvido = new Envolvido(ov);
		} else {
			envolvido = new Envolvido(envolvidoRepository.findOne(ov.getId()));
			envolvido.Set(ov);
		}

		ModelAndView model = new ModelAndView("redirect:lista");
		envolvidoRepository.save(envolvido);
		return model;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView pessoa(@PathVariable int id) {
		Envolvido env = envolvidoRepository.findOne(id);
		System.out.println(env);
		return novo(env);
	}

	@RequestMapping(value = "/lista", method = RequestMethod.GET)
	public ModelAndView lista(@ModelAttribute("view") EnvolvidoListaView view, @PageableDefault(value = REGISTROPORGAGINA) Pageable page) {
		ModelAndView model = new ModelAndView("envolvido/consulta");
		view.setPage(move(page, view.getPagina()));
		Page<Envolvido> list = envolvidoRepository.findAll(view.Busca(), page);
		view.setLista(list);
		model.addObject("view", view);
		return model;
	}

	public Pageable move(Pageable page, int s) {
		s--;
		if (page.getPageNumber() > s) {
			for (int i = page.getPageNumber(); i > s; i--) {
				page = page.previousOrFirst();
			}
		}
		if (page.getPageNumber() < s) {
			for (int i = page.getPageNumber(); i < s; i++) {
				page = page.next();
			}
		}

		return page;
	}
}
