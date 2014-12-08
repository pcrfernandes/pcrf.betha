package pcrf.betha.rest.lista;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import pcrf.betha.admin.entity.usuario.Usuario;
import pcrf.betha.admin.fcd.usuario.UsuarioFcd;
import pcrf.betha.compras.entity.lista.Lista;
import pcrf.betha.compras.entity.lista.ListaItem;
import pcrf.betha.compras.fcd.lista.ListaFcd;

@Path("/compras/lista")
@Stateless
public class ListaResources extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	private ListaFcd listaFcd; 
	
	@Inject
	private UsuarioFcd usuarioFcd; 
	
	@GET
	@Path("/all")
	@Produces(MediaType.TEXT_XML)
	public List<Lista> getXML() {
		return listaFcd.obterTodosCompletos();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_XML)
	public Lista getLista(@PathParam("id") int id) {
		return listaFcd.obterCompleto(id);
	}

	@GET
	@Path("/list")
	@Produces(MediaType.TEXT_PLAIN)
	public String getList() {
		return listaFcd.obterTodosCompletos().toString();
	}
	
	@GET
	@Path("/label/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLabel(@PathParam("id") int id) {
		Lista lista = listaFcd.obter(id);
		
		if (lista == null)
			return "(Lista não encontrada)";
		
		return lista.getLabel();
	}
	
	@POST
	@Path("/add")
	@Produces(MediaType.TEXT_PLAIN)
	public Response addLista(@FormParam("titulo") String titulo, @FormParam("login") String login, @FormParam("senha") String senha) {
		Usuario usuario = usuarioFcd.obterUsuarioLogin(login);
		
		if (usuario != null && usuarioFcd.verificarSenha(usuario, senha)) {
			Lista lista = listaFcd.novo();
			lista.setTitulo(titulo);
			lista.setUsuario(usuario);

			Lista managed = listaFcd.salvar(lista);
			
			/* Retorna o código da nova lista */
			ResponseBuilder response = Response.ok(managed.getIdlista().toString());
			return response.build();
		}
		
		ResponseBuilder response = Response.ok("Usuário inválido");
		return response.build();
	}

	@POST
	@Path("/additem")
	@Produces(MediaType.TEXT_PLAIN)
	public String addItem(@FormParam("idlista") Integer idlista, @FormParam("item") String item, @FormParam("preco") Double preco, @FormParam("login") String login, @FormParam("senha") String senha) {
		Usuario usuario = usuarioFcd.obterUsuarioLogin(login);
		
		if (usuario != null && usuarioFcd.verificarSenha(usuario, senha)) {
			Lista lista = listaFcd.obterCompleto(idlista);
			
			if (lista == null)
				return "Lista não encontrada";
			
			if (!lista.getUsuario().equals(usuario))
				return "A lista não pertence ao usuário informado";
			
			ListaItem li = listaFcd.novoItem();
			li.setLista(lista);
			li.setTitulo(item);
			li.setPreco(preco);
			listaFcd.addItem(lista, li);
			
			listaFcd.salvar(lista);
			
			return "ok";
		}
		
		return "Usuário inválido";
	}

	@POST
	@Path("/rem")
	@Produces(MediaType.TEXT_PLAIN)
	public String remLista(@FormParam("idlista") Integer idlista, @FormParam("login") String login, @FormParam("senha") String senha) {
		Usuario usuario = usuarioFcd.obterUsuarioLogin(login);
		
		if (usuario != null && usuarioFcd.verificarSenha(usuario, senha)) {
			Lista lista = listaFcd.obter(idlista);
			
			if (lista == null)
				return "Lista não encontrada";
			
			if (!lista.getUsuario().equals(usuario))
				return "A lista não pertence ao usuário informado";

			try {
				listaFcd.inativar(idlista);
				return "ok";
			} catch(Exception e) {
				return e.getMessage();
			}
		}
		
		return "Usuário inválido";
	}

}
