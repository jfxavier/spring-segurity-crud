package px.main.ocorrencia.views;

import java.util.List;

import lombok.Data;
import px.main.ocorrencia.modelos.Envolvido;
import px.main.ocorrencia.modelos.Ocorrencia;

@Data
public class AddView {
	int pagina;
	Ocorrencia ocorrencia;
	List<Envolvido> lista;
	Integer envolvido;
	String comando;
	String filtro;
}
