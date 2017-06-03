import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by carme on 14/04/2017.
 */
public class CBCTest
{
    private final static String testFolderPath = "C:/Users/carme/Desktop/examples_ascii";
    @Test
    public void simpleTest() throws IOException {

        String[] args2 = {"-a", "sub_cbc_10", "-c","decryption",
                "–t", testFolderPath+"/examples/cipherMsg_example.txt",
                "–k", testFolderPath+"/examples/key_example.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/TTT.txt"};
        Main.CBC(args2);
        assertEquals(true,FileCompare(testFolderPath+"/examples/TTT.txt"
                ,testFolderPath+"/examples/plainMsg_example.txt"));

        new File(testFolderPath+"/TTT.txt").delete();
    }
    @Test
    public void simpleTest01() throws IOException {
        String[] args1 = {"-a", "sub_cbc_10", "-c", "encryption",
                "–t", testFolderPath+"/examples/Corpus/Alice.txt",
                "–k", testFolderPath+"/examples/key_example.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/TTT.txt"};
        Main.CBC(args1);

        String[] args2 = {"-a", "sub_cbc_10", "-c","decryption",
                "–t", testFolderPath+"/examples/TTT.txt",
                "–k", testFolderPath+"/examples/key_example.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/output01.txt"};
        Main.CBC(args2);
        assertEquals(true,FileCompare(testFolderPath+"/examples/output01.txt",
                testFolderPath+"/examples/Corpus/Alice.txt"));
    }
    @Test
    public void simpleTest02() throws IOException {
        String[] args1 = {"-a", "sub_cbc_10", "-c", "encryption",
                "–t", testFolderPath+"/examples/Corpus/Alice.txt",
                "–k", testFolderPath+"/examples/key_example.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/temp.txt"};
        Main.CBC(args1);

        String[] args2 = {"-a", "sub_cbc_10", "-c","decryption",
                "–t", testFolderPath+"/examples/temp.txt",
                "–k", testFolderPath+"/examples/key_example.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/output02.txt"};
        Main.CBC(args2);
        assertEquals(true,FileCompare(testFolderPath+"/examples/Corpus/Alice.txt",
                testFolderPath+"/examples/output02.txt"));

        new File(testFolderPath+"/temp.txt").delete();
        new File(testFolderPath+"/output02.txt").delete();
    }
    @Test
    public void simpleTest03() throws IOException {
        String[] args1 = {"-a", "sub_cbc_10", "-c", "encryption",
                "–t", testFolderPath+"/examples/Corpus/Tolstoy.txt",
                "–k", testFolderPath+"/examples/key_example.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/temp.txt"};
        Main.CBC(args1);

        String[] args2 = {"-a", "sub_cbc_10", "-c","decryption",
                "–t", testFolderPath+"/examples/temp.txt",
                "–k", testFolderPath+"/examples/key_example.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/output03.txt"};
        Main.CBC(args2);
        assertEquals(true,FileCompare(testFolderPath+"/examples/Corpus/Tolstoy.txt",
                testFolderPath+"/examples/output03.txt"));

        new File(testFolderPath+"/temp.txt").delete();
        new File(testFolderPath+"/output03.txt").delete();
    }
//    @Test
//    public void simpleTest04() throws IOException {
//        String[] args1 = {"-a", "sub_cbc_10", "-c", "encryption",
//                "–t", testFolderPath+"/examples/above128ascii.txt",
//                "–k", testFolderPath+"/examples/key_example.txt",
//                "–v", testFolderPath+"/examples/IV_example.txt",
//                "–o", testFolderPath+"/examples/temp.txt"};
//        Main.CBC(args1);
//
//        String[] args2 = {"-a", "sub_cbc_10", "-c", "decryption",
//                "–t", testFolderPath+"/examples/temp.txt",
//                "–k", testFolderPath+"/examples/key_example.txt",
//                "–v", testFolderPath+"/examples/IV_example.txt",
//                "–o", testFolderPath+"/examples/output04.txt"};
//        Main.CBC(args2);
//        assertEquals(true,FileCompare(testFolderPath+"/examples/above128ascii.txt",
//                testFolderPath+"/examples/output04.txt"));
//
//        new File(testFolderPath+"/temp.txt").delete();
//        new File(testFolderPath+"/output04.txt").delete();
//    }
    @Test
    public void simpleTest05() throws IOException {

        String[] args2 = {"-a", "sub_cbc_10", "-c","encryption",
                "–t", testFolderPath+"/examples/plainMsg_example.txt",
                "–k", testFolderPath+"/examples/key_example.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/TTT.txt"};
        Main.CBC(args2);
        assertEquals(true,FileCompare(testFolderPath+"/examples/TTT.txt"
                ,testFolderPath+"/examples/cipherMsg_example.txt"));

        new File(testFolderPath+"/TTT.txt").delete();
    }
    @Test
    public void partBTest01() throws IOException {
        String[] args2 = {"-a", "sub_cbc_10", "-c", "encryption",
                "–t", testFolderPath+"/examples/Corpus/Alice.txt",
                "–k", testFolderPath+"/key01.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/Temp01.txt"};
        Main.CBC(args2);
        String[] args1 = {"-a", "sub_cbc_10", "-c", "attack",
                "–t", testFolderPath+"/examples/Temp01.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/keytest01.txt"};
        Main.CBC(args1);
        assertEquals(true,FileCompare(testFolderPath+"/keytest01.txt",
                testFolderPath+"/key01.txt"));

        new File(testFolderPath+"/Temp01.txt").delete();
        new File(testFolderPath+"/keytest01.txt").delete();
    }
    @Test
    public void partBTest02() throws IOException {
        String[] args2 = {"-a", "sub_cbc_10", "-c", "encryption",
                "–t", testFolderPath+"/examples/Corpus/Alice.txt",
                "–k", testFolderPath+"/key02.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/Temp02.txt"};
        Main.CBC(args2);
        String[] args1 = {"-a", "sub_cbc_10", "-c", "attack",
                "–t", testFolderPath+"/examples/Temp02.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/keytest02.txt"};
        Main.CBC(args1);
        assertEquals(true,FileCompare(testFolderPath+"/keytest02.txt",
                testFolderPath+"/key02.txt"));
    }
    @Test
    public void partBMEGATest() throws IOException {
        String[] filename = {"Alice","Introducing_Science_Alan_Isaacs","Steam Steel and Electricity",
                "The Furnace of Gold","The Prince and Betty",
                "The Wonderful Wizard of OZ","Tolstoy"};
        String[] keys = {"key01","key02","key03","key04"};
        int counter = 0;
        for (int i = 0; i < filename.length; i++) {

            for (int j = 0; j < keys.length; j++) {
                String[] args2 = {"-a", "sub_cbc_10", "-c", "encryption",
                        "–t", testFolderPath+"/examples/Corpus/"+filename[i]+".txt",
                        "–k", testFolderPath+"/"+keys[j]+".txt",
                        "–v", testFolderPath+"/examples/IV_example.txt",
                        "–o", testFolderPath+"/examples/Temp"+String.valueOf(counter)+".txt"};
                Main.CBC(args2);
                String[] args1 = {"-a", "sub_cbc_10", "-c", "attack",
                        "–t", testFolderPath+"/examples/Temp"+String.valueOf(counter)+".txt",
                        "–v", testFolderPath+"/examples/IV_example.txt",
                        "–o", testFolderPath+"/keytest"+String.valueOf(counter)+".txt"};
                Main.CBC(args1);
                System.out.println("text is:"+ filename[i]+" and key is "+keys[j]);
                assertEquals(true,FileCompare(testFolderPath+"/keytest"+String.valueOf(counter)+".txt",
                        testFolderPath+"/"+keys[j]+".txt"));
                counter++;
                new File(testFolderPath+"/keytest"+String.valueOf(counter)+".txt").delete();
            }
        }

    }
    @Test
    public void partBTest04() throws IOException {
        String[] args2 = {"-a", "sub_cbc_10", "-c", "encryption",
                "–t", testFolderPath+"/examples/Corpus/Introducing_Science_Alan_Isaacs.txt",
                "–k", testFolderPath+"/key03.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/Temp04.txt"};
        Main.CBC(args2);
        String[] args1 = {"-a", "sub_cbc_10", "-c", "attack",
                "–t", testFolderPath+"/examples/Temp04.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/keytest04.txt"};
        Main.CBC(args1);
        assertEquals(true,FileCompare(testFolderPath+"/keytest04.txt",
                testFolderPath+"/key03.txt"));
    }
    @Test
    public void partBTest05() throws IOException {
        String[] args2 = {"-a", "sub_cbc_10", "-c", "encryption",
                "–t", testFolderPath+"/examples/Corpus/Steam Steel and Electricity.txt",
                "–k", testFolderPath+"/key02.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/Temp05.txt"};
        Main.CBC(args2);
        String[] args1 = {"-a", "sub_cbc_10", "-c", "attack",
                "–t", testFolderPath+"/examples/Temp05.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/keytest05.txt"};
        Main.CBC(args1);
        assertEquals(true,FileCompare(testFolderPath+"/keytest05.txt",
                testFolderPath+"/key02.txt"));
    }
    @Test
    public void partBTest06() throws IOException {
        String[] args2 = {"-a", "sub_cbc_10", "-c", "encryption",
                "–t", testFolderPath+"/examples/Corpus/The Furnace of Gold.txt",
                "–k", testFolderPath+"/key01.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/Temp06.txt"};
        Main.CBC(args2);
        String[] args1 = {"-a", "sub_cbc_10", "-c", "attack",
                "–t", testFolderPath+"/examples/Temp06.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/keytest06.txt"};
        Main.CBC(args1);
        assertEquals(true,FileCompare(testFolderPath+"/keytest06.txt",
                testFolderPath+"/key01.txt"));
    }
    @Test
    public void partBTest07() throws IOException {
        String[] args2 = {"-a", "sub_cbc_10", "-c", "encryption",
                "–t", testFolderPath+"/examples/Corpus/The Prince and Betty.txt",
                "–k", testFolderPath+"/key03.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/Temp07.txt"};
        Main.CBC(args2);
        String[] args1 = {"-a", "sub_cbc_10", "-c", "attack",
                "–t", testFolderPath+"/examples/Temp06.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/keytest06.txt"};
        Main.CBC(args1);
        assertEquals(true,FileCompare(testFolderPath+"/keytest06.txt",
                testFolderPath+"/key01.txt"));
    }
    @Test
    public void partBTest08() throws IOException {
        String[] args2 = {"-a", "sub_cbc_10", "-c", "encryption",
                "–t", testFolderPath+"/examples/Corpus/Alice.txt",
                "–k", testFolderPath+"/key03.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/Temp08.txt"};
        Main.CBC(args2);
        String[] args1 = {"-a", "sub_cbc_10", "-c", "attack",
                "–t", testFolderPath+"/examples/Temp08.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/keytest08.txt"};
        Main.CBC(args1);
        assertEquals(true,FileCompare(testFolderPath+"/keytest08.txt",
                testFolderPath+"/key03.txt"));
    }
    @Test
    public void partBTest09() throws IOException {
        String[] args2 = {"-a", "sub_cbc_10", "-c", "encryption",
                "–t", testFolderPath+"/examples/Corpus/Alice.txt",
                "–k", testFolderPath+"/key01.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/examples/Temp09.txt"};
        Main.CBC(args2);
        String[] args1 = {"-a", "sub_cbc_10", "-c", "attack",
                "–t", testFolderPath+"/examples/Temp09.txt",
                "–v", testFolderPath+"/examples/IV_example.txt",
                "–o", testFolderPath+"/keytest09.txt"};
        Main.CBC(args1);
        assertEquals(true,FileCompare(testFolderPath+"/keytest09.txt",
                testFolderPath+"/key01.txt"));
    }
    @Test
    public void partBTestodd() throws IOException {
        String[] args1 = {"-a", "sub_cbc_52", "-c", "decryption",
                "–t", testFolderPath+"/PartC/unknown_cipher2.txt",
                "–v", testFolderPath+"/PartC/IV_long.txt",
                "–k", testFolderPath+"/PartC/key_long.txt",
                "–o", testFolderPath+"/PartC/plainTest2.txt"};
        Main.CBC(args1);
    }
    @Ignore
    @Test
    public void partBTestodd2() throws IOException {
        String[] args1 = {"-a", "sub_cbc_10", "-c", "decryption",
                "–t", testFolderPath+"/PartB/cipher.txt",
                "–v", testFolderPath+"/PartB/IV_short.txt",
                "–k", testFolderPath+"/PartB/key_short.txt",
                "–o", testFolderPath+"/PartB/testplain.txt"};
        Main.CBC(args1);

        //assertEquals(true, FileCompare(testFolderPath+"/PartC/knowCipherTEST.txt",
        //testFolderPath+"/PartC/known_cipher.txt"));
    }
    @Test
    public void partBTestodd3() throws IOException {
        String[] args2 = {"-a", "sub_cbc_52", "-c", "encryption",
                "–t", testFolderPath+"/PartC/known_plain_long.txt",
                "–v", testFolderPath+"/PartC/IV_long.txt",
                "–k", testFolderPath+"/PartC/key_long.txt",
                "–o", testFolderPath+"/PartC/knowCipherTEST.txt"};
        Main.CBC(args2);

        String[] args1 = {"-a", "sub_cbc_52", "-c", "decryption",
                "–t", testFolderPath+"/PartC/knowCipherTEST.txt",
                "–v", testFolderPath+"/PartC/IV_long.txt",
                "–k", testFolderPath+"/PartC/key_long.txt",
                "–o", testFolderPath+"/PartC/knowPlaintextTEST.txt"};
        Main.CBC(args1);
        assertEquals(true, FileCompare(testFolderPath+"/PartC/knowPlaintextTEST.txt",
                testFolderPath+"/PartC/known_plain_long.txt"));
    }

