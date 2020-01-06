package smartshop.co.mz.ui;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.NumberFormat;

public class VerificarCampos {

        private String letraInicialMaicula;

    public String getFormatValor() {
        return formatValor;
    }

    public void setFormatValor(String formatValor) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        nf.format(formatValor);
        this.formatValor = formatValor;
    }

    private String formatValor;

    public String getLetraInicialMaicula() {
        return letraInicialMaicula;
    }

    public void setLetraInicialMaicula(String letraInicialMaicula) {

        this.letraInicialMaicula = letraInicialMaicula.substring(0 , 1).toUpperCase()+letraInicialMaicula.substring(1).toLowerCase();
    }

    public boolean isCampoVazio(String valor){

            boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty() );
            return resultado;

        }


       public boolean isEmailValido(String email){

            boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
            return resultado;

        }

        public boolean isNumeroVodacom(String numero){
            boolean resultado = (numero.length() == 9 && numero.startsWith("84") || numero.startsWith("85") );
            return resultado;

        }

        public boolean isPasswordMpesaValido(String password){
            boolean resultado = (password.trim().length() > 3);
            return resultado;
        }



}
