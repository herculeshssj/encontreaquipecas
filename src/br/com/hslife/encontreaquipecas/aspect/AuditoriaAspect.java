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

package br.com.hslife.encontreaquipecas.aspect;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.hslife.encontreaquipecas.entity.Auditoria;
import br.com.hslife.encontreaquipecas.entity.AuditoriaDados;
import br.com.hslife.encontreaquipecas.entity.EntityPersistence;
import br.com.hslife.encontreaquipecas.entity.Usuario;

@Aspect
@Component
public class AuditoriaAspect {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@AfterReturning(pointcut="execution(public void br.com.hslife.encontreaquipecas.repository.IRepository.save(..)) && args(entity)")
	public void afterSave(EntityPersistence entity) {
		System.out.println("Salvamento detectado. Executando auditoria!");
		System.out.println("Classe detectada: " + entity.getClass().getName());
		
		Auditoria auditoria = new Auditoria();
		
		auditoria.setClasse(entity.getClass().getSimpleName());
		auditoria.setTransacao("INSERT");
		//auditoria.setUsuario(((Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado")).getLogin());
		
		/*
		 * Mudança realizada em virtude do caso de uso de registro de usuário. Ainda é necessário pensar em uma forma de registrar
		 * quem está efetuando o registro para gravar na auditoria. Por enquanto será gravado o mesmo IP do computador que efetuou
		 * o registro
		 */
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado") == null) {
			auditoria.setUsuario(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr());
		} else {
			auditoria.setUsuario(((Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado")).getLogin());
		}
		
		auditoria.setIp(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr());
		
		for (String s : entity.getFieldValues().keySet()) {
			auditoria.getDadosAuditoria().add(new AuditoriaDados(entity.getFieldValues().get(s), s, "AFTER"));
		}
		
		sessionFactory.getCurrentSession().persist(auditoria);
		
		System.out.println("Auditoria realizada!");
	}
	
	@Around("execution(public void br.com.hslife.encontreaquipecas.repository.IRepository.update(..)) && args(entity)")
	public void afterUpdate(ProceedingJoinPoint executaMetodo, EntityPersistence entity) throws Throwable {
		System.out.println("Atualiza��o detectada. Auditoria executada!");
		System.out.println("Classe detectada: " + entity.getClass().getName());
		
		Auditoria auditoria  = new Auditoria();
		
		auditoria.setClasse(entity.getClass().getSimpleName());
		auditoria.setTransacao("UPDATE");
		//auditoria.setUsuario(((Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado")).getLogin());
		
		/*
		 * Mudança realizada em virtude do caso de uso de registro de usuário. Ainda é necessário pensar em uma forma de registrar
		 * quem está efetuando o registro para gravar na auditoria. Por enquanto será gravado o mesmo IP do computador que efetuou
		 * o registro
		 */
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado") == null) {
			auditoria.setUsuario(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr());
		} else {
			auditoria.setUsuario(((Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado")).getLogin());
		}
		
		auditoria.setIp(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr());
		
		EntityPersistence entityBefore = (EntityPersistence) sessionFactory.getCurrentSession().get(entity.getClass(), entity.getId());
		
		for (String s : entity.getFieldValues().keySet()) {
			auditoria.getDadosAuditoria().add(new AuditoriaDados(entityBefore.getFieldValues().get(s), s, "BEFORE"));
		}
		
		executaMetodo.proceed();
		
		for (String s : entity.getFieldValues().keySet()) {
			auditoria.getDadosAuditoria().add(new AuditoriaDados(entity.getFieldValues().get(s), s, "AFTER"));
		}
		
		sessionFactory.getCurrentSession().persist(auditoria);
		
		System.out.println("Auditoria realizada!");
	}
	
	@AfterReturning(pointcut="execution(public void br.com.hslife.encontreaquipecas.repository.IRepository.delete(..)) && args(entity)")
	public void afterDelete(EntityPersistence entity) {
		System.out.println("Exclus�o detectada. Executando auditoria!");
		System.out.println("Classe detectada: " + entity.getClass().getName());
		
		Auditoria auditoria = new Auditoria();
		
		auditoria.setClasse(entity.getClass().getSimpleName());
		auditoria.setTransacao("DELETE");
		auditoria.setUsuario(((Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado")).getLogin());
		auditoria.setIp(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr());
		
		for (String s : entity.getFieldValues().keySet()) {
			auditoria.getDadosAuditoria().add(new AuditoriaDados(entity.getFieldValues().get(s), s, "BEFORE"));
		}
		
		sessionFactory.getCurrentSession().persist(auditoria);
		
		System.out.println("Auditoria realizada!");
	}
}