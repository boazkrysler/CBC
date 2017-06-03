import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Dictionary
{
    private Set<String> wordsSet;
    public Dictionary() {
        Path path = Paths.get("words.txt");
        byte[] readBytes = new byte[0];
        try {
            readBytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String wordListContents = null;
        try {
            wordListContents = new String(readBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] words = wordListContents.split("\n");
        wordsSet = new HashSet<>();
        Collections.addAll(wordsSet, words);
    }
    public boolean contains(String word)
    {
        return wordsSet.contains(word);
    }
    public boolean mightBeInEnglish(String text,double correctnessflexibility,double minWordsDensity) {
        double mistakenWordsCounter = 0;
        double totalWordsCounter = 30;
        double mistakeRatioAllowed = 1 - correctnessflexibility;
        text = text.replaceAll("([A-Z][a-z]+)|([0-9]*)|([a-zA-Z]*[.])]","");
        text = text.toLowerCase();
        text = text.replaceAll("[-\",.()'!?/:#\\[\\]&`~\"“”’‘”— ]","");
        text = text.replaceAll("\r\n|\r|\n"," ");
        text = text.trim().replaceAll(" +", " ");
//        byte b;
//        char c;
//        for (int i = 0; i < text.length(); i++) {
//            c = text.charAt(i);
//            b  = (byte) c;
//            if((b<32)||(b>126)) {
//                return false;
//              }
//        }
        int totalCharacterCounter = text.length()-20;

        String[] possibleWords = text.split(" ");
        if(totalCharacterCounter/possibleWords.length > minWordsDensity)
        {
            return false;
        }
        for (int i = 0; i < possibleWords.length-2; i++) {
            if (!contains(possibleWords[i]))
            {
                mistakenWordsCounter++;
                totalWordsCounter++;
            }
            else totalWordsCounter++;
        }
        double mistakeRatio = mistakenWordsCounter / totalWordsCounter;
        if (mistakeRatio > mistakeRatioAllowed) {
            return false;
        }
        else{
            return true;
        }
    }
}