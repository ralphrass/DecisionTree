package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created by Ralph on 27/08/2015.
 * Lê um arquivo de texto com instâncias e transforma cada instância em um Vetor de Strings
 */
public class LerArquivo {

    public List<String[]> lerArquivo(String caminho){

        List<String[]> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {

            String line = null;
            String[] lineArray = new String[10];

            while ((line = br.readLine()) != null) {

                //System.out.println(line);

                lineArray = line.split(",");
                lista.add(lineArray);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }
}