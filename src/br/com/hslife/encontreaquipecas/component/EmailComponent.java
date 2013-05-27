package br.com.hslife.encontreaquipecas.component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
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