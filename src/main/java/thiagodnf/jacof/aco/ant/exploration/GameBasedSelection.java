package thiagodnf.jacof.aco.ant.exploration;

import org.apache.log4j.Logger;
import thiagodnf.jacof.aco.ACO;
import thiagodnf.jacof.aco.ant.Ant;
import thiagodnf.jacof.aco.ant.selection.AbstractAntSelection;
import thiagodnf.jacof.aco.ant.selection.RouletteWheel;

import static com.google.common.base.Preconditions.checkState;

/**
 * This class represents how an ant in AS algorithm chooses the next node
 *
 * @author Thiago N. Ferreira
 * @version 1.0.0
 */
public class GameBasedSelection extends AbstractAntExploration {

    static final Logger LOGGER = Logger.getLogger(GameBasedSelection.class);
    private double selectBestPheromone;
    private double selectShortestPath;
    private double selectRandom;

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

    /**
     * Constructor by using RouletteWheel as default ant selection
     *
     * @param aco The ant colony optimization used
     * @param aco The ant colony optimization used
     * @param aco The ant colony optimization used
     * @param aco The ant colony optimization used
     */
    public GameBasedSelection(ACO aco, double selectBestPheromone, double selectShortestPath, double selectRandom) {
        this(aco, new RouletteWheel());
        this.selectBestPheromone = selectBestPheromone;
        this.selectShortestPath = selectShortestPath;
        this.selectRandom = selectRandom;
    }

    @Override
    public int getNextNode(Ant ant, int i) {
        return doExploration(ant, i);
    }

    public int doExploration(Ant ant, int i) {

        int nextNode = -1;

        int bestFeromoneNodeIdx = 0;
        double bestFeromoneNodeVal = 0.0;
        int shortestPathNodeIdx = 0;
        double shortestPathNodeVal = Double.MAX_VALUE;


        // Find shortest path and most feromone path
        for (Integer j : ant.getNodesToVisit()) {
            checkState(aco.getGraph().getTau(i, j) != 0.0, "The tau(i,j) should not be 0.0");

            if (aco.getGraph().getTau(i, j) > bestFeromoneNodeVal) {
                bestFeromoneNodeIdx = j;
                bestFeromoneNodeVal = aco.getGraph().getTau(i, j);
            }

            if (aco.getProblem().getNij(i, j) < shortestPathNodeVal) {
                shortestPathNodeIdx = j;
                shortestPathNodeVal = aco.getProblem().getNij(i, j);
            }
        }

        double singleSelectRandom = selectRandom / ant.getNodesToVisit().size();


        //Assign probabilities
        double[] probability = new double[aco.getProblem().getNumberOfNodes()];
        double sumProbability = 0.0;

        for (Integer j : ant.getNodesToVisit()) {
            probability[j] = singleSelectRandom;
            sumProbability += probability[j];
        }

        probability[bestFeromoneNodeIdx] += selectBestPheromone;
        sumProbability += selectBestPheromone;

        probability[shortestPathNodeIdx] += selectShortestPath;
        sumProbability += selectShortestPath;

        checkState(sumProbability != 0.0, "The sum cannot be 0.0");

        // Select the next node by probability
        nextNode = antSelection.select(probability, sumProbability);

        checkState(nextNode != -1, "The next node should not be -1");

        return nextNode;
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
}
