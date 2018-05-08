import java.util.Random;

public class Population {
	private final int GRIDS = 9;
	private Individual[] pop;
	private final int popSize;
	private Individual[] child; // create empty array of parents
	private Individual[] parent = new Individual[2]; //2 parents
	private int fittestIndex;
	private int highestFit;

	public Population(Individual[] pop, int popSize) {
		this.pop = pop;
		this.popSize = popSize;
		child = new Individual[popSize];

	}

	// Tournament selection.
	// Int k determines how many Individuals you want to compete to become parents. k should always be even to avoid some individuals not competing.
	public Individual [] tournamentSelection(int k) {
		
		//Special cases for k. 
			//If k is bigger than the population size, then the popSize is used.
			//If k is lower than 2 then it's set to two so the parents can be chosen.
		if(k >= popSize)		k = popSize - 1;
		if ( k < 2)				k = 2;
		
		Individual[] choices = new Individual[k]; // choose 2 randoms to compete
		Random random = new Random();
		
		int i = 0; 
		// while the child array is empty
		while (i != child.length && child[i] == null) {
			int counter = 0;
			
			//chooses Individuals for tournament
			while (counter < k) {
				choices[counter++] = pop[random.nextInt(popSize)];
			}
			
			//finds the index for the two fittest individuals in the chosen Individuals.
			int max  = Integer.MIN_VALUE;
			int max2 = Integer.MIN_VALUE;
			int fitIndex2 = 0;
			for(int j = 0; j < k; j++) {
				if(choices[j].evaluateFitness() > max){
					max2 = max;
					max = choices[j].evaluateFitness();
					highestFit = max;
					fittestIndex = j;
				}
				else if (choices[j].evaluateFitness() > max2) {
					fitIndex2 = j;
				}
			}
			
			parent[0] = choices[fittestIndex];
			parent[1] = choices[fitIndex2];
			
			//Make two individuals switching the order of the parents.
			//The individual with the highest fitness is then added to the child array.
			Integer[][] childarr  = this.crossover(parent[0], parent[1], 0.6);
			Integer[][] childarr2 = this.crossover(parent[1], parent[0], 0.6);
			Individual person     = new Individual(childarr, parent[0].isGiven());
			Individual person2    = new Individual(childarr2, parent[0].isGiven());
			this.mutation(person, 0.5);
			this.mutation(person2, 0.5);
			
			if(person.evaluateFitness() >= person2.evaluateFitness()) {
				child[i] = person;
			}
			else{
				child[i] = person2;
			}
			
			i++;
		}
		return child;
	}
	
	//Returns the Average fitness of the population.
	public double evaluateAvgPopFitness(){
		double sum = 0.0;
		for(int i = 0; i < popSize; i++) {
			sum += pop[i].evaluateFitness();
		}
		return sum/popSize;
	}

	//Method that returns the highest fitness value in the Population.
	//Also stores the highest index.
	public int highestFit() {
		return highestFit;
	}
	
	public int getFittestIndex() {
		return fittestIndex;
	}
	public Individual[] getIndividuals() {
		return pop;
	}

	public int lowestFit() {
		int min = Integer.MAX_VALUE;
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
	
	//Method for mutation. 
	//for each row, if the mutation rate is smaller than a random number, swap two values if these are not givens.
	public void mutation(Individual child, double pm) {
		Integer [][] grid = child.getGrid();
		boolean [][] givens = child.isGiven();
		Random rand = new Random();
		
		for(int i = 0; i < GRIDS; i ++) {
			int index1 = rand.nextInt(9); //random index value for column
			int index2 = rand.nextInt(9); //random index value for column
			
			while(index1 == index2){
				index1 = rand.nextInt(9);
				index2 = rand.nextInt(9);
				
			}
			if(Math.random() > pm) {
				if(givens[i][index1] && givens[i][index2]) {
					int temp = grid[i][index1];
					grid[i][index1] = grid[i][index2];
					grid[i][index2] = temp;
				}
			}
		}
	}
}