package pcrf.betha.compras.bean.lista;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pcrf.betha.base.bean.ConsBean;
import pcrf.betha.base.fcd.Fcd;
import pcrf.betha.compras.entity.lista.Lista;
import pcrf.betha.compras.fcd.lista.ListaFcd;

@Named
@ViewScoped
public class ListaConsBean extends ConsBean<Lista> {
	private static final long serialVersionUID = 1L;

	@Inject
	private ListaFcd fcd;
	
	@Override
	protected Fcd<Lista> getFcd() {
		return fcd;
	}

	@Override
	protected void obterDados() {
		this.lista = fcd.obterAtivos();
	}

	@Override
	public void inativar() {
		fcd.inativar(selected.getIdlista());
		
		this.obterDados();
	}

}
