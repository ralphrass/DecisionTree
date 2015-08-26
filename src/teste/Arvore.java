package teste;

import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

import entidade.Animal;
import entidade.Nodo;

public class Arvore {

	static List<Object> dataSet = new ArrayList<Object>();
	static List<Object> valoresPossiveisDoAtributo = new ArrayList<Object>();
	static List<Object> populacao = new ArrayList<Object>();
	
	static List<Field> atributosDisponiveis = new ArrayList<Field>();

	static int indiceArvore = 0;
				
	static Field ATRIBUTO_DE_CLASSE;
	
	public static void main(String args[]) throws NoSuchFieldException, SecurityException{
				
		ATRIBUTO_DE_CLASSE = Animal.class.getField("classe");
						
		preencherDataSet();
		preencherAtributosDisponíveis();

		Nodo raiz = crescerArvore(populacao);

		System.out.print(raiz);
	}
	
	public static void preencherAtributosDisponíveis(){
		
		Field[] atributos = Animal.class.getFields();
		
		//apenas booleanos
		for (int i=0; i<atributos.length; i++){
			
			String tipo = atributos[i].getType().getTypeName();
			String tipoAceitavel = Boolean.class.getTypeName();
			
			if (tipo.equals(tipoAceitavel)){
				
				atributosDisponiveis.add(atributos[i]);
			}
		}

		atributosDisponiveis.add(ATRIBUTO_DE_CLASSE);
	}
	
	public static void preencherDataSet(){
		
		Animal a0 = new Animal(1, "Flamingo", true, false, true, "Nao-Mamifero");
		Animal a1 = new Animal(2, "Gaivota", true, false, true, "Nao-Mamifero");
		Animal a2 = new Animal(3, "Elefante", true, true, false, "Mamifero");
		Animal a3 = new Animal(4, "Macaco", true, true, false, "Mamifero");
		Animal a4 = new Animal(5, "Jacare", false, false, false, "Nao-Mamifero");
		Animal a5 = new Animal(6, "Humano", true, true, false, "Mamifero");
		
		dataSet.add(a0);
		dataSet.add(a1);
		dataSet.add(a2);
		dataSet.add(a3);
		dataSet.add(a4);
		dataSet.add(a5);

		Animal a6 = new Animal(7, "Gafanhoto", false, false, true, "Nao-Mamifero");
		Animal a7 = new Animal(8, "Arara", true, false, true, "Nao-Mamifero");
		Animal a8 = new Animal(9, "Ornitorrinco", true, false, false, "Mamifero");
		Animal a9 = new Animal(10, "Tubarao", false, false, false, "Nao-Mamifero");
		Animal a10 = new Animal(11, "Baleia", false, true, false, "Mamifero");
		Animal a11 = new Animal(12, "Gnu", true, true, false, "Mamifero");

		dataSet.add(a6);
		dataSet.add(a7);
		dataSet.add(a8);
		dataSet.add(a9);
		dataSet.add(a10);
		dataSet.add(a11);
		
		populacao = dataSet;
	}
		
	public static Nodo crescerArvore(List<Object> populacao){
		
		Nodo raiz = null;
		
		if (deveParar(populacao) == true){
		
			Nodo folha = new Nodo(indiceArvore++);
			folha.setPopulacao(populacao);
			folha.setLabel(classificar(populacao));
			
			return folha;
			
		} else {
									
			Field melhorAtributo = encontrarMelhorDivisao(populacao);
			valoresPossiveisDoAtributo = obterValoresPossiveisDoAtributo(melhorAtributo, populacao);

			raiz = new Nodo(indiceArvore++);
			raiz.setLabel(melhorAtributo.getName());
			
			for (Object valor : valoresPossiveisDoAtributo) {

				List<Object> subPopulacao = obterSubPopulacaoParaOValor(populacao, melhorAtributo, valor);
				populacao.removeAll(subPopulacao);
				Nodo nodoFilho = crescerArvore(subPopulacao);

				//Ajusta a label da ARESTA, concatenando com a descrição formal do melhor atributo
				raiz.getNodosFilhos().put(melhorAtributo.getName() + " = "+valor, nodoFilho);
			}
		}
		
		return raiz;
	}
	
	public static String classificar(List<Object> populacao){
		
		//Verifica qual classe mais se repete na população recebida e a retorna
		Map<Object, Integer> contadorValores = new HashMap<Object, Integer>();
		
		for (Object item : populacao) {
			
			Object valor = obterValorDoAtributo(item, ATRIBUTO_DE_CLASSE);
			
			if (contadorValores.containsKey(valor)){
			
				Integer valorAtual = contadorValores.get(valor);
				contadorValores.replace(valor, valorAtual++);
				
			} else {
			
				contadorValores.put(valor, 1);
			}
		}
		
		Object maisSeRepete = null;
		Integer contador=0;
		
		for (Entry<Object, Integer> entry : contadorValores.entrySet()) {
			
			if (entry.getValue() > contador) {
				
				contador = entry.getValue();
				maisSeRepete = entry.getKey();
			}
		}
		
		return ATRIBUTO_DE_CLASSE.getName()+" = "+maisSeRepete.toString();
	}
	
