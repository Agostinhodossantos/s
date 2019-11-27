package smartshop.co.mz.model;

public class ChatList {
    private String nome , mensagem , tempo , img , uid;

    public ChatList(String nome, String mensagem, String tempo, String img, String uid) {
        this.nome = nome;
        this.mensagem = mensagem;
        this.tempo = tempo;
        this.img = img;
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
