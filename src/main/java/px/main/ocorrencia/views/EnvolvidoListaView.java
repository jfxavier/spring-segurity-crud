package px.main.ocorrencia.views;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;

import lombok.Data;
import px.main.ocorrencia.modelos.Envolvido;
import px.main.ocorrencia.repository.EnvolvidoSpecification;

@Data
public class EnvolvidoListaView {
	private Page<Envolvido> lista;
	private int pagina;
	private String nome;
	private Pageable page;

	public EnvolvidoListaView() {
		nome="";
	}

	public EnvolvidoListaView(Page<Envolvido> lista) {
		this.page= null;
		this.lista = lista;
		this.pagina = 1;
		this.nome = "";
	}

	public Specifications<Envolvido> Busca() {
		Specifications<Envolvido> s = null;
		if(!this.nome.isEmpty()) {
		s = Specifications.where(EnvolvidoSpecification.ByField(getNome(), "nome"));
		}else {
			s = Specifications.where(EnvolvidoSpecification.ByField("", "nome"));
	}
		return s;
	}

}
