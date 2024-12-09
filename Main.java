import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int n;
        Scanner in = new Scanner(System.in);
        while(true) {
            //get user input for n and validate the data
            System.out.println("Enter a number between 4-20, or 0 to exit.");
            n = in.nextInt();
            while(n < 4 && n != 0 || n > 20) {
                System.out.println("Invalid input. Enter a number between 4-20, or 0 to exit.");
                n = in.nextInt();
            }
            if(n == 0)
                break;

            //creates first, randomly generated, generation of chromosomes
            long startTime = System.nanoTime();
            int numberGenerations = 1;
            Chromosome[] generation = new Chromosome[100];
            for(int i = 0; i < generation.length; i++)
                generation[i] = new Chromosome(n);

            //loop that runs until a solution is found
            while(true) {
                //checks if max fitness was obtained, if so print the solution and exit the loop
                int c = 0;
                boolean maxFound = false;
                int genMax = 0;
                for(int i = 0; i < generation.length; i++) {
                    if(genMax < generation[i].getFitness()) {
                        genMax = generation[i].getFitness();
                        c = i;
                    }
                    if(generation[i].getFitness() == generation[i].maxFitness()) {
                        maxFound = true;
                        break;
                    }
                }

                //print max fitness of generation, and solution if found
                System.out.print("Generation " + numberGenerations + " has max fitness " + genMax + " [");
                for(int i = 0; i < generation[c].getGenePool().length; i++) {
                    if (i < generation[c].getGenePool().length - 1)
                        System.out.print(generation[c].getGenePool()[i] + ", ");
                    else
                        System.out.print(generation[c].getGenePool()[i]);
                }
                System.out.println("]");
                if(maxFound) {
                    //prints out the final board and exits loop
                    System.out.println("Solution found in " + numberGenerations + " generations");
                    generation[c].printBoard();
                    long endTime = System.nanoTime();
                    long executionTime = (endTime - startTime) / 1000000; //milliseconds
                    System.out.println("Run time: " + (executionTime / 1000) + "s" + (executionTime % 1000) + "ms");
                    break;
                }

                //tournament style selection
                ArrayList<Chromosome> victors = new ArrayList<>();
                Random random = new Random();
                for(int i = 0; i < generation.length / 4; i++) {
                    int max = 0;
                    int pos = 0;
                    int r = random.nextInt(generation.length / 2);
                    for(int j = r; j < r + generation.length / 2; j++) {
                        if(generation[j].getFitness() > max && !victors.contains(generation[j])) {
                            max = generation[j].getFitness();
                            pos = j;
                        }
                    }
                    victors.add(generation[pos]);
                }

                //crossover & mutation
                for(int i = 0; i < generation.length; i = i + 2) {
                    int r = random.nextInt(n - 1);
                    generation[i] = new Chromosome(r, victors.get(i % victors.size()), victors.get((i + 1) % victors.size()));
                    generation[i] = new Chromosome(r, victors.get((i + 1) % victors.size()), victors.get(i % victors.size()));
                }
                numberGenerations++;
            }
        }
        in.close();
    }
}