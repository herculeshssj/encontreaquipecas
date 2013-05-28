package br.com.hslife.encontreaquipecas.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	
	/** Seção de constantes **/
	
	public static final String DATAHORA = "datahora";
	public static final String DATA = "data";
	public static final String HORA = "hora";
	public static final String DATABASE = "database";
	
	private Util() {
		// Esta classe não pode ser instanciada
	}
	
	public static String MD5(String texto) {
        String sen = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, md.digest(texto.getBytes()));
            sen = hash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }        
        return sen;
    }

    public static String SHA1(String texto) {
        String sen = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            BigInteger hash = new BigInteger(1, md.digest(texto.getBytes()));
            sen = hash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return sen;
    }
    
    public static String moedaBrasil(double valor) {
    	NumberFormat formatarMoeda = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
    	return formatarMoeda.format(valor);  
    }
    
    public static String formataDataHora(Date dataHora, String opcao) {
    	SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    	if (dataHora == null) return "";
    	if (opcao.equals(Util.DATAHORA)) {
    		formata = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    	}
    	if (opcao.equals(Util.DATA)) {
    		formata = new SimpleDateFormat("dd/MM/yyyy");
    	}
    	if (opcao.equals(Util.HORA)) {
    		formata = new SimpleDateFormat("hh:mm");
    	}    	
        if (opcao.equals(Util.DATABASE)) {
        	formata = new SimpleDateFormat("yyyy-MM-dd");
        }

        return formata.format(dataHora);
    }
    
    /*
     * Retorna o primeiro dia do mês anterior a partir da data atual
     */
    public static Date primeiroDiaMesAnterior() {
    	// Pega a data atual
    	Calendar dataAtual = Calendar.getInstance();
    	
    	// Seta para o mês anterior 
    	dataAtual.add(Calendar.MONTH, -1);
    	
    	// Seta para o primeiro dia do mês 
    	dataAtual.set(Calendar.DAY_OF_MONTH, 1);
    	
    	// Retorna o resultado
    	return dataAtual.getTime();
    }
    
    /*
     * Retorna o primeiro dia do mês anterior a partir da data atual
     */
    public static Date ultimoDiaMesAnterior() {
    	// Pega a data atual
    	Calendar dataAtual = Calendar.getInstance();
    	
    	// Seta para o primeiro dia do mês 
    	dataAtual.set(Calendar.DAY_OF_MONTH, 1);
    	
    	// "Rola" para o dia anterior do ano
    	dataAtual.add(Calendar.DAY_OF_YEAR, -1);
    	
    	// Retorna o resultado
    	return dataAtual.getTime();
    }
    
    /*
     * Retorna o primeiro dia do mês atual
     */
    public static Date primeiroDiaMesAtual() {
    	// Pega a data atual
    	Calendar dataAtual = Calendar.getInstance();
    	
    	// Seta para primeiro dia do mês 
    	dataAtual.add(Calendar.DAY_OF_MONTH, 1);
    	
    	// Retorna o resultado
    	return dataAtual.getTime();
    }
    
    /**
     * Remove todos os acentos da string passada como parâmetro
     * @param acentuada string a ter os acentos removidos
     * @return nova string sem os acentos
     */
    public static String removerAcentos(String acentuada) {
    	CharSequence cs = new StringBuilder(acentuada);
    	return Normalizer.normalize(cs, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
    
    /*
     * Arredonda valores reais para duas casas decimais.
     */
    public static double arredondar(double valor) {
		BigDecimal valorAArredondar = new BigDecimal(valor);
		valorAArredondar.setScale(2, BigDecimal.ROUND_CEILING);
		return valorAArredondar.doubleValue();
    }
    
    /*
     * Verifica se o e-mail informado é válido.
     */
    public static boolean validaEmail(String email) {
    	Pattern p = Pattern.compile("[a-zA-Z0-9]+[a-zA-Z0-9_.-]+@{1}[a-zA-Z0-9_.-]*\\.+[a-z]{2,4}");
    	Matcher m = p.matcher(email);
    	boolean matchFound = m.matches();
    	if (matchFound) {
    		return true;
    	} else {
    		return false;
    	}    	
    }  
    
    public static String montarString(String[] strings) {
    	StringBuilder messageBuilder = new StringBuilder();
		for (String s : strings) {
			messageBuilder.append(s);
			messageBuilder.append("\n\n");
		}
		return messageBuilder.toString();
    }
    
    // Verifica se o CNPJ informado é válido
    // Retorna true se o CNPJ é válido
    public static boolean validCnpj(String cnpj) {
        int soma = 0, aux, dig;

        if (cnpj.length() != 14) {
            return false;
        }

        String cnpj_calc = cnpj.substring(0, 12);

        char[] chr_cnpj = cnpj.toCharArray();

        /* Primeira parte */
        for (int i = 0; i < 4; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
            }
        }
        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
            }
        }
        dig = 11 - (soma % 11);

        cnpj_calc += (dig == 10 || dig == 11)
                ? "0" : Integer.toString(dig);

        /* Segunda parte */
        soma = 0;
        for (int i = 0; i < 5; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
            }
        }
        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
            }
        }
        dig = 11 - (soma % 11);
        cnpj_calc += (dig == 10 || dig == 11)
                ? "0" : Integer.toString(dig);

        return cnpj.equals(cnpj_calc);
    }
}