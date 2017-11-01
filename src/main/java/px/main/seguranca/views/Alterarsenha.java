package px.main.seguranca.views;

public class Alterarsenha {
	private String senha,nova,repetida;


	public Alterarsenha() {
		super();
	}
	
	public Alterarsenha(String senha, String nova, String repetida) {
		super();
		this.senha = senha;
		this.nova = nova;
		this.repetida = repetida;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNova() {
		return nova;
	}

	public void setNova(String nova) {
		this.nova = nova;
	}

	public String getRepetida() {
		return repetida;
	}

	public void setRepetida(String repetida) {
		this.repetida = repetida;
	}
	@Override
	public String toString() {
		return "Alterarsenha [senha=" + senha + ", nova=" + nova + ", repetida=" + repetida + "]";
	}

	public boolean CompararSenhas(){
		if(nova.equals(repetida)){
		return true;	
		}else{
		return false;
		}
	}
}