    @Test
    public void partBwordChecker() throws IOException {
        Dictionary d = new Dictionary();
        System.out.println(d.contains("her"));
        System.out.println(d.contains("adventures"));
    }
//    @Test
//    public void statisticChecker() throws IOException {
//        StatisticAnalysis s = new StatisticAnalysis(null,"","","","");
//        LinkedHashMap<Integer,String> characterCount =StatisticAnalysis.characterCount("aaba EsE c b J ssssess");
//        System.out.println("yo");
//    }
    @Test
    public void partBTest() throws IOException {
        String[] args1 = {"-a", "sub_cbc_10", "-c", "attack",
                "–t", testFolderPath + "/PartB/cipher.txt",
                "–v", testFolderPath + "/PartB/IV_short.txt",
                "–o", testFolderPath + "/PartB/TestKey.txt"};
        Main.CBC(args1);
        assertEquals(true, FileCompare(testFolderPath + "/PartB/TestKey.txt",
                testFolderPath + "/PartB/key_short.txt"));
    }
    @Test
    public void longTest() throws IOException {
        String[] args1 = {"-a", "sub_cbc_52", "-c", "encryption",
                "–t", testFolderPath+"/PartC/known_plain_long.txt",
                "–k", testFolderPath+"/PartC/key_long.txt",
                "–v", testFolderPath+"/PartC/IV_long.txt",
                "–o", testFolderPath+"/examples/test-encrypt.txt"};
        Main.CBC(args1);

        String[] args2 = {"-a", "sub_cbc_52", "-c", "decryption",
                "–t", testFolderPath+"/examples/test-encrypt.txt",
                "–k", testFolderPath+"/PartC/key_long.txt",
                "–v", testFolderPath+"/PartC/IV_long.txt",
                "–o",testFolderPath+"/examples/test-decrypt.txt"};
        Main.CBC(args2);
        assertEquals(true,FileCompare(testFolderPath+"/PartC/known_plain_long.txt",
                testFolderPath+"/examples/test-decrypt.txt"));
    }
    @Test
    public void partCEncDec() throws IOException {
        String[] filename = {"Alice","Introducing_Science_Alan_Isaacs","Steam Steel and Electricity",
                "The Furnace of Gold","The Prince and Betty",
                "The Wonderful Wizard of OZ","Tolstoy"};
        for (String aFilename : filename) {
            String[] args1 = {"-a", "sub_cbc_52", "-c", "encryption",
                    "–t", testFolderPath + "/examples/Corpus/" + aFilename + ".txt",
                    "–k", testFolderPath + "/PartC/key_long.txt",
                    "–v", testFolderPath + "/PartC/IV_long.txt",
                    "–o", testFolderPath + "/examples/test-encrypt.txt"};
            Main.CBC(args1);

            String[] args2 = {"-a", "sub_cbc_52", "-c", "decryption",
                    "–t", testFolderPath + "/examples/test-encrypt.txt",
                    "–k", testFolderPath + "/PartC/key_long.txt",
                    "–v", testFolderPath + "/PartC/IV_long.txt",
                    "–o", testFolderPath + "/examples/test-encrypt01.txt"};
            Main.CBC(args2);
            assertEquals(true, FileCompare(testFolderPath + "/examples/Corpus/"+aFilename+".txt",
                    testFolderPath + "/examples/test-encrypt01.txt"));
        }
        new File(testFolderPath+"/temp.txt").delete();
        new File(testFolderPath+"/examples/test-encrypt01.txt").delete();
    }
    @Test
    public void partCattack01() throws IOException {
        String[] args1 = {"-a", "sub_cbc_52", "-c", "attack",
                "–t", testFolderPath + "/PartC/unknown_cipher1.txt",
                "–kp", testFolderPath + "/PartC/known_plain_long.txt",
                "–kc", testFolderPath + "/PartC/known_cipher.txt",
                "–v", testFolderPath + "/PartC/IV_long.txt",
                "–o", testFolderPath + "/PartC/test-partC.txt"};
        Main.CBC(args1);
        assertEquals(true, FileCompare(
                testFolderPath + "/PartC/test-partC.txt",
                testFolderPath + "/PartC/key_long-Organized.txt"));
    }
    @Test
    public void partCattack02() throws IOException {
        String[] args1 = {"-a", "sub_cbc_52", "-c", "attack",
                "–t", testFolderPath + "/PartC/unknown_cipher2.txt",
                "–kp", testFolderPath + "/PartC/known_plain_long.txt",
                "–kc", testFolderPath + "/PartC/known_cipher.txt",
                "–v", testFolderPath + "/PartC/IV_long.txt",
                "–o", testFolderPath + "/examples/test-partC.txt"};
        Main.CBC(args1);
        assertEquals(true, FileCompare(testFolderPath + "/examples/test-partC.txt",
                testFolderPath + "/PartC/key_long-Organized.txt"));
        new File(testFolderPath + "/examples/test-partC.txt").delete();
    }
    @Test
    public void partCattack03() throws IOException {
        String[] args1 = {"-a", "sub_cbc_52", "-c", "decryption",
                "–t", testFolderPath + "/PartC/known_cipher.txt",
                "–k", testFolderPath + "/PartC/test-partC.txt",
                "–v", testFolderPath + "/PartC/IV_long.txt",
                "–o", testFolderPath + "/PartC/test-partC-odd.txt"};
        Main.CBC(args1);
        assertEquals(true, FileCompare(
                testFolderPath + "/PartC/test-partC-odd.txt",
                testFolderPath + "/PartC/known_plain_long.txt"));
    }
//    @Test
//    public void partC3attack01() throws IOException {
//        String[] args1 = {"-a", "sub_cbc_52", "-c", "attack",
//                "–t", testFolderPath + "/PartC3/unknown_cipher1.txt",
//                "–kp", testFolderPath + "/PartC3/known_plain_long.txt",
//                "–kc", testFolderPath + "/PartC3/known_cipher.txt",
//                "–v", testFolderPath + "/PartC3/IV_long.txt",
//                "–o", testFolderPath + "/PartC3/test-partC.txt"};
//        Main.CBC(args1);
//        assertEquals(true, FileCompare(
//                testFolderPath + "/PartC3/test-partC.txt",
//                testFolderPath + "/PartC3/key_long-Organized.txt"));
//    }
    @Test
    public void partBTestodd4() throws IOException {
        String[] args1 = {"-a", "sub_cbc_52", "-c", "encryption",
                "–t", testFolderPath+"/PartC3/known_plain_long.txt",
                "–v", testFolderPath+"/PartC3/IV_long.txt",
                "–k", testFolderPath+"/PartC3/key_long.txt",
                "–o", testFolderPath+"/PartC3/known_cipher.txt"};
        Main.CBC(args1);

    }
    @Test
    public void partC2attack02() throws IOException {
        String[] args1 = {"-a", "sub_cbc_52", "-c", "attack",
                "–t", testFolderPath + "/PartC2/unknown_cipher.txt",
                "–kp", testFolderPath + "/PartC2/known_plain_long.txt",
                "–kc", testFolderPath + "/PartC2/known_cipher.txt",
                "–v", testFolderPath + "/PartC2/IV_long.txt",
                "–o", testFolderPath + "/PartC2/test-partC.txt"};
        Main.CBC(args1);
        assertEquals(true, FileCompare(testFolderPath + "/PartC2/test-partC.txt",
                testFolderPath + "/PartC2/key_long-Organized.txt"));
        new File(testFolderPath + "/examples/test-partC.txt").delete();
    }
//    @Test
//    public void permuatationChecker() throws IOException {
//        String perm1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
//        String perm2= "ABCDEFGH";
//
//        int row = 1;
//
//        for (PermutationIterative myperm = new PermutationIterative(perm1); myperm.hasNext(); ) {
//            String permutation = myperm.next();
//            System.out.printf("%2d: %s\n", row++, permutation);
//        }
//    }
    @Test
    public void mightBeInEnglishTest() {
        Dictionary d = new Dictionary();
        assertEquals(true,(d.mightBeInEnglish("THIS                                         DIRECT AND LIVELY BOOK perf orms a genuine \n" +
                "service to the reader eager to know what modern \n" +
                "science is all about. \n" +
                "\n" +
                "Organized around two central concepts matter \n" +
                "and energy Introducing Science uses no \"scien- \n" +
                "tific\" word without first explaining it and requires \n" +
                "no prior knowledge of mathematics. It makes \n" +
                "easily comprehensible the most recent advances \n" +
                "i dont understand if this works please!\n" +
                "please tell me it does,\n" +
                "i really hope it does, however i am willing to work hard to fix it\n" +
                "but only if needed.\n" +
                "why does add spaces?? this is sooo odd..        cant you see the spaces?\n" +
                "THIS DIRECT AND LIVELY BOOK perf orms a genuine \n" +
                "service to the reader eager to know what modern \n" +
                "science is all about. \n" +
                "\n" +
                "Organized around two central concepts matter \n" +
                "and energy Introducing Science uses no \"scien- \n" +
                "tific\" word without first explaining it and requires \n" +
                "no prior knowledge of mathematics. It makes \n" +
                "easily comprehensible the most recent advances \n" +
                "i dont understand if this works please!\n" +
                "please tell me it does,\n" +
                "i really hope it does, however i am willing to work hard to fix it\n" +
                "but only if needed.\n" +
                "why does add spaces?? this is sooo odd..        cant you see the spaces?\n" +
                "THIS DIRECT AND LIVELY BOOK perf orms a genuine \n" +
                "service to the reader eager to know what modern \n" +
                "science is all about. \n" +
                "\n" +
                "Organized around two central concepts matter \n" +
                "and energy Introducing Science uses no \"scien- \n" +
                "tific\" word without first explaining it and requires \n" +
                "no prior knowledge of mathematics. It makes \n" +
                "easily comprehensible the most recent advances \n" +
                "i dont understand if this works please!\n" +
                "please tell me it does,\n" +
                "i really hope it does, however i am willing to work hard to fix it\n" +
                "but only if needed.\n" +
                "why does add spaces?? this is sooo odd..        cant you see the spaces?\n" +
                "THIS DIRECT AND LIVELY BOOK perf orms a genuine \n" +
                "service to the reader eager to know what modern \n" +
                "science is all about. \n" +
                "\n" +
                "Organized around two central concepts matter \n" +
                "and energy Introducing Science uses no \"scien- \n" +
                "tific\" word without first explaining it and requires \n" +
                "no prior knowledge of mathematics. It makes \n" +
                "easily comprehensible the most recent advances \n" +
                "i dont understand if this works please!\n" +
                "please tell me it does,\n" +
                "i really hope it does, however i am willing to work hard to fix it\n" +
                "but only if needed.\n" +
                "why does add spaces?? this is sooo odd..        cant you see the spaces?\n" +
                "THIS DIRECT AND LIVELY BOOK perf orms a genuine \n" +
                "service to the reader eager to know what modern \n" +
                "science is all about. \n" +
                "\n" +
                "Organized around two central concepts matter \n" +
                "and energy Introducing Science uses no \"scien- \n" +
                "tific\" word without first explaining it and requires \n" +
                "no prior knowledge of mathematics. It makes \n" +
                "easily comprehensible the most recent advances \n" +
                "i dont understand if this works please!\n" +
                "please tell me it does,\n" +
                "i really hope it does, however i am willing to work hard to fix it\n" +
                "but only if needed.\n" +
                "why does add spaces?? this is sooo odd..        cant you see the spaces?\n" +
                "THIS DIRECT AND LIVELY BOOK perf orms a genuine \n" +
                "service to the reader eager to know what modern \n" +
                "science is all about. \n" +
                "\n" +
                "Organized around two central concepts matter \n" +
                "and energy Introducing Science uses no \"scien- \n" +
                "tific\" word without first explaining it and requires \n" +
                "no prior knowledge of mathematics. It makes \n" +
                "easily comprehensible the most recent advances \n" +
                "i dont understand if this works please!\n" +
                "please tell me it does,\n" +
                "i really hope it does, however i am willing to work hard to fix it\n" +
                "but only if needed.\n" +
                "why does add spaces?? this is sooo odd..        cant you see the spaces?\n" +
                "THIS DIRECT AND LIVELY BOOK perf orms a genuine \n" +
                "service to the reader eager to know what modern \n" +
                "science is all about. \n" +
                "\n" +
                "Organized around two central concepts matter \n" +
                "and energy Introducing Science uses no \"scien- \n" +
                "tific\" word without first explaining it and requires \n" +
                "no prior knowledge of mathematics. It makes \n" +
                "easily comprehensible the most recent advances \n" +
                "i dont understand if this works please!\n" +
                "please tell me it does,\n" +
                "i really hope it does, however i am willing to work hard to fix it\n" +
                "but only if needed.\n" +
                "why does add spaces?? this is sooo odd..        cant you see the spaces?\n" +
                "THIS DIRECT AND LIVELY BOOK perf orms a genuine \n" +
                "service to the reader eager to know what modern \n" +
                "science is all about. \n" +
                "\n" +
                "Organized around two central concepts matter \n" +
                "and energy Introducing Science uses no \"scien- \n" +
                "tific\" word without first explaining it and requires \n" +
                "no prior knowledge of mathematics. It makes \n" +
                "easily comprehensible the most recent advances \n" +
                "i dont understand if this works please!\n" +
                "please tell me it does,\n" +
                "i really hope it does, however i am willing to work hard to fix it\n" +
                "but only if needed.\n" +
                "why does add spaces?? this is sooo odd..        cant you see the spaces?\n" +
                "THIS DIRECT AND LIVELY BOOK perf orms a genuine \n" +
                "service to the reader eager to know what modern \n" +
                "science is all about. \n" +
                "\n" +
                "Organized around two central concepts matter \n" +
                "and energy Introducing Science uses no \"scien- \n" +
                "tific\" word without first explaining it and requires \n" +
                "no prior knowledge of mathematics. It makes \n" +
                "easily comprehensible the most recent advances \n" +
                "i dont understand if this works please!\n" +
                "please tell me it does,\n" +
                "i really hope it does, however i am willing to work hard to fix it\n" +
                "but only if needed.\n" +
                "why does add spaces?? this is sooo odd..        cant you see the spaces?\n" +
                "THIS DIRECT AND LIVELY BOOK perf orms a genuine \n" +
                "service to the reader eager to know what modern \n" +
                "science is all about. \n" +
                "\n" +
                "Organized around two central concepts matter \n" +
                "and energy Introducing Science uses no \"scien- \n" +
                "tific\" word without first explaining it and requires \n" +
                "no prior knowledge of mathematics. It makes \n" +
                "easily comprehensible the most recent advances \n" +
                "i dont understand if this works please!\n" +
                "please tell me it does,\n" +
                "i really hope it does, however i am willing to work hard to fix it\n" +
                "but only if needed.\n" +
                "why does add spaces?? this is sooo odd..        cant you see the spaces?\n" +
                "THIS DIRECT AND LIVELY BOOK perf orms a genuine \n" +
                "service to the reader eager to know what modern \n" +
                "science is all about. \n" +
                "\n" +
                "Organized around two central concepts matter \n" +
                "and energy Introducing Science uses no \"scien- \n" +
                "tific\" word without first explaining it and requires \n" +
                "no prior knowledge of mathematics. It makes \n" +
                "easily comprehensible the most recent advances \n" +
                "i dont understand if this works please!\n" +
                "please tell me it does,\n" +
                "i really hope it does, however i am willing to work hard to fix it\n" +
                "but only if needed.\n" +
                "why does add spaces?? this is sooo odd..        cant you see the spaces?\n" +
                "THIS DIRECT AND LIVELY BOOK perf orms a genuine \n" +
                "service to the reader eager to know what modern \n" +
                "science is all about. \n" +
                "\n" +
                "Organized around two central concepts matter \n" +
                "and energy Introducing Science uses no \"scien- \n" +
                "tific\" word without first explaining it and requires \n" +
                "no prior knowledge of mathematics. It makes \n" +
                "easily comprehensible the most recent advances \n" +
                "i dont understand if this works please!\n" +
                "please tell me it does,\n" +
                "i really hope it does, however i am willing to work hard to fix it\n" +
                "but only if needed.\n" +
                "why does add spaces?? this is sooo odd..        cant you see the spaceSSSSSSSSSs?\n",0.9,10)));
    }
    private boolean FileCompare(String path1, String path2) throws IOException {
        String s1 = "", s2;
        String s3 = "", s4;

        File file1 = new File(path1);
        File file2 = new File(path2);

        BufferedReader bfr = new BufferedReader(new FileReader(file1));
        BufferedReader bfr1 = new BufferedReader(new FileReader(file2));

        while ((s4 = bfr1.readLine()) != null)
            s3 += s4;

        while ((s2 = bfr.readLine()) != null)
            s1 += s2;
        bfr.close();
        bfr.close();
        if (s3.equals(s1)) {
            return true;
        } else {
            return false;
        }
    }
}