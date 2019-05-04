//Assignment 2 by:-
//1. 16L-4348 (l164348@lhr.nu.edu.pk) , CS-A
//2. 16L-4282 (l164282@lhr.nu.edu.pk) , CS-A 

//The purpose of this program is to find all the Palindromes,like noon, ward and draw etc,
//present in words.txt and the result is saved in results.txt. This program uses threads to find
//palindromes in parallel. And it also makes use of HashMaps.

package palindrome;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.lang.Integer;
import java.util.Scanner;
import java.lang.Thread;


public class Palindrome {
    static int num = 0;   //used to store total number of threads entered by user
    static int Number = 0; //total number of palindrome
    static int ThreadNum = 0; // keeps track of thread id
    static HashMap<String, ArrayList<String>> BagOfWords;
public static void main(String[] args) {
        File F = new File("src\\palindrome\\words.txt");   //opens file from this specific location
        BufferedReader BR = null;
        try {
            BR = new BufferedReader(new FileReader(F));
            BagOfWords = new HashMap<String, ArrayList<String>>();  //used to store words in array list length-wise
            String Str;
            while ((Str = BR.readLine()) != null) {
                String StrLen = Integer.toString((Str.length()));
                ArrayList<String> temp = BagOfWords.get(StrLen); //returns an arraylist on specific Key
                if (temp == null) {
                    temp = new ArrayList<String>();
                }
                temp.add(Str);
                BagOfWords.put(StrLen, temp);   //used to store ArrayList using length as key in hashMap
            }
            int Size = BagOfWords.size();
            Scanner Input = new Scanner(System.in);
            System.out.print("Enter Number of threads between (1 and " + Size + ")inclusive:: "); //taking input the desire number of threads in a limit.
            num = Integer.parseInt(Input.nextLine());
            System.out.println("You Entered Number of Threads : "+num);
            if (num < 1 || num > Size) {
                System.out.println("Invalid Number of Threads");
                return;
            }
            int Schedular = Size / num, Size2 = 0, Start = 0;
            ArrayList<String> Keys = new ArrayList(BagOfWords.keySet());  //used to store keys of whole HashMap
            ArrayList<String> SharedArr = new ArrayList<String>();  //used to store result
            ArrayList<Thread> Arr = new ArrayList<Thread>();   //used to store addresses of threads
            Thread T2 = new Thread(new PalindromeWriter(SharedArr));         //this thread is for maaking output file
            Arr.add(T2);
            T2.start();
            int k=0;
            while (Size2 < Size) {
                Start = Size2;
                Size2 += Schedular;
                k++;
                
                if (Size2 + Schedular > Size || k==num) { //calulation for making generic threads
                    int x = Size % Size2;
                    Size2 += x;
                }
                HashMap<String, ArrayList<String>> Temp = new HashMap<String, ArrayList<String>>(); //created for sending specific bags of word in thread
                for (int i = Start; i < Size2; i++) {
                    Temp.put(Keys.get(i), BagOfWords.get(Keys.get(i)));
                }
                PalindromeWorker obj = new PalindromeWorker(Temp, SharedArr);
                Thread T1 = new Thread(obj); //these threads are used for finding palindromes from specific number of bags
                Arr.add(T1); //adding addresses of threads in array list
                T1.start();
            }
            for (int i = 0; i < Arr.size(); i++) {
                Arr.get(i).join();  //used for waiting for all worker and writer threads to complete there task
            }
        } catch (FileNotFoundException Ex) {
            System.out.println("File Not Found");
        } catch (IOException Ex) {
            System.out.println("IO Exception has occured in main ");
            Ex.printStackTrace();
        } catch (Exception Ex) {
            System.out.println("Exception has occured in main...Kindly Enter Something");
        } finally {
            try {
                if (BR != null) {
                    BR.close();
                }
            } catch (Exception e) {
            }

        }
    }
    
}

/*

result or Output is saved in results.txt

*/