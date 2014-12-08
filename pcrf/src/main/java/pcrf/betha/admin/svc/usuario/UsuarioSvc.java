package pcrf.betha.admin.svc.usuario;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import pcrf.betha.admin.dao.usuario.UsuarioDao;
import pcrf.betha.admin.entity.usuario.Usuario;
import pcrf.betha.base.dao.Dao;
import pcrf.betha.base.svc.Svc;
import pcrf.betha.util.CryptUtils;
import pcrf.betha.util.GeneralException;

@Stateless
public class UsuarioSvc extends Svc<Usuario> {
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDao dao;
	
	@Override
	public Dao<Usuario> getDao() {
		return dao;
	}
	
	@Override
	public Usuario novo() {
		Usuario usuario = super.novo();
		
		usuario.setAtivo(true);
		
		return usuario;
	}

	@Override
	public Usuario salvar(Usuario entity) {
		entity.setLogin(entity.getLogin().toLowerCase().trim());
		entity.setSenha(this.gerarSenha(entity.getSenha()));
		
		return super.salvar(entity);
	}

	private String gerarSenha(String senha) {
		if (senha == null)
			return null;
		
		return CryptUtils.toMD5(senha);
	}
	
	public Boolean verificarSenha(Usuario usuario, String senha) {
		return senha != null && usuario.getSenha().equals(this.gerarSenha(senha));
	}
	
	public void alterarSenha(Integer id, String senhaAtual, String novaSenha) {
		Usuario usuario = this.obter(id);
		
		if (usuario == null)
			throw new GeneralException("Usuário não localizado");

		if (!this.verificarSenha(usuario, senhaAtual))
			throw new GeneralException("Senha atual não confere");
		
		usuario.setSenha(this.gerarSenha(novaSenha));
		
		super.salvar(usuario);
	}
	
	public Usuario obterUsuarioLogin(String login) {
		login = login.toLowerCase();
		
		TypedQuery<Usuario> query = this.getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.login = ?1", Usuario.class);
		query.setParameter(1, login);
		
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void inativar(Integer id) {
		Usuario usuario = this.obter(id);
		
		if (usuario == null)
			throw new GeneralException("Usuário não localizado");
		
		if (!usuario.getAtivo())
			throw new GeneralException("Usuário já está inativo");
		
		usuario.setAtivo(false);
		
		super.salvar(usuario);
	}
	
	public void reativar(Integer id) {
		Usuario usuario = this.obter(id);
		
		if (usuario == null)
			throw new GeneralException("Usuário não localizado");
		
		if (usuario.getAtivo())
			throw new GeneralException("Usuário já está ativo");
		
		usuario.setAtivo(true);
		
		super.salvar(usuario);
	}

	public List<Usuario> obterAtivos() {
		TypedQuery<Usuario> query = this.getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.ativo = 1", Usuario.class);
		return query.getResultList();
	}

}
