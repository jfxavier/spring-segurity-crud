package px.main.controle;

import java.text.ParseException;
import java.util.Calendar;

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

import px.main.ocorrencia.modelos.Ocorrencia;
import px.main.ocorrencia.repository.OcorrenciaRepository;
import px.main.ocorrencia.views.ConsultaView;
import px.main.ocorrencia.views.OcorrenciaView;

@Controller
@RequestMapping(value = "/ocorrencia")
public class ControleOcorrencias {

	private static final int REGISTROPORGAGINA = 10;

	@Autowired
	OcorrenciaRepository ocorrenciaRepository;

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
	public ModelAndView novo(OcorrenciaView ocorrenciaView) {
		ModelAndView model = new ModelAndView("ocorrencia/ocorrencia");
		model.addObject("ocorrenciaView", ocorrenciaView);
		return model;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView ocorrencia(@PathVariable int id) {
		Ocorrencia ocorrencia = ocorrenciaRepository.findOne(id);
		System.out.println(ocorrencia);
		return novo(ocorrencia.Get());
	}

	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid OcorrenciaView ov, BindingResult result, RedirectAttributes attributes) throws ParseException {
		if (result.hasErrors()) {
			return novo(ov);
		}
		Calendar calendar = Calendar.getInstance();
		Ocorrencia ocorrencia;

		if (ov.getNumero().equals("")) {
			ocorrencia = new Ocorrencia(ov);
			int i = 1;
			if (ocorrenciaRepository.ExistOcorrencia(calendar.get(Calendar.YEAR))!=null) {
				i = (ocorrenciaRepository.MaiorNumero(calendar.get(Calendar.YEAR))) + 1;
			}
			ocorrencia.setNumero(i);
			ocorrencia.setUsuario(ControleUsuarios.UsuarioLogado());
			ocorrencia.setUsuarioAlteracao(ControleUsuarios.UsuarioLogado());
			ocorrencia.setDataRegistro(calendar.getTime());
			ocorrencia.setUltimaAlteracao(calendar.getTime());
		} else

		{
			ocorrencia=ocorrenciaRepository.findOne(ov.getId());
			ocorrencia.Set(ov);
			
			ocorrencia.setUsuarioAlteracao(ControleUsuarios.UsuarioLogado());
			ocorrencia.setUltimaAlteracao(calendar.getTime());
		}

		System.out.println(ocorrencia);
		ModelAndView model = new ModelAndView("redirect:ocorrencia/lista");
		ocorrenciaRepository.save(ocorrencia);
		System.out.println(ocorrencia);
		return model;
	}

	/*
	 * @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET) public
	 * ModelAndView delete(@PathVariable Integer id) {
	 * ocorrenciaRepository.delete(id); return lista("Registro excluido com êxito("
	 * + id + ")."); }
	 * 
	 * public ModelAndView lista(String msg) { ModelAndView model = new
	 * ModelAndView("/ocorrencia/lista"); model.addObject("lista",
	 * ocorrenciaRepository.findAll()); model.addObject("msg", msg); return model; }
	 * 
	 * @RequestMapping(value = "/rel/{id}", method = RequestMethod.GET) public
	 * ModelAndView relatorio(@PathVariable Integer id) { Ocorrencia ocorrencia =
	 * ocorrenciaRepository.findOne(id); if (ocorrencia.getId() == null) { return
	 * lista("Ocorrencia não localizada."); } ModelAndView model = new
	 * ModelAndView("/ocorrencia/relatorio"); model.addObject("ocorrencia",
	 * ocorrencia); model.addObject("comp", ocorrencia.getNumeroAno()); return
	 * model; }
	 */

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