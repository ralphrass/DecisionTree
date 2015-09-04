package entidade;

import qualificador.Atributo;

/**
 * Created by Ralph on 27/08/2015.
 */
public class CancerMamaRecorrente extends Entidade {

    @Atributo(testavel = true)
    public String idade;
    @Atributo(testavel = true)
    public String menopausa;
    @Atributo(testavel = true)
    public String tamanhoTumor;
    @Atributo(testavel = true)
    public String invNodos;
    @Atributo(testavel = true)
    public Boolean capsNodos;
    @Atributo(testavel = true)
    public String grauMaligno;
    @Atributo(testavel = true)
    public Boolean seioEsquerdo;
    @Atributo(testavel = true)
    public String quadranteSeio;
    @Atributo(testavel = true)
    public Boolean irradiat;
    public String classe;

    public CancerMamaRecorrente(String[] instancia){

        this((String)instancia[0], (String)instancia[1], (String)instancia[2], (String)instancia[3],
                Boolean.parseBoolean(instancia[4]), (String)instancia[5], Boolean.parseBoolean(instancia[6]), (String)instancia[7],
                Boolean.parseBoolean(instancia[8]), (String)instancia[9]);
    }

    public CancerMamaRecorrente(String idadeP, String menopausaP, String tamanhoTumorP, String invNodosP,
                                Boolean capsNodosP, String grauMalignoP, Boolean seioEsquerdoP, String quadranteSeioP,
                                Boolean irradiatP, String classeP){

        this.setIdade(idadeP);
        this.setMenopausa(menopausaP);
        this.setTamanhoTumor(tamanhoTumorP);
        this.setInvNodos(invNodosP);
        this.setCapsNodos(capsNodosP);
        this.setGrauMaligno(grauMalignoP);
        this.setSeioEsquerdo(seioEsquerdoP);
        this.setQuadranteSeio(quadranteSeioP);
        this.setIrradiat(irradiatP);
        this.setClasse(classeP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CancerMamaRecorrente that = (CancerMamaRecorrente) o;

        if (idade != null ? !idade.equals(that.idade) : that.idade != null) return false;
        if (menopausa != null ? !menopausa.equals(that.menopausa) : that.menopausa != null) return false;
        if (tamanhoTumor != null ? !tamanhoTumor.equals(that.tamanhoTumor) : that.tamanhoTumor != null) return false;
        if (invNodos != null ? !invNodos.equals(that.invNodos) : that.invNodos != null) return false;
        if (capsNodos != null ? !capsNodos.equals(that.capsNodos) : that.capsNodos != null) return false;
        if (grauMaligno != null ? !grauMaligno.equals(that.grauMaligno) : that.grauMaligno != null) return false;
        if (seioEsquerdo != null ? !seioEsquerdo.equals(that.seioEsquerdo) : that.seioEsquerdo != null) return false;
        if (quadranteSeio != null ? !quadranteSeio.equals(that.quadranteSeio) : that.quadranteSeio != null)
            return false;
        if (irradiat != null ? !irradiat.equals(that.irradiat) : that.irradiat != null) return false;
        return !(classe != null ? !classe.equals(that.classe) : that.classe != null);

    }

    @Override
    public int hashCode() {
        int result = idade != null ? idade.hashCode() : 0;
        result = 31 * result + (menopausa != null ? menopausa.hashCode() : 0);
        result = 31 * result + (tamanhoTumor != null ? tamanhoTumor.hashCode() : 0);
        result = 31 * result + (invNodos != null ? invNodos.hashCode() : 0);
        result = 31 * result + (capsNodos != null ? capsNodos.hashCode() : 0);
        result = 31 * result + (grauMaligno != null ? grauMaligno.hashCode() : 0);
        result = 31 * result + (seioEsquerdo != null ? seioEsquerdo.hashCode() : 0);
        result = 31 * result + (quadranteSeio != null ? quadranteSeio.hashCode() : 0);
        result = 31 * result + (irradiat != null ? irradiat.hashCode() : 0);
        result = 31 * result + (classe != null ? classe.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CancerMamaRecorrente{" +
                "idade='" + idade + '\'' +
                ", menopausa='" + menopausa + '\'' +
                ", tamanhoTumor='" + tamanhoTumor + '\'' +
                ", invNodos='" + invNodos + '\'' +
                ", capsNodos=" + capsNodos +
                ", grauMaligno='" + grauMaligno + '\'' +
                ", ladoSeio=" + seioEsquerdo +
                ", quadranteSeio='" + quadranteSeio + '\'' +
                ", irradiat=" + irradiat +
                ", classe='" + classe + '\'' +
                '}';
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getMenopausa() {
        return menopausa;
    }

    public void setMenopausa(String menopausa) {
        this.menopausa = menopausa;
    }

    public String getTamanhoTumor() {
        return tamanhoTumor;
    }

    public void setTamanhoTumor(String tamanhoTumor) {
        this.tamanhoTumor = tamanhoTumor;
    }

    public String getInvNodos() {
        return invNodos;
    }

    public void setInvNodos(String invNodos) {
        this.invNodos = invNodos;
    }

    public Boolean getCapsNodos() {
        return capsNodos;
    }

    public void setCapsNodos(Boolean capsNodos) {
        this.capsNodos = capsNodos;
    }

    public String getGrauMaligno() {
        return grauMaligno;
    }

    public void setGrauMaligno(String grauMaligno) {
        this.grauMaligno = grauMaligno;
    }

    public Boolean getSeioEsquerdo() {
        return seioEsquerdo;
    }

    public void setSeioEsquerdo(Boolean ladoSeio) {
        this.seioEsquerdo = ladoSeio;
    }

    public String getQuadranteSeio() {
        return quadranteSeio;
    }

    public void setQuadranteSeio(String quadranteSeio) {
        this.quadranteSeio = quadranteSeio;
    }

    public Boolean getIrradiat() {
        return irradiat;
    }

    public void setIrradiat(Boolean irradiat) {
        this.irradiat = irradiat;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
