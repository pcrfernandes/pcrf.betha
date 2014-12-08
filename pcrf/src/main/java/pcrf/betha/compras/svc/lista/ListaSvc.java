package pcrf.betha.compras.svc.lista;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import pcrf.betha.base.dao.Dao;
import pcrf.betha.base.svc.Svc;
import pcrf.betha.compras.dao.lista.ListaDao;
import pcrf.betha.compras.entity.lista.Lista;
import pcrf.betha.compras.entity.lista.ListaItem;
import pcrf.betha.util.GeneralException;

@Stateless
public class ListaSvc extends Svc<Lista> {
	private static final long serialVersionUID = 1L;

	@Inject
	private ListaDao dao;
	
	@Override
	public Dao<Lista> getDao() {
		return dao;
	}

	@Override
	public Lista novo() {
		Lista lista = super.novo();
		
		lista.setAtivo(true);
		lista.setTotal(new Double(0));
		
		return lista;
	}
	
	@Override
	public Lista salvar(Lista entity) {
		this.calcularTotal(entity);
		
		return super.salvar(entity);
	}

	public ListaItem novoItem() {
		ListaItem item = new ListaItem();
		
		item.setPreco(new Double(0));
		
		return item;
	}
	
	public void addItem(Lista lista, ListaItem item) {
		item.setLista(lista);
		lista.getItens().add(item);
		
		this.calcularTotal(lista);
	}
	
	public void remItem(Lista lista, ListaItem item) {
		if (lista.getItens().contains(item)) {
			lista.getItens().remove(item);
			this.calcularTotal(lista);
		}
	}
	
	protected void calcularTotal(Lista lista) {
		Double total = new Double(0);
		
		for (ListaItem item: lista.getItens()) {
			total = total + item.getPreco();
		}
		
		lista.setTotal(total);
	}

	public List<Lista> obterAtivos(Integer idusuario) {
		TypedQuery<Lista> query = this.getEntityManager().createQuery("SELECT l FROM Lista l WHERE l.ativo = 1 and l.usuario.idusuario = ?1", Lista.class);
		query.setParameter(1, idusuario);
		
		return query.getResultList();
	}

	@Override
	public Lista obterEdicao(Object id) {
		Lista lista = super.obterEdicao(id);
		
		if (lista == null)
			throw new GeneralException(String.format("Lista não encontrada (%s)", id.toString()));
		
		/* Fetch itens */
		lista.getItens().size();
		
		return lista;
	}

	public Lista obterCompleto(Object id) {
		Lista lista = this.obter(id);
		
		if (lista != null) {
			/* Fetch itens */
			lista.getItens().size();
		}
		
		return lista;
	}

	public List<Lista> obterTodosCompletos() {
		List<Lista> list = this.obterTodos();
		
		for (Lista lista: list) {
			/* Fetch itens */
			lista.getItens().size();
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> obterSugestoesItens(String query) {
		String param = query == null? "": query.toLowerCase();
		
		Query q = this.getEntityManager().createQuery("SELECT distinct(i.titulo) FROM ListaItem i WHERE lower(i.titulo) like ?1");
		q.setParameter(1, "%" + param + "%");
		q.setMaxResults(10);

		return q.getResultList();
	}

	public void inativar(Integer id) {
		Lista lista = this.obter(id);
		
		if (lista == null)
			throw new GeneralException("Lista não localizada");
		
		if (!lista.getAtivo())
			throw new GeneralException("A lista já está inativa");
		
		lista.setAtivo(false);
		
		super.salvar(lista);
	}

	/*public byte[] obterImg(Integer iditem) {
		TypedQuery<ListaItem> query = this.getEntityManager().createQuery("SELECT l FROM ListaItem l WHERE l.iditem = ?1", ListaItem.class);
		query.setParameter(1, iditem);
		
		ListaItem item = query.getSingleResult();
		
		if (item != null)
			return item.getImg();
		
		return null;
	}*/
}
