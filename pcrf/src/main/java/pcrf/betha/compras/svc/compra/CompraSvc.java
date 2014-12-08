package pcrf.betha.compras.svc.compra;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import org.joda.time.LocalDate;

import pcrf.betha.base.dao.Dao;
import pcrf.betha.base.svc.Svc;
import pcrf.betha.compras.dao.compra.CompraDao;
import pcrf.betha.compras.entity.compra.Compra;
import pcrf.betha.compras.entity.compra.CompraItem;
import pcrf.betha.util.GeneralException;

@Stateless
public class CompraSvc extends Svc<Compra> {
	private static final long serialVersionUID = 1L;

	@Inject
	private CompraDao dao;
	
	@Override
	public Dao<Compra> getDao() {
		return dao;
	}

	@Override
	public Compra novo() {
		Compra compra = super.novo();
		
		compra.setData((new LocalDate()).toDateTimeAtStartOfDay().toDate());
		compra.setTotal(new Double(0));
		
		return compra;
	}
	
	@Override
	public Compra salvar(Compra entity) {
		this.calcularTotais(entity);
		
		return super.salvar(entity);
	}

	public CompraItem novoItem() {
		CompraItem item = new CompraItem();
		
		item.setChecked(false);
		item.setQtd(new Double(1));
		item.setPreco(new Double(0));
		
		return item;
	}
	
	public void addItem(Compra compra, CompraItem item) {
		item.setCompra(compra);
		compra.getItens().add(item);
		
		this.calcularTotais(compra);
	}
	
	public void remItem(Compra compra, CompraItem item) {
		if (compra.getItens().contains(item)) {
			compra.getItens().remove(item);
			
			this.calcularTotais(compra);
		}
	}

	@Override
	public Compra obterEdicao(Object id) {
		Compra compra = super.obterEdicao(id);
		
		if (compra == null)
			throw new GeneralException(String.format("Compra não encontrada (%s)", id.toString()));
		
		/* Fetch itens */
		compra.getItens().size();
		
		return compra;
	}

	public Compra obterCompleto(Object id) {
		Compra compra = this.obter(id);
		
		if (compra != null) {
			/* Fetch itens */
			compra.getItens().size();
		}
		
		return compra;
	}

	protected void calcularTotais(Compra compra) {
		Double total = new Double(0);
		
		for (CompraItem item: compra.getItens()) {
			total = total + (item.getPreco() * item.getQtd());
		}
		
		compra.setTotal(total);
	}

	public List<Compra> obterTodosUsuario(Integer idusuario) {
		TypedQuery<Compra> query = this.getEntityManager().createQuery("SELECT c FROM Compra c WHERE c.usuario.idusuario = ?1", Compra.class);
		query.setParameter(1, idusuario);
		
		return query.getResultList();
	}

	public List<Compra> obterTodosCompletos() {
		List<Compra> list = this.obterTodos();
		
		for (Compra compra: list) {
			/* Fetch itens */
			compra.getItens().size();
		}
		
		return list;
	}
}
