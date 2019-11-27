package smartshop.co.mz.model;

public class ProdutoDestaque {
    private String localizacao ,nome , img , preco , uid , categoria ;

    public ProdutoDestaque(String localizacao, String nome, String img, String preco, String uid, String categoria) {
        this.localizacao = localizacao;
        this.nome = nome;
        this.img = img;
        this.preco = preco;
        this.uid = uid;
        this.categoria = categoria;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
