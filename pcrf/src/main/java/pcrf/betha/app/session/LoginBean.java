package pcrf.betha.app.session;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pcrf.betha.util.FacesUtils;

@Named
@ViewScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private SessionBean session;
	
	private String usuario;
	private String senha;
	
	public void login() {
		session.login(usuario, senha);
		
		FacesUtils.redir("/index.xhtml");
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
