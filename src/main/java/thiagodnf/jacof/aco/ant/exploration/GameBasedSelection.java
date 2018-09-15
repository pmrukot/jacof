package thiagodnf.jacof.aco.ant.exploration;

import org.apache.log4j.Logger;
import thiagodnf.jacof.aco.ACO;
import thiagodnf.jacof.aco.ant.Ant;
import thiagodnf.jacof.aco.ant.GameBasedAnt;
import thiagodnf.jacof.aco.ant.selection.AbstractAntSelection;
import thiagodnf.jacof.aco.ant.selection.RouletteWheel;

import static com.google.common.base.Preconditions.checkState;

/**
 * This class represents how an ant in GameBasedAS algorithm chooses the next node
 *
 * @author
 * @version 1.0.0
 */
public class GameBasedSelection extends AbstractAntExploration {

    static final Logger LOGGER = Logger.getLogger(GameBasedSelection.class);

    /**
     * Constructor
     *
     * @param aco          The ant colony optimization used
     * @param antSelection The ant selection used
     */
    public GameBasedSelection(ACO aco, AbstractAntSelection antSelection) {
        super(aco, antSelection);
    }

    /**
     * Constructor by using RouletteWheel as default ant selection
     *
     * @param aco The ant colony optimization used
     */
    public GameBasedSelection(ACO aco) {
        this(aco, new RouletteWheel());
    }


    @Override
    public int getNextNode(Ant ant, int i) {
        return doExploration((GameBasedAnt) ant, i);
    }


    public int doExploration(GameBasedAnt ant, int i) {

        int nextNode = -1;

        int bestPheromoneNodeIdx = 0;
        double bestPheromoneNodeVal = 0.0;
        int shortestPathNodeIdx = 0;
        double shortestPathNodeVal = Double.MAX_VALUE;


        // Find shortest path and most pheromone path
        for (Integer j : ant.getNodesToVisit()) {
            checkState(aco.getGraph().getTau(i, j) != 0.0, "The tau(i,j) should not be 0.0");

            if (aco.getGraph().getTau(i, j) > bestPheromoneNodeVal) {
                bestPheromoneNodeIdx = j;
                bestPheromoneNodeVal = aco.getGraph().getTau(i, j);
            }

            if (aco.getProblem().getNij(i, j) < shortestPathNodeVal) {
                shortestPathNodeIdx = j;
                shortestPathNodeVal = aco.getProblem().getNij(i, j);
            }
        }


        //Assign probabilities depending on problem class
        double[] probability = new double[aco.getProblem().getNumberOfNodes()];

        if (shortestPathNodeIdx == bestPheromoneNodeIdx) {

            // Assign equal probability value to each possible node
            double singleSelectRandomProbability = ant.getNOTBestPheromoneAndShortestPath() / (ant.getNodesToVisit().size() - 1);
            for (Integer j : ant.getNodesToVisit()) {
                probability[j] = singleSelectRandomProbability;
            }
            probability[bestPheromoneNodeIdx] = ant.getBestPheromoneAndShortestPathProbability();

        } else {

            // Assign equal probability value to each possible node
            double singleSelectRandomProbability = ant.getRandomChooseProbability() / (ant.getNodesToVisit().size() - 2);
            for (Integer j : ant.getNodesToVisit()) {
                probability[j] = singleSelectRandomProbability;
            }

            //Assign values fo 'best' nodes overiting "random"
            probability[bestPheromoneNodeIdx] = ant.getBestPheromonePathProbability();
            probability[shortestPathNodeIdx] = ant.getShortestPathProbability();

        }

        // Count the sum of probabilities
        double sumProbability = 0.0;
        for (double val : probability) {
            sumProbability += val;
        }
        checkState(sumProbability != 0.0, "The sum cannot be 0.0");


        // Select the next node by probability
        nextNode = antSelection.select(probability, sumProbability);
        checkState(nextNode != -1, "The next node should not be -1");

        if (nextNode == bestPheromoneNodeIdx || nextNode == shortestPathNodeIdx) {
            return nextNode;
        }

        return doRegularExploration(ant, i);
    }

    //TODO: Below is not true, but now not relevant
    @Override
    public double getNodeAttractiveness(int i, int j) {
        double tau = Math.pow(aco.getGraph().getTau(i, j), aco.getAlpha());
        double n = Math.pow(aco.getProblem().getNij(i, j), aco.getBeta());
        return tau * n;
    }

    @Override
    public String toString() {
        return GameBasedSelection.class.getSimpleName() + " with " + antSelection;
    }

    public int doRegularExploration(Ant ant, int i) {

        int nextNode = -1;

        double sum = 0.0;

        double[] tij = new double[aco.getProblem().getNumberOfNodes()];
        double[] nij = new double[aco.getProblem().getNumberOfNodes()];

        // Update the sum
        for (Integer j : ant.getNodesToVisit()) {

            checkState(aco.getGraph().getTau(i, j) != 0.0, "The tau(i,j) should not be 0.0");

            tij[j] = Math.pow(aco.getGraph().getTau(i, j), aco.getAlpha());
            nij[j] = Math.pow(aco.getProblem().getNij(i, j), aco.getBeta());

            sum += tij[j] * nij[j];
        }

        checkState(sum != 0.0, "The sum cannot be 0.0");

        double[] probability = new double[aco.getProblem().getNumberOfNodes()];

        double sumProbability = 0.0;

        for (Integer j : ant.getNodesToVisit()) {

            probability[j] = (tij[j] * nij[j]) / sum;

            sumProbability += probability[j];
        }

        // Select the next node by probability
        nextNode = antSelection.select(probability, sumProbability);

        checkState(nextNode != -1, "The next node should not be -1");

        return nextNode;
    }
}
