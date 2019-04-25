/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package broot;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/**
 *
 * Клас підбору паролю
 */
public class Brooter {
    private static Brooter broot = null;
    public int stopSignal = 0, variant;
    
    private Brooter () {
        
    }
    
    public static Brooter getInctanse () {
        if (broot == null) broot = new Brooter();
        return broot;
    }
/**
 *
 * Створення масиву сиволів для підбору паролю
 * на вхід отримує відмічені в вікні варіанти
 */
    public String[] createSample (boolean AZ, boolean az, boolean AY, boolean ay, boolean ZN, boolean CPS, boolean SP) {
        String azS[] = { "a","e","i","o","u","y","b","c","d","f","g","h","j","k","l","m","n","p","q","r","s","t","v","w","x","z"};
        String AZS[] = { "A","E","I","O","U","Y","B","C","D","F","G","H","J","K","L","M","N","P","Q","R","S","T","V","W","X","Z"};
        String ayS[] = { "а","о","у","е","и","ы","б","п","д","т","г","к","ф","ж","з","ш","с","х","ч","ц","в","й","м","н","л","р","ъ","ё","і","ґ","ї","я","є","э","ю","ь"};
        String AYS[] = { "А","О","У","Е","И","Ы","Б","П","Д","Т","Г","К","Ф","Ж","З","Ш","С","Х","Ч","Ц","В","Й","М","Н","Л","Р","Ъ","Ё","І","Ґ","Ї","Я","Є","Э","Ю","Ь"};
        String SPS[] = { "(",")","!","@","#","$","%","^","&","*","-","=","_","+","/","|","<",">","`","~","{","}","[","]",".",",",":",";", "'"};
        String CPSS[] = {" "};
        String ZNS[] = {"0","1","2","3","4","5","6","7","8","9"};
        
        int len = 0;
        
        len = AZ == true ? len + AZS.length : len;
        len = az == true ? len + azS.length : len;
        len = AY == true ? len + AYS.length : len;
        len = ay == true ? len + ayS.length : len;
        len = ZN == true ? len + ZNS.length : len;
        len = CPS == true ? len + CPSS.length : len;
        len = SP == true ? len + SPS.length : len;
        
        String sample[] = new String[len];

        int k = 0;
        if (az == true) {
            for (String azS1 : azS) {
                sample[k] = azS1;
                k++;
            }
        }
        if (AZ == true) {
            for (String AZS1 : AZS) {
                sample[k] = AZS1;
                k++;
            }
        }
        if (ay == true) {
            for (String ayS1 : ayS) {
                sample[k] = ayS1;
                k++;
            }
        }
        if (AY == true) {
            for (String AYS1 : AYS) {
                sample[k] = AYS1;
                k++;
            }
        }
        if (CPS == true) {
            for (String CPSS1 : CPSS) {
                sample[k] = CPSS1;
                k++;
            }
        }
        if (SP == true) {
            for (String SPS1 : SPS) {
                sample[k] = SPS1;
                k++;
            }
        }
        if (ZN == true) {
            for (String ZNS1 : ZNS) {
                sample[k] = ZNS1;
                k++;
            }
        }
        for (int i = 0; i < sample.length; i++) {
            System.out.println(sample[i]);
        }
        return sample;
    }
    /**
 *
 * Початок підбору паролю
 * @param sample вибірка символів
 * @param keyL довжина паролю
 */
    public void start(String[] sample, int startL, int finishL, int algorithm) {
        for (int j = 0; j <= (finishL-startL); j++) {
            
        int keyL = startL+j;
        double all = Math.pow(sample.length, keyL);
        
        stopSignal = 0;
        Thread myThready = new Thread(() -> //Этот метод будет выполняться в побочном потоке
        {
            MyForm.getInctanse().jProgressBar1.setMaximum((int)all);
            System.out.println("Start");
            String brootKey = "", correctKey = null;
            int letterNumb = keyL;
            int letterCheck = 0, exampleCheck = 0, last = keyL-1;
            ArrayList<Double> prosto = new ArrayList<>();
            for (int i = 0; i < keyL; i++) {
                prosto.add(0.);
            }
            MyForm.getInctanse().jLabel2.setText("Всього: "  + String.valueOf(all));
            
            do {
                brootKey = "";
                try {
                for (int i = 0; i < keyL; i++) {
                    brootKey += sample[prosto.get(i).intValue()];
                }
                } catch (ArrayIndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(null, "Ви не обрали символи", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
                System.out.println(brootKey);
                letterCheck++;
                
                if (letterCheck == keyL) {
                    letterCheck = 0;
                }
                double k;
                for (int i = 0; i < keyL; i++) {
                    if (prosto.get(i).intValue() == sample.length-1 && i != 0) {
                        prosto.remove(i);
                        prosto.add(i, 0.0);
                        
                        if (i - 1 != -1) {
                            
                            k = prosto.get(i-1) + 1.;
                            prosto.remove(i-1);
                            prosto.add(i-1, k);
                            
                        } else {
                            if (last != 0) {
                                last--;
                            } else {
                                last = keyL - 1;
                            }      
                        }
                        
                    } else {
                        k = prosto.get(last) + 1/(double)keyL;
                        prosto.remove(last);
                        prosto.add(last, k);
                    }
                }
                
                
                if (PasswordMaintenance.getInctanse().checkCaches(Cahcing.cachingToMD5(brootKey, algorithm))) {
                    correctKey = brootKey;
                    stopSignal = 1;
                }
                
                
                if (stopSignal == 1) {
                    System.out.println("pizda");
                    break;
                }
                
                exampleCheck++;
                MyForm.getInctanse().jLabel4.setText("Залишилось: "  + (all - exampleCheck));
                MyForm.getInctanse().jProgressBar1.setValue(exampleCheck);
                MyForm.getInctanse().jLabel5.setText(Math.round(exampleCheck*100/all) + "%/100%");
            } while (all != exampleCheck);
            stopSignal = 0;
            JOptionPane.showMessageDialog(null, correctKey != null ? "Пароль взламано - '" + correctKey + "'" : "Пароль надто важкий", "PASSWORD", JOptionPane.INFORMATION_MESSAGE);
            MyForm.getInctanse().jProgressBar1.setValue((int)all);
            MyForm.getInctanse().jLabel5.setText("100%/100%");
            if (MyForm.getInctanse().jCheckBox8.isSelected() && correctKey != null) {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("y.M.d HH:mm:ss");
                FileWork fw = new FileWork();
                fw.write("passwords.txt",  "[ " + correctKey + " ]"+ " : " + "[ " + PasswordMaintenance.getInctanse().getCachingPassword() + " ]" +  "(" +sdf.format(cal.getTime()) + ")\n");
            }
            if (MyForm.getInctanse().jCheckBox9.isSelected()) {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("y.M.d HH:mm:ss");
                MyForm.getInctanse().jTextPane1.setText(MyForm.getInctanse().jTextPane1.getText() + "\n" + "Спроба №" + exampleCheck + " Пароль: " + brootKey + " (" +sdf.format(cal.getTime()) + ")\n" + (correctKey != null ? "Пароль взламано: " + correctKey : "Пароль не взламано") + "\n");
                FileWork fw = new FileWork();
                fw.write("log.txt", "Спроба №" + exampleCheck + " Пароль: " + brootKey + " (" +sdf.format(cal.getTime()) + ")\n" + (correctKey != null ? "Пароль взламано: " + correctKey : "Пароль не взламано") + "\n");
            }
        });
            myThready.start();   
        }
    }
    

}


