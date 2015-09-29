package util;

import entidade.Entidade;
import entidade.Transfusao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ralph on 23/09/2015.
 */
public class LerArquivoTransfusao {

    public Set<Entidade> lerInstancias(){

        Set<Entidade> dataSet = new HashSet<>();
        LerArquivo la = new LerArquivo();
        List<String[]> dataSetComoString = la.lerArquivo(
                "C:\\Users\\Ralph\\workspace-gpin-intellij\\DecisionTree\\src\\recursos\\dataSet-Transfusion.txt");

        for (String[] instanciaString : dataSetComoString) {

            Transfusao instancia = new Transfusao(instanciaString);

            dataSet.add(instancia);
        }

        return dataSet;
    }
}
