
public class Population {
	public final int GRIDS = 9;
	public Individual[] pop;
	public final int popSize = 10;
	public Individual[] child = new Individual[popSize]; // create empty array of parents
	public Individual[] parent = new Individual[2]; //2 parents

	public Population(Individual[] pop) {
		this.pop = pop;

	}

	// 1. Parent Selection
	// for loop to evaluate fitness and implement tournament selection
	public void parentSelection() {
		int i = 0;
		Individual[] rand = new Individual[2]; // choose 2 randoms to compete
		while (i != child.length && child[i] == null) { // while the child array is empty
			// is empty
			// Implement Tournament selection
			
			//loop to fill up parent array
			int j=0;
			while (j<parent.length){
				// choose k=2 random individuals from population
				rand[0] = pop[(int) (Math.random() * 10 - 1)];
				rand[1] = pop[(int) (Math.random() * 10 - 1)];
				// fill the parent array with the 'best'/fittest individual
				if (rand[0].evaluateFitness() >= rand[1].evaluateFitness())
					parent[j] = rand[0];
				else 
					parent[j] = rand[1];
				j++;
			}
			//After parents are chosen, call recombination method
			crossover(parent[0], parent[1]);
			
			
			i++;
		}

	}

	// 2. Recombination
	public void crossover(Individual parent1, Individual parent2){
		//convert parents to StringBuffer
		String p1 = parent1.toString();
		String p2 = parent2.toString();

		//Permutation representation 
		//1. choose 2 crossover points randomly
		int crossPoint1 =(int) (Math.random()*10-1);
		int crossPoint2 = (int) (Math.random()*10-1);
		int min = Math.min(crossPoint1, crossPoint2);
		int max = Math.max(crossPoint1, crossPoint2);
		String child1 = p1.substring(0, min) + p2.substring(min, max) + p1.substring(max);
		String child2 = p2.substring(0, min) + p1.substring(min, max) + p2.substring(max);		
		
	}

	// 3. Mutation

}
