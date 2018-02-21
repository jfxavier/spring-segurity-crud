package px.main.ocorrencia.modelos;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "envolvidos")
public class Envolvido {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	
	private String rg;
	
	private String cpf;
	
	private String endereco,numero,complemento,cep,bairro,cidadeUF,telefone,celular, email;

	@NotBlank(message = "Informe o nome do envolvido.")
	private String nome;

	@Type(type="text")
	private String obs;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mmS")
	private Date data;
	
	private boolean ativo;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "envolvido", orphanRemoval = true)
	private List<Vinculo> ocorrencias;
	
	
}
