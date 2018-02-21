package px.main.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import px.main.ocorrencia.modelos.Envolvido;
import px.main.ocorrencia.repository.EnvolvidoRepository;

@Controller
@RequestMapping(value = "/envolvido")
public class ControleEnvolvido {

	@Autowired
	EnvolvidoRepository envolvidoRepository;

	@RequestMapping("")
	public ModelAndView novo(Envolvido env) {
		ModelAndView model = new ModelAndView("envolvido/formulario");
		model.addObject("view", env);
		return model;
	}

}
