package pcrf.betha.compras.entity.base;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import pcrf.betha.base.entity.Entity;

@MappedSuperclass
public abstract class Item extends Entity {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min=1, max=100)
	private String titulo;

	@NotNull
	private Double preco;

	private String img;
	
	@Override
	public String getLabel() {
		return titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
