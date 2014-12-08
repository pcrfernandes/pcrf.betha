package pcrf.betha.compras.fcd.compra;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pcrf.betha.app.session.SessionBean;
import pcrf.betha.base.fcd.Fcd;
import pcrf.betha.base.svc.Svc;
import pcrf.betha.compras.entity.compra.Compra;
import pcrf.betha.compras.entity.compra.CompraItem;
import pcrf.betha.compras.svc.compra.CompraSvc;

@Stateless
public class CompraFcd extends Fcd<Compra> {
	private static final long serialVersionUID = 1L;

	@Inject
	private CompraSvc svc;

	@Inject
	private SessionBean session;

	@Override
	public Svc<Compra> getSvc() {
		return svc;
	}

	@Override
	public Compra novo() {
		Compra compra = super.novo();
		
		compra.setUsuario(session.getUsuario());
		
		return compra;
	}
	
	public CompraItem novoItem() {
		return svc.novoItem();
	}
	
	public void addItem(Compra compra, CompraItem item) {
		svc.addItem(compra, item);
	}
	
	public void remItem(Compra compra, CompraItem item) {
		svc.remItem(compra, item);
	}

	public Compra obterCompleto(Object id) {
		return svc.obterCompleto(id);
	}
	
	@Override
	public Compra obterEdicao(Object id) {
		Compra compra = super.obterEdicao(id);
		
		if (compra != null && compra.getUsuario().equals(session.getUsuario()))
			return compra;
		
		return null;
	}

	@Override
	public List<Compra> obterTodos() {
		return svc.obterTodosUsuario(session.getUsuario().getIdusuario());
	}

	public List<Compra> obterTodosCompletos() {
		return svc.obterTodosCompletos();
	}

}
