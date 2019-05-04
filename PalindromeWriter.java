// this class is writing the desire palindromes founded from words.txt to results.txt using shared array!  
package palindrome;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class PalindromeWriter implements Runnable {         // implementing Runnable to use run method for threads
     ArrayList<String> SharedArr;

    @Override
    public void run() {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter("results.txt"));  // writing output to file results.txt
            int i = 0;
            while (i<Palindrome.num) {
                    synchronized (SharedArr) {
                    if(i<SharedArr.size() && SharedArr.get(i).startsWith(" Worker name: Thread ")){
                    i++;
                    }
                    else if(i<SharedArr.size()) {
                    out.write(SharedArr.remove(i));
                    out.newLine();
                    }
            }  
        }
        }catch (FileNotFoundException Ex) { // exception handling
            System.out.println("File Not Found");
        } catch (IOException Ex) {
            System.out.println("IO Exception has occured in writer Thread");
            Ex.printStackTrace();
        } catch (Exception Ex) {
            System.out.println("Exception has occured in Writer Thread" + Ex);
        } finally {
            try {
                if (out != null) {
                    for(int i=0;i<SharedArr.size();i++){
                        out.newLine();
                        out.write(SharedArr.get(i));
                        System.out.println(SharedArr.get(i));
                    }
                    out.newLine();
                    out.write("Total count for Palindromes = "+Palindrome.Number);//writing in file total palindromes found
                    System.out.println("Total count for Palindromes = "+Palindrome.Number);  //printing total palindromes found
                    out.close();                                                             // closing the file 
                }
            } catch (IOException e) {
            }
        }

    }

    PalindromeWriter(ArrayList<String> SharedArr) {
        this.SharedArr = SharedArr;
    }
}
