package entidade;

import enumerador.TipoAtributo;
import qualificador.Atributo;

/**
 * Created by Ralph on 24/09/2015.
 */
public class Caloteiro extends Entidade {

    @Atributo(testavel = true, tipoAtributo = TipoAtributo.CONTINUO)
    public Integer rendaAnual;

    public String classe;

    public Caloteiro(String[] instancia){

        this(Integer.parseInt(instancia[0]), instancia[1]);
    }

    public Caloteiro(Integer rendaAnualP, String classeP){

        this.setRendaAnual(rendaAnualP);
        this.setClasse(classeP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Caloteiro caloteiro = (Caloteiro) o;

        if (rendaAnual != null ? !rendaAnual.equals(caloteiro.rendaAnual) : caloteiro.rendaAnual != null) return false;
        return !(classe != null ? !classe.equals(caloteiro.classe) : caloteiro.classe != null);

    }

    @Override
    public int hashCode() {
        int result = rendaAnual != null ? rendaAnual.hashCode() : 0;
        result = 31 * result + (classe != null ? classe.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Caloteiro{" +
                "rendaAnual=" + rendaAnual +
                ", classe='" + classe + '\'' +
                '}';
    }

    public Integer getRendaAnual() {
        return rendaAnual;
    }

    public void setRendaAnual(Integer rendaAnual) {
        this.rendaAnual = rendaAnual;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
