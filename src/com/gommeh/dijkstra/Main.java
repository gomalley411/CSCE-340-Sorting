package com.gommeh.dijkstra;

import com.gommeh.EWDG.*;

//Unit tests the DijkstraSP data type.

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
   public static void main(String[] args) throws FileNotFoundException {
       File inFile = new File("tinyEWG.txt");
       Scanner input = new Scanner(inFile);
       EdgeWeightedDigraph G = new EdgeWeightedDigraph(input);
       
       Scanner input1 = new Scanner(System.in);
       System.out.print("Enter source vertex: ");
       int s = input1.nextInt();

       // compute shortest paths
       DijkstraSP sp = new DijkstraSP(G, s);


       // print shortest path
       for (int t = 0; t < G.V(); t++) {
           if (sp.hasPathTo(t)) {
               System.out.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
               for (DirectedEdge e : sp.pathTo(t)) 
                   System.out.print(e + "   ");
               
               System.out.println();
           }
           else 
               System.out.printf("%d to %d         no path\n", s, t);
       }
   }
}
