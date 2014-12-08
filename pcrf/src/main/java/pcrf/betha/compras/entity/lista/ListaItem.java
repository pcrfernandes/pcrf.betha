package pcrf.betha.compras.entity.lista;

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

@XmlRootElement(name="item")
@XmlAccessorType(XmlAccessType.FIELD)
@javax.persistence.Entity
@Table(name="listaitem")
public class ListaItem extends Item {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer iditem;

	@NotNull
    @JoinColumn(name = "idlista", referencedColumnName = "idlista")
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
	@XmlTransient
	private Lista lista;
	
	@Override
	public Object getId() {
		return iditem;
	}

	public Integer getIditem() {
		return iditem;
	}

	public void setIditem(Integer iditem) {
		this.iditem = iditem;
	}

	public Lista getLista() {
		return lista;
	}

	public void setLista(Lista lista) {
		this.lista = lista;
	}

}
