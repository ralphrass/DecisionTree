package entidade;

import enumerador.TipoAtributo;
import qualificador.Atributo;

/**
 * Created by Ralph on 29/09/2015.
 */
public class SobreviventesCancerMama extends Entidade {

    @Atributo(testavel = true, tipoAtributo = TipoAtributo.CONTINUO)
    public Integer idade;
    @Atributo(testavel = true, tipoAtributo = TipoAtributo.CONTINUO)
    public Integer anoOperacao;
    @Atributo(testavel = true, tipoAtributo = TipoAtributo.CONTINUO)
    public Integer nodosAxilares;
    public String classe;

    public SobreviventesCancerMama(Integer idadeP, Integer anoOperacaoP, Integer nodosAxilaresP, String classe){

        this.setIdade(idadeP);
        this.setAnoOperacao(anoOperacaoP);
        this.setNodosAxilares(nodosAxilaresP);
        this.setClasse(classe);
    }

    public SobreviventesCancerMama(String[] instancia){

        this(Integer.parseInt(instancia[0]), Integer.parseInt(instancia[1]),
                Integer.parseInt(instancia[2]), (String)instancia[3]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SobreviventesCancerMama that = (SobreviventesCancerMama) o;

        if (idade != null ? !idade.equals(that.idade) : that.idade != null) return false;
        if (anoOperacao != null ? !anoOperacao.equals(that.anoOperacao) : that.anoOperacao != null) return false;
        if (nodosAxilares != null ? !nodosAxilares.equals(that.nodosAxilares) : that.nodosAxilares != null)
            return false;
        return !(classe != null ? !classe.equals(that.classe) : that.classe != null);

    }

    @Override
    public int hashCode() {
        int result = idade != null ? idade.hashCode() : 0;
        result = 31 * result + (anoOperacao != null ? anoOperacao.hashCode() : 0);
        result = 31 * result + (nodosAxilares != null ? nodosAxilares.hashCode() : 0);
        result = 31 * result + (classe != null ? classe.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SobreviventesCancerMama{" +
                "idade=" + idade +
                ", anoOperacao=" + anoOperacao +
                ", nodosAxilares=" + nodosAxilares +
                ", classe='" + classe + '\'' +
                '}';
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Integer getAnoOperacao() {
        return anoOperacao;
    }

    public void setAnoOperacao(Integer anoOperacao) {
        this.anoOperacao = anoOperacao;
    }

    public Integer getNodosAxilares() {
        return nodosAxilares;
    }

    public void setNodosAxilares(Integer nodosAxilares) {
        this.nodosAxilares = nodosAxilares;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
