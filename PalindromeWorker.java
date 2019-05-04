// this class is finding the palindromes from the array list of specific length
package palindrome;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

public class PalindromeWorker implements Runnable {
    int id;
    HashMap<String, ArrayList<String>> Bags;
    ArrayList<String> SharedArr;
    int count;

    PalindromeWorker(HashMap<String, ArrayList<String>> Bags, ArrayList<String> SharedArr) {
        id = ++Palindrome.ThreadNum;
        count = 0;
        this.Bags = Bags;
        this.SharedArr = SharedArr;
    }

    @Override
    public void run() {
        try {
            ArrayList<String> BagKeys = new ArrayList(Bags.keySet());  //used to store keys of whole HashMap
            for (int i = 0; i < BagKeys.size(); i++) {
                String Key = BagKeys.get(i);
                ArrayList<String> Temp = new ArrayList<String>(Bags.get(Key));//used to find Palindrome in specific arraylist of same length
                for (int j = 0; j < Temp.size(); j++) {
                    StringBuffer StrBuf = new StringBuffer(Temp.get(j));  //used for reversing the string to check palindrome
                    String Str = StrBuf.reverse().toString();
                    for (int k = 0; k < Temp.size(); k++) {
                        if (Str.equals(Temp.get(k))) {
                            Palindrome.Number++;  //increasing total number of palindrome
                            synchronized (SharedArr) {
                                SharedArr.add(Str);
                                count++; //used to store number of palindrome in this specific thread
                            }
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception caught in Workerthread " + id);
        } finally {
            synchronized (SharedArr) {
                SharedArr.add(" Worker name: Thread " + id + ",  palindrome_count: " + count+" ");
            }
        }
    }

}
