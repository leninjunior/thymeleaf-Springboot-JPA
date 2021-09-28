package curso.springboot.springboot.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String descricao;
	
	
	@OneToMany(mappedBy = "curso",orphanRemoval = true,  cascade = CascadeType.ALL)
	private List<Pessoa> pessoa;
	
	
	
	

	public List<Pessoa> getPessoa() {
		return pessoa;
	}

	public void setPessoa(List<Pessoa> pessoa) {
		this.pessoa = pessoa;
	}

	public Curso(Long id, String name, String descricao) {
		this.id = id;
		this.name = name;
		this.descricao = descricao;
	}

	public Curso() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", name=" + name + ", descricao=" + descricao + ", pessoa=" + pessoa + "]";
	}
	

}
