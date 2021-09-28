package curso.springboot.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.springboot.model.Pessoa;
import curso.springboot.springboot.model.Telefone;
import curso.springboot.springboot.repository.PessoaRepository;
import curso.springboot.springboot.repository.TelefoneRepository;

@Controller
public class PessoaController {

	@Autowired
	PessoaRepository pessoaReporitory;
	
	@Autowired
	TelefoneRepository telefoneRepository;

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
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult ) { /*Validação padrão, pode ser decorada*/
		if(bindingResult.hasErrors()) {
			
			ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");  /*"Model" para mandar o usuario permanecer na vi para a view*/
			Iterable<Pessoa> pessoasIt = pessoaReporitory.findAll(); /*puxar os objetos*/
			andView.addObject("pessoas", pessoasIt); /*passando o atributo que está no html*/
			andView.addObject("pessoaobj", pessoa); //carregando o objeto pai
			
			List<String> msg = new ArrayList<String>(); //for nos erros, se tiver 2 erros, terá 2 erros mostrando na view
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); //vem das anotações @notempty
				
				
			}
			andView.addObject("msg", msg);
			return andView;
		}
		
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
	
	@PostMapping("/addfonePessoa/{pessoaid}")/*action: addFonePessoa/ vai vir o pessoaid(mesmo da view ({/addfonePessoa/{pessoaid}))*/
	public ModelAndView addFonePessoa( Telefone telefone, @PathVariable("pessoaid") Long pessoaid) { /*recebe um objeto telefone(vai ser injetado automatiamente na classe telefone)*/ /*o pathvariable vai pegar essa info*/
		
	
Pessoa pessoa = pessoaReporitory.findById(pessoaid).get();
		
		if(telefone != null && telefone.getNumero().isEmpty() 
				|| telefone.getTipo().isEmpty()) {
			
			ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
			modelAndView.addObject("pessoaobj", pessoa);
			modelAndView.addObject("telefones", telefoneRepository.getTelefone(pessoaid));
			
			List<String> msg = new ArrayList<String>();
			if (telefone.getNumero().isEmpty()) {
				msg.add("Numero deve ser informado");
			}
			
			if (telefone.getTipo().isEmpty()) {
				msg.add("Tipo deve ser informado");
			}
			modelAndView.addObject("msg", msg);
			
			return modelAndView;
			
		}
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");

		telefone.setPessoa(pessoa);
		
		telefoneRepository.save(telefone);
		
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefone(pessoaid));
		return modelAndView;
	}
	
	
	@GetMapping("/removertelefone/{idtelefone}")
	public ModelAndView removerTelefone(@PathVariable("idtelefone") Long idtelefone) {
		
		Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa(); /*carregou objeto telefone e detro de telefone tem o objeto pessoa(get.Pessoa)*/
		
		telefoneRepository.deleteById(idtelefone);/*deletou o objeto telefone*/
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");/*permanecer na pagina telefones*/
		modelAndView.addObject("pessoaobj", pessoa); /*carregar o objeto pai(no caso pessoa) / para mostrar na tela*/
		modelAndView.addObject("telefones",telefoneRepository.getTelefone(pessoa.getId())); /*carregar o objeto telefone menos oq foi removido*/
		return modelAndView;
		
		
		
	}
	
	
	
	

}
