package smartshop.co.mz.model;

public class LojaModel {
    private String uid ,uid_user , nome ,localizacao , img , data;
    private long posicao;


    public LojaModel(String uid, String uid_user, String nome, String localizacao, String img, String data, long posicao) {
        this.uid = uid;
        this.uid_user = uid_user;
        this.nome = nome;
        this.localizacao = localizacao;
        this.img = img;
        this.data = data;
        this.posicao = posicao;
    }

    public LojaModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid_user() {
        return uid_user;
    }

    public void setUid_user(String uid_user) {
        this.uid_user = uid_user;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
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
