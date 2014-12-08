package pcrf.betha.compras.bean.compra;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pcrf.betha.base.bean.ConsBean;
import pcrf.betha.base.fcd.Fcd;
import pcrf.betha.compras.entity.compra.Compra;
import pcrf.betha.compras.fcd.compra.CompraFcd;

@Named
@ViewScoped
public class CompraConsBean extends ConsBean<Compra> {
	private static final long serialVersionUID = 1L;

	@Inject
	private CompraFcd fcd;
	
	@Override
	protected Fcd<Compra> getFcd() {
		return fcd;
	}

}
