package br.com.hslife.orcamento.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hslife.orcamento.component.EmailComponent;
import br.com.hslife.orcamento.component.UsuarioComponent;
import br.com.hslife.orcamento.entity.Usuario;
import br.com.hslife.orcamento.enumeration.TipoUsuario;
import br.com.hslife.orcamento.exception.BusinessException;
import br.com.hslife.orcamento.facade.IUsuario;
import br.com.hslife.orcamento.repository.UsuarioRepository;
import br.com.hslife.orcamento.util.Util;

@Service("usuarioService")
public class UsuarioService implements IUsuario {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioComponent component;
	
	@Autowired
	private EmailComponent emailComponent;

	public UsuarioRepository getRepository() {
		return repository;
	}

	public void setRepository(UsuarioRepository repository) {
		this.repository = repository;
	}

	public UsuarioComponent getComponent() {
		return component;
	}

	public void setComponent(UsuarioComponent component) {
		this.component = component;
	}
	
	public void setEmailComponent(EmailComponent emailComponent) {
		this.emailComponent = emailComponent;
	}

	@Override
	public void cadastrar(Usuario entity, String novaSenha, String confirmaSenha) throws BusinessException {
		if (novaSenha != null && confirmaSenha != null && !novaSenha.trim().isEmpty() && !confirmaSenha.trim().isEmpty() && novaSenha.equals(confirmaSenha)) {
			Usuario u = getRepository().findByLogin(entity.getLogin());
			if (u != null) {
				throw new BusinessException("Login já cadastrado para outro usuário!");
			} else {
				entity.setSenha(Util.SHA1(confirmaSenha));
				getRepository().save(entity);
			}
		} else {
			if (!novaSenha.equals(confirmaSenha)) {
				throw new BusinessException("As senhas não coincidem!");
			}
			throw new BusinessException("Informe a senha do usuário!");
		}
	}
	
	@Override
	public void alterar(Usuario entity, String novaSenha, String confirmaSenha) throws BusinessException {
		if (novaSenha != null && confirmaSenha != null && !novaSenha.trim().isEmpty() && !confirmaSenha.trim().isEmpty() && novaSenha.equals(confirmaSenha)) {
			Usuario u = getRepository().findById(entity.getId());
			if (u.getSenha().equals(Util.SHA1(confirmaSenha))) {
				throw new BusinessException("Nova senha não pode ser igual a senha atual!");
			} else 
				entity.setSenha(Util.SHA1(confirmaSenha));			
		} else {
			if (!novaSenha.equals(confirmaSenha)) {
				throw new BusinessException("As senhas não coincidem!");
			}			
		}
		getRepository().update(entity);
	}
	
	@Override
	public void efetuarRegistro(Usuario entity) throws BusinessException {
		// Gera hash da data atual para extrair a senha
		String hash = Util.MD5((new Date().toString()));
		
		// Extrai do hash a senha aleatória do usuário
		String senha = hash.substring(0, 8);
		
		// invoca o método de cadastrar usuário
		this.cadastrar(entity, senha, senha);
		
		// Efetua o envio do e-mail com a senha
		StringBuilder mensagemEmail = new StringBuilder();
		
		mensagemEmail.append("Prezado " + entity.getNome() + "\n\n");
		mensagemEmail.append("Segue abaixo a senha gerada:\n\n");
		mensagemEmail.append("Senha: " + senha + "\n\n");
		mensagemEmail.append("Caso tenha dificuldade de acesso entre em contato com o administrador para alterar sua senha.\n\n\n");
		mensagemEmail.append("HSlife Serviços de TI");
		
		try {
			emailComponent.enviaEmailSimples("HSlife Serviços de TI", "contato@hslife.com.br", entity.getNome(), entity.getEmail(), "Encontre Aqui Peças - Registro de Usuário", mensagemEmail);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public void recuperarSenha(Usuario entity) throws BusinessException {
		Usuario u = getRepository().findByLogin(entity.getLogin());
		
		if (u == null || u.getLogin().equals("admin")) {
			throw new BusinessException("Login não encontrado!");
		} else if (u.getEmail().equals(entity.getEmail())) {
			// Gera hash da data atual para extrair a senha
			String hash = Util.MD5((new Date().toString()));
			
			// Extrai do hash a senha aleatória do usuário
			String senha = hash.substring(0, 8);
			
			// invoca o método de alterar usuário
			this.alterar(u, senha, senha);
			
			// Efetua o envio do e-mail com a senha
			StringBuilder mensagemEmail = new StringBuilder();
			
			mensagemEmail.append("Prezado " + u.getNome() + "\n\n");
			mensagemEmail.append("Segue abaixo a senha gerada:\n\n");
			mensagemEmail.append("Senha: " + senha + "\n\n");
			mensagemEmail.append("Caso tenha dificuldade de acesso entre em contato com o administrador para alterar sua senha.\n\n\n");
			mensagemEmail.append("HSlife Serviços de TI");
			
			try {
				emailComponent.enviaEmailSimples("HSlife Serviços de TI", "contato@hslife.com.br", u.getNome(), u.getEmail(), "Encontre Aqui Peças - Recuperação de senha", mensagemEmail);
			} catch (Exception e) {
				throw new BusinessException(e);
			}
		} else {
			throw new BusinessException("E-Mail informado não confere com o cadastrado!");
		}		
	}
	
	@Override
	public List<Usuario> getListaUsuarios() throws BusinessException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		if (getComponent().getUsuarioLogado().getTipoUsuario().equals(TipoUsuario.ROLE_ADMIN)) {
			for (Usuario u : getRepository().findAll()) {
				usuarios.add(u);
			}
		} else {
			usuarios.add(getComponent().getUsuarioLogado());
		}
		return usuarios;
	}
	
	@Override
	public Usuario buscarPorLogin(String login) throws BusinessException {
		return getRepository().findByLogin(login);
	}
	
	@Override
	public List<Usuario> buscarTodosPorLogin(String login) throws BusinessException {
		return getRepository().findAllByLogin(login);
	}
	
	@Override
	public Usuario buscarPorId(Long id) throws BusinessException {
		return getRepository().findById(id);
	}
}