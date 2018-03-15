package px.main.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import px.main.ocorrencia.modelos.Envolvido;
import px.main.ocorrencia.modelos.Ocorrencia;
import px.main.ocorrencia.repository.EnvolvidoRepository;
import px.main.ocorrencia.repository.OcorrenciaRepository;

@Controller
@RequestMapping(value = "/vinculacao")
public class CtrlVinculacao {
	@Autowired
	EnvolvidoRepository envolvidoRepository;
	@Autowired
	OcorrenciaRepository ocorrenciaRepository;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView vinculacao(@PathVariable int id) {
		Ocorrencia ocorrencia = ocorrenciaRepository.findOne(id);
		System.out.println(ocorrencia);
		ModelAndView model = new ModelAndView("envolvido/vinculacao");
		model.addObject("view", ocorrencia);
		List<Envolvido> lista =envolvidoRepository.findAll();
		model.addObject("envolvidos",lista);
		return model;
	}
	
}
