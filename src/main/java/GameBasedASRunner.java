import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import thiagodnf.jacof.aco.MixedGameBasedAntSystem;
import thiagodnf.jacof.problem.Problem;
import thiagodnf.jacof.problem.tsp.TravellingSalesmanProblem;
import thiagodnf.jacof.util.ExecutionStats;

import java.io.IOException;

public class GameBasedASRunner {
    /**
     * The class logger
     */
    static final Logger LOGGER = Logger.getLogger(GameBasedASRunner.class);

    private static final Integer numberOfAnts = 100;
    private static final Integer numberOfIterations = 100;
    private static final int numberOfRepetitions = 3;
    private static final Double rho = 0.01;

    public static void main(String[] args) throws ParseException, IOException {

        Problem tspProblem = new TravellingSalesmanProblem("src/main/resources/problems/tsp/berlin52.tsp");
        String experimentName = "GB_10-90_10p";
        double alpha = 2.0;
        double beta = 3.0;

        String csvFile = "src/main/resources/ants_2_copy.csv";

        for (int i = 0; i < numberOfRepetitions; i++) {

            String experimentId = experimentName + "-" + Integer.toString(i);

//            // Regular Ant System
//
//            AntSystem tspAS = new AntSystem(tspProblem);
//            tspAS.setNumberOfAnts(numberOfAnts);
//            tspAS.setNumberOfIterations(numberOfIterations);
//            tspAS.setRho(rho);
//            tspAS.setExperimentId(experimentId);
//            tspAS.setAlpha(alpha);
//            tspAS.setBeta(beta);
//
//            ExecutionStats asES = ExecutionStats.execute(tspAS, tspProblem);


            // Mixed Game Based Ant System which is sourced from csv file

            MixedGameBasedAntSystem tspCsvGbAS = new MixedGameBasedAntSystem(tspProblem, csvFile);
            tspCsvGbAS.setNumberOfAnts(numberOfAnts);
            tspCsvGbAS.setNumberOfIterations(numberOfIterations);
            tspCsvGbAS.setRho(rho);
            tspCsvGbAS.setExperimentId(experimentId);
            tspCsvGbAS.setAlpha(alpha);
            tspCsvGbAS.setBeta(beta);

            ExecutionStats csvGbAsES = ExecutionStats.execute(tspCsvGbAS, tspProblem);

            // Results
//            asES.printStats();
            csvGbAsES.printStats();
        }
    }


}
