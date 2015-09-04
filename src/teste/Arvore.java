package teste;

import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

import entidade.Animal;
import entidade.CancerMamaRecorrente;
import entidade.Entidade;
import entidade.Nodo;
import qualificador.Atributo;
import util.LerArquivoAnimais;
import util.LerArquivoCancerMama;

import javax.smartcardio.ATR;

public class Arvore<E extends Entidade> {

	static Set<Entidade> dataSet = new HashSet<>();
	//static Set<Entidade> totalDeInstancias = new HashSet<>();

	static Set<Object> valoresPossiveisDoAtributo;
	static Set<Object> valoresPossiveisDoAtributoDeClasse = new HashSet<>();
	
	static List<Field> atributosDisponiveis = new ArrayList<Field>();

	static int indiceArvore = 0;
				
	static Field ATRIBUTO_DE_CLASSE;

	final static String SEPARADOR_ARVORE = "----";
	
	public static void main(String args[]) throws NoSuchFieldException, SecurityException{

		Field[] atributos = definirInstancia();
		preencherAtributosDisponiveis(atributos);

		Nodo raiz = crescerArvore(dataSet);

		String nivelRaiz = "";

		mostrarArvore(raiz, nivelRaiz);
	}

	public static void mostrarArvore(Nodo raiz, String nivelNodo){

		for (Entry<String, Nodo> nodoFilho : raiz.getNodosFilhos().entrySet()) {

			if (nodoFilho.getValue().getNodosFilhos() != null
					&& !nodoFilho.getValue().getNodosFilhos().isEmpty()) {

				System.out.println(nivelNodo + nodoFilho.getKey());

				mostrarArvore(nodoFilho.getValue(), nivelNodo.concat(SEPARADOR_ARVORE));

			} else {

				String labelFilho = nodoFilho.getValue().getLabel();

				System.out.println(nivelNodo + nodoFilho.getKey());
				System.out.println(nivelNodo.concat(SEPARADOR_ARVORE) + labelFilho + " - DataSet: "+ nodoFilho.getValue().getPopulacao().size());
			}
		}
	}
	
	public static void preencherAtributosDisponiveis(Field[] atributos){

		//apenas atributos testaveis
		for (int i=0; i<atributos.length; i++){

			if (atributos[i].getAnnotation(Atributo.class) != null){

				atributosDisponiveis.add(atributos[i]);
			}
		}
	}
	
