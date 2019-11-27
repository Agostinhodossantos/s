package smartshop.co.mz.model;

import java.io.Serializable;

public class ProdutoImagem  implements Serializable {

    private String img , uuid;

    public ProdutoImagem(String img, String uuid) {
        this.img = img;
        this.uuid = uuid;
    }

    public ProdutoImagem(){
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
