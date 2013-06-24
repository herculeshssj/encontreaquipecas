package br.com.hslife.encontreaquipecas.repository;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.hslife.encontreaquipecas.entity.Produto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration
@Transactional
public class ProdutoRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public void setProdutoRepository(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}

	@Test
	public void testFindByNome() {		
		String nomeProduto = "Produto 1";
		List<Produto> produtos = produtoRepository.findByNome(nomeProduto);
		for (Produto p : produtos) {
			if (p.getNome().equals(nomeProduto)) {
				return;
			}
		}
		fail("Produto não encontrado!");
	}

}
