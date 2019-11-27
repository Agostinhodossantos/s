package smartshop.co.mz.model;

public class Produto {

    private String provincia , distrito ,nome , img , preco , uid , categoria , sub_categoria, idUser , tempo  ;
    private long posicao;


    public Produto(String provincia, String distrito, String nome, String img, String preco, String uid, String categoria, String sub_categoria, String idUser, String tempo, long posicao) {
        this.provincia = provincia;
        this.distrito = distrito;
        this.nome = nome;
        this.img = img;
        this.preco = preco;
        this.uid = uid;
        this.categoria = categoria;
        this.sub_categoria = sub_categoria;
        this.idUser = idUser;
        this.tempo = tempo;
        this.posicao = posicao;
    }

    public Produto(){}

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
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

    public String getSub_categoria() {
        return sub_categoria;
    }

    public void setSub_categoria(String sub_categoria) {
        this.sub_categoria = sub_categoria;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public long getPosicao() {
        return posicao;
    }

    public void setPosicao(long posicao) {
        this.posicao = posicao;
    }
}
