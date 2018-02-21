package px.main.ocorrencia.modelos;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "vinculos")
public class Vinculo {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	
	private String tipo;

	@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name = "ocorrencia_id")
	private Ocorrencia ocorrencia;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name = "envolvido_id")
	private Envolvido envolvido;
}
