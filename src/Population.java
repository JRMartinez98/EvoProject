import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population {
	private final int GRIDS = 9;
	private Individual[] pop;
	private final int popSize = 10;
	private Individual[] child = new Individual[popSize]; // create empty array of parents
	private Individual[] parent = new Individual[2]; //2 parents
	public int fittestIndex;

	public Population(Individual[] pop) {
		this.pop = pop;

	}

	// 1. Parent Selection
	// for loop to evaluate fitness and implement tournament selection
	public Individual [] tournamentSelection(int k) {
		int i = 0;
		Individual[] rand = new Individual[k]; // choose 2 randoms to compete
		while (i != child.length && child[i] == null) { // while the child array is empty
			//loop to fill up parent array
			int j=0;
			while (j < parent.length){
				// choose k=2 random individuals from population
				rand[0] = pop[(int) (Math.random() * popSize - 1)];
				rand[1] = pop[(int) (Math.random() * popSize - 1)];
				// fill the parent array with the 'best'/fittest individual
				if (rand[0].evaluateFitness() >= rand[1].evaluateFitness())
					parent[j] = rand[0];
				else {
					parent[j] = rand[1];
				}
				j++;
			}
			//After parents are chosen, call recombination method
			Integer[][] childarr = this.crossover(parent[0], parent[1], 0.6);
			Individual person = new Individual(childarr, parent[0].isGiven());
			person.evaluateFitness();
			this.mutation(person, 0.5);
			child[i] = person;
			/*
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();


			for (int m = 0; m < GRIDS; m++) {

				for (int n = 0; n < GRIDS; n++) {
					System.out.print(childarr[m][n] + " ");
				}
				System.out.println();
			}
			 */
			i++;
		}
		return child;
	}

	public double evaluateAvgPopFitness(){
		double sum = 0.0;
		for(int i = 0; i < popSize; i++) {
			sum += pop[i].evaluateFitness();
		}
		return sum/popSize;
	}

	public double highestFit() {
		double max = 0.0;
		for(int i = 0; i < popSize; i++) {
			if(max < pop[i].evaluateFitness() ){
				max = pop[i].evaluateFitness();
				fittestIndex = i;
			}
		}
		return max;
	}

	public int getFittestIndex() {
		return fittestIndex;
	}
	public Individual[] getIndividuals() {
		return pop;
	}

	public double lowestFit() {
		double min = Double.MAX_VALUE;
		for(int i = 0; i < popSize; i++) {
			if(min > pop[i].evaluateFitness() ){
				min = pop[i].evaluateFitness();
			}
		}
		return min;
	}
	public Integer [][] crossover(Individual parent1, Individual parent2, double pC) {
		Integer [][] grid1 = parent1.getGrid(); //will eventually be changed
		Integer [][] grid2 = parent2.getGrid();
		Integer [][] child = new Integer[GRIDS][GRIDS];
		for(int i = 0; i < GRIDS; i++) {

			if(Math.random() > pC) {
				for(int j = 0; j < GRIDS; j++) {
					child[i][j] = grid2[i][j];
				}
			}
			else {
				for(int j = 0; j < GRIDS; j++) {
					child[i][j] = grid1[i][j];
				}
			}
		}
		return child;
	}
	// 2. Mutation
	public void mutation(Individual child, double pm){
		Integer [][] grid = child.getGrid();
		boolean [][] givens = child.isGiven();
		Random rand = new Random();

		if(Math.random() > pm) {
			

			boolean isSwapped =  false;
			while(!isSwapped){
				//System.out.println("reached isSwapped while");
				//If both indexes the same, keep looping until both numbers are different.
				int row = rand.nextInt(8);
				int index1 = rand.nextInt(8);
				int index2 = rand.nextInt(8);
				while(index1 == index2){
					//System.out.println("reached index whiles");
					index1 = rand.nextInt(8);
					index2 = rand.nextInt(8);
				}

				if(givens[row][index1] && givens[row][index2]){
					
					//System.out.println("reached mutation if 1");
					if(child.isColumnUnique(row, grid[row][index1]) && child.isColumnUnique(row, grid[row][index2])){
						   // System.out.println("reached mutation if 2"); 
						    int temp = grid[row][index1];
							grid[row][index1] = grid[row][index2];
							grid[row][index2] = temp;
							isSwapped = true;
						
						//if(child.isSubgridUnique(row, index1, grid[row][index1]) && child.isSubgridUnique(row, index2, grid[row][index2])) {
							
							//System.out.println("reached mutation if 3");
							
						//}
					}
				}
			}
		}
		
	}

	/*
			//if the random number is higher than the mutation rate, mutate the incorrect numbers in each row, scramble them around for each child.
			if(Math.random() > pm) {
				ArrayList <Integer> scramble1 = new ArrayList <Integer>();
				for (int j = 0; j < GRIDS; j++) {
					//if value is a 1
					if (error1[i][j]){	//if value is a duplicate		
						scramble1.add(grid[i][j]);
					}
				}
				Collections.shuffle(scramble1);
				int counter = 0;
				for (int j = 0; j < GRIDS; j++) {
					if (error1[i][j]) {
						grid[i][j] = scramble1.get(counter);
						counter++;
					}
				}
			}
		} 
	 */
}
