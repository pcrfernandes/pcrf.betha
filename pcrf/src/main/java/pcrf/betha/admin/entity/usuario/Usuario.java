package pcrf.betha.admin.entity.usuario;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import pcrf.betha.base.entity.Entity;

@XmlRootElement(name="usuario")
@XmlAccessorType(XmlAccessType.FIELD)
@javax.persistence.Entity
@Table(name="usuario")
public class Usuario extends Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idusuario;
	
	@NotNull
	@Size(min=1, max=100)
	private String login;

	@NotNull
	@Size(min=1, max=100)
	@XmlTransient
	private String senha;

	@NotNull
	private Boolean ativo;
	
	@Override
	public Object getId() {
		return idusuario;
	}

	@Override
	public String getLabel() {
		return login;
	}

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
