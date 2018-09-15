package thiagodnf.jacof.aco.ant;

import thiagodnf.jacof.aco.ACO;

public class GameBasedAnt extends Ant {

    protected double bestPheromonePathProbability;
    protected double shortestPathProbability;
    protected double randomChooseProbability;
    protected double bestPheromoneAndShortestPathProbability;
    protected double NOTBestPheromoneAndShortestPath;

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
        return "GameBasedAnt{" +
                "bestPheromonePathProbability=" + bestPheromonePathProbability +
                ", shortestPathProbability=" + shortestPathProbability +
                ", randomChooseProbability=" + randomChooseProbability +
                ", bestPheromoneAndShortestPathProbability=" + bestPheromoneAndShortestPathProbability +
                ", NOTBestPheromoneAndShortestPath=" + NOTBestPheromoneAndShortestPath +
                '}';
    }

    public double getBestPheromoneAndShortestPathProbability() {
        return bestPheromoneAndShortestPathProbability;
    }

    public void setBestPheromoneAndShortestPathProbability(double bestPheromoneAndShortestPathProbability) {
        this.bestPheromoneAndShortestPathProbability = bestPheromoneAndShortestPathProbability;
    }

    public double getNOTBestPheromoneAndShortestPath() {
        return NOTBestPheromoneAndShortestPath;
    }

    public void setNOTBestPheromoneAndShortestPath(double NOTBestPheromoneAndShortestPath) {
        this.NOTBestPheromoneAndShortestPath = NOTBestPheromoneAndShortestPath;
    }
}
