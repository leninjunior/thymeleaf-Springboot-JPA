package curso.springboot.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.springboot.model.Curso;


@Repository
@Transactional
public interface CursoRepository  extends CrudRepository<Curso, Long>{
	
	

}
