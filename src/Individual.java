import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Individual{
	public final int GRIDS = 9;
	public Integer[][] gridf = new Integer [GRIDS][GRIDS];
	public int counter = 0;
	//makes an individual 

	public Individual() throws FileNotFoundException  {
		Scanner scan = new Scanner(new File("Givens.txt"));


		//read file and store into Sudoku array
		boolean[][] givens = new boolean[GRIDS][GRIDS];
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

	//Used for fitness equation to determine whether a subgrid is unique.
	public boolean isSubgridUnique(int subGrid) {

		int counter = 0;

		int [] repNumbers = new int [GRIDS];

		if(subGrid == 0) {

			for (int i = 0; i < 3; i++) {

				for(int j = 0; j < 3; j++) {
 
					repNumbers[ Integer.valueOf( gridf[i][j] - 1 ) ]++;

					if (repNumbers[Integer.valueOf(gridf[i][j] - 1)] > 1) {

						counter++;

						//System.out.println("Repetitions:" + gridf[i][j]);

					}

				}

			}

		}

		else if (subGrid == 1) {

			for (int i = 0; i < 3; i++) {

				for(int j = 3; j < 6; j++) {

					repNumbers[ Integer.valueOf( gridf[i][j] - 1 ) ]++;

					if (repNumbers[Integer.valueOf(gridf[i][j] - 1)] > 1) {

						counter++;

						//System.out.println("Repetitions:" + gridf[i][j]);

					}

				}

			}

		}

		else if(subGrid == 2) {

			for (int i = 0; i < 3; i++) {

				for(int j = 6; j < 9; j++) {

					repNumbers[ Integer.valueOf( gridf[i][j] - 1 ) ]++;

					if (repNumbers[Integer.valueOf(gridf[i][j] - 1)] > 1) {

						counter++;

						//System.out.println("Repetitions:" + gridf[i][j]);

					}

				}

			}

		}

		else if(subGrid == 3) {

			for (int i = 3; i < 6; i++) {

				for(int j = 3; j < 6; j++) {

					repNumbers[ Integer.valueOf( gridf[i][j] - 1 ) ]++;

					if (repNumbers[Integer.valueOf(gridf[i][j] - 1)] > 1) {

						counter++;

						//System.out.println("Repetitions:" + gridf[i][j]);

					}

				}

			}

		}

		else if(subGrid == 4) {

			for (int i = 3; i < 6; i++) {

				for(int j = 3; j < 6; j++) {

					repNumbers[ Integer.valueOf( gridf[i][j] - 1 ) ]++;

					if (repNumbers[Integer.valueOf(gridf[i][j] - 1)] > 1) {

						counter++;

						//System.out.println("Repetitions:" + gridf[i][j]);

					}

				}

			}

		}

		else if(subGrid == 5) {

			for (int i = 3; i < 6; i++) {

				for(int j = 6; j < 9; j++) {

					repNumbers[ Integer.valueOf( gridf[i][j] - 1 ) ]++;

					if (repNumbers[Integer.valueOf(gridf[i][j] - 1)] > 1) {

						counter++;

						//System.out.println("Repetitions:" + gridf[i][j]);

					}

				}

			}

		}

		else if(subGrid == 6) {

			for (int i = 6; i < 9; i++) {

				for(int j = 0; j < 3; j++) {

					repNumbers[ Integer.valueOf( gridf[i][j] - 1 ) ]++;

					if (repNumbers[Integer.valueOf(gridf[i][j] - 1)] > 1) {

						counter++;

						//System.out.println("Repetitions:" + gridf[i][j]);

					}

				}

			}

		}

		else if(subGrid == 7) {

			for (int i = 6; i < 9; i++) {

				for(int j = 3; j < 6; j++) {

					repNumbers[ Integer.valueOf( gridf[i][j] - 1 ) ]++;

					if (repNumbers[Integer.valueOf(gridf[i][j] - 1)] > 1) {

						counter++;

						//System.out.println("Repetitions:" + gridf[i][j]);

					}

				}

			}

		}

		else if(subGrid == 8) {

			for (int i = 6; i < 9; i++) {

				for(int j = 6; j < 9; j++) {

					repNumbers[ Integer.valueOf( gridf[i][j] - 1 ) ]++;

					if (repNumbers[Integer.valueOf(gridf[i][j] - 1)] > 1) {

						counter++;

						//System.out.println("Repetitions:" + gridf[i][j]);

					}

				}

			}

		}

		else {

			throw new NullPointerException("Error inputing the subgrid");

		}

		if (counter != 0) {

			return false;

		}

		//System.out.println(counter);

		return true; 

	}



	//Used for fitness equation to determine whether a column is unique.
	public boolean isColumnUnique(int column) {
		int counter=0;
		int [] repNumbers = new int [GRIDS];
		for (int i = 0; i < GRIDS; i++) { //rows

			repNumbers[ Integer.valueOf( gridf[i][column] - 1 ) ]++;
			if(repNumbers[ Integer.valueOf(gridf[i][column]) - 1] > 1) {
				counter++;
			}
		}

		if (counter != 0) {
			return false;
		}

		return true;  
	}

	//Used for fitness equation to determine whether a row is unique.
	public boolean isRowUnique(int row) {

		int counter=0;
		int [] repNumbers = new int [GRIDS];

		for (int i = 0; i < GRIDS; i++) {

			repNumbers[ Integer.valueOf( gridf[row][i] - 1 ) ]++;
			if(repNumbers[ Integer.valueOf(gridf[row][i]) - 1] > 1) { 
				counter++;
			}
		}

		if (counter != 0) {
			return false;
		}
		return true; 
	}

	//Fitness function for the individuals. Calculated by dividing the number of points by the maximum amount of points (27). 
	//1 point is awarded for every row, column, or subgrid that is unique
	public double evaluateFitness(){
		int counter = 0;
		for (int i = 0; i < GRIDS; i++) {
			if(this.isColumnUnique(i)) 					counter++;
			if(this.isRowUnique(i))						counter++;
			if(this.isSubgridUnique(i))					counter++;
		}
		return counter/27.0;
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



