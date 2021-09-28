package curso.springboot.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



@Entity
public class Telefone {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@NotEmpty(message = "Número não pode ser vazio")
	@NotNull(message = "Numero não pode ser nulo")
	private String numero;
	
	
	@NotEmpty(message = "Número não pode ser vazio")
	@NotNull(message = "Numero não pode ser nulo")
	private String tipo;
	
	
	@ManyToOne /*mapeandso N para 1*/
	@JoinColumn(name = "pessoa_id") /*criando chave estrangeira*/
	private Pessoa pessoa;
	
	
	
	public Telefone(String numero) {
		this.numero = numero;
	}



	public Telefone(){
		
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public Pessoa getPessoa() {
		return pessoa;
	}


	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}








	




	@Override
	public String toString() {
		return "Telefone [numero=" + numero + ", tipo=" + tipo + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefone other = (Telefone) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	


	
}
