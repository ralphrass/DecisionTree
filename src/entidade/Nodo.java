package entidade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Nodo {

	private int indice;
	//Aresta, Nodo Filho
	private Map<String, Nodo> nodosFilhos = new HashMap<String, Nodo>();
	private String label;
	private Set<Entidade> populacao;

	public Nodo(){ }
	
	public Nodo(int indice){
		
		this.indice = indice;
	}
	
	@Override
	public String toString(){
		
		return new Integer(this.indice).toString();
	}

	public int getIndice() {
		return indice;
	}
	public void setIndice(int indice) {
		this.indice = indice;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) { this.label = label; }
	public Set<Entidade> getPopulacao() {
		return populacao;
	}
	public void setPopulacao(Set<Entidade> populacao) {
		this.populacao = populacao;
	}

	public Map<String, Nodo> getNodosFilhos() {
		return nodosFilhos;
	}

	public void setNodosFilhos(Map<String, Nodo> nodosFilhos) {
		this.nodosFilhos = nodosFilhos;
	}
}
