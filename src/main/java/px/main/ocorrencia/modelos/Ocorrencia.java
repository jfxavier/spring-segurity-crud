package px.main.ocorrencia.modelos;

import static javax.persistence.GenerationType.IDENTITY;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import px.main.ocorrencia.views.OcorrenciaView;

@Entity
@Table(name = "ocorrencia")
public class Ocorrencia {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	
	private int numero;

	private String local, usuario, turno, usuarioAlteracao, setor, status;

	@NotBlank(message = "Informe um código para a ocorrência.")
	private String codigo;

	@NotBlank(message = "Registre alguma informação para a ocorrência.")
	@Type(type = "text")
	private String informacao;
	@Type(type = "text")
	private String guardas;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date data;
	@DateTimeFormat(pattern = "HH:mm")
	private Date hora;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataRegistro;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date ultimaAlteracao;

	public Ocorrencia() {
		this.id = null;
		Calendar calendar = Calendar.getInstance();
		this.data = this.dataRegistro = this.ultimaAlteracao = calendar.getTime();
		this.hora = new Date();
		this.status = "Ativo";
	}

	public Ocorrencia(Integer id, String local, String usuario, String turno, String usuarioAlteracao, String setor, String codigo, int numero, String informacao, String guardas, Date data, Date hora, Date dataRegistro,
			Date ultimaAlteracao, String status) {
		super();
		this.id = id;
		this.local = local;
		this.usuario = usuario;
		this.turno = turno;
		this.usuarioAlteracao = usuarioAlteracao;
		this.setor = setor;
		this.codigo = codigo;
		this.numero = numero;
		this.informacao = informacao;
		this.guardas = guardas;
		this.data = data;
		this.hora = hora;
		this.dataRegistro = dataRegistro;
		this.ultimaAlteracao = ultimaAlteracao;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(String usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getInformacao() {
		return informacao;
	}

	public void setInformacao(String informacao) {
		this.informacao = informacao;
	}

	public String getGuardas() {
		return guardas;
	}

	public void setGuardas(String guardas) {
		this.guardas = guardas;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Ocorrencia [id=" + id + ", local=" + local + ", usuario=" + usuario + ", turno=" + turno + ", usuarioAlteracao=" + usuarioAlteracao + ", setor=" + setor + ", codigo=" + codigo + ", numero=" + numero + ", informacao="
				+ informacao + ", guardas=" + guardas + ", data=" + data + ", hora=" + hora + ", dataRegistro=" + dataRegistro + ", ultimaAlteracao=" + ultimaAlteracao + ", status=" + status + "]";
	}

	public OcorrenciaView Get() {
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat f2 = new SimpleDateFormat("hh:mm");
		SimpleDateFormat f3 = new SimpleDateFormat("yyyy");

		OcorrenciaView oV = new OcorrenciaView();
		oV.setId(this.id);
		oV.setNumero(this.numero + "/" + f3.format(this.dataRegistro));
		oV.setLocal(this.local);
		oV.setUsuario(this.usuario);
		oV.setTurno(this.turno);
		oV.setUsuarioAlteracao(this.usuarioAlteracao);
		oV.setSetor(this.setor);
		oV.setStatus(this.status);
		oV.setData(f.format(this.data));
		oV.setHora(f2.format(this.hora));
		oV.setDataRegistro(f.format(this.dataRegistro));
		oV.setUltimaAlteracao(f.format(this.ultimaAlteracao));
		oV.setCodigo(this.codigo);
		oV.setInformacao(this.informacao);
		oV.setGuardas(this.guardas);
		return oV;

	}

	public Ocorrencia(OcorrenciaView ov) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat f2 = new SimpleDateFormat("hh:mm");

		this.id = ov.getId();
		if(ov.getNumero()!=""){this.numero = Integer.getInteger(ov.getNumero().split("/")[0]);}
		this.local = ov.getLocal();
		this.turno = ov.getTurno();
		this.setor = ov.getSetor();
		this.codigo = ov.getCodigo();
		this.informacao = ov.getInformacao();
		this.guardas = ov.getGuardas();
		this.usuario = ov.getUsuario();
		this.status = ov.getStatus();
		if(ov.getData()!="")this.data = f.parse(ov.getData());
		if(ov.getHora()!="")this.hora = f2.parse(ov.getHora());
		if(ov.getDataRegistro()!="")this.dataRegistro = f.parse(ov.getDataRegistro());
		if(ov.getUltimaAlteracao()!="")this.ultimaAlteracao =  f.parse(ov.getUltimaAlteracao());
		
		this.usuario = ov.getUsuario();
		this.usuarioAlteracao = ov.getUsuarioAlteracao();
	}
	public void Set(OcorrenciaView ov) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat f2 = new SimpleDateFormat("hh:mm");

		this.id = ov.getId();
		this.local = ov.getLocal();
		this.turno = ov.getTurno();
		this.setor = ov.getSetor();
		this.codigo = ov.getCodigo();
		this.informacao = ov.getInformacao();
		this.guardas = ov.getGuardas();
		this.usuario = ov.getUsuario();
		this.status = ov.getStatus();
		if(ov.getData()!="")this.data = f.parse(ov.getData());
		if(ov.getHora()!="")this.hora = f2.parse(ov.getHora());
		if(ov.getDataRegistro()!="")this.dataRegistro = f.parse(ov.getDataRegistro());
		if(ov.getUltimaAlteracao()!="")this.ultimaAlteracao =  f.parse(ov.getUltimaAlteracao());
		
		this.usuario = ov.getUsuario();
		this.usuarioAlteracao = ov.getUsuarioAlteracao();
	}
}