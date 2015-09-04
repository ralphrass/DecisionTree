package entidade;

import qualificador.Atributo;

public class Animal extends Entidade {
	
	public Integer id;
	public String nome;
	@Atributo(testavel = true)
	public Boolean sangueQuente;
	@Atributo(testavel = true)
	public Boolean daLuz;
	@Atributo(testavel = true)
	public Boolean temAsas;
	public String classe;

	public Animal(String[] instancia){

		this(Integer.parseInt(instancia[0]), (String)instancia[1], Boolean.parseBoolean(instancia[2]),
				Boolean.parseBoolean(instancia[3]), Boolean.parseBoolean(instancia[4]), (String)instancia[5]);
	}
	
	public Animal(int i, String n, boolean s, boolean d, boolean t, String c){
		
		id = i;
		nome = n;
		sangueQuente = s;
		daLuz = d;
		temAsas = t;
		classe = c;
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
		Animal other = (Animal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Animal [id=" + id + ", classe= "+classe+"]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean isSangueQuente() {
		return sangueQuente;
	}

	public void setSangueQuente(boolean sangueQuente) {
		this.sangueQuente = sangueQuente;
	}

	public Boolean isDaLuz() {
		return daLuz;
	}

	public void setDaLuz(boolean daLuz) {
		this.daLuz = daLuz;
	}

	public Boolean isTemAsas() {
		return temAsas;
	}

	public void setTemAsas(boolean temAsas) {
		this.temAsas = temAsas;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}
}
