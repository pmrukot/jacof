import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import thiagodnf.jacof.aco.AntSystem;
import thiagodnf.jacof.aco.GameBasedAntSystem;
import thiagodnf.jacof.aco.ant.exploration.GameBasedSelection;
import thiagodnf.jacof.problem.Problem;
import thiagodnf.jacof.problem.tsp.TravellingSalesmanProblem;
import thiagodnf.jacof.util.ExecutionStats;

import java.io.IOException;

public class GameBasedASRunner {
    /**
     * The class logger
     */
    static final Logger LOGGER = Logger.getLogger(GameBasedASRunner.class);

    private static final Integer numberOfAnts = 50;
    private static final Integer numberOfIterations = 50;
    private static final Double rho = 0.01;
    private static final int numberOfRepetitions = 1;

    public static void main(String[] args) throws ParseException, IOException {

        Problem tspProblem = new TravellingSalesmanProblem("src/main/resources/problems/tsp/berlin52.tsp");
        String experimentName = "game_based";
        double alpha = 2.0;
        double beta = 3.0;

        double selectBestPheromone = 0.3;
        double selectShortestPath = 0.5;
        double selectRandom = 0.2;

        String csvFile = "src/main/resources/ants.csv";

        for (int i = 0; i < numberOfRepetitions; i++) {

            String experimentId = experimentName + "-" + Integer.toString(i);

            // Regular Ant System

            AntSystem tspAS = new AntSystem(tspProblem);
            tspAS.setNumberOfAnts(numberOfAnts);
            tspAS.setNumberOfIterations(numberOfIterations);
            tspAS.setRho(rho);
            tspAS.setExperimentId(experimentId);
            tspAS.setAlpha(alpha);
            tspAS.setBeta(beta);

            ExecutionStats asES = ExecutionStats.execute(tspAS, tspProblem);


            // Regular Ant System which is game  with set of params

            AntSystem tspGbAS = new AntSystem(tspProblem);
            tspGbAS.setAntExploration(new GameBasedSelection(tspAS, selectBestPheromone, selectShortestPath, selectRandom));
            tspGbAS.setNumberOfAnts(numberOfAnts);
            tspGbAS.setNumberOfIterations(numberOfIterations);
            tspGbAS.setRho(rho);
            tspGbAS.setExperimentId(experimentId);
            tspGbAS.setAlpha(alpha);
            tspGbAS.setBeta(beta);

            ExecutionStats gbasES = ExecutionStats.execute(tspGbAS, tspProblem);


            // Regular Ant System which is game based on csv file

            GameBasedAntSystem tspCsvGbAS = new GameBasedAntSystem(tspProblem, csvFile);
            tspCsvGbAS.setNumberOfAnts(numberOfAnts);
            tspCsvGbAS.setNumberOfIterations(numberOfIterations);
            tspCsvGbAS.setRho(rho);
            tspCsvGbAS.setExperimentId(experimentId);
            tspCsvGbAS.setAlpha(alpha);
            tspCsvGbAS.setBeta(beta);

            ExecutionStats csvGbAsES = ExecutionStats.execute(tspCsvGbAS, tspProblem);

            // Results
            asES.printStats();
            gbasES.printStats();
            csvGbAsES.printStats();
        }
    }


}
