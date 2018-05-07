import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Sudoku {
public static final int generations = 10;
	private static final int popSize = 10;
	private static final int GRIDS = 9;
	private static final Integer [][] SudokuSkelly = new Integer[GRIDS][GRIDS];
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("Givens.txt"));
		
		
		//read file and store into skeleton array for given numbers
		boolean[][] givens = new boolean[GRIDS][GRIDS];
		for (int i=0; i< GRIDS; i++){ 
			for (int j = 0; j < GRIDS; j++) {
				SudokuSkelly[i][j] = scan.nextInt();
				if (SudokuSkelly[i][j] == 0)					givens[i][j] = true;
				
			}
		}
		scan.close();
		
		//Creates individual array and creates random individuals for the parent population.
		Individual[] ind = new Individual[popSize];
		
		for (int i = 0; i < popSize; i ++) {
			ind[i] = new Individual();
		}
		
		Population curr = new Population(ind);
		for (int i=0; i < popSize;i++){
			System.out.println(ind[i].toString());
			ind[i].evaluateFitness();
			
		}
		boolean [][] inc = ind[9].getIncorrect();
		for (int i=0; i< GRIDS; i++){ 
			for (int j = 0; j < GRIDS; j++) {
				if (inc[i][j]){
					System.out.print(1 + " ");
				}
				else {
					System.out.print(0 + " ");
				}
			}
			System.out.println();
		}
		
		//While loop for generations.
		int genCount = 0;
		while(genCount < popSize) {
			
			genCount++;
		}
		
		//curr.parentSelection();
		
	}
}