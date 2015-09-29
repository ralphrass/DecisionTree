package util;

import entidade.CancerMamaRecorrente;
import entidade.Entidade;
import entidade.SobreviventesCancerMama;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ralph on 29/09/2015.
 */
public class LerArquivoSobreviventesCancerMama {

    public Set<Entidade> lerInstancias(){

        Set<Entidade> dataSet = new HashSet<>();
        LerArquivo la = new LerArquivo();
        List<String[]> dataSetComoString = la.lerArquivo(
                "C:\\Users\\Ralph\\workspace-gpin-intellij\\DecisionTree\\src\\recursos\\dataSet-SobreviventesCancerMama.txt");

        for (String[] instanciaString : dataSetComoString) {

            SobreviventesCancerMama instancia = new SobreviventesCancerMama(instanciaString);

            dataSet.add(instancia);
        }

        return dataSet;
    }
}
