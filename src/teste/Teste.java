package teste;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Ralph on 24/09/2015.
 */
public class Teste {

    public static void main(String args[]){

        SortedSet<Integer> conjunto = new TreeSet<>();

        conjunto.add(60);
        conjunto.add(54);
        conjunto.add(32);
        conjunto.add(68);
        conjunto.add(44);

        conjunto.add(conjunto.first() - 5);
        conjunto.add(conjunto.last() + 5);

        System.out.println(conjunto.toString());

        for (int i=0; i<(conjunto.size()-1); i++){

            Integer valorMedio = ((Integer)conjunto.toArray()[i] + (Integer)conjunto.toArray()[i+1]) / 2;

            System.out.println(valorMedio);
        }
    }
}
