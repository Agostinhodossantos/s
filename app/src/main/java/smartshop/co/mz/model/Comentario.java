package smartshop.co.mz.model;

public class Comentario {
    private String uid , nome , comentario , img ,  data ;
    private long posicao;


    public Comentario(String uid, String nome, String comentario, String img, String data, long posicao) {
        this.uid = uid;
        this.nome = nome;
        this.comentario = comentario;
        this.img = img;
        this.data = data;
        this.posicao = posicao;
    }

    public Comentario(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getPosicao() {
        return posicao;
    }

    public void setPosicao(long posicao) {
        this.posicao = posicao;
    }
}
