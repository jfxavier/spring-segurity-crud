package px.main.ocorrencia.views;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import lombok.Data;
import px.main.ocorrencia.modelos.Ocorrencia;
import px.main.ocorrencia.repository.OcorrenciaSpecification;


@Data
@Component
public class ConsultaView {
	
	private int pagina;
	private String usuario, turno, setor, codigo,datainicio,datafim,horainicio,horafim,registroinicio,registrofim;
	
	
	public Specifications<Ocorrencia> Query() throws ParseException{
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat formatter2 = new SimpleDateFormat("HH:mm");
		
		
		Specifications<Ocorrencia> s=null;
		if(!this.getDatainicio().isEmpty()){
				s= Specifications.where(OcorrenciaSpecification
						.DateGreaterThanOrEqual((Date)formatter.parse(this.getDatainicio()),"data"));
		}else{
			Calendar c=Calendar.getInstance();
			c.set(2000,1,1);
			s= Specifications.where(OcorrenciaSpecification
					.DateGreaterThanOrEqual(c.getTime(),"data"));
		}
		if(!this.getDatafim().isEmpty()){
			s= s.and(OcorrenciaSpecification
					.DateLessThanOrEqual((Date)formatter.parse(this.getDatafim()),"data"));
		}
		if(!this.getHorainicio().isEmpty()){
			s= s.and(OcorrenciaSpecification
					.DateGreaterThanOrEqual((Date)formatter2.parse(this.getHorainicio()),"hora"));
		}
		if(!this.getHorafim().isEmpty()){
			s= s.and(OcorrenciaSpecification
					.DateLessThanOrEqual((Date)formatter2.parse(this.getHorafim()),"hora"));
		}
		if(!this.getRegistroinicio().isEmpty()){
			s= s.and(OcorrenciaSpecification
					.DateGreaterThanOrEqual((Date)formatter.parse(this.getRegistroinicio()),"dataRegistro"));
		}
		if(!this.getRegistrofim().isEmpty()){
			s= s.and(OcorrenciaSpecification
					.DateLessThanOrEqual((Date)formatter.parse(this.getRegistrofim()),"dataRegistro"));
		}
		if(!this.getCodigo().isEmpty()){
			s= s.and(OcorrenciaSpecification
					.ByField(this.getCodigo(),"codigo"));
		}
		if(!this.getUsuario().isEmpty()){
			s= s.and(OcorrenciaSpecification
					.ByField(this.getUsuario(),"guarda"));
		}
		if(!this.getTurno().isEmpty()){
			s= s.and(OcorrenciaSpecification
					.ByField(this.getTurno(),"turno"));
		}
		if(!this.getSetor().isEmpty()){
			s= s.and(OcorrenciaSpecification
					.ByField(this.getSetor(),"setor"));
		}
		return s;
	}	
}
