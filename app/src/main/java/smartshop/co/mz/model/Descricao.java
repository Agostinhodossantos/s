package smartshop.co.mz.model;

public class Descricao {
    private String conceito , definicao;

    public Descricao(String conceito, String definicao) {
        this.conceito = conceito;
        this.definicao = definicao;
    }

    public Descricao(){

    }

    public String getConceito() {
        return conceito;
    }

    public void setConceito(String conceito) {
        this.conceito = conceito;
    }

    public String getDefinicao() {
        return definicao;
    }

    public void setDefinicao(String definicao) {
        this.definicao = definicao;
    }
}

