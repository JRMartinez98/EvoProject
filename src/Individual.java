import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Individual{
	private final int GRIDS = 9;
	private Integer[][] gridf = new Integer [GRIDS][GRIDS];
	private boolean [][] givens = new boolean[GRIDS][GRIDS];
	private int fitness;
	
	//Constructor for the parent population. Reads from givens text file and inserts random numbers into the rows, providing an entirely random grid.
	public Individual() throws FileNotFoundException  {
		Scanner scan = new Scanner(new File("Givens.txt"));


		//read file and store into Sudoku array
		for (int i=0; i< GRIDS; i++){
			for (int j = 0; j < GRIDS; j++) {
				gridf[i][j] = scan.nextInt();

				if (gridf[i][j] == 0){
					givens[i][j] = true;
				}
			}
		}
		scan.close();

		for (int i=0; i < GRIDS; i++) {
			
			//generate a new list of randoms for each row
			ArrayList <Integer> rand = new ArrayList<Integer> ();

			for(int j = 0; j < GRIDS; j++) {
				rand.add(j + 1); 
			}
			Collections.shuffle(rand); 
			int counter = 0;
			//if spot is a given, removes the value of that given from the list.
			for (int j=0; j < GRIDS; j++) { //columns	
				if (!givens[i][j]){ 
					rand.remove(Integer.valueOf(gridf[i][j])); //remove the value of a given at that point from list
				}
			}
			//if spot is blank adds it to the grid
			for (int j=0; j < GRIDS; j++) {	
				if (givens[i][j]) { 
					gridf[i][j] = rand.get(counter);
					counter++;
				}
			}	
		}
	}
	
	//Constructor for the every generation after the first. takes the grid and the givens as parameters.
	public Individual (Integer[][] grid, boolean [][] givens){
		gridf = grid;
		this.givens = givens;
	}

	//Method to determine if the subgrid is unique.
	public boolean isSubgridUnique(int subGrid) {
		int [] repNumbers = new int [GRIDS];
		if(subGrid == 0) {
			for (int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					repNumbers[gridf[i][j] - 1]++;
					if (repNumbers[gridf[i][j] - 1] > 1) {
						return false;
					}
				}
			}
		}
		else if (subGrid == 1) {
			for (int i = 0; i < 3; i++) {
				for(int j = 3; j < 6; j++) {
					repNumbers[gridf[i][j] - 1]++;
					if (repNumbers[gridf[i][j] - 1] > 1) {
						return false;
					}
				}
			}
		}
		else if(subGrid == 2) {
			for (int i = 0; i < 3; i++) {
				for(int j = 6; j < 9; j++) {
					repNumbers[gridf[i][j] - 1]++;
					if (repNumbers[gridf[i][j] - 1] > 1) {
						return false;
					}
				}
			}
		}
		else if(subGrid == 3) {
			for (int i = 3; i < 6; i++) {
				for(int j = 3; j < 6; j++) {
					repNumbers[gridf[i][j] - 1]++;
					if (repNumbers[gridf[i][j] - 1] > 1) {
						return false;
					}
				}
			}
		}
		else if(subGrid == 4) {
			for (int i = 3; i < 6; i++) {
				for(int j = 3; j < 6; j++) {
					repNumbers[gridf[i][j] - 1]++;
					if (repNumbers[gridf[i][j] - 1] > 1) {
						return false;
					}
				}
			}
		}
		else if(subGrid == 5) {
			for (int i = 3; i < 6; i++) {
				for(int j = 6; j < 9; j++) {
					repNumbers[gridf[i][j] - 1]++;
					if (repNumbers[gridf[i][j] - 1] > 1) {
						return false;
					}
				}
			}
		}
		else if(subGrid == 6) {
			for (int i = 6; i < 9; i++) {
				for(int j = 0; j < 3; j++) {
					repNumbers[gridf[i][j] - 1]++;
					if (repNumbers[gridf[i][j] - 1] > 1) {
						return false;
					}
				}
			}
		}
		else if(subGrid == 7) {
			for (int i = 6; i < 9; i++) {
				for(int j = 3; j < 6; j++) {
					repNumbers[gridf[i][j] - 1]++;
					if (repNumbers[gridf[i][j] - 1] > 1) {
						return false;
					}
				}
			}
		}
		else if(subGrid == 8) {
			for (int i = 6; i < 9; i++) {
				for(int j = 6; j < 9; j++) {
					repNumbers[gridf[i][j] - 1]++;
					if (repNumbers[gridf[i][j] - 1] > 1) {
						return false;
					}
				}
			}
		}
		else {
			throw new NullPointerException("Error inputing the subgrid");
		}
		return true; 
	}
			
	//Used for fitness equation to determine whether a column is unique.
	public boolean isColumnUnique(int column) {
		int [] repNumbers = new int [GRIDS];
		
		for (int i = 0; i < GRIDS; i++) { //rows
			repNumbers[gridf[i][column] - 1]++;
			if(repNumbers[gridf[i][column] - 1] > 1) {
				return false;
			}
		}
		return true;  
	}

	//Used for fitness equation to determine whether a row is unique.
	public boolean isRowUnique(int row) {
		int [] repNumbers = new int [GRIDS];

		for (int i = 0; i < GRIDS; i++) {
			repNumbers[gridf[row][i] - 1]++;
			if(repNumbers[gridf[row][i] - 1] > 1) {
				return false;
			}
		}
		return true; 
	}

	//Fitness function for the individuals. Calculated by dividing the number of points by the maximum amount of points (9). 
	//1 point is awarded for every row, column, and subgrid that is unique
	public int evaluateFitness(){
		int counter = 0;
		for (int i = 0; i < GRIDS; i++) {
			if(this.isColumnUnique(i) && this.isRowUnique(i) && this.isSubgridUnique(i))		
				counter++;
		}
		fitness = counter;
		return fitness;
	}
	//Method to determine if the grid is solved.
	public boolean isSolved() {
		for (int i = 0; i < GRIDS; i++) {
			if( !(this.isColumnUnique(i) && this.isRowUnique(i) && this.isSubgridUnique(i)) ){
				return false;
			}
		}
		return true;
	}
	
	//returns the givens of the individuals.
	public boolean [][] isGiven(){
		return givens;
	}
	
	//returns the grid of the individual.
	public Integer[][] getGrid() {
		return gridf;
	}
	
	public String toString() {
		String result = "_________________________________________ \n";
		for(int i=0; i < GRIDS; i++) { //rows 
			if (i>0 && i%3 == 0) {
				result+= "| |-------------------------------------|| \n";
			}
			result += "| ";
			for (int j=0; j <GRIDS; j++) { //columns 
				result+= "|" + " " + gridf[i][j]+" ";
				if (j%3 ==2) {
					result += "|";
				}
			}
			result += "| \n";
		}
		result += "_________________________________________ \n";
		return result;
	}
}