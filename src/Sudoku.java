import java.io.*;

public class Sudoku {
	public static final int GENERATIONS = 500000;
	private static final int POP_SIZE = 20;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		//Creates individual array and creates random individuals for the parent population.
		Individual[] ind = new Individual[POP_SIZE];
		
		for (int i = 0; i < POP_SIZE; i ++) {
			ind[i] = new Individual();
		}
		
		Population parentPop = new Population(ind, POP_SIZE);
		
		for (int i=0; i < POP_SIZE;i++){
			System.out.println(ind[i].toString());
			ind[i].evaluateFitness();
			
		}
		
		//While loop for generations.
		int genCount = 0;
		while(genCount < GENERATIONS) {
			Individual[] pop = parentPop.getIndividuals();
			Population curr = new Population(parentPop.tournamentSelection(10), POP_SIZE);
			
			if(genCount%10000 == 0){
				System.out.println("Currently in Generation " + genCount);
				System.out.println("Average fitness of population " + parentPop.evaluateAvgPopFitness());
				System.out.println("Highest fitness of population " + parentPop.highestFit());
				System.out.println("Lowest fitness of population " + parentPop.lowestFit());
				System.out.println();
			}
		
			if(parentPop.highestFit() == 9 && pop[parentPop.getFittestIndex()].isSolved()) {
				System.out.println("Solution for the current Sudoku grid has been found in generation " + genCount);
				System.out.println( pop[parentPop.getFittestIndex()] );
				System.out.println("Fitness of the Individual " + pop[parentPop.getFittestIndex()].evaluateFitness());
				System.out.println("Average fitness of population " + parentPop.evaluateAvgPopFitness());
				System.out.println("Lowest fitness of population " + parentPop.lowestFit());
				System.out.println();
				break;
			}
			parentPop = curr;
			genCount++;
		}
		
		System.out.println(" No solution was found, please try again.");
		
		
	}
}