	public static Field[] definirInstancia(){

		Field[] atributos = null;

		//TODO Alterar para cada Entidade
		try {

			ATRIBUTO_DE_CLASSE = CancerMamaRecorrente.class.getField("classe");
			dataSet = new LerArquivoCancerMama().lerInstancias();
			atributos = CancerMamaRecorrente.class.getFields();
			/*ATRIBUTO_DE_CLASSE = Animal.class.getField("classe");
			dataSet = new LerArquivoAnimais().lerInstancias();
			atributos = Animal.class.getFields();*/

			//Obtém todos os valores possíveis do atributos de classe
			for (Entidade instancia : dataSet) {

				valoresPossiveisDoAtributoDeClasse.add(obterValorDoAtributo(instancia, ATRIBUTO_DE_CLASSE));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return atributos;
	}
		
	public static Nodo crescerArvore(Set<Entidade> listaDeInstancias){
		
		Nodo raiz = null;
		
		if (deveParar(listaDeInstancias) == true){
		
			Nodo folha = new Nodo(indiceArvore++);
			folha.setPopulacao(listaDeInstancias);
			folha.setLabel(classificar(listaDeInstancias));
			
			return folha;
			
		} else {

			Map<Field, Map<Object, Set<Entidade>>> mapaDeInstanciasDoAtributoVencedor = encontrarMelhorDivisao(listaDeInstancias);

			Field melhorAtributo = (Field)mapaDeInstanciasDoAtributoVencedor.keySet().toArray()[0];
			Map<Object, Set<Entidade>> mapaDeInstanciasPorValor = mapaDeInstanciasDoAtributoVencedor.get(melhorAtributo);

			raiz = new Nodo(indiceArvore++);
			raiz.setLabel(melhorAtributo.getName());

			//Para cada valor possivel do atributo
			for (Object valor : valoresPossiveisDoAtributo) {

				try {

					Set<Entidade> subListaDeInstancias = mapaDeInstanciasPorValor.get(valor);
					listaDeInstancias.removeAll(subListaDeInstancias);
					Nodo nodoFilho = crescerArvore(subListaDeInstancias);

					//Ajusta a label da ARESTA, concatenando com a descricao formal do melhor atributo
					raiz.getNodosFilhos().put(melhorAtributo.getName() + " = "+valor, nodoFilho);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
		
		return raiz;
	}

	/**
	 * Verifica qual classe mais se repete na populacao recebida e a retorna
	 * */
	public static String classificar(Set<Entidade> populacao){

		Map<Object, Set<Entidade>> contadorValores = new HashMap<>();
		Set<Entidade> entidades = null;

		for (Entidade item : populacao) {
			
			Object valor = obterValorDoAtributo(item, ATRIBUTO_DE_CLASSE);
			
			if (contadorValores.containsKey(valor)){

				entidades = contadorValores.get(valor);
				
			} else {

				entidades = new HashSet<>();
			}

			entidades.add(item);
			contadorValores.put(valor, entidades);
		}
		
		Object maisSeRepete = null;
		Integer contador = 0;
		
		for (Entry<Object, Set<Entidade>> entry : contadorValores.entrySet()) {

			Integer tamanhoDataSet = entry.getValue().size();

			if (tamanhoDataSet > contador) {
				
				contador = tamanhoDataSet;
				maisSeRepete = entry.getKey();
			}
		}
		
		return ATRIBUTO_DE_CLASSE.getName()+" = "+maisSeRepete.toString();
	}

	/**
	 * Varre os atributos disponiveis contando quantas instancias assumem cada valor possivel destes atributos
	 * */
	public static Map<Field, Map<Object, Set<Entidade>>> encontrarMelhorDivisao(Set<Entidade> populacao){

		Map<Object, Set<Entidade>> mapaDeInstanciasPorValor;
		Map<Field, Map<Object, Set<Entidade>>> mapaDeAtributos = new HashMap<>();
		Map<Field, Set<Object>> mapaDeValoresPossiveis = new HashMap<>();
		Field melhorAtributo = null;
		double melhorGini = 1.0;

		//Para cada atributo dos objetos da populacao, pega o valor deste atributo e divide os objetos de acordo com este valor
		for (Field atributoTeste : atributosDisponiveis) {

			//Obtem os valores possiveis do atributo por iteracao
			Set<Object> valoresPossiveis = new HashSet<>();
			Map<Object, Set<Entidade>> subMapaDeInstanciasPorValor = new HashMap<>();

			//Percorre todas as instâncias para mapeá-las pelo valor do atributo corrente
			for (Entidade item : populacao) {

				Entidade instancia = (Entidade)item;
				Object valor = obterValorDoAtributo(instancia, atributoTeste);

				valoresPossiveis.add(valor);

				//Pega as instancias mapeadas ao valor atual
				Set<Entidade> instancias = null;

				//Valor ainda não mapeado
				if (subMapaDeInstanciasPorValor.containsKey(valor)) {

					instancias = subMapaDeInstanciasPorValor.get(valor);

				} else {

					instancias = new HashSet<>();
				}

				instancias.add(item);
				subMapaDeInstanciasPorValor.put(valor, instancias);
			} // loop das instancias

			//obtém o gini atual para as instâncias mapeadas por valor possível do atributo
			double giniAtual = obterIndiceGini(subMapaDeInstanciasPorValor, populacao.size());

			mapaDeAtributos.put(atributoTeste, subMapaDeInstanciasPorValor);
			mapaDeValoresPossiveis.put(atributoTeste, valoresPossiveis);

			//Atualiza o indice Gini e o atrubuto comparativo
			if (melhorGini > giniAtual) {

				melhorGini = giniAtual;
				melhorAtributo = atributoTeste;
			}

		}//laco atributos

		//Atualiza os valores possiveis do atributo e o mapa de instancias de acordo com o atributo vencedor
		valoresPossiveisDoAtributo = mapaDeValoresPossiveis.get(melhorAtributo);
		Map<Object, Set<Entidade>> mapaDeInstanciasVencedor = mapaDeAtributos.get(melhorAtributo);
		mapaDeAtributos = new HashMap<>();
		mapaDeAtributos.put(melhorAtributo, mapaDeInstanciasVencedor);

		atributosDisponiveis.remove(melhorAtributo);

		return mapaDeAtributos;
	}
	
	public static double obterIndiceGini(Map<Object, Set<Entidade>> subMapaDeInstanciasPorValor, double tamanhoTotal){

		double gini = 0;

		//Percorre os possíveis nodos do atributo atual
		for (Entry<Object, Set<Entidade>> entrada : subMapaDeInstanciasPorValor.entrySet()){

			Map<Object, Integer> qtInstanciasPorValorDoAtributoDeClasse =
					inicializarMapaDeQuantidadeDeInstanciasPorValorDoAtributoDeClasse();
			double giniDoNodo = 0d;
			int totalDeInstanciasDoNodo = 0;

			//Percorre as instâncias do nodo atual e conta quantas instâncias tem por valor do atributo de classe
			for (Entidade instancia : entrada.getValue()){

				Object valorDoAtributoDeClasse = obterValorDoAtributo(instancia, ATRIBUTO_DE_CLASSE);
				Integer valorAtual = qtInstanciasPorValorDoAtributoDeClasse.get(valorDoAtributoDeClasse);
				qtInstanciasPorValorDoAtributoDeClasse.replace(valorDoAtributoDeClasse, ++valorAtual);

			}//laço das instâncias

			totalDeInstanciasDoNodo = entrada.getValue().size();

			giniDoNodo = calcularGiniDoNodo(qtInstanciasPorValorDoAtributoDeClasse, totalDeInstanciasDoNodo);

			//Pondera o gini de todos os nodos
			gini = gini + ( (totalDeInstanciasDoNodo/tamanhoTotal) * giniDoNodo);
		}

		return gini;
	}

	/**
	 * Inicializa o mapa com "zero" para cada valor possível do atributo de instância
	 * */
	static Map<Object, Integer> inicializarMapaDeQuantidadeDeInstanciasPorValorDoAtributoDeClasse(){

		Map<Object, Integer> mapa = new HashMap<>();

		for (Object valor : valoresPossiveisDoAtributoDeClasse) {

			mapa.put(valor, 0);
		}

		return mapa;
	}

	static double calcularGiniDoNodo(Map<Object, Integer> qtInstanciasPorValorDoAtributoDeClasse,
							   final double totalDeInstanciasDoNodo){

		double giniDoNodo = 1d;
		double giniDoValor;

		for (Entry<Object, Integer> entrada : qtInstanciasPorValorDoAtributoDeClasse.entrySet()){

			Integer qtDeInstancias = entrada.getValue();
			giniDoValor = Math.pow( (qtDeInstancias/totalDeInstanciasDoNodo), 2.0);
			giniDoNodo = giniDoNodo - giniDoValor;
		}

		return giniDoNodo;
	}
	
	public static Object obterValorDoAtributo(Object item, Field atributoTeste){
		
		try {
						
			return atributoTeste.get(item);
		
		} catch (Exception e) {

			if (atributoTeste != null){
				System.out.println("Falha ao obter o valor do atributo " + atributoTeste.getName());
			}
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Retorna "true" quando todos os valores dos atributos de classe da populacao forem iguais
	 * */
	public static boolean deveParar(Set<Entidade> populacao) {

		if (atributosDisponiveis.size() == 0){

			return true;
		}

		final int limiteMaximo = populacao.size();
		Object[] instancias = populacao.toArray();

		for (int i = 0; i < limiteMaximo; i++) {

			final Entidade registroCorrente = (Entidade)instancias[i];
			final Object valorCorrente = obterValorDoAtributo(registroCorrente, ATRIBUTO_DE_CLASSE);

			for (int j = i + 1; j < limiteMaximo; j++) {

				final Entidade registroAComparar = (Entidade)instancias[j];
				final Object valorAComparar = obterValorDoAtributo(registroAComparar, ATRIBUTO_DE_CLASSE);

				if (!valorCorrente.equals(valorAComparar)) {

					return false;
				}
			}
		}

		return true;
	}
}