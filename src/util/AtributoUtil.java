package util;

import java.lang.reflect.Field;

/**
 * Created by Ralph on 29/09/2015.
 */
public class AtributoUtil {

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
}
