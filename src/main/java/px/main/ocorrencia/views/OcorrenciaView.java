package px.main.ocorrencia.views;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class OcorrenciaView {
	
	private Integer id;
	private String numero, local, usuario, turno, usuarioAlteracao, setor, status, data, hora, dataRegistro, ultimaAlteracao;
	@NotBlank(message = "Informe um código para a ocorrência.")
	private String codigo;

	@NotBlank(message = "Registre alguma informação para a ocorrência.")
	@Type(type = "text")
	private String informacao;

	@Type(type = "text")
	private String guardas;
	
	public OcorrenciaView(){
		
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat f=new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat f2=new SimpleDateFormat("hh:mm");
		
		this.numero="";
		this.status="Ativo";
		this.data=f.format(calendar.getTime());
		this.hora=f2.format(calendar.getTime());
		this.dataRegistro=f.format(calendar.getTime());
		this.ultimaAlteracao=f.format(calendar.getTime());
	}
	
}
