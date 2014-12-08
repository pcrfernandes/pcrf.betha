package pcrf.betha.compras.entity.compra;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import pcrf.betha.compras.entity.base.Item;
import pcrf.betha.compras.entity.lista.Lista;
import pcrf.betha.compras.entity.lista.ListaItem;

@XmlRootElement(name="item")
@XmlAccessorType(XmlAccessType.FIELD)
@javax.persistence.Entity
@Table(name="compraitem")
public class CompraItem extends Item {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idcompraitem;

	@NotNull
    @JoinColumn(name = "idcompra", referencedColumnName = "idcompra")
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
	@XmlTransient
	private Compra compra;
	
    @JoinColumn(name = "idlista", referencedColumnName = "idlista")
    @ManyToOne(optional=true)
	@XmlTransient
	private Lista lista;
	
    @JoinColumn(name = "iditem", referencedColumnName = "iditem")
    @ManyToOne(optional=true)
	@XmlTransient
	private ListaItem item;

    @NotNull
    private Double qtd;
    
    @NotNull
    private Boolean checked;
    
	@Override
	public Object getId() {
		return idcompraitem;
	}

	public Integer getIdcompraitem() {
		return idcompraitem;
	}

	public void setIdcompraitem(Integer idcompraitem) {
		this.idcompraitem = idcompraitem;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Lista getLista() {
		return lista;
	}

	public void setLista(Lista lista) {
		this.lista = lista;
	}

	public ListaItem getItem() {
		return item;
	}

	public void setItem(ListaItem item) {
		this.item = item;
		
		if (item != null) {
			this.setTitulo(item.getTitulo());
			this.setPreco(item.getPreco());
			this.setImg(item.getImg());
		}
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Double getQtd() {
		return qtd;
	}

	public void setQtd(Double qtd) {
		this.qtd = qtd;
	}

	public Double getTotal() {
		if (this.getPreco() == null)
			return new Double(0);
		else
			return new Double(this.getPreco() * this.getQtd());
	}

}
