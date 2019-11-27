package smartshop.co.mz.model;

public class Usuario {

    private String email , password , photo , nome,estado, localizacao ,uid ,data ;

    public Usuario(String email, String password, String photo, String nome, String estado, String localizacao, String uid, String data) {
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.nome = nome;
        this.estado = estado;
        this.localizacao = localizacao;
        this.uid = uid;
        this.data = data;
    }

    public Usuario(){

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
