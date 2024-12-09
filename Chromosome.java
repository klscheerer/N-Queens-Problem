import java.util.Arrays;
import java.util.Random;

public class Chromosome {
    private final int[] genePool;
    private int fitness;

    //default constructor method
    public Chromosome(int n) {
        genePool = new int[n];
        fitness = 0;
        Random random = new Random();
        Arrays.setAll(genePool, i -> random.nextInt(n));
        calculateFitness();
    }

    //crossover & mutation constructor method
    public Chromosome(int r, Chromosome c1, Chromosome c2) {
        genePool = new int[c1.genePool.length];
        fitness = 0;
        for(int i = 0; i < r; i++) {
            genePool[i] = c1.genePool[i];
        }
        for(int i = r; i < c2.genePool.length; i++) {
            genePool[i] = c2.genePool[i];
        }
        Random random = new Random();
        genePool[random.nextInt(genePool.length)] = random.nextInt(genePool.length);
        calculateFitness();
    }

    public int[] getGenePool() {
        return genePool;
    }

    public int getFitness() {
        return fitness;
    }

    public void printBoard() {
        for(int x = 0; x < genePool.length; x++) {
            for(int y = 0; y < genePool.length; y++) {
                if(genePool[x] == y)
                    System.out.print("Q");
                else
                    System.out.print("-");
            }
            System.out.println();
        }
    }

    public void calculateFitness() {
        for(int i = 0; i < genePool.length - 1; i++) {
            fitness += genePool.length - (i + 1);

            //checks if there is a queen in the same column
            for(int j = i + 1; j < genePool.length; j++) {
                if(genePool[i] == genePool[j]) {
                    fitness--;
                    break;
                }
            }

            //checks if there is a diagonal conflict (down/right)
            for(int j = i + 1; j < genePool.length; j++) {
                if(genePool[i] + (j - i) == genePool[j]) {
                    fitness--;
                    break;
                }
            }

            //checks if there is a diagonal conflict (down/left)
            for(int j = i + 1; j < genePool.length; j++) {
                if(genePool[i] - (j - i) == genePool[j]) {
                    fitness--;
                    break;
                }
            }
        }
    }

    public int maxFitness() {
        int max = 0;
        for(int i = 1; i < genePool.length; i++)
            max += i;
        return max;
    }
}
