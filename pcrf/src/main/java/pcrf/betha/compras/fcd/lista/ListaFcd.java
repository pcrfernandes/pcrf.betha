package pcrf.betha.compras.fcd.lista;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pcrf.betha.app.session.SessionBean;
import pcrf.betha.base.fcd.Fcd;
import pcrf.betha.base.svc.Svc;
import pcrf.betha.compras.entity.lista.Lista;
import pcrf.betha.compras.entity.lista.ListaItem;
import pcrf.betha.compras.svc.lista.ListaSvc;

@Stateless
public class ListaFcd extends Fcd<Lista> {
	private static final long serialVersionUID = 1L;

	@Inject
	private ListaSvc svc;
	
	@Inject
	private SessionBean session;
	
	@Override
	public Svc<Lista> getSvc() {
		return svc;
	}

	@Override
	public Lista novo() {
		Lista lista = super.novo();
		
		lista.setUsuario(session.getUsuario());
		
		return lista;
	}
	
	public ListaItem novoItem() {
		return svc.novoItem();
	}
	
	public void addItem(Lista lista, ListaItem item) {
		svc.addItem(lista, item);
	}
	
	public void remItem(Lista lista, ListaItem item) {
		svc.remItem(lista, item);
	}

	public Lista obterCompleto(Object id) {
		return svc.obterCompleto(id);
	}

	public List<Lista> obterTodosCompletos() {
		return svc.obterTodosCompletos();
	}
	
	public List<String> obterSugestoesItens(String query) {
		return svc.obterSugestoesItens(query);
	}

	@Override
	public Lista obterEdicao(Object id) {
		Lista lista = super.obterEdicao(id);
		
		if (lista != null && lista.getUsuario().equals(session.getUsuario()))
			return lista;
		
		return null;
	}

	public List<Lista> obterAtivos() {
		return svc.obterAtivos(session.getUsuario().getIdusuario());
	}
	
	public void inativar(Integer id) {
		svc.inativar(id);
	}
	
	/*public byte[] obterImg(Integer iditem) {
		return svc.obterImg(iditem);
	}*/
	
}
