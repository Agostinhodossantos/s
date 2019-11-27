package smartshop.co.mz.model;

public class DescricaoProduto {
    private String uid , bairo , descricao , contacto , latitude , longitude , negociavel ;


    public DescricaoProduto(String uid, String bairo, String descricao, String contacto, String latitude, String longitude, String negociavel) {
        this.uid = uid;
        this.bairo = bairo;
        this.descricao = descricao;
        this.contacto = contacto;
        this.latitude = latitude;
        this.longitude = longitude;
        this.negociavel = negociavel;
    }

    public DescricaoProduto() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBairo() {
        return bairo;
    }

    public void setBairo(String bairo) {
        this.bairo = bairo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNegociavel() {
        return negociavel;
    }

    public void setNegociavel(String negociavel) {
        this.negociavel = negociavel;
    }
}
