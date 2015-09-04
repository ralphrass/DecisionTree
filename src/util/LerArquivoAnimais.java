package util;

import entidade.Animal;
import entidade.Entidade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ralph on 27/08/2015.
 */
public class LerArquivoAnimais {

    public Set<Entidade> lerInstancias(){

        Set<Entidade> dataSet = new HashSet<>();
        LerArquivo la = new LerArquivo();
        List<String[]> dataSetComoString = la.lerArquivo(
                "C:\\Users\\Ralph\\workspace-gpin-intellij\\DecisionTree\\src\\recursos\\dataSet-Animais.txt");

        for (String[] instanciaString : dataSetComoString) {

            Animal instancia = new Animal(instanciaString);

            dataSet.add(instancia);
        }

        return dataSet;
    }
}
