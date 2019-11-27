package smartshop.co.mz.model;

public class DadosProduto {

    private int visitas  , contactos ;

    public DadosProduto(int visitas, int contactos) {
        this.visitas = visitas;
        this.contactos = contactos;
    }

    public DadosProduto() {
    }

    public int getVisitas() {
        return visitas;
    }

    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }

    public int getContactos() {
        return contactos;
    }

    public void setContactos(int contactos) {
        this.contactos = contactos;
    }
}
