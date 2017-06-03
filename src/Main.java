import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args)
    {
        try {
            CBC(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void CBC(String[] args) throws IOException {

        char[] byteIV = null, byteOutput, text = null, knownPlaintext = null, knownCipher = null;
        String outputPath = null, method = null,answerKey;
        HashMap<Character, Character> keyTable = null, reverseKeyTable = null;
        String encryptionType;
        int blockSize = -1;
        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(1) == 'a') {
                encryptionType = args[i + 1];
                 switch (encryptionType)
                 {
                     case "sub_cbc_10":
                        blockSize = 10;
                        break;
                     case "sub_cbc_52":
                         blockSize = 8128;
                        break;
                 }
            }
            if (args[i].charAt(1) == 'c') {
                method = (args[i + 1]);
            }
            if(args[i].charAt(1) == 't' && args[i].charAt(0) != 'a')
            {
                text=Utilities.readFile(args[i+1]);
            }
            if (args[i].charAt(1) == 'v') {
                byteIV = Utilities.readFile(args[i + 1]);
            }
            if ((args[i].charAt(1) == 'k')&&(args[i].length()<=2)) {
                reverseKeyTable = Utilities.initKeyTable(Utilities.readFile(args[i + 1]), false);
                keyTable = Utilities.initKeyTable(Utilities.readFile(args[i + 1]), true);
            }
            if ((args[i].charAt(1) == 'k')&&(args[i].length()>2)&& (args[i].charAt(2) == 'p')){
                knownPlaintext = Utilities.readFile(args[i + 1]);
            }
            if ((args[i].charAt(1) == 'k')&&(args[i].length()>2)&& (args[i].charAt(2) == 'c')){
                knownCipher = Utilities.readFile(args[i+1]);
            }
            if (args[i].charAt(1) == 'o') {
                outputPath = args[i + 1];
            }
        }
        Encrypter encrypter = new Encrypter(byteIV,blockSize);
        switch (method)
        {
            case "encryption":
                encrypter.setKeyTable(keyTable);
                byteOutput = encrypter.encrypt(text);
                Utilities.writeFile(outputPath, byteOutput);
                break;
            case "decryption":
                encrypter.setReverseKeyTable(reverseKeyTable);
                byteOutput = encrypter.decrypt(text);
                Utilities.writeFile(outputPath, Utilities.removePadding(byteOutput)); // need to do trim
                break;
            case "attack":
                String keySet;
                if(blockSize == 10) {
                    keySet = "abcdefgh";
                    answerKey = encrypter.attack(text,keySet,keySet);
                }
                else {
                    keySet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                    answerKey = encrypter.advancedAttack(text,knownPlaintext,knownCipher,keySet);
                }

                Utilities.writeKeyTable(outputPath,answerKey,keySet);
                break;
        }
    }
}