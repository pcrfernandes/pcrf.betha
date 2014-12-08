package pcrf.betha.app.session;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import pcrf.betha.admin.entity.usuario.Usuario;
import pcrf.betha.admin.fcd.usuario.UsuarioFcd;
import pcrf.betha.util.GeneralException;

@Named
@SessionScoped
public class SessionBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String USER_ADMIN = "admin";

	@Inject
	private UsuarioFcd usuarioFcd;
	
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}
	
	public Boolean isActive() {
		return usuario != null;
	}
	
	public void login(String login, String senha) {
		Usuario uCheck = usuarioFcd.obterUsuarioLogin(login);
		
		if (uCheck == null)
			throw new GeneralException("Usuário inexistente");

		if (!uCheck.getAtivo())
			throw new GeneralException("Usuário inativo");
		
		if (!usuarioFcd.verificarSenha(uCheck, senha))
			throw new GeneralException("Senha inválida");
		
		this.usuario = uCheck;
	}
	
	public String logout() {
		usuario = null;
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		RequestContext ctx = RequestContext.getCurrentInstance();
		ctx.execute("sessionStorage.clear();");
		
		return "/login.xhtml";
	}

	public Boolean isAdmin() {
		return this.isActive() && this.usuario.getLogin().equalsIgnoreCase(USER_ADMIN);
	}
	
}
