package px.main.seguranca.modelos;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios_regras")
public class UsuarioRegra {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	private String regra;

	public UsuarioRegra(Integer id, Usuario usuario, String regra) {
		this.id = id;
		this.usuario = usuario;
		this.regra = regra;
	}
	public UsuarioRegra(String regra, Usuario usuario) {
		this.usuario = usuario;
		this.regra = regra;
	}
	public UsuarioRegra() {
		this.usuario = new Usuario();
	}
	public UsuarioRegra(String regra) {
		this.usuario = new Usuario();
		this.regra = regra;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public Integer getUsuarioId() {
		return usuario.getId();
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getRegra() {
		return regra;
	}
	public void setRegra(String regra) {
		this.regra = regra;
	}
	@Override
	public String toString() {
		return "UsuarioRegra [id=" + id + ", regra=" + regra + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((regra == null) ? 0 : regra.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioRegra other = (UsuarioRegra) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (regra == null) {
			if (other.regra != null)
				return false;
		} else if (!regra.equals(other.regra))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
	

}