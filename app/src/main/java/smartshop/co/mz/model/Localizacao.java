package smartshop.co.mz.model;

   public class Localizacao{
        private boolean isLocalizacao ;
        private String pronvicai ;
        private String id_user;

       public Localizacao(boolean isLocalizacao, String pronvicai, String id_user) {
           this.isLocalizacao = isLocalizacao;
           this.pronvicai = pronvicai;
           this.id_user = id_user;
       }

       public boolean isLocalizacao() {
           return isLocalizacao;
       }

       public void setLocalizacao(boolean localizacao) {
           isLocalizacao = localizacao;
       }

       public String getPronvicai() {
           return pronvicai;
       }

       public void setPronvicai(String pronvicai) {
           this.pronvicai = pronvicai;
       }

       public String getId_user() {
           return id_user;
       }

       public void setId_user(String id_user) {
           this.id_user = id_user;
       }
   }
