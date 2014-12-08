package pcrf.betha.compras.bean.lista;

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
import pcrf.betha.compras.entity.lista.Lista;
import pcrf.betha.compras.entity.lista.ListaItem;
import pcrf.betha.compras.fcd.lista.ListaFcd;
import pcrf.betha.util.GeneralException;
import pcrf.betha.util.ImgUtils;

@Named
@ViewScoped
public class ListaCadBean extends CadBean<Lista> {
	private static final long serialVersionUID = 1L;

	@Inject
	private ListaFcd fcd;

	private Boolean addingItem = false;
	private String tituloTmp;
	private Double precoTmp;
	
	ListaItem itemTmp;
	
	@Override
	protected Fcd<Lista> getFcd() {
		return fcd;
	}

	@Override
	public String getViewRedir() {
		return "/compras/lista/listacons.xhml";
	}

	@Override
	public void begin() {
		if (FacesContext.getCurrentInstance().isPostback())
			return;
		
		super.begin();
		
		if (!this.getEditing())
			this.prepararNovoItem();
	}

	public void prepararNovoItem() {
		tituloTmp = null;
		precoTmp = new Double(0);
		this.addingItem = true;
	}
		
	public void addItem() {
		if (tituloTmp == null || tituloTmp.trim() == "")
			throw new GeneralException("Item não informado");
			
		if (precoTmp == null)
			throw new GeneralException("Preço não informado");
		
		ListaItem item = fcd.novoItem();
		item.setTitulo(tituloTmp);
		item.setPreco(precoTmp);
		fcd.addItem(this.getEntity(), item);
		
		this.prepararNovoItem();
	}
	
	public void remItem(ListaItem item) {
		fcd.remItem(this.getEntity(), item);
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

	public List<String> completeItens(String query) {
		List<String> sugestoes = new ArrayList<String>();
		
		if (query == null || query.trim() == "")
			return sugestoes;
		
		sugestoes = fcd.obterSugestoesItens(query);
		
		for (ListaItem item: this.getEntity().getItens()) {
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

	public Double getPrecoTmp() {
		return precoTmp;
	}

	public void setPrecoTmp(Double precoTmp) {
		this.precoTmp = precoTmp;
	}

	public Boolean getAddingItem() {
		return addingItem;
	}

	public void prepararImg(ListaItem item) {
		itemTmp = item;

		RequestContext ctx = RequestContext.getCurrentInstance();
		ctx.execute("PF('imgDlg').show()");
	}
	
	public void removerImg(ListaItem item) {
		item.setImg(null);
	}
}