	/**
	 * Mapeia cada valor possível de um atributo como chave que aponta para uma lista de registros que possuem aquele valor
	 * */
	public static Map<Object, List<Object>> obterSubPopulacoes(List<Object> populacaoP, List<Object> valoresPossiveisDoAtributo, Field atributo){
		
		Map<Object, List<Object>> subPopulacoes = new HashMap<Object, List<Object>>();
		
		for (Object valorDoAtributo : valoresPossiveisDoAtributo) {
			
			List<Object> subPopulacao = obterSubPopulacaoParaOValor(populacaoP, atributo, valorDoAtributo);
			
			subPopulacoes.put(valorDoAtributo, subPopulacao);
		}
		
		return subPopulacoes;
	}
	
	public static List<Object> obterSubPopulacaoParaOValor(List<Object> populacao, Field melhorAtributo, Object valorAresta){
		
		List<Object> subPopulacao = new ArrayList<Object>();
		
		for (Object item : populacao){
			
			try {
			
				//Busca o item de acordo com o índice na população 
				Animal animal = (Animal)item;				
								
				//Busca o valor do atributo selecionado para o objeto em questão
				Object valor = melhorAtributo.get(animal);
				
				//Se o valor do atributo do objeto corrente é o mesmo que o valor da aresta do nodo
				if (valor.equals(valorAresta)){
					
					subPopulacao.add(animal);
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
			
		return subPopulacao;
	}
		
	public static List<Object> obterValoresPossiveisDoAtributo(Field atributo, List<Object> populacao){
		
		List<Object> valoresPossiveis = new ArrayList<Object>();
		
		for (Object item : populacao){
		
			Animal animal = (Animal)item;
			Object valor = obterValorDoAtributo(animal, atributo);
			
			if (!valoresPossiveis.contains(valor)){
				
				valoresPossiveis.add(valor);
			}
		}
		
		return valoresPossiveis;
	}

	public static Field encontrarMelhorDivisao(List<Object> populacao){

		Field melhorAtributo = null;
		double melhorGini = 1.0;

		//Para cada atributo dos objetos da população, pega o valor deste atributo e divide os objetos de acordo com este valor
		for (Field atributoTeste : atributosDisponiveis) {

			Map<Object, Integer> contadorValores = new HashMap<Object, Integer>();

			for (Object item : populacao) {

				Animal animal = (Animal)item;
				Object valor = obterValorDoAtributo(animal, atributoTeste);

				if (contadorValores.containsKey(valor)){

					Integer valorAtual = contadorValores.get(valor);
					valorAtual = valorAtual + 1;
					contadorValores.replace(valor, valorAtual);

				} else {

					contadorValores.put(valor, 1);
				}
			}

			double giniAtual = obterIndiceGini(contadorValores, populacao.size());

			//Atualiza o índice Gini e o atrubuto comparativo
			if (melhorGini > giniAtual) {

				melhorGini = giniAtual;
				melhorAtributo = atributoTeste;
			}

		}//laço atributos

		atualizarAtributosDisponíveis(melhorAtributo);
				
		return melhorAtributo;
	}
	
	public static void atualizarAtributosDisponíveis(Field atributo){

		if (atributosDisponiveis.size() == 0){

			return;
		}

		int i = 0;
		
		while (true) {
			
			if (atributosDisponiveis.get(i).equals(atributo)){
				break;
			}
			
			i++;
		}
		
		atributosDisponiveis.remove(i);
	}
	
	public static double obterIndiceGini(Map<Object, Integer> contadorValores, double tamanhoTotal){

		double gini = 1.0;
		double resultado = 0.0;

		for (Entry<Object, Integer> entrada : contadorValores.entrySet()){
			Integer valor = entrada.getValue();
			resultado = resultado + Math.pow((valor / tamanhoTotal), 2);
		}

		return (gini - resultado);
	}
	
	public static Object obterValorDoAtributo(Object item, Field atributoTeste){
		
		try {
						
			return atributoTeste.get(item);
		
		} catch (Exception e) {

			System.out.println("Falha ao obter o valor do atributo " + atributoTeste.getName());
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Retorna "true" quando todos os valores dos atributos de classe da população forem iguais
	 * */
	public static boolean deveParar(List<Object> populacao){
			
		final int limiteMaximo = populacao.size();
				
		for (int i=0; i<limiteMaximo; i++){
			
			final Animal registroCorrente = (Animal)populacao.get(i);
			final Object valorCorrente = obterValorDoAtributoDeClasse(registroCorrente);
						
			for (int j=i+1; j<limiteMaximo; j++){
			
				final Animal registroAComparar = (Animal)populacao.get(j);
				final Object valorAComparar = obterValorDoAtributoDeClasse(registroAComparar);
				
				if (!valorCorrente.equals(valorAComparar)){
					
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static Object obterValorDoAtributoDeClasse(Object objeto){
		
		try {
		
			Object valor = ATRIBUTO_DE_CLASSE.get(objeto);
			return valor;
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
}