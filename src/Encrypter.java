import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.util.HashMap;

/**
 * Created by carme on 14/04/2017.
 */
public class Encrypter {
    private int blockSize;
    private char[] vector;
    private final char[] initVector;
    private HashMap<Character, Character> keyTable;
    private HashMap<Character, Character> reverseKeyTable;
    private StringBuilder unknownCharSet;
    private StringBuilder unknownCharSetCanBeMappedTo;

    public Encrypter(char[] byteIV,int blockSize){
        if(blockSize < 0)
        {
            try {
                throw new Exception("BlockSize was not initialized");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.blockSize = blockSize;
        this.initVector = byteIV;
        this.vector = initVector.clone();
        this.keyTable = new HashMap<>();
        this.reverseKeyTable = new HashMap<>();
    }
    public void setKeyTable(HashMap<Character, Character> keyTable) {
        this.keyTable = keyTable;
    }
    public char[] encrypt(char[] plainText) {
        System.arraycopy(initVector,0,vector,0,blockSize);
        char[] plainTextPadded = Utilities.padding(plainText,blockSize);
        char[] subText = new char[blockSize];
        for (int i = 0; i < plainTextPadded.length/blockSize; i++) {
            System.arraycopy(plainTextPadded,i*blockSize,subText,0,blockSize);
            vector = xor(subText,vector);
            vector = BlockCipherKeySubstitute(vector,keyTable);
            System.arraycopy(vector,0,plainTextPadded,i*blockSize,blockSize);
        }
        return plainTextPadded;
    }
    public char[] decrypt(char[] cipherText) {
        System.arraycopy(initVector,0,vector,0,blockSize);
        char[] subText = new char[blockSize];
        char[] subTextUnTocuhed = new char[blockSize];
        char[] cipherTextPadded = Utilities.padding(cipherText,blockSize);
        for (int i = 0; i < cipherTextPadded.length/blockSize; i++) {
            System.arraycopy(cipherTextPadded,i*blockSize,subTextUnTocuhed,0,blockSize);
            System.arraycopy(subTextUnTocuhed,0,subText,0,blockSize);
            subText = BlockCipherKeySubstitute(subText,reverseKeyTable);
            subText = xor(subText,vector);
            System.arraycopy(subText,0,cipherTextPadded,i*blockSize,blockSize);
            System.arraycopy(subTextUnTocuhed,0,vector,0,blockSize);
        }
        return cipherTextPadded;
    }
    public String attack(char[] cipherText, String keyChars,String keyCharsMappedTo) throws UnsupportedEncodingException {
        char[] cipherTextPadded;
        Dictionary dic = new Dictionary();
        cipherTextPadded = Utilities.padding(cipherText,blockSize);
     //  String[] permu = Utilities.permutation(keyCharsMappedTo);
        PermutationIterative permu;
        StringBuilder cipherSubstring = new StringBuilder();
        cipherSubstring.append(cipherTextPadded);
       // String[] permu = {"faghbdce"}; //key 03
        // String[] permu = {"cdefghab"}; //key 02
      //   String[] permu = {"bcdefgha"}; //key 01
        //    String[] permu = {"cgedfhab"}; //key for partB
        for(double flexibility = 0.96;flexibility>=0.5;flexibility-= 0.05)
        {
            for (permu = new PermutationIterative(keyCharsMappedTo); permu.hasNext();)
            {
                char[] currPermu = permu.next();
                setReverseKeyTable( Utilities.initKeyTableAttack(reverseKeyTable,currPermu,keyChars.toCharArray()));
                int size = Math.min(cipherTextPadded.length,1000);
                while(size<cipherTextPadded.length)
                {
                    String mightBePlainText = new String (decrypt (byteArraySubSet(cipherTextPadded,0,size)));
                    if (!dic.mightBeInEnglish(mightBePlainText,flexibility,10))
                        break;
                    size+=size;
                }
                if ((size>=cipherTextPadded.length)&&(dic.mightBeInEnglish(new String(decrypt(cipherTextPadded.clone())),flexibility,10)))
                {
                    return new String(currPermu);
                }
            }
        }
        return null; //failed
    }
    public String advancedAttack(char[] cipherText,char[] knownplainText,char[] knowncipher, String keyChars) throws UnsupportedEncodingException, CharacterCodingException {
        knownPlainTextAttack(knownplainText,knowncipher,keyChars);
        char[] answer = new char[keyChars.length()];
        answer = addCharToAnswer(answer,keyChars);
        int[] answerPosition =AdvanceAttackStatus(answer,keyChars);
        if((unknownCharSet.length()<9)&&(unknownCharSet.length()>0))
        {
            answer = BridgeAdvanceAttacktoAttack(cipherText,answer,answerPosition);
        }
        else if(unknownCharSet.length()>=9)
        {
            PaddingVulnerability(cipherText);
            addCharToAnswer(answer,keyChars);
            answerPosition = AdvanceAttackStatus(answer,keyChars);
            if((unknownCharSet.length()<=9)&&(unknownCharSet.length()>0))
            answer = BridgeAdvanceAttacktoAttack(cipherText,answer,answerPosition);
            else {
                StatisticAnalysis statistic = new StatisticAnalysis(cipherText, unknownCharSet.toString(), unknownCharSetCanBeMappedTo.toString());
                char[] blockSizeCipher = new char[blockSize];
                System.arraycopy(cipherText, 0, blockSizeCipher, 0, blockSize);
                keyTable = statistic.analyse(decrypt(blockSizeCipher), keyTable);
                answer = addCharToAnswer(answer, keyChars);
            }
        }
        return new String(answer);
    }
    private char[] BridgeAdvanceAttacktoAttack(char[] cipherText,char[] answer,int[] answerPosition) throws UnsupportedEncodingException {
        String charSet = attack(cipherText, unknownCharSet.toString(),unknownCharSetCanBeMappedTo.toString());
        for (int i = 0; i < unknownCharSet.length(); i++) {
            answer[answerPosition[i]] = charSet.charAt(i);
        }
        return answer;
    }
    private int[] AdvanceAttackStatus(char[] answer,String keyChars)
    {
        int[] answerPosition = new int[answer.length];
        int counter = 0;
        unknownCharSet = new StringBuilder();
        unknownCharSetCanBeMappedTo = new StringBuilder();
        unknownCharSetCanBeMappedTo.append(keyChars);
        for (int i = 0; i < keyChars.length(); i++) {
            if(answer[i]==0)
            {
                unknownCharSet.append(keyChars.charAt(i));
                answerPosition[counter] = i;
                counter++;
            }
            else
            {
                unknownCharSetCanBeMappedTo.deleteCharAt(unknownCharSetCanBeMappedTo.indexOf(String.valueOf(keyTable.get(keyChars.charAt(i)))));
            }
        }
        return answerPosition;
    }
    private void PaddingVulnerability(char[] cipherText) {
        char[] vulnerabilityChars;
        int vulnerabilityIndex;
        boolean logicError = false;
        if(cipherText.length/blockSize>=1)
        {
            vulnerabilityIndex = cipherText.length-1-blockSize;
            vulnerabilityChars = cipherText;
        }
        else
        {
            vulnerabilityIndex = initVector.length-1;
            vulnerabilityChars = initVector;
        }
        for (int i = cipherText.length-1; (i%blockSize > 0)&&(!logicError); i--) {
            char cipherChar =cipherText[i];
            char vulnerabilityChar = vulnerabilityChars[vulnerabilityIndex];
            if(cipherChar!=vulnerabilityChar)
            {
                if((Character.isAlphabetic(cipherChar))&&(Character.isAlphabetic(vulnerabilityChar)))
                {
                    if((!keyTable.containsKey(vulnerabilityChar))&&(!keyTable.containsValue(cipherChar))) {
                        keyTable.put(vulnerabilityChar, cipherChar);
                        reverseKeyTable.put(cipherChar,vulnerabilityChar);
                    }
                    else if(keyTable.get(vulnerabilityChar)!=cipherChar)
                        logicError = true;
                }
                else
                {
                    logicError = true;
                }
            }
            vulnerabilityIndex--;
        }
    }


    private void knownPlainTextAttack(char[] knownPlaintext, char[] knownCipher, String charSet) throws UnsupportedEncodingException {
        System.arraycopy(initVector,0,vector,0,blockSize);
        char[] plainTextPadded = Utilities.padding(knownPlaintext,blockSize);
        char[] subText = new char[blockSize];
        for (int i = 0; i < plainTextPadded.length/blockSize; i++) {
            System.arraycopy(plainTextPadded,i*blockSize,subText,0,blockSize);
            vector = xor(subText,vector);
            BlockCipherFindKey(vector,knownCipher,charSet);
            vector = BlockCipherKeySubstitute(vector,keyTable);
            System.arraycopy(vector,0,plainTextPadded,i*blockSize,blockSize);
        }
    }
    private void BlockCipherFindKey(char[] xorPlainText, char[] knownCipher,String charSet) throws UnsupportedEncodingException {
        int minLength = Math.min(Math.min(blockSize,knownCipher.length),xorPlainText.length);
        for (int i = 0; i < minLength; i++) {
            if ((charSet.indexOf(xorPlainText[i])>=0)&&(charSet.indexOf(knownCipher[i])>=0)&&(!keyTable.containsKey(xorPlainText[i]))&&(!keyTable.containsValue(knownCipher[i]))) {
                keyTable.put(xorPlainText[i],knownCipher[i]);
                reverseKeyTable.put(knownCipher[i],xorPlainText[i]);
            }
        }
    }
    private char[] addCharToAnswer(char[] answer,String charset) {
        for (int i = 0; i < charset.length(); i++) {
            if(keyTable.containsKey(charset.charAt(i)))
            {
                answer[i] = keyTable.get(charset.charAt(i));
            }
        }
        return answer;
    }
//    private static byte[] xor(byte[] a, byte[] b) {
//        byte[] result = new byte[Math.min(a.length, b.length)];
//        for (int i = 0; i < result.length; i++) {
//            result[i] = (byte) (0xFF&(a[i]^ b[i]));
//        }
//        return result;
//    }
    private static char[] xor(char[] a, char[] b) {
        int minLength = Math.min(a.length, b.length);
        char[] result = new char[minLength];
        for (int i = 0; i < minLength; i++) {
            result[i] = (char) ((int)a[i]^ (int)b[i]);
        }
        return result;
    }
    private static char[] BlockCipherKeySubstitute(char[] text, HashMap<Character, Character> key) {
        for (int i = 0; i < text.length; i++) {
            if (key.containsKey(text[i])) {
                text[i] = key.get(text[i]);
            }
        }
        return text;
    }
    private static char[] byteArraySubSet(char[] byteArray, int startIndex, int EndIndex) {
        char[] subSet = new char[EndIndex - startIndex];
        System.arraycopy(byteArray,startIndex,subSet,0,subSet.length);
        return subSet;
    }
    public void setReverseKeyTable(HashMap<Character,Character> reverseKeyTable) {
        this.reverseKeyTable = reverseKeyTable;
    }
}
