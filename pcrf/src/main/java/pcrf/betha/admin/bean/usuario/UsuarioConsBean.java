package pcrf.betha.admin.bean.usuario;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pcrf.betha.admin.entity.usuario.Usuario;
import pcrf.betha.admin.fcd.usuario.UsuarioFcd;
import pcrf.betha.base.bean.ConsBean;
import pcrf.betha.base.fcd.Fcd;

@Named
@ViewScoped
public class UsuarioConsBean extends ConsBean<Usuario> {
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioFcd fcd;
	
	@Override
	protected Fcd<Usuario> getFcd() {
		return fcd;
	}

	@Override
	protected void obterDados() {
		this.lista = fcd.obterAtivos();
	}

	@Override
	public void inativar() {
		fcd.inativar(selected.getIdusuario());
		
		this.obterDados();
	}
	
}
