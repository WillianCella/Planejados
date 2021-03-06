// Classe ConsultarPessoaController com uma lista de Pessoa
package br.cella.pessoa.controller;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.cella.model.PessoaModel;
import br.cella.repository.PessoaRepository;

/**
 * Essa Classe é utilizada para controlar as consultas de Pessoas
 * 
 * @author Willian Cella
 */

// Classe ConsultarPessoaController gerenciada pelo CDI com escopo do tipo
// @ViewScoped
@Named(value = "consultarPessoaController")
@ViewScoped
public class ConsultarPessoaController implements Serializable {
	private static final long serialVersionUID = 1L;

	// Injeção de PessoaModel
	@Inject
	transient private PessoaModel pessoaModel;

	// Lista de pessoa que vai ser utilizada posteriormente
	@Produces
	private List<PessoaModel> pessoas;

	// Injeção de PessoaRepository
	@Inject
	transient private PessoaRepository pessoaRepository;

	// Get e Set da lista de Pessoas
	public List<PessoaModel> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<PessoaModel> pessoas) {
		this.pessoas = pessoas;
	}

	// Get e Set de PessoaModel
	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	// Aqui as Pessoas vão ser carregadas na inicialização do sistema
	@PostConstruct
	public void init() {
		// Retorna todas as pessoas cadastradas que estão persistidas no bando
		// de dados
		this.pessoas = pessoaRepository.GetPessoas();
	}

	// Busca e carrega as informações de uma Pessoa para ser editada
	public void Editar(PessoaModel pessoaModel) {
		// Retorna a apenas a primeira letra do sexo para setar no campo sexo
		pessoaModel.setSexo(pessoaModel.getSexo().substring(0, 1));
		this.pessoaModel = pessoaModel;

	}

	// Atualiza os registros que tiveram alteração
	public void AlterarRegistro() {
		this.pessoaRepository.AlterarRegistro(this.pessoaModel);
		// Recarrega os registros do banco de dados
		this.init();
	}

	// Exclui um registro de Pessoa no modelo
	public void ExcluirPessoa(PessoaModel pessoaModel) {
		// Excluindo o registro
		this.pessoaRepository.ExcluirRegistro(pessoaModel.getCodigo());
		// Excluindo a pessoa da lista, e também, atualizando o banco de dados
		this.pessoas.remove(pessoaModel);

	}

}