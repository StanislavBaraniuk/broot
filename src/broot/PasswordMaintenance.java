/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package broot;

/**
 *
 * Клас обробки паролів
 */
public class PasswordMaintenance {
    private static PasswordMaintenance operator = null;
    private String password, cachingPassword;
    
    private PasswordMaintenance () {
        
    }
    
    public static PasswordMaintenance getInctanse () {
        if (operator == null) operator = new PasswordMaintenance();
        return operator;
    }
    
    public void setPassword (String key) {
        password = key;
//        cachingPassword = Cahcing.cachingToMD5(password);
    }
    
    public String getCachingPassword () {
        return cachingPassword;
    }
    
    public String getPassword () {
        return password;
    }
    
    public boolean checkCaches (String key) {
        return (password == null ? key == null : password.equals(key));
    }
    

}
