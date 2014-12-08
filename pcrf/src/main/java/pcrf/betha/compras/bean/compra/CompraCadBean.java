package pcrf.betha.compras.bean.compra;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;

import pcrf.betha.base.bean.CadBean;
import pcrf.betha.base.fcd.Fcd;
import pcrf.betha.compras.entity.compra.Compra;
import pcrf.betha.compras.entity.compra.CompraItem;
import pcrf.betha.compras.entity.lista.Lista;
import pcrf.betha.compras.entity.lista.ListaItem;
import pcrf.betha.compras.fcd.compra.CompraFcd;
import pcrf.betha.compras.fcd.lista.ListaFcd;
import pcrf.betha.util.GeneralException;
import pcrf.betha.util.ImgUtils;

@Named
@ViewScoped
public class CompraCadBean extends CadBean<Compra> {
	private static final long serialVersionUID = 1L;

	@Inject
	private CompraFcd fcd;

	@Inject
	private ListaFcd listafcd;

	private Boolean addingItem = false;
	private String tituloTmp;
	private Double qtdTmp;
	private Double precoTmp;

	CompraItem itemTmp;

	private List<Lista> listas;
	private Lista lista;
	
	private List<CompraItem> comprar;
	private List<CompraItem> comprados;

	private Boolean verComprados = false;

	private Double totalComprar;
	private Double totalComprados;

	@Override
	protected Fcd<Compra> getFcd() {
		return fcd;
	}

	@Override
	public String getViewRedir() {
		return "/compras/compra/compracons.xhml";
	}

	@Override
	public void begin() {
		if (FacesContext.getCurrentInstance().isPostback())
			return;
		
		super.begin();
		
		this.carregarListas();
		this.carregarSubgrupos();
		
		if (this.getEditing() && this.getComprados().size() > 0 && this.getComprar().size() == 0)
			verComprados = true;
		
		/*if (!this.getEditing())
			this.prepararNovoItem();*/
	}
	
	protected void carregarListas() {
		this.listas = listafcd.obterAtivos();
	}
	
	protected void carregarSubgrupos() {
		this.carregarPendentes();
		this.carregarComprados();
	}
	
	protected void carregarPendentes() {
		this.comprar = new ArrayList<CompraItem>();
		this.totalComprar = new Double(0);
		
		for (CompraItem item: this.getEntity().getItens()) {
			if (!item.getChecked()) {
				comprar.add(item);
				totalComprar = totalComprar + item.getTotal();
			}
		}
	}

	protected void carregarComprados() {
		this.comprados = new ArrayList<CompraItem>();
		this.totalComprados = new Double(0);
		
		for (CompraItem item: this.getEntity().getItens()) {
			if (item.getChecked()) {
				comprados.add(item);
				totalComprados = totalComprados + item.getTotal();
			}
		}
	}

	public void prepararNovoItem() {
		tituloTmp = null;
		qtdTmp = new Double(1);
		precoTmp = new Double(0);
		this.addingItem = true;
	}
	
	public void addItem() {
		if (tituloTmp == null || tituloTmp.trim() == "")
			throw new GeneralException("Item não informado");
			
		if (qtdTmp == null || qtdTmp == 0)
			throw new GeneralException("Quantidade não informada");

		if (precoTmp == null)
			throw new GeneralException("Preço não informado");

		CompraItem item = fcd.novoItem();
		item.setTitulo(tituloTmp);
		item.setQtd(qtdTmp);
		item.setPreco(precoTmp);
		fcd.addItem(this.getEntity(), item);
		
		this.carregarSubgrupos();
		
		this.prepararNovoItem();
	}
	
	public void remItem(CompraItem item) {
		fcd.remItem(this.getEntity(), item);
		
		this.carregarSubgrupos();
	}

	public List<String> completeItens(String query) {
		List<String> sugestoes = new ArrayList<String>();
		
		if (query == null || query.trim() == "")
			return sugestoes;
		
		sugestoes = listafcd.obterSugestoesItens(query);
		
		for (CompraItem item: this.getEntity().getItens()) {
			if (item.isTransient() && item.getTitulo().toLowerCase().contains(query.toLowerCase()) && !sugestoes.contains(item.getTitulo()))
				sugestoes.add(item.getTitulo());
		}
		
		return sugestoes;
	}

	public String getTituloTmp() {
		return tituloTmp;
	}

	public void setTituloTmp(String tituloTmp) {
		this.tituloTmp = tituloTmp;
	}

	public Double getQtdTmp() {
		return qtdTmp;
	}

	public void setQtdTmp(Double qtdTmp) {
		this.qtdTmp = qtdTmp;
	}

	public Double getPrecoTmp() {
		return precoTmp;
	}

	public void setPrecoTmp(Double precoTmp) {
		this.precoTmp = precoTmp;
	}

	public Boolean getAddingItem() {
		return addingItem;
	}

	public Lista getLista() {
		return lista;
	}

	public void setLista(Lista lista) {
		this.lista = lista;
	}

	public List<Lista> getListas() {
		return listas;
	}
	
	public void listaChanged() {
		if (lista != null) {
			Lista l = listafcd.obterCompleto(lista.getIdlista());
			
			if (l != null) {
				for (ListaItem item: l.getItens()) {
					CompraItem ci = fcd.novoItem();
					ci.setItem(item);
					ci.setLista(l);
					
					fcd.addItem(this.getEntity(), ci);
				}
			}
			
			this.carregarPendentes();
		}
	}

	public List<CompraItem> getComprar() {
		return comprar;
	}

	public List<CompraItem> getComprados() {
		return comprados;
	}

	public void itemCheck(CompraItem item) {
		this.carregarSubgrupos();
	}

	public void itemUncheck(CompraItem item) {
		item.setChecked(false);
		
		this.carregarSubgrupos();
	}

	public Double getTotalComprar() {
		return totalComprar;
	}

	public Double getTotalComprados() {
		return totalComprados;
	}

	public Boolean getVerComprados() {
		return verComprados;
	}
	
	public void verCompleto() {
		this.verComprados = true;
	}

	public void handleFileUpload(FileUploadEvent event) {
		itemTmp.setImg(ImgUtils.saveImg(event.getFile().getContents()));
		
		RequestContext ctx = RequestContext.getCurrentInstance();
		ctx.execute("PF('imgDlg').hide()");
		ctx.update("form:itensTable");
	}
    
	public void capturarFoto(CaptureEvent captureEvent) {
		itemTmp.setImg(ImgUtils.saveImg(captureEvent.getData()));

		RequestContext ctx = RequestContext.getCurrentInstance();
		ctx.execute("PF('imgDlg').hide()");
		ctx.update("form:itensTable");
    }
	
	public void prepararImg(CompraItem item) {
		itemTmp = item;

		RequestContext ctx = RequestContext.getCurrentInstance();
		ctx.execute("PF('imgDlg').show()");
	}
	
	public void removerImg(CompraItem item) {
		item.setImg(null);
	}

}
