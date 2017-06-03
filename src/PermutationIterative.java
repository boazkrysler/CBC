/**
 * Created by carme on 24/04/2017.
 */
import java.util.*;
public class PermutationIterative
{
    private char[] S; // INSTANCE VARIABLE
    private int count;

    public boolean hasNext() {
        return hasNext;
    }

    private boolean hasNext;
    public PermutationIterative(String Word){ // CONSTRUCTOR
        S=Word.toCharArray();
        bubbleSort();
        if(S.length>0)
            hasNext = true;
    }

    private boolean permuteNext()
    {
        int i,j;
        i=S.length-2; // 1. STARTING FROM END FINDING i SUCH THAT S[i]<S[i+1]
        while(i>=0 && S[i]>S[i+1])
            i--;
        if(i<0) // NO MORE PERMUTATIONS POSSIBLE
            return false;
        j=S.length-1; // 2. ITERATING BACK FINDING j S[j]>S[i]
        while((int)S[j]<(int)S[i])
            j--;
        char temp=S[i]; // 3. SWAPPING arr[i] AND arr[j]
        S[i]=S[j];
        S[j]=temp;
        int f=i+1,b=S.length-1; // 4. REVERSING ELEMENTS AFTER i
        while(f<b){
            temp=S[f];
            S[f++]=S[b];
            S[b--]=temp;
        }
        return true;
    }

    public char[] next(){
        if(hasNext) {
            char[] res = S.clone();
            hasNext = permuteNext();
            count++;
            return res;
        }
        return null;
    }


    public void bubbleSort()
    {
        for(int i=S.length-1;i>0;i--){ // NUMBER OF PASSES
            for(int j=0;j<i;j++){        // NUMBER OF COMPARISONS IN EACH PASS
                if(S[j]>S[j+1]){     // IF GREATER, SWAPPING J AND J+1 ELEMENT
                    char temp=S[j];    S[j]=S[j+1];    S[j+1]=temp; // SWAPPING ELEMENTS
                }
            } // END OF COMPARISION (j) LOOP
        } // END OF PASS (i) LOOP
    }
}