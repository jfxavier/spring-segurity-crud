package px.main.seguranca.modelos;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "grupos")
public class Grupo {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	int id;
	String nome;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "grupo")
	private List<GrupoRegra> regras;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "grupos_usuarios", joinColumns = { @JoinColumn(name = "grupo_id") }, inverseJoinColumns = { @JoinColumn(name = "usuario_id") })
	private List<Usuario> usuarios;

	public Grupo(int id, String nome, List<GrupoRegra> regras,
			List<Usuario> usuarios) {
		this.id = id;
		this.nome = nome;
		this.regras = regras;
		this.usuarios = usuarios;
	}

	public Grupo() {
		regras=new ArrayList<GrupoRegra>();
		usuarios=new ArrayList<Usuario>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<GrupoRegra> getRegras() {
		return regras;
	}

	public void setRegras(List<GrupoRegra> regras) {
		this.regras = regras;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public void getUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Grupo))
			return false;
		if (obj == this)
			return true;

		Grupo rhs = (Grupo) obj;
		if ((id != rhs.id) || (nome != rhs.nome) || (regras != rhs.regras)
				|| (usuarios != rhs.usuarios)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("{id:%s, nome:%s, regras:%s, usuarios:%s}", id,
				nome, regras, usuarios);
	}


	public boolean regrasContains(String v) {
		if(regras.size()==0) return false;
		for (GrupoRegra gr : regras) {
			if (gr.getRegra().equals(v)) {
				return true;
			}
		}
		return false;
	}
	public boolean usuariosContains(String v) {
		for (Usuario gr : usuarios) {
			if (gr.getLogin().equals(v)) {
				return true;
			}
		}
		return false;
	}

	public void regrasRemove(String v) {
		System.out.println(regras.size());
		for(int i=0;i<regras.size();i++){
			if (regras.get(i).getRegra().equals(v)) {
				System.out.println(regras.get(i).getRegra());
				this.regras.remove(i);
			}
		}
	}
	public void usuariosRemove(String v) {
		for(int i=0;i<usuarios.size();i++){
			if (usuarios.get(i).getLogin().equals(v)) {
				this.usuarios.remove(i);
			}
			
		}
	}

}
