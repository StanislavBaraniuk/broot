/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package broot;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.Adler32;
/**
 *
 * Кешування паролю
 */
public class Cahcing {
    private static Cahcing operator = null;
    
    private Cahcing () {
        
    }
    
    public static Cahcing getInctanse () {
        if (operator == null) operator = new Cahcing();
        return operator;
    }
    /**
    *
    * Кешування паролю
    * @param st строка для кешування
    * @return зашифрований String 
    */
    public static String cachingToMD5(String st, int i) {
        String ALGO[] = {"MD5","MD2","SHA-1","SHA-512","SHA-256","SHA-384"};
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance(ALGO[i]);
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }
    
}
