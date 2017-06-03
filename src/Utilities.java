import com.sun.xml.internal.fastinfoset.util.CharArrayArray;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by carme on 14/04/2017.
 */
//All the read / write functions
class Utilities {
    static private int permutationCounter = 0;
    static char[] padding(char[] text,int blockSize) {
        char[] textPadded;
        if (text.length % blockSize != 0) {
            textPadded = new char[(text.length / blockSize + 1) * blockSize];
            for (int i = 0; i < text.length; i++) {
                textPadded[i] = text[i];
            }
            for (int i = text.length; i < (text.length / blockSize + 1) * blockSize; i++) {
                textPadded[i] = 0;
            }
        } else textPadded = text;

        return textPadded;
    }
    static void writeKeyTable(String path,String keyMappedTo,String keyChar) throws IOException {
        StringBuilder keyToFile = new StringBuilder();
        for (int i = 0; i < keyChar.length(); i++)
        {
            keyToFile.append(keyChar.charAt(i));
            keyToFile.append(" ");
            keyToFile.append(keyMappedTo.charAt(i));
            keyToFile.append(((char)10)+"");
        }
        int trimLastNewLineIndex = (keyToFile.length())-((((char)10)+"").length());
        writeFile(path,keyToFile.substring(0,trimLastNewLineIndex).toCharArray());
    }
    static void writeFile(String path, char[] output) throws IOException {
        Files.write(Paths.get(path),new String(output).getBytes());
    }
    static char[] readFile(String path) throws IOException {
        return (new String(Files.readAllBytes(Paths.get(path)),"UTF-8")).toCharArray();
    }
    static String[] permutation(String str) {
        permutationCounter = 0;
        String[] permu = new String[factorial(str.length())];
        return permutation("", str, permu);
    }
    private static String[] permutation(String prefix, String str, String[] permu) {
        int n = str.length();
        if (n == 0)
        {
            permu[permutationCounter] = prefix;
            permutationCounter++;
        }
        else
        {
            for (int i = 0; i < n; i++) {
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), permu);
            }
        }
        return permu;
    }
    private static int factorial(int n) {
        int fact = 1; // this  will be the result
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
    static HashMap<Character,Character> initKeyTableAttack(HashMap<Character,Character> keyTable,char[] permuToMap, char[] key) {
        for (int i=0; i<permuToMap.length; i++)
        {
            keyTable.put(permuToMap[i],key[i]);
        }
        return keyTable;
    }
    static HashMap<Character, Character> initKeyTable(char[] keyByte, boolean encrypt) {
        HashMap<Character,Character> keyTable = new HashMap<Character, Character>();
        for (int i = 0; i < keyByte.length - 2;) {
            if (encrypt)
                keyTable.put(keyByte[i],keyByte[i + 2]);
            else
                keyTable.put(keyByte[i + 2],keyByte[i]);
            i=i+3;
            while((i<keyByte.length)&&((keyByte[i]=='\n')||(keyByte[i]=='\r')))
                i++;
        }
        return keyTable;
    }
    public static String StringCleanNonSense(String text) {
        text = text.replaceAll("([A-Z][a-z]+)|([0-9]*)|([a-zA-Z]*[.])]","");
        text = text.replaceAll("[-\",.()'!?/:#\\[\\]&`~\"“”’‘”— ]","");
        text = text.replaceAll("\r\n|\r|\n"," ");
        text.trim().replaceAll(" +", " ");
        return text;
    }
    public static char[] removePadding(char[] cipherTextPadded) {
        int i = cipherTextPadded.length-1;
        while (i > 0 && cipherTextPadded[i] == 0) {
            i--;
        }
        char[] cipherText = new char[i+1];
        System.arraycopy(cipherTextPadded,0,cipherText,0,i+1);
        return cipherText;
    }
}
