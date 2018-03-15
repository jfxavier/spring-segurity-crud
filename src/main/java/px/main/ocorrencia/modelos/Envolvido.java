package px.main.ocorrencia.modelos;

import static javax.persistence.GenerationType.IDENTITY;

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

	private String endereco, numero, complemento, cep, bairro, cidadeUF, telefone, celular, email;

	@NotBlank(message = "Informe o nome do envolvido.")
	private String nome;

	@Type(type = "text")
	private String obs;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "envolvido", orphanRemoval = true)
	private List<Vinculo> ocorrencias;

	public Envolvido() {
	}

	public Envolvido(Envolvido env) {
		this.id = env.id;
		this.rg = env.rg;
		this.cpf = env.cpf;
		this.endereco = env.endereco;
		this.numero = env.numero;
		this.complemento = env.complemento;
		this.cep = env.cep;
		this.bairro = env.bairro;
		this.cidadeUF = env.cidadeUF;
		this.telefone = env.telefone;
		this.celular = env.celular;
		this.email = env.email;
		this.nome = env.nome;
		this.obs = env.obs;
		this.ocorrencias = env.ocorrencias;
	}

	public void Set(Envolvido env) {
		this.rg = env.rg;
		this.cpf = env.cpf;
		this.endereco = env.endereco;
		this.numero = env.numero;
		this.complemento = env.complemento;
		this.cep = env.cep;
		this.bairro = env.bairro;
		this.cidadeUF = env.cidadeUF;
		this.telefone = env.telefone;
		this.celular = env.celular;
		this.email = env.email;
		this.nome = env.nome;
		this.obs = env.obs;
	}
	

}
