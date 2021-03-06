package Day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import computer.Intcode_Day07;

public class D7P1 {
  public static void main(String[] args){
    ArrayList<Integer> tape = parseInput(new File(args[0]));
    int max = 0;

    // This is ugly, but works, we run each computer one after the other.
    for(int i = 12345; i < 54321; i++){
      if(validState(i)){
        int[] digits = splitDigits(i);
        Integer[] in = {digits[0], 0};
        Intcode_Day07 computer1 = new Intcode_Day07(tape, in);
        computer1.run();

        in[0] = digits[1];
        in[1] = computer1.getOutput();
        Intcode_Day07 computer2 = new Intcode_Day07(tape, in);
        computer2.run();

        in[0] = digits[2];
        in[1] = computer2.getOutput();
        Intcode_Day07 computer3 = new Intcode_Day07(tape, in);
        computer3.run();

        in[0] = digits[3];
        in[1] = computer3.getOutput();
        Intcode_Day07 computer4 = new Intcode_Day07(tape, in);
        computer4.run();

        in[0] = digits[4];
        in[1] = computer4.getOutput();
        Intcode_Day07 computer5 = new Intcode_Day07(tape, in);
        computer5.run();

        int out = computer5.getOutput();

        if(out > max){max = out;}
        
      }
    }

    System.out.printf("The Maximum signal that can be sent is %d\n", max);
  }

  /**
   * Splits the given num into it's digits
   * @param num - the number to be split
   * @return an int[] such that {0, 1, 2, 3, 4} = {D₀, D₁, D₂, D₃, D₄}
   */
  private static int[] splitDigits(int num){
    //the number will always be 5 digits
    int[] digits = new int[5];
    for(int i = 4, div = 1; i >= 0; i--, div *= 10){
      digits[i] = num/div % 10;
    }
    return digits;
  }

  /**
   * A state is valid if D₀ ≠ D₁ ≠ D₂ ≠ D₃ ≠ D₄
   * @param state - The state to be checked
   * @return D₀ ≠ D₁ ≠ D₂ ≠ D₃ ≠ D₄
   */
  private static boolean validState(int state){
    int[] digits = splitDigits(state);
    for(int i = 0; i < 5; i++){
      for(int j = 0; j < 5; j++){
        if(i != j && digits[i] == digits[j]){
          return false;
        }
      }
    }
    return true;
  }

  /**
   * This function parses our input file into the necessary format for use with 
   * the Intcode_Day07 class
   * @param file
   * @return ArrayList<Integer> - Our instruction Tape
   */
  public static ArrayList<Integer> parseInput(File file){
    try {
      Scanner in = new Scanner(file);
      String[] temp = in.nextLine().split(",");
      ArrayList<Integer> al = new ArrayList<>(); 
      in.close();

      for(String s : temp){
        al.add(Integer.parseInt(s));
      }
      for(int i = 0; i < 20000; i++){
        al.add(i);// Allocate large amount of memory to computer
      }
      return al;
    } catch (FileNotFoundException e){
      e.printStackTrace();
    }
    return null;
  }
}