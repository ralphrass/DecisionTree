package util;

import entidade.CancerMamaRecorrente;
import entidade.Compras;
import entidade.Entidade;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ralph on 29/09/2015.
 */
public class LerArquivoCompras {

    public Set<Entidade> lerInstancias(){

        Set<Entidade> dataSet = new HashSet<>();
        LerArquivo la = new LerArquivo();
        List<String[]> dataSetComoString = la.lerArquivo(
                "C:\\Users\\Ralph\\workspace-gpin-intellij\\DecisionTree\\src\\recursos\\dataSet-Compras.txt");

        for (String[] instanciaString : dataSetComoString) {

            Compras instancia = new Compras(instanciaString);

            dataSet.add(instancia);
        }

        return dataSet;
    }
}
