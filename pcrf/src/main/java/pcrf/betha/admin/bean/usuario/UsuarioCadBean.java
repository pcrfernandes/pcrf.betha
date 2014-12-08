package pcrf.betha.admin.bean.usuario;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pcrf.betha.admin.entity.usuario.Usuario;
import pcrf.betha.admin.fcd.usuario.UsuarioFcd;
import pcrf.betha.base.bean.CadBean;
import pcrf.betha.base.fcd.Fcd;

@Named
@ViewScoped
public class UsuarioCadBean extends CadBean<Usuario> {
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioFcd fcd;
	
	@Override
	protected Fcd<Usuario> getFcd() {
		return fcd;
	}

	@Override
	public String getViewRedir() {
		return "/admin/usuario/usuariocons.xhml";
	}

}
