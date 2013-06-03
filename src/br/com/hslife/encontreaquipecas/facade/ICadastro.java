package br.com.hslife.encontreaquipecas.facade;

import br.com.hslife.encontreaquipecas.entity.Consumidor;
import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.exception.BusinessException;


public interface ICadastro {
	
	public void atualizarCadastro(Loja loja) throws BusinessException;
	
	public void atualizarCadastro(Consumidor consumidor) throws BusinessException;
	
	public Loja buscarLojaPorLogin(String loginUsuario) throws BusinessException;
	
	public Consumidor buscarConsumidorPorLogin(String loginUsuario) throws BusinessException;
}
