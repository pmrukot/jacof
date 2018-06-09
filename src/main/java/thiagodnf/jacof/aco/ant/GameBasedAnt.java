package thiagodnf.jacof.aco.ant;

import thiagodnf.jacof.aco.ACO;

public class GameBasedAnt extends Ant {

    protected double bestPheromonePathProbability;
    protected double shortestPathProbability;
    protected double randomChooseProbability;

    public GameBasedAnt(ACO aco, int id) {
        super(aco, id);
    }

    public double getBestPheromonePathProbability() {
        return bestPheromonePathProbability;
    }

    public void setBestPheromonePathProbability(double bestPheromonePathProbability) {
        this.bestPheromonePathProbability = bestPheromonePathProbability;
    }

    public double getShortestPathProbability() {
        return shortestPathProbability;
    }

    public void setShortestPathProbability(double shortestPathProbability) {
        this.shortestPathProbability = shortestPathProbability;
    }

    public double getRandomChooseProbability() {
        return randomChooseProbability;
    }

    public void setRandomChooseProbability(double randomChooseProbability) {
        this.randomChooseProbability = randomChooseProbability;
    }

    /**
     * Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return "Ant_" + id +
                " bestPheromonePathProbability: " + bestPheromonePathProbability +
                " shortestPathProbability: " + shortestPathProbability +
                " randomChooseProbability: " + randomChooseProbability;
    }
}
