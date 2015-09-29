package entidade;

import enumerador.TipoAtributo;
import qualificador.Atributo;

/**
 * Created by Ralph on 23/09/2015.
 */
public class Transfusao extends Entidade {

    @Atributo(testavel = true, tipoAtributo = TipoAtributo.CONTINUO)
    public Integer recencia;
    @Atributo(testavel = true, tipoAtributo = TipoAtributo.CONTINUO)
    public Integer frequencia;
    @Atributo(testavel = true, tipoAtributo = TipoAtributo.CONTINUO)
    public Integer centimetrosCubicosDoados;
    @Atributo(testavel = true, tipoAtributo = TipoAtributo.CONTINUO)
    public Integer mesesDesdeUltimaDoacao;
    public String classe;

    public Transfusao(String[] instancia){

        this(Integer.parseInt(instancia[0]), Integer.parseInt(instancia[1]),
             Integer.parseInt(instancia[2]), Integer.parseInt(instancia[3]), instancia[4]);
    }

    public Transfusao(Integer recenciaP, Integer frequenciaP, Integer centimetrosCubicosDoadosP,
                      Integer mesesDesdeUltimaDoacaoP, String classeP){

        this.setRecencia(recenciaP);
        this.setFrequencia(frequenciaP);
        this.setCentimetrosCubicosDoados(centimetrosCubicosDoadosP);
        this.setMesesDesdeUltimaDoacao(mesesDesdeUltimaDoacaoP);
        this.setClasse(classeP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transfusao that = (Transfusao) o;

        if (recencia != null ? !recencia.equals(that.recencia) : that.recencia != null) return false;
        if (frequencia != null ? !frequencia.equals(that.frequencia) : that.frequencia != null) return false;
        if (centimetrosCubicosDoados != null ? !centimetrosCubicosDoados.equals(that.centimetrosCubicosDoados) : that.centimetrosCubicosDoados != null)
            return false;
        if (mesesDesdeUltimaDoacao != null ? !mesesDesdeUltimaDoacao.equals(that.mesesDesdeUltimaDoacao) : that.mesesDesdeUltimaDoacao != null)
            return false;
        return !(classe != null ? !classe.equals(that.classe) : that.classe != null);
    }

    @Override
    public int hashCode() {
        int result = recencia != null ? recencia.hashCode() : 0;
        result = 31 * result + (frequencia != null ? frequencia.hashCode() : 0);
        result = 31 * result + (centimetrosCubicosDoados != null ? centimetrosCubicosDoados.hashCode() : 0);
        result = 31 * result + (mesesDesdeUltimaDoacao != null ? mesesDesdeUltimaDoacao.hashCode() : 0);
        result = 31 * result + (classe != null ? classe.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Transfusao{" +
                "recencia=" + recencia +
                ", frequencia=" + frequencia +
                ", centimetrosCubicosDoados=" + centimetrosCubicosDoados +
                ", mesesDesdeUltimaDoacao=" + mesesDesdeUltimaDoacao +
                ", classe='" + classe + '\'' +
                '}';
    }

    public Integer getRecencia() {
        return recencia;
    }

    public void setRecencia(Integer recencia) {
        this.recencia = recencia;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

    public Integer getCentimetrosCubicosDoados() {
        return centimetrosCubicosDoados;
    }

    public void setCentimetrosCubicosDoados(Integer centimetrosCubicosDoados) {
        this.centimetrosCubicosDoados = centimetrosCubicosDoados;
    }

    public Integer getMesesDesdeUltimaDoacao() {
        return mesesDesdeUltimaDoacao;
    }

    public void setMesesDesdeUltimaDoacao(Integer mesesDesdeUltimaDoacao) {
        this.mesesDesdeUltimaDoacao = mesesDesdeUltimaDoacao;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}