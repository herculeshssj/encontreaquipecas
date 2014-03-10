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

package br.com.hslife.encontreaquipecas.component;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Component;

/**
 * 
 * Classe EmailComponent
 * 
 * Classe responsável por enviar mensagem de acordo com o método escolhido
 * O código foi obtido em http://www.guj.com.br/posts/list/106280.java e 
 * adaptado para este projeto.
 *
 * @author Hércules
 * @version 1.0
 */
@Component
public class EmailComponent {

	/**
     * envia email simples (smente texto)
     * Nome remetente, e-mail remetente, nome destinatario, e-mail destinatario,
     * assunto, mensagem
     * @param nomeRemetente
     * @param nomeDestinatario
     * @param emailRemetente
     * @param emailDestinatario
     * @param assunto
     * @param mensagem
     * @throws EmailException
     */
    public void enviaEmailSimples(String nomeRementente, String emailRemetente,
            String nomeDestinatario, String emailDestinatario,
            String assunto, StringBuilder mensagem) throws EmailException {

        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.hslife.com.br"); // o servidor SMTP para envio do e-mail
        email.addTo(emailDestinatario, nomeDestinatario); //destinatário
        email.setFrom(emailRemetente, nomeRementente); // remetente
        email.setSubject(assunto); // assunto do e-mail
        email.setMsg(mensagem.toString()); //conteudo do e-mail
        email.setAuthentication("contato@hslife.com.br", "hssj1985");
        email.setCharset("UTF-8");
        email.setSmtpPort(465);
        email.setSSL(true);
        email.setTLS(true);        
        email.send();
    }	
}