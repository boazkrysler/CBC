import java.util.*;

public class StatisticAnalysis {

    char[] english_freq = new char[]{'e','t','a','o','i','n','s','h','r','d','l','c','u','m','w','f','g','y','p','b','v','k','j','x','q','z',
                                     'T','S','A','M','C','I','N','B','R','P','E','D','H','W','L','O','F','Y','G','J','U','K','V','Q','X','Z'};
    private String unknown;
    private final String unknownMappedTo;
    public StatisticAnalysis(char[] cipherText,String unknown,String unknownMappedTo) {
        this.unknown = unknown;
        this.unknownMappedTo = unknownMappedTo;


    }
    public void bubbleSort(char[] charArray,int[] intArray)
    {
        for(int i=intArray.length-1;i>0;i--){ // NUMBER OF PASSES
            for(int j=0;j<i;j++){        // NUMBER OF COMPARISONS IN EACH PASS
                if(intArray[j]>intArray[j+1]){     // IF GREATER, SWAPPING J AND J+1 ELEMENT
                    char tempChar=charArray[j];
                    int tempInt = intArray[j];

                    charArray[j]=charArray[j+1];
                    intArray[j]=intArray[j+1];

                    charArray[j+1]=tempChar; // SWAPPING ELEMENTS
                    intArray[j+1]=tempInt;
                }
            } // END OF COMPARISION (j) LOOP
        } // END OF PASS (i) LOOP
    }

    public HashMap<Character,Character> analyse(char[] text,HashMap<Character,Character> statisticKey) {
        String cleanString = Utilities.StringCleanNonSense(new String(text));
        int[] charFreq = new int[unknown.length()]; //matches the unknown string place
        char[] unknownCharArray = unknown.toCharArray();
        for (int i = 0; i < cleanString.length(); i++) {
            int currIndex = unknown.indexOf(cleanString.charAt(i));
            if(0<=currIndex)
            {
                charFreq[currIndex]++;
            }
        }
        bubbleSort(unknownCharArray,charFreq);
        int j = unknownCharArray.length-1;
        for (int i = 0; i < english_freq.length; i++) {
            if(unknownMappedTo.indexOf(english_freq[i])>=0)
            {
                statisticKey.put(unknownCharArray[j],english_freq[i]);
                j--;
            }
        }
        return statisticKey;
    }
}

