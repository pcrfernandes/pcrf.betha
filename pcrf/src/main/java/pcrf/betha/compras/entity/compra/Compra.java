package pcrf.betha.compras.entity.compra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import pcrf.betha.admin.entity.usuario.Usuario;
import pcrf.betha.base.entity.Entity;

@XmlRootElement(name="compra")
@XmlAccessorType(XmlAccessType.FIELD)
@javax.persistence.Entity
@Table(name="compra")
public class Compra extends Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idcompra;
	
	@NotNull
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional=false)
	private Usuario usuario;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Size(max=100)
	private String local;

	@NotNull
	private Double total;

	@OneToMany(mappedBy="compra", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
	@XmlElementWrapper(name="itens")
	private List<CompraItem> itens = new ArrayList<CompraItem>();
	
	@Override
	public Object getId() {
		return idcompra;
	}

	@Override
	public String getLabel() {
    	/*String dataStr = new SimpleDateFormat("dd/MM/yyyy").format(data);

    	if (local != null && !local.isEmpty())
    		return local + " em " + dataStr; 
    	else
    		return dataStr;*/
		
		return local;
	}

	public Integer getIdcompra() {
		return idcompra;
	}

	public void setIdcompra(Integer idcompra) {
		this.idcompra = idcompra;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<CompraItem> getItens() {
		return itens;
	}

	public void setItens(List<CompraItem> itens) {
		this.itens = itens;
	}

}
