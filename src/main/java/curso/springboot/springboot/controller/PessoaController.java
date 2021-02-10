package curso.springboot.springboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.springboot.model.Pessoa;
import curso.springboot.springboot.repository.PessoaRepository;

@Controller
public class PessoaController {

	@Autowired
	PessoaRepository pessoaReporitory;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {
		
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaobj", new Pessoa()); /*retornar um objeto vazio*/
		Iterable<Pessoa> pessoasIt = pessoaReporitory.findAll(); /*puxar os objetos*/
		modelAndView.addObject("pessoas", pessoasIt); /*passando o atributo que está no html*/
		return modelAndView;

	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa") /* recebe pessoa do  html(action:salvarpessoa)
																			 */
	public ModelAndView salvar(Pessoa pessoa) {
		pessoaReporitory.save(pessoa);
		
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");  /*"Model" para mandar do model para a view*/
		Iterable<Pessoa> pessoasIt = pessoaReporitory.findAll(); /*puxar os objetos*/
		andView.addObject("pessoas", pessoasIt); /*passando o atributo que está no html*/
		andView.addObject("pessoaobj", new Pessoa()); /*passando objeto vazio*/
		return andView;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
	public ModelAndView pessoas() {

		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");  /*"Model" para mandar do model para a view*/
		Iterable<Pessoa> pessoasIt = pessoaReporitory.findAll();
		andView.addObject("pessoas", pessoasIt); /*passando o atributo que está no html*/
		andView.addObject("pessoaobj", new Pessoa()); /*passando objeto vazio*/
		return andView;

	}
	
	
	@GetMapping("/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) { /*interceptar a URL passando o idpessoa*/
		Optional<Pessoa> pessoa = pessoaReporitory.findById(idpessoa);/*Carregar objeto do banco de dados*/
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");/*voltar para mesma tela*/
		
		modelAndView.addObject("pessoaobj", pessoa.get()); /*passar o objeto para tela(voltar para edição), passando no object*/
		return modelAndView;
	}
	
	@GetMapping("/excluirpessoa/{idpessoa}")
	public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) { /*interceptar a URL passando o idpessoa*/
		
		pessoaReporitory.deleteById(idpessoa); /*Deletar por id(lembrando do tipo)*/
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");/*voltar para mesma tela*/
		
		modelAndView.addObject("pessoas", pessoaReporitory.findAll()); /*carregar todas as pessoas*/
		modelAndView.addObject("pessoaobj", new Pessoa()); /*tem que passar o objeto vazio*/
		return modelAndView;
	}
	
	@PostMapping("**/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) { /*interceptar oq o usúario escrever*/
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoas", pessoaReporitory.findPessoaByName(nomepesquisa)); /*query criado chamando o nomepesquisa*/
		modelAndView.addObject("pessoaobj", new Pessoa()); /*tem que passar o objeto vazio*/
		return modelAndView;
}
	
	@GetMapping("/telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) { /*interceptar a URL passando o idpessoa*/
		Optional<Pessoa> pessoa = pessoaReporitory.findById(idpessoa);/*Carregar objeto do banco de dados*/
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");/*voltar para mesma tela*/
		
		modelAndView.addObject("pessoaobj", pessoa.get()); /*passar o objeto para tela(voltar para edição), passando no object*/
		return modelAndView;
		
	}

}
