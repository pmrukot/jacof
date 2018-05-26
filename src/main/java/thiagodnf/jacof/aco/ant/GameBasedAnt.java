package thiagodnf.jacof.aco.ant;

import thiagodnf.jacof.aco.ACO;

public class GameBasedAnt extends Ant {

    protected double selectBestPheromone;
    protected double selectShortestPath;
    protected double selectRandom;

    public GameBasedAnt(ACO aco, int id) {
        super(aco, id);
    }

    public double getSelectBestPheromone() {
        return selectBestPheromone;
    }

    public void setSelectBestPheromone(double selectBestPheromone) {
        this.selectBestPheromone = selectBestPheromone;
    }

    public double getSelectShortestPath() {
        return selectShortestPath;
    }

    public void setSelectShortestPath(double selectShortestPath) {
        this.selectShortestPath = selectShortestPath;
    }

    public double getSelectRandom() {
        return selectRandom;
    }

    public void setSelectRandom(double selectRandom) {
        this.selectRandom = selectRandom;
    }

    /**
     * Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return "Ant_" + id +
                " selectBestPheromone: " + selectBestPheromone +
                " selectShortestPath: " + selectShortestPath +
                " selectRandom: " + selectRandom;
    }
}
