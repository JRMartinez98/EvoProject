import java.util.ArrayList;
import java.util.Collections;

public class Population {
	private final int GRIDS = 9;
	private Individual[] pop;
	private final int popSize = 10;
	private Individual[] child = new Individual[popSize]; // create empty array of parents
	private Individual[] parent = new Individual[2]; //2 parents

	public Population(Individual[] pop) {
		this.pop = pop;

	}

	// 1. Parent Selection
	// for loop to evaluate fitness and implement tournament selection
	public void tournamentSelection(int k) {
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
			crossover(parent[0], parent[1]);
			
			i++;
		}
	}

	// 2. Recombination
	public void crossover(Individual parent1, Individual parent2){
		//convert parents to String
		String p1 = parent1.getGridString();
		String p2 = parent2.getGridString();
		String givens = parent1.getGivenString();
		
		//Permutation representation 
		//1. choose 2 crossover points randomly
		int crossPoint1 =(int) (Math.random()*p1.length() - 1);
		int crossPoint2 = (int) (Math.random()*p1.length() - 1);
		int min = Math.min(crossPoint1, crossPoint2);
		int max = Math.max(crossPoint1, crossPoint2);
		
		StringBuffer child1SB = new StringBuffer();
		StringBuffer child2SB = new StringBuffer();
		

		// loop to see if that value has been copied over yet
		boolean isCopied = false;
			while (min < max){
				int value = p2.charAt(min);
				for (int i=min; i<max; i++){ //loop over value to see if it's in middle segment (values that have been copied over already)
					if (value == p1.charAt(min)){
						isCopied = true;
					}
					
				}
				if (isCopied==true){
					//do nothing
				}
				else{ //else the value of the index in P2 is not copied over yet
					//get value of index in P1
					int indexValue = p1.charAt(min);
					//search through P2 for position of that value
					for (int i=0; i < p2.length();i++){
						if (p2.charAt(i) == indexValue){
							child1SB.insert(i, value);		
						}
					}
				}
				min++;	
		}
	}

	// 3. Mutation
	public void mutation(Individual child1, Individual child2, double pm){
		Integer [][] grid1 = child1.getGrid();
		Integer [][] grid2 = child2.getGrid();
		boolean [][] error1 = child1.getIncorrect();
		boolean [][] error2 = child2.getIncorrect();
		
		for(int i = 0; i < GRIDS; i++) {
			//if the random number is higher than the mutation rate, mutate the incorrect numbers in each row, scramble them around for each child.
			if(Math.random() > pm) {
				ArrayList <Integer> scramble1 = new ArrayList <Integer>();
				ArrayList <Integer> scramble2 = new ArrayList <Integer>();
				for (int j = 0; j < GRIDS; j++) {
					if (error1[i][j]) {
						scramble1.add(grid1[i][j]);
					}
					if (error2[i][j]) {
						scramble2.add(grid2[i][j]);
					}
				}
				Collections.shuffle(scramble1);
				Collections.shuffle(scramble2);
				int counter1 = 0;
				int counter2 = 0;
				for (int j = 0; j < GRIDS; j++) {
					if (error1[i][j]) {
						grid1[i][j] = scramble1.get(counter1);
						counter1++;
					}
					if (error2[i][j]) {
						grid2[i][j] = scramble2.get(counter2);
						counter2++;
					}
				}
			}
		}
		
	}

}
