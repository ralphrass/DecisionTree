package service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

import entidade.*;
import qualificador.Atributo;
import util.*;

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

			/*ATRIBUTO_DE_CLASSE = CancerMamaRecorrente.class.getField("classe");
			dataSet = new LerArquivoCancerMama().lerInstancias();
			atributos = CancerMamaRecorrente.class.getFields();*/

			/*ATRIBUTO_DE_CLASSE = Animal.class.getField("classe");
			dataSet = new LerArquivoAnimais().lerInstancias();
			atributos = Animal.class.getFields();*/

			/*ATRIBUTO_DE_CLASSE = Caloteiro.class.getField("classe");
			dataSet = new LerArquivoCaloteiro().lerInstancias();
			atributos = Caloteiro.class.getFields();*/

			/*ATRIBUTO_DE_CLASSE = Transfusao.class.getField("classe");
			dataSet = new LerArquivoTransfusao().lerInstancias();
			atributos = Transfusao.class.getFields();*/

			/*ATRIBUTO_DE_CLASSE = SobreviventesCancerMama.class.getField("classe");
			dataSet = new LerArquivoSobreviventesCancerMama().lerInstancias();
			atributos = SobreviventesCancerMama.class.getFields();*/

			ATRIBUTO_DE_CLASSE = Compras.class.getField("classe");
			dataSet = new LerArquivoCompras().lerInstancias();
			atributos = Compras.class.getFields();

			//Obtém todos os valores possíveis do atributos de classe
			for (Entidade instancia : dataSet) {

				valoresPossiveisDoAtributoDeClasse.add(AtributoUtil.obterValorDoAtributo(instancia, ATRIBUTO_DE_CLASSE));
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

			Map<Field, Map<Object, Set<Entidade>>> mapaDeInstanciasDoAtributoVencedor =
					EncontrarMelhorDivisao.encontrarMelhorDivisao(listaDeInstancias);

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
					if (valor instanceof String &&
							( ((String)valor).contains("<") ||
									((String)valor).contains(">") )){ //Contínuos, tem o sinal de "<=" ou ">"

						final String SEPARADOR = ((String) valor).substring(0, 2);
						final String VALOR = ((String)valor).replace(SEPARADOR, "");

						raiz.getNodosFilhos().put(melhorAtributo.getName() + " " + SEPARADOR + " " + VALOR, nodoFilho);


					} else { //Discreto, Nominal

						raiz.getNodosFilhos().put(melhorAtributo.getName() + " = "+valor, nodoFilho);
					}

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
			
			Object valor = AtributoUtil.obterValorDoAtributo(item, ATRIBUTO_DE_CLASSE);
			
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
	 * Inicializa o mapa com "zero" para cada valor possível do atributo de instância
	 * */
	public static Map<Object, Integer> inicializarMapaDeQuantidadeDeInstanciasPorValorDoAtributoDeClasse(){

		Map<Object, Integer> mapa = new HashMap<>();

		for (Object valor : valoresPossiveisDoAtributoDeClasse) {

			mapa.put(valor, 0);
		}

		return mapa;
	}


	/**
	 * Retorna "true" quando todos os valores dos atributos de classe da populacao forem iguais
	 * ou se a população tiver um tamanho menor que 3.
	 * */
	public static boolean deveParar(Set<Entidade> populacao) {

		if (populacao.size() < 3) {

			return true;
		}

		/*if (atributosDisponiveis.size() == 0){

			return true;
		}*/

		final int limiteMaximo = populacao.size();
		Object[] instancias = populacao.toArray();

		for (int i = 0; i < limiteMaximo; i++) {

			final Entidade registroCorrente = (Entidade)instancias[i];
			final Object valorCorrente = AtributoUtil.obterValorDoAtributo(registroCorrente, ATRIBUTO_DE_CLASSE);

			for (int j = i + 1; j < limiteMaximo; j++) {

				final Entidade registroAComparar = (Entidade)instancias[j];
				final Object valorAComparar = AtributoUtil.obterValorDoAtributo(registroAComparar, ATRIBUTO_DE_CLASSE);

				if (!valorCorrente.equals(valorAComparar)) {

					return false;
				}
			}
		}

		return true;
	}
}