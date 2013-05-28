package br.com.hslife.encontreaquipecas.facade;

import java.util.List;

import br.com.hslife.encontreaquipecas.entity.Consumidor;
import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.entity.Usuario;
import br.com.hslife.encontreaquipecas.exception.BusinessException;


public interface IUsuario {
	
	public List<Usuario> getListaUsuarios() throws BusinessException;
	
	public Usuario buscarPorLogin(String login) throws BusinessException;
	
	public List<Usuario> buscarTodosPorLogin(String login) throws BusinessException;
	
	public Usuario buscarPorId(Long id) throws BusinessException;
	
	public void cadastrar(Usuario entity, String novaSenha, String confirmaSenha) throws BusinessException;
	
	public void alterar(Usuario entity, String novaSenha, String confirmaSenha) throws BusinessException;
	
	public void efetuarRegistro(Usuario entity) throws BusinessException;
	
	public void recuperarSenha(Usuario entity) throws BusinessException;
	
	public void efetuarRegistro(Loja loja) throws BusinessException;
	
	public void efetuarRegistro(Consumidor consumidor) throws BusinessException;
	 
}
