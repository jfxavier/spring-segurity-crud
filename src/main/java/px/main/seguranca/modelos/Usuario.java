package px.main.seguranca.modelos;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import org.hibernate.validator.constraints.NotBlank;

import px.main.seguranca.views.UsuarioView;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;

	@NotBlank
	private String login;

	@NotBlank
	private String nome;

	private String senha, setor, obs;
	private boolean enabled;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "usuario", orphanRemoval = true)
	private List<UsuarioRegra> regras;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "grupos_usuarios", joinColumns = { @JoinColumn(name = "grupo_id") }, inverseJoinColumns = { @JoinColumn(name = "usuario_id") })
	private List<Grupo> grupos;

	public Usuario(Integer id, String usuario, String senha, String nome, String setor, String obs, boolean enabled, List<UsuarioRegra> regras, List<Grupo> grupos) {
		this.id = id;
		this.login = usuario;
		this.senha = senha;
		this.nome = nome;
		this.setor = setor;
		this.obs = obs;
		this.enabled = enabled;
		this.regras = regras;
		this.grupos = grupos;
	}

	public Usuario() {
		id = 0;
		senha = "";
		enabled = true;
		regras = new ArrayList<UsuarioRegra>();
		grupos = new ArrayList<Grupo>();
	}

	public Usuario(Usuario user) {
		this.id = user.getId();
		this.login = user.getLogin();
		this.senha = user.getSenha();
		this.nome = user.getNome();
		this.setor = user.getSetor();
		this.obs = user.getObs();
		this.enabled = user.isEnabled();
		this.regras = user.getRegras();
		this.grupos = user.getGrupos();
	}

	public Usuario(UsuarioView view) {
		this.id = view.getId();
		this.login = view.getLogin();
		this.nome = view.getNome();
		this.senha = view.getSenha();
		this.setor = view.getSetor();
		this.obs = view.getObs();
		this.enabled = view.isEnabled();
		this.regras = new ArrayList<UsuarioRegra>();
		if (view.isUsuario()) {
			if (this.findRegrasInt("ROLE_USER") == 0) {
				this.regras.add(new UsuarioRegra("ROLE_USER", this));
			}
		} else {
			if (this.findRegrasInt("ROLE_USER") != 0) {
				delRegras("ROLE_USER");
			}
		}
		if (view.isAdm()) {
			if (this.findRegrasInt("ROLE_ADM") == 0) {
				this.regras.add(new UsuarioRegra("ROLE_ADM", this));
			}
		} else {
			if (this.findRegrasInt("ROLE_ADM") != 0) {
				delRegras("ROLE_ADM");

			}
		}
		if (view.isVisitante()) {
			if (this.findRegrasInt("ROLE_VISITANTE") == 0) {
				this.regras.add(new UsuarioRegra("ROLE_VISITANTE", this));
			}
		} else {
			if (this.findRegrasInt("ROLE_VISITANTE") != 0) {
				delRegras("ROLE_VISITANTE");
			}
		}
		grupos = new ArrayList<Grupo>();

	}

	public void Set(UsuarioView view) {

		if (view.getId() != 0)
			this.id = view.getId();
		if (view.getLogin() != "")
			this.login = view.getLogin();
		if (view.getNome() != "")
			this.nome = view.getNome();
		if (view.getSenha() != "")
			this.senha = view.getSenha();
		if (view.getSetor() != "")
			this.setor = view.getSetor();
		if (view.getObs() != "")
			this.obs = view.getObs();
		this.enabled = view.isEnabled();
		if (view.isUsuario()) {
			if (this.findRegrasInt("ROLE_USER") == 0) {
				this.regras.add(new UsuarioRegra("ROLE_USER", this));
			}
		} else {
			if (this.findRegrasInt("ROLE_USER") != 0) {
				delRegras("ROLE_USER");
			}
		}
		if (view.isAdm()) {
			if (this.findRegrasInt("ROLE_ADM") == 0) {
				this.regras.add(new UsuarioRegra("ROLE_ADM", this));
			}
		} else {
			if (this.findRegrasInt("ROLE_ADM") != 0) {
				delRegras("ROLE_ADM");

			}
		}
		if (view.isVisitante()) {
			if (this.findRegrasInt("ROLE_VISITANTE") == 0) {
				this.regras.add(new UsuarioRegra("ROLE_VISITANTE", this));
			}
		} else {
			if (this.findRegrasInt("ROLE_VISITANTE") != 0) {
				delRegras("ROLE_VISITANTE");
			}
		}
		grupos = new ArrayList<Grupo>();

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String usuario) {
		this.login = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = MD5(senha);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String funcao) {
		this.setor = funcao;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<UsuarioRegra> getRegras() {
		return regras;
	}

	public List<String> getRegrasList() {
		List<String> lista = new ArrayList<String>();
		for (UsuarioRegra regra : this.regras) {
			lista.add(regra.getRegra());
		}
		return lista;
	}

	public void setRegras(List<UsuarioRegra> regras) {
		this.regras = regras;
	}

	public void setRegras(String usuarioRegra) {
		this.regras.add(new UsuarioRegra(usuarioRegra, this));
	}

	public UsuarioRegra findRegras(String regra) {
		for (UsuarioRegra u : this.regras) {
			if (u.getRegra().equals(regra))
				return u;
		}
		return null;
	}

	public int findRegrasInt(String regra) {
		for (UsuarioRegra u : this.regras) {
			if (u.getRegra().equals(regra))
				return u.getId();
		}
		return 0;
	}

	public void delRegras(String r) {
		this.regras.remove(this.findRegras(r));
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	@Override
	public String toString() {
		return String.format("{id:%s, login:%s, senha:%s, nome:%s, funcao:%s, obs:%s, enabled:%s, regras:%s, grupos:%s}", id, login, senha, nome, setor, obs, enabled, regras, grupos);
	}

	public static String MD5(String s) {
		MessageDigest m;
		String temp = "";
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(), 0, s.length());
			temp = new BigInteger(1, m.digest()).toString(16);
			return temp;
		} catch (NoSuchAlgorithmException e) {

			System.out.println("-------------------------->ERRO:" + e.getMessage());
			e.printStackTrace();
		}
		return "d41d8cd98f00b204e9800998ecf8427e";
	}
}
