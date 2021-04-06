package com.gommeh.EWDG;

//Unit tests the EdgeWeightedDigraph data type.

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
  public static void main(String[] args) throws FileNotFoundException {
      File inFile = new File("tinyEWG.txt");
      Scanner input = new Scanner(inFile);
      
      EdgeWeightedDigraph G = new EdgeWeightedDigraph(input);
      System.out.println(G);
  }
}
