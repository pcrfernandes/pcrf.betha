package pcrf.betha.admin.fcd.usuario;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pcrf.betha.admin.entity.usuario.Usuario;
import pcrf.betha.admin.svc.usuario.UsuarioSvc;
import pcrf.betha.base.fcd.Fcd;
import pcrf.betha.base.svc.Svc;

@Stateless
public class UsuarioFcd extends Fcd<Usuario> {
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioSvc svc;
	
	@Override
	public Svc<Usuario> getSvc() {
		return svc;
	}

	public Usuario obterUsuarioLogin(String login) {
		return svc.obterUsuarioLogin(login);
	}
	
	public Boolean verificarSenha(Usuario usuario, String senha) {
		return svc.verificarSenha(usuario, senha);
	}
	
	public void inativar(Integer id) {
		svc.inativar(id);
	}
	
	public void reativar(Integer id) {
		svc.reativar(id);
	}

	public List<Usuario> obterAtivos() {
		return svc.obterAtivos();
	}
}
