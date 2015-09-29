package entidade;

import enumerador.TipoAtributo;
import qualificador.Atributo;

/**
 * Created by Ralph on 29/09/2015.
 */
public class Compras extends Entidade {

    //Canal (1 = Hotel/Restaurante/Café; 2 = Varejo) índice 0
    public String classe;

    //1 = Lisboa, 2 = Porto, 3 = Outro índice 1
    @Atributo(testavel = true, tipoAtributo = TipoAtributo.DISCRETO)
    public String regiao;

    @Atributo(testavel = true, tipoAtributo = TipoAtributo.CONTINUO)
    public Integer qtFrescos;
    @Atributo(testavel = true, tipoAtributo = TipoAtributo.CONTINUO)
    public Integer qtDerivadosLeite;
    @Atributo(testavel = true, tipoAtributo = TipoAtributo.CONTINUO)
    public Integer qtAlimentosGerais;
    @Atributo(testavel = true, tipoAtributo = TipoAtributo.CONTINUO)
    public Integer qtCongelados;
    @Atributo(testavel = true, tipoAtributo = TipoAtributo.CONTINUO)
    public Integer qtLimpeza;
    @Atributo(testavel = true, tipoAtributo = TipoAtributo.CONTINUO)
    public Integer qtImportados;

    public Compras(String classeP, String regiaoP, Integer qtFrescosP, Integer qtDerivadosLeiteP,
                   Integer qtAlimentosGeraisP, Integer qtCongeladosP, Integer qtLimpezaP, Integer qtImportadosP){

        this.setClasse(classeP);
        this.setRegiao(regiaoP);
        this.setQtFrescos(qtFrescosP);
        this.setQtDerivadosLeite(qtDerivadosLeiteP);
        this.setQtAlimentosGerais(qtAlimentosGeraisP);
        this.setQtCongelados(qtCongeladosP);
        this.setQtLimpeza(qtLimpezaP);
        this.setQtImportados(qtImportadosP);
    }

    public Compras(String[] instancias){

        this((String)instancias[0], (String)instancias[1], Integer.parseInt(instancias[2]),
                Integer.parseInt(instancias[3]), Integer.parseInt(instancias[4]),
                Integer.parseInt(instancias[5]), Integer.parseInt(instancias[6]),
                Integer.parseInt(instancias[7]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Compras compras = (Compras) o;

        if (classe != null ? !classe.equals(compras.classe) : compras.classe != null) return false;
        if (regiao != null ? !regiao.equals(compras.regiao) : compras.regiao != null) return false;
        if (qtFrescos != null ? !qtFrescos.equals(compras.qtFrescos) : compras.qtFrescos != null) return false;
        if (qtDerivadosLeite != null ? !qtDerivadosLeite.equals(compras.qtDerivadosLeite) : compras.qtDerivadosLeite != null)
            return false;
        if (qtAlimentosGerais != null ? !qtAlimentosGerais.equals(compras.qtAlimentosGerais) : compras.qtAlimentosGerais != null)
            return false;
        if (qtCongelados != null ? !qtCongelados.equals(compras.qtCongelados) : compras.qtCongelados != null)
            return false;
        if (qtLimpeza != null ? !qtLimpeza.equals(compras.qtLimpeza) : compras.qtLimpeza != null) return false;
        return !(qtImportados != null ? !qtImportados.equals(compras.qtImportados) : compras.qtImportados != null);

    }

    @Override
    public int hashCode() {
        int result = classe != null ? classe.hashCode() : 0;
        result = 31 * result + (regiao != null ? regiao.hashCode() : 0);
        result = 31 * result + (qtFrescos != null ? qtFrescos.hashCode() : 0);
        result = 31 * result + (qtDerivadosLeite != null ? qtDerivadosLeite.hashCode() : 0);
        result = 31 * result + (qtAlimentosGerais != null ? qtAlimentosGerais.hashCode() : 0);
        result = 31 * result + (qtCongelados != null ? qtCongelados.hashCode() : 0);
        result = 31 * result + (qtLimpeza != null ? qtLimpeza.hashCode() : 0);
        result = 31 * result + (qtImportados != null ? qtImportados.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Compras{" +
                "classe='" + classe + '\'' +
                ", regiao='" + regiao + '\'' +
                ", qtFrescos=" + qtFrescos +
                ", qtDerivadosLeite=" + qtDerivadosLeite +
                ", qtAlimentosGerais=" + qtAlimentosGerais +
                ", qtCongelados=" + qtCongelados +
                ", qtLimpeza=" + qtLimpeza +
                ", qtImportados=" + qtImportados +
                '}';
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public Integer getQtFrescos() {
        return qtFrescos;
    }

    public void setQtFrescos(Integer qtFrescos) {
        this.qtFrescos = qtFrescos;
    }

    public Integer getQtDerivadosLeite() {
        return qtDerivadosLeite;
    }

    public void setQtDerivadosLeite(Integer qtDerivadosLeite) {
        this.qtDerivadosLeite = qtDerivadosLeite;
    }

    public Integer getQtAlimentosGerais() {
        return qtAlimentosGerais;
    }

    public void setQtAlimentosGerais(Integer qtAlimentosGerais) {
        this.qtAlimentosGerais = qtAlimentosGerais;
    }

    public Integer getQtCongelados() {
        return qtCongelados;
    }

    public void setQtCongelados(Integer qtCongelados) {
        this.qtCongelados = qtCongelados;
    }

    public Integer getQtLimpeza() {
        return qtLimpeza;
    }

    public void setQtLimpeza(Integer qtLimpeza) {
        this.qtLimpeza = qtLimpeza;
    }

    public Integer getQtImportados() {
        return qtImportados;
    }

    public void setQtImportados(Integer qtImportados) {
        this.qtImportados = qtImportados;
    }
}
