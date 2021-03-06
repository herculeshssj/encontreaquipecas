/***
  
  	Copyright (c) 2013 Hércules S. S. José

    Este arquivo é parte do programa EncontreAquiPeças.
    

    EncontreAquiPeças é um software livre; você pode redistribui-lo e/ou 

    modificá-lo dentro dos termos da Licença Pública Geral Menor GNU como 

    publicada pela Fundação do Software Livre (FSF); na versão 2.1 da 

    Licença.
    

    Este programa é distribuído na esperança que possa ser útil, 

    mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÂO a 
    
    qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública 
    
    Geral Menor GNU em português para maiores detalhes.
    

    Você deve ter recebido uma cópia da Licença Pública Geral Menor GNU sob o 

    nome de "LICENSE.TXT" junto com este programa, se não, acesse o site HSlife
    
    no endereco www.hslife.com.br ou escreva para a Fundação do Software 
    
    Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301, USA.
    

    Para mais informações sobre o programa EncontreAquiPeças e seu autor acesse o 

    endereço www.hslife.com.br, pelo e-mail contato@hslife.com.br ou escreva para 

    Hércules S. S. José, Av. Ministro Lafaeyte de Andrade, 1683 - Bl. 3 Apt 404, 

    Marco II - Nova Iguaçu, RJ, Brasil.
  
*/

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
