package pcrf.betha.compras.entity.lista;

import java.util.ArrayList;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import pcrf.betha.admin.entity.usuario.Usuario;
import pcrf.betha.base.entity.Entity;

@XmlRootElement(name="lista")
@XmlAccessorType(XmlAccessType.FIELD)
@javax.persistence.Entity
@Table(name="lista")
public class Lista extends Entity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idlista;
	
	@NotNull
	@Size(min=1, max=100)
	private String titulo;

	@NotNull
	private Boolean ativo;
	
	@NotNull
	private Double total;
	
	@NotNull
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional=false)
	private Usuario usuario;
	
	@OneToMany(mappedBy="lista", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
	@XmlElementWrapper(name="itens")
	private List<ListaItem> itens = new ArrayList<ListaItem>();
	
	@Override
	public Object getId() {
		return idlista;
	}

	@Override
	public String getLabel() {
		return titulo;
	}

	public Integer getIdlista() {
		return idlista;
	}

	public void setIdlista(Integer idlista) {
		this.idlista = idlista;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ListaItem> getItens() {
		return itens;
	}

	public void setItens(List<ListaItem> itens) {
		this.itens = itens;
	}

}
