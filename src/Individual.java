import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Individual{
	private final int GRIDS = 9;
	private Integer[][] gridf = new Integer [GRIDS][GRIDS];
	private int counter = 0;
	private boolean [][] givens = new boolean[GRIDS][GRIDS];
	private boolean [][] incorrect = new boolean[GRIDS][GRIDS];
	//makes an individual 

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
			for (int j=0; j < GRIDS; j++) { //columns	
				if (!givens[i][j]){ //if spot is a given
					rand.remove(Integer.valueOf(gridf[i][j])); //remove the value of given at that point from list
					rand.add(rand.size(), -1); //add random value at end of list in order to keep size
				}
			}
			for (int j=0; j < GRIDS; j++) {	
				if (givens[i][j]) { //if givens is true (the spot has a 0)
					gridf[i][j] = rand.get(counter); //print the shuffled value there
					counter++;
				}
			}	
		}
	}

	public Individual (Integer[][] grid, boolean [][] givens){
		gridf = grid;
		this.givens = givens;
		this.evaluateFitness();
	}

	//Used for fitness equation to determine whether a subgrid is unique.
	public boolean isSubgridUnique(int row, int column, int value) {
		int counter = 0;
		int i =  3 * ( (int) (row/3) );
		int j =  3 * ( (int) (column/3) ); 
		if(gridf[i][j] == value){
			counter++;
		}
		if(gridf[i][j+1] == value){
			counter++;
		}
		if(gridf[i][j+2] == value){
			counter++;
		}
		if(gridf[i+1][j] == value){
			counter++;
		}
		if(gridf[i+1][j+1] == value){
			counter++;
		}
		if(gridf[i+1][j+2] == value){
			counter++;
		}
		if(gridf[i+2][j] == value){
			counter++;
		}
		if(gridf[i+2][j+1] == value){
			counter++;
		}
		if(gridf[i+2][j+2] == value){
			counter++;
		}
		if (counter > 1){
			return false;
		}
		
		return true;
	}
	
	//Used for fitness equation to determine whether a column is unique.
	public boolean isColumnUnique(int column) {
		int counter=0;
		int [] repNumbers = new int [GRIDS];
		int [] prevSpot = new int[GRIDS];
		for (int i = 0; i < GRIDS; i++) { //rows
			
			repNumbers[gridf[i][column] - 1]++;
			if(repNumbers[gridf[i][column] - 1] > 1) {
				
				//Special case in which there is a given spot but the given number appears before. Sets the number as incorrect while keeping the given spot correct
				if(!givens[i][column]) {
					//int spot = prevSpot[gridf[i][column] - 1];
					
					for(int j = 0; j < GRIDS; j++) {
						if ((i != j) && givens[j][column] && (gridf[j][column] == gridf[i][column])) {
							incorrect[j][column] = true;
						}
					}
				}
				
				if(givens[i][column]){
					incorrect[i][column] = true;
				}	
				counter++;
			}
			prevSpot[gridf[i][column] - 1] = i;
		}

		if (counter > 1) {
			return false;
		}
		return true;  
	}

	//Used for fitness equation to determine whether a column is unique. Used in mutation to determine which values are swapped.
	public boolean isColumnUnique(int column, int value) {
		int counter = 0;
		for (int i = 0; i < GRIDS; i++) {
			if(gridf[i][column] == value) {
				counter++;
			}
		}
		
		if (counter > 1) {
			return false;
		}
		return true; 
	}
	//Used for fitness equation to determine whether a row is unique.
	public boolean isRowUnique(int row) {
		int counter=0;
		int [] repNumbers = new int [GRIDS];

		for (int i = 0; i < GRIDS; i++) {

			repNumbers[ gridf[row][i] - 1]++;
			if(repNumbers[ gridf[row][i] - 1] > 1) {
				
				//Special case in which there is a given spot but the given number appears before. Sets the number as incorrect while keeping the given spot correct
				if(!givens[row][i]) {
					for(int j = 0; j < GRIDS; j++) {
						if ( (j != i) && givens[row][j] && gridf[row][j] == gridf[row][i]) {
							incorrect[row][i] = true;
						}
					}
				}
				else if(givens[row][i]){
					incorrect[row][i] = true;
				}
				
				
				counter++;
			}
		}

		if (counter > 1) {
			return false;
		}
		return true; 
	}

	//Fitness function for the individuals. Calculated by dividing the number of points by the maximum amount of points (27). 
	//1 point is awarded for every row, column, or subgrid that is unique
	public double evaluateFitness(){
		int counter = 0;
		int subGridcounter = 0;
		for (int i = 0; i < GRIDS; i++) {
			if(this.isColumnUnique(i)) 											counter++;
			if(this.isRowUnique(i))												counter++;
			for(int j = 0; j < GRIDS; j++) {
				if (subGridcounter%9 == 0 && subGridcounter != 0)				counter++;
				for(int k = 0; k < GRIDS; k++) {
					if(this.isSubgridUnique(j, k, i))					subGridcounter++;
				}

			}	
		}
		return counter/27.0;
	}

	//get Grid and returns as String.
	public String getGridString() {
		String gridString = "";
		for (int i = 0; i < GRIDS; i++) {
			for (int j = 0; j < GRIDS; j++) {
				gridString += gridf[i][j];
			}
		}
		return gridString;
	}
	
	public String getGivenString() {
		String givenString = "";
		for (int i = 0; i < GRIDS; i++) {
			for (int j = 0; j < GRIDS; j++) {
				if(givens[i][j]) {
					givenString += 1;
				}
				else {
					givenString += 0;
				}
			}
		}
		return givenString;
	}
	
	public boolean [][] isGiven(){
		return givens;
	}
	public boolean [][] getIncorrect() {
		return incorrect;
	}
	
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
