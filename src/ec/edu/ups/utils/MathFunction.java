package ec.edu.ups.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MathFunction {
	
	public static double getTrunkDecimal(double n) {
		n = Math.round(n * 100.0)/100.0;
		return n;
	}
	
	public static String getMd5(String input) { 
        try { 
  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
	}
	
	
	public static boolean validateDNI(String dni) {
		boolean cedulaCorrecta = false;

		try {

		if (dni.length() == 10) // ConstantesApp.LongitudCedula
		{
		int tercerDigito = Integer.parseInt(dni.substring(2, 3));
		if (tercerDigito < 6) {
		// Coeficientes de validación cédula
		// El decimo digito se lo considera dígito verificador
		 int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
		 int verificador = Integer.parseInt(dni.substring(9,10));
		 int suma = 0;
		 int digito = 0;
		for (int i = 0; i < (dni.length() - 1); i++) {
		 digito = Integer.parseInt(dni.substring(i, i + 1))* coefValCedula[i];
		 suma += ((digito % 10) + (digito / 10));
		}

		if ((suma % 10 == 0) && (suma % 10 == verificador)) {
			cedulaCorrecta = true;
		}
		else if ((10 - (suma % 10)) == verificador) {
			cedulaCorrecta = true;
		} else {
			cedulaCorrecta = false;
		}
		} else {
			cedulaCorrecta = false;
		}
		} else {
			cedulaCorrecta = false;
		}
		} catch (NumberFormatException nfe) {
			cedulaCorrecta = false;
		} catch (Exception err) {
			cedulaCorrecta = false;
		}

		if (!cedulaCorrecta) {
		}
		return cedulaCorrecta;
		}
}