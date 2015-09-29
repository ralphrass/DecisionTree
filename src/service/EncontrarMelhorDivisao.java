package service;

import entidade.Entidade;
import enumerador.TipoAtributo;
import qualificador.Atributo;
import util.AtributoUtil;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Ralph on 29/09/2015.
 */
public class EncontrarMelhorDivisao {

    static double melhorGiniContinuo = 1d;
    static Field melhorAtributoContinuo = null;
    static Map<Field, Set<Object>> valoresContinuosPossiveis = new HashMap<>();

    /**
     * Varre os atributos disponiveis contando quantas instancias assumem cada valor possivel destes atributos
     * */
    public static Map<Field, Map<Object, Set<Entidade>>> encontrarMelhorDivisao(Set<Entidade> populacao){

        Map<Object, Set<Entidade>> mapaDeInstanciasPorValor;
        Map<Field, Map<Object, Set<Entidade>>> mapaDeAtributos = new HashMap<>();
        Map<Field, Set<Object>> mapaDeValoresPossiveis = new HashMap<>();
        Field melhorAtributoDiscreto = null, melhorAtributoGlobal = null;
        double melhorGiniDiscreto = 1d;
        melhorGiniContinuo = 1d;
        melhorAtributoContinuo = null;

        //Attrs contínuos
        mapaDeAtributos = encontrarMelhorDivisaoParaAtributosContinuos(populacao);

        //Não há attrs contínuos
        if (mapaDeAtributos == null) {

            mapaDeAtributos = new HashMap<>();
        }

        //Para cada attr DISCRETO dos objetos da populacao, pega o valor deste atributo e divide os objetos
        for (Field atributoTeste : Arvore.atributosDisponiveis) {

            if (!atributoTeste.getAnnotation(Atributo.class).
                    tipoAtributo().equals(TipoAtributo.DISCRETO)){

                continue;
            }

            //Obtem os valores possiveis do atributo por iteracao
            Set<Object> valoresPossiveis = new HashSet<>();
            Map<Object, Set<Entidade>> subMapaDeInstanciasPorValor = new HashMap<>();

            //Percorre todas as instâncias para mapeá-las pelo valor do atributo corrente
            for (Entidade item : populacao) {

                Entidade instancia = (Entidade)item;
                Object valor = AtributoUtil.obterValorDoAtributo(instancia, atributoTeste);

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
            if (melhorGiniDiscreto > giniAtual) {

                melhorGiniDiscreto = giniAtual;
                melhorAtributoDiscreto = atributoTeste;
            }

        }//laco atributos

        if (melhorGiniContinuo < melhorGiniDiscreto) {

            melhorAtributoGlobal = melhorAtributoContinuo;
            Arvore.valoresPossiveisDoAtributo = valoresContinuosPossiveis.get(melhorAtributoContinuo);

        } else {

            melhorAtributoGlobal = melhorAtributoDiscreto;
            Arvore.valoresPossiveisDoAtributo = mapaDeValoresPossiveis.get(melhorAtributoGlobal);
        }

        //Atualiza os valores possiveis do atributo e o mapa de instancias de acordo com o atributo vencedor
        Map<Object, Set<Entidade>> mapaDeInstanciasVencedor = mapaDeAtributos.get(melhorAtributoGlobal);
        mapaDeAtributos = new HashMap<>();
        mapaDeAtributos.put(melhorAtributoGlobal, mapaDeInstanciasVencedor);

        Arvore.atributosDisponiveis.remove(melhorAtributoGlobal);

        return mapaDeAtributos;
    }

    static double obterIndiceGini(Map<Object, Set<Entidade>> subMapaDeInstanciasPorValor, double tamanhoTotal){

        double gini = 0;

        //Percorre os possíveis nodos do atributo atual
        for (Map.Entry<Object, Set<Entidade>> entrada : subMapaDeInstanciasPorValor.entrySet()){

            Map<Object, Integer> qtInstanciasPorValorDoAtributoDeClasse =
                    Arvore.inicializarMapaDeQuantidadeDeInstanciasPorValorDoAtributoDeClasse();
            double giniDoNodo = 0d;
            int totalDeInstanciasDoNodo = 0;

            //Percorre as instâncias do nodo atual e conta quantas instâncias tem por valor do atributo de classe
            for (Entidade instancia : entrada.getValue()){

                Object valorDoAtributoDeClasse = AtributoUtil.obterValorDoAtributo(instancia, Arvore.ATRIBUTO_DE_CLASSE);
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

    static double calcularGiniDoNodo(Map<Object, Integer> qtInstanciasPorValorDoAtributoDeClasse,
                                     final double totalDeInstanciasDoNodo){

        double giniDoNodo = 1d;
        double giniDoValor;

        for (Map.Entry<Object, Integer> entrada : qtInstanciasPorValorDoAtributoDeClasse.entrySet()){

            Integer qtDeInstancias = entrada.getValue();
            giniDoValor = Math.pow((qtDeInstancias / totalDeInstanciasDoNodo), 2.0);
            giniDoNodo = giniDoNodo - giniDoValor;
        }

        return giniDoNodo;
    }

    private static Map<Field, Map<Object, Set<Entidade>>>
        encontrarMelhorDivisaoParaAtributosContinuos(Set<Entidade> populacao){

        double giniAtual, melhorGiniLocal; //controla o melhor gini dentre os valores possíveis de um attr continuo
        int melhorValorMedioLocal = 0, melhorValorMedioGlobal = 0;
        Map<Field, Integer> mapaDeMelhoresValoresMediosPorAttr = new HashMap<>();

        for (Field atributo : Arvore.atributosDisponiveis) {

            if (!atributo.getAnnotation(Atributo.class).
                    tipoAtributo().equals(TipoAtributo.CONTINUO)){

                continue;
            }

            melhorGiniLocal = 1d;

            SortedSet<Object> valoresPossiveis = new TreeSet<>();

            // #1 Ordenar os valores possiveis para o atributo corrente
            for (Entidade instancia : populacao){

                Integer valor = (Integer)AtributoUtil.obterValorDoAtributo(instancia, atributo);
                valoresPossiveis.add(valor);
            }

            // #2 Definir valores médios para cada valor possível. {O gini dos valores de margem SÃO IGNORADOS}
            for (int i=0; i<(valoresPossiveis.size()-1); i++){

                Integer valorUm = (Integer)valoresPossiveis.toArray()[i];
                Integer valorDois = (Integer)valoresPossiveis.toArray()[(i+1)];
                Integer valorMedio = (valorUm + valorDois) / 2;

                //Computa o gini de valores menores ou iguais e de valores maiores
                giniAtual = obterIndiceGiniParaAtributoContinuo(populacao, valorMedio, atributo);

                if (melhorGiniLocal > giniAtual){

                    melhorGiniLocal = giniAtual;
                    melhorValorMedioLocal = valorMedio;
                }
            }

            mapaDeMelhoresValoresMediosPorAttr.put(atributo, melhorValorMedioLocal);

            if (melhorGiniContinuo > melhorGiniLocal) {

                melhorGiniContinuo = melhorGiniLocal;
                melhorAtributoContinuo = atributo;
                melhorValorMedioGlobal = melhorValorMedioLocal;
            }
        }

        if (melhorAtributoContinuo == null){

            return null;
        }

        Map<Field, Integer> mapaDoAttrVencedor = new HashMap<>();
        mapaDoAttrVencedor.put(melhorAtributoContinuo, mapaDeMelhoresValoresMediosPorAttr.get(melhorAtributoContinuo));

        Map<Field, Map<Object, Set<Entidade>>> mapaDeAtributos = dividirAtributoContinuo(
                melhorAtributoContinuo, melhorValorMedioGlobal, populacao);

        //return mapaDoAttrVencedor;
        return mapaDeAtributos;
    }

    /**
     * Divide as instâncias de acordo com o melhor valor médio do melhor attr contínuo
     * */
    private static Map<Field, Map<Object, Set<Entidade>>> dividirAtributoContinuo(
            Field atributo, int valorMedio, Set<Entidade> instancias){

        Map<Field, Map<Object, Set<Entidade>>> mapaDoAtributo = new HashMap<>();
        Map<Object, Set<Entidade>> mapaDeInstancias = new HashMap<>();
        Set<Entidade> instanciasComValorMenorOuIgual = new HashSet<>();
        Set<Entidade> instanciasComValorMaior = new HashSet<>();

        for (Entidade instancia : instancias) {

            Integer valor = (Integer)AtributoUtil.obterValorDoAtributo(instancia, atributo);

            if (valor <= valorMedio){

                instanciasComValorMenorOuIgual.add(instancia);

            } else {

                instanciasComValorMaior.add(instancia);
            }
        }

        //TODO - organizar strings
        Set<Object> valores = new HashSet<>();
        valores.add("<="+valorMedio);
        valores.add("> "+valorMedio);
        valoresContinuosPossiveis.put(atributo, valores);
        //valoresContinuosPossiveis.put(atributo, );

        mapaDeInstancias.put("<="+valorMedio, instanciasComValorMenorOuIgual);
        mapaDeInstancias.put("> "+valorMedio, instanciasComValorMaior);

        mapaDoAtributo.put(atributo, mapaDeInstancias);

        return mapaDoAtributo;
    }

    /**
     * Divisão binária de atributos contínuos
     * */
    private static double obterIndiceGiniParaAtributoContinuo(Set<Entidade> populacao,
                                                              Integer valorMedio, Field atributo){

        double giniDosMenores, giniDosMaiores, qtInstanciasMenorOuIgual = 0d, qtInstanciasMaior = 0d;
        int qtInstanciasCorrente;

        Map<Object, Integer> qtMenoresOuIguaisPorAttrDeClasse = Arvore.inicializarMapaDeQuantidadeDeInstanciasPorValorDoAtributoDeClasse();
        Map<Object, Integer> qtMaioresPorAttrDeClasse = Arvore.inicializarMapaDeQuantidadeDeInstanciasPorValorDoAtributoDeClasse();

        for (Entidade instancia : populacao){

            Integer valor = (Integer)AtributoUtil.obterValorDoAtributo(instancia, atributo);
            String chave = (String)AtributoUtil.obterValorDoAtributo(instancia, Arvore.ATRIBUTO_DE_CLASSE);

            if (valor <= valorMedio) {

                qtInstanciasCorrente = qtMenoresOuIguaisPorAttrDeClasse.get(chave);
                qtMenoresOuIguaisPorAttrDeClasse.replace(chave, ++qtInstanciasCorrente);
                qtInstanciasMenorOuIgual++;

            } else {

                qtInstanciasCorrente = qtMaioresPorAttrDeClasse.get(chave);
                qtMaioresPorAttrDeClasse.replace(chave, ++qtInstanciasCorrente);
                qtInstanciasMaior++;
            }
        }

        //Computa o gini para as quantidades do valor médio atual, por attr de classe
        giniDosMenores = calcularGiniDoNodo(qtMenoresOuIguaisPorAttrDeClasse, qtInstanciasMenorOuIgual);
        giniDosMaiores = calcularGiniDoNodo(qtMaioresPorAttrDeClasse, qtInstanciasMaior);

        //Pondera o gini de todos os nodos:
        final double giniPonderadoDosMenores = ((qtInstanciasMenorOuIgual/populacao.size()) * giniDosMenores);
        final double giniPonderadoDosMaiores = ((qtInstanciasMaior/populacao.size()) * giniDosMaiores);

        return (giniPonderadoDosMenores + giniPonderadoDosMaiores);
    }

    /**
     * Multiplica o nro de casas decimais do valor pelo fator
     * */
    private static Integer obterValorContinuoDeMargem(Integer valor){

        final int fator = 10;
        final int nrCasasDecimais = valor.toString().length();

        if (nrCasasDecimais == 1) {

            return 1;

        } else if (nrCasasDecimais == 2) {

            return fator/2;

        } else {

            return fator*(nrCasasDecimais-2);
        }
    }
}
