package curso.springboot.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.springboot.model.Curso;
import curso.springboot.springboot.repository.CursoRepository;

@Controller
public class CursoController {

	@Autowired
	CursoRepository cursoRepository;

	@GetMapping("/cadastrarcurso")
	public String home() {
		return "cadastro/cadastrocurso";
	}

	@PostMapping("/cadastrarcurso")
	public String addCurso(Curso curso) {

		cursoRepository.save(curso);

		return "cadastro/cadastrocurso";
	}
	
	
	@GetMapping("/cadastrarcurso/listarcursos")
	public ModelAndView listarCurso() {
		
		ModelAndView view = new ModelAndView("cadastro/cadastrocurso");
		
		Iterable<Curso> cursosIt = cursoRepository.findAll();
		
		view.addObject("cursolist", cursosIt);
		
		return view;
	}

}
