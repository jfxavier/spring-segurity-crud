package px.main.controle;

import java.text.ParseException;
import java.util.Calendar;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
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
import px.main.ocorrencia.modelos.Ocorrencia;
import px.main.ocorrencia.modelos.Vinculo;
import px.main.ocorrencia.repository.EnvolvidoRepository;
import px.main.ocorrencia.repository.EnvolvidoSpecification;
import px.main.ocorrencia.repository.OcorrenciaRepository;
import px.main.ocorrencia.repository.VinculoRepository;
import px.main.ocorrencia.views.AddView;
import px.main.ocorrencia.views.ConsultaView;

@Controller
@RequestMapping(value = "/ocorrencia")
public class ControleOcorrencias {

	private static final int REGISTROPORGAGINA = 10;

	@Autowired
	OcorrenciaRepository ocorrenciaRepository;
	@Autowired
	EnvolvidoRepository envolvidoRepository;
	@Autowired
	VinculoRepository vinculoRepository;


	@RequestMapping(value = "/lista")
	public ModelAndView consulta(@PageableDefault(value = REGISTROPORGAGINA) Pageable page) {
		ModelAndView model = new ModelAndView("ocorrencia/consulta");
		model.addObject("view", new ConsultaView());
		model.addObject("page", page);
		model.addObject("lista", ocorrenciaRepository.findAll(page));
		return model;
	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public ModelAndView consulta(@ModelAttribute("view") ConsultaView view, @PageableDefault(value = REGISTROPORGAGINA) Pageable page) throws ParseException {
		ModelAndView model = new ModelAndView("ocorrencia/consulta");
		page = move(page, view.getPagina());
		Page<Ocorrencia> list = ocorrenciaRepository.findAll(view.Query(), page);
		model.addObject("view", view);
		model.addObject("page", page);
		model.addObject("lista", list);
		return model;
	}

	@RequestMapping("")
	public ModelAndView novo(Ocorrencia ocorrencia) {
		ModelAndView model = new ModelAndView("ocorrencia/ocorrencia");
		model.addObject("view", ocorrencia);
		return model;
	}

	@RequestMapping("/addenv")
	public ModelAndView addpessoa(AddView view) {
		ModelAndView model = new ModelAndView("ocorrencia/addenv");
		view.setOcorrencia(ocorrenciaRepository.findOne(view.getOcorrencia().getId()));

		Vinculo v = new Vinculo();
		switch (view.getComando()) {
		case "add":
			v = new Vinculo("Acusado", envolvidoRepository.findOne(view.getEnvolvido()), view.getOcorrencia());
			view.getOcorrencia().getEnvolvidos().add(v);
			break;
		case "del":
			v = new Vinculo("Acusado", envolvidoRepository.findOne(view.getEnvolvido()), view.getOcorrencia());
			view.getOcorrencia().getEnvolvidos().remove(vinculoRepository.findOne(view.getEnvolvido()));
			break;
		default:
			break;
		}
		ocorrenciaRepository.save(view.getOcorrencia());

		if (view.getFiltro()!="") {
			Specifications<Envolvido> e;
			e = Specifications.where(EnvolvidoSpecification.ByField(view.getFiltro(), "nome"));
			view.setLista(envolvidoRepository.findAll(e));
		} else {
			view.setLista(envolvidoRepository.findAll());
		}
		if (!view.getOcorrencia().getEnvolvidos().isEmpty()) {
			for (Vinculo env : view.getOcorrencia().getEnvolvidos()) {
				view.getLista().remove(view.getLista().indexOf(envolvidoRepository.findOne(env.getEnvolvido().getId())));
			}
		}
		model.addObject("view", view);
		return model;
	}

	/*
	 * @RequestMapping(value ="/addenv", method= RequestMethod.GET)
	 * public @ResponseBody String addpessoa(String nome) {
	 * 
	 * String result="Ola Mundo!          " + nome; return result; }
	 */

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView ocorrencia(@PathVariable int id) {
		Ocorrencia ocorrencia = ocorrenciaRepository.findOne(id);
		return novo(ocorrencia);
	}

	@RequestMapping(value = "/slvocorr", method = RequestMethod.POST)
	public ModelAndView salvar(@ModelAttribute("view") @Valid Ocorrencia ocorrencia, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			System.out.println(result);
			return novo(ocorrencia);
		}
		Calendar calendar = Calendar.getInstance();

		if (ocorrencia.getNumero() == 0) {
			int i = 1;
			if (ocorrenciaRepository.ExistOcorrencia(calendar.get(Calendar.YEAR)) != null) {
				i = (ocorrenciaRepository.MaiorNumero(calendar.get(Calendar.YEAR))) + 1;
			}
			ocorrencia.setNumero(i);
			ocorrencia.setUsuario(ControleUsuarios.UsuarioLogado());
			ocorrencia.setUsuarioAlteracao(ControleUsuarios.UsuarioLogado());
			ocorrencia.setDataRegistro(calendar.getTime());
			ocorrencia.setUltimaAlteracao(calendar.getTime());
		} else {

			Ocorrencia temp = ocorrencia;
			ocorrencia = ocorrenciaRepository.findOne(ocorrencia.getId());
			temp.setEnvolvidos(ocorrencia.getEnvolvidos());
			ocorrencia = temp;
			ocorrencia.setUsuarioAlteracao(ControleUsuarios.UsuarioLogado());
			ocorrencia.setUltimaAlteracao(calendar.getTime());
		}
		ModelAndView model = new ModelAndView("ocorrencia/addenv");
		ocorrenciaRepository.save(ocorrencia);
		System.out.print("Ocorrencia " + ocorrencia.getId() + " SALVA COM SUCESSO.");
		AddView view = new AddView();
		view.setOcorrencia(ocorrencia);
		view.setLista(envolvidoRepository.findAll());
		if (!ocorrencia.getEnvolvidos().isEmpty()) {
			for (Vinculo env : ocorrencia.getEnvolvidos()) {
				view.getLista().remove(view.getLista().indexOf(envolvidoRepository.findOne(env.getEnvolvido().getId())));
			}
		}
		view.setComando("add");
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