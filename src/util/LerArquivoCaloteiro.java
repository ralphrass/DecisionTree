package util;

import entidade.Caloteiro;
import entidade.Entidade;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ralph on 24/09/2015.
 */
public class LerArquivoCaloteiro {

    public Set<Entidade> lerInstancias(){

        Set<Entidade> dataSet = new HashSet<>();
        LerArquivo la = new LerArquivo();
        List<String[]> dataSetComoString = la.lerArquivo(
                "C:\\Users\\Ralph\\workspace-gpin-intellij\\DecisionTree\\src\\recursos\\dataSet-Caloteiros.txt");

        for (String[] instanciaString : dataSetComoString) {

            Caloteiro instancia = new Caloteiro(instanciaString);

            dataSet.add(instancia);
        }

        return dataSet;
    }
}
