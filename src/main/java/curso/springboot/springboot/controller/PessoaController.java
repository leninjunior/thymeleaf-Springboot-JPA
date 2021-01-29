package curso.springboot.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.springboot.model.Pessoa;
import curso.springboot.springboot.repository.PessoaRepository;

@Controller
public class PessoaController {

	@Autowired
	PessoaRepository pessoaReporitory;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public String inicio() {

		return "cadastro/cadastropessoa";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/salvarpessoa") /* recebe pessoa do  html(action:salvarpessoa)
																			 */
	public String salvar(Pessoa pessoa) {
		pessoaReporitory.save(pessoa);

		return "cadastro/cadastropessoa";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
	public ModelAndView pessoas() {

		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");  /*"Model" para mandar do model para a view*/
		Iterable<Pessoa> pessoasIt = pessoaReporitory.findAll();
		andView.addObject("pessoas", pessoasIt); /*passando o atributo que está no html*/
		return andView;

	}

}
