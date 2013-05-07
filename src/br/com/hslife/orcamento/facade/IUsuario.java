package br.com.hslife.orcamento.facade;

import java.util.List;

import br.com.hslife.orcamento.entity.Usuario;
import br.com.hslife.orcamento.exception.BusinessException;


public interface IUsuario {
	
	public List<Usuario> getListaUsuarios() throws BusinessException;
	
	public Usuario buscarPorLogin(String login) throws BusinessException;
	
	public List<Usuario> buscarTodosPorLogin(String login) throws BusinessException;
	
	public Usuario buscarPorId(Long id) throws BusinessException;
	
	public void cadastrar(Usuario entity, String novaSenha, String confirmaSenha) throws BusinessException;
	
	public void alterar(Usuario entity, String novaSenha, String confirmaSenha) throws BusinessException;
	
	public void efetuarRegistro(Usuario entity) throws BusinessException;
	
	public void recuperarSenha(Usuario entity) throws BusinessException;
	
}
