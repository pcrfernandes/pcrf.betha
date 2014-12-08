package pcrf.betha.rest.compra;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pcrf.betha.admin.entity.usuario.Usuario;
import pcrf.betha.admin.fcd.usuario.UsuarioFcd;
import pcrf.betha.compras.entity.compra.Compra;
import pcrf.betha.compras.entity.compra.CompraItem;
import pcrf.betha.compras.fcd.compra.CompraFcd;

@Path("/compras/compra")
public class CompraResources extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	private CompraFcd compraFcd; 

	@Inject
	private UsuarioFcd usuarioFcd; 

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/all")
	public List<Compra> getXML() {
		return compraFcd.obterTodosCompletos();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_XML)
	public Compra getCompra(@PathParam("id") int id) {
		return compraFcd.obterCompleto(id);
	}

	@GET
	@Path("/list")
	@Produces(MediaType.TEXT_PLAIN)
	public String getList() {
		return compraFcd.obterTodosCompletos().toString();
	}
	
	@GET
	@Path("/label/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLabel(@PathParam("id") int id) {
		Compra compra = compraFcd.obter(id);
		
		if (compra == null)
			return "(Compra não encontrada)";
		
    	String data = new SimpleDateFormat("dd/MM/yyyy").format(compra.getData());

		return compra.getLocal() + " em " + data;
	}

	@POST
	@Path("/add")
	@Produces(MediaType.TEXT_PLAIN)
	public String addCompra(@FormParam("data") Date data, @FormParam("local") String local, @FormParam("login") String login, @FormParam("senha") String senha) {
		Usuario usuario = usuarioFcd.obterUsuarioLogin(login);
		
		if (usuario != null && usuarioFcd.verificarSenha(usuario, senha)) {
			Compra compra = compraFcd.novo();
			compra.setData(data);
			compra.setLocal(local);
			compra.setUsuario(usuario);
			
			Compra managed = compraFcd.salvar(compra);
			
			/* Retorna o código da nova compra */
			return managed.getIdcompra().toString();
		}
		
		return "Usuário inválido";
	}

	@POST
	@Path("/additem")
	@Produces(MediaType.TEXT_PLAIN)
	public String addItem(@FormParam("idcompra") Integer idcompra, @FormParam("item") String item, @FormParam("qtd") Double qtd, @FormParam("preco") Double preco, @FormParam("checked") Boolean checked, @FormParam("login") String login, @FormParam("senha") String senha) {
		Usuario usuario = usuarioFcd.obterUsuarioLogin(login);
		
		if (usuario != null && usuarioFcd.verificarSenha(usuario, senha)) {
			Compra compra = compraFcd.obterCompleto(idcompra);
			
			if (compra == null)
				return "Compra não encontrada";
			
			if (!compra.getUsuario().equals(usuario))
				return "A compra não pertence ao usuário informado";
			
			CompraItem li = compraFcd.novoItem();
			li.setCompra(compra);
			li.setTitulo(item);
			li.setQtd(qtd);
			li.setPreco(preco);
			li.setChecked(checked);
			compraFcd.addItem(compra, li);
			
			compraFcd.salvar(compra);
			
			return "ok";
		}
		
		return "Usuário inválido";
	}

}
