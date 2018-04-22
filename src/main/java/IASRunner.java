import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import thiagodnf.jacof.aco.AntSystem;
import thiagodnf.jacof.aco.IndependentAntSystem;
import thiagodnf.jacof.problem.Problem;
import thiagodnf.jacof.problem.tsp.TravellingSalesmanProblem;
import thiagodnf.jacof.util.ExecutionStats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IASRunner {
    /**
     * The class logger
     */
    static final Logger LOGGER = Logger.getLogger(IASRunner.class);

    private static final Integer numberOfAnts = 100;
    private static final Integer numberOfIterations = 100;
    private static final Double rho = 0.01;
    private static final int numberOfRepetitions = 3;


    public static void main(String[] args) throws ParseException, IOException {

        Problem tspProblem = new TravellingSalesmanProblem("src/main/resources/problems/tsp/berlin52.tsp");

        runDifferentBetas("exp", tspProblem);
    }

    private static void runDifferentBetas(String experimentName, Problem tspProblem) {
        List<Double> betaValues = new ArrayList<>();
        betaValues.add(3.0);
        betaValues.add(2.5);
        betaValues.add(2.0);
        betaValues.add(3.5);
        betaValues.add(4.0);

        for (Double beta : betaValues) {
            runDifferentAlphas(experimentName + "_beta" + beta,
                    tspProblem, beta);
        }
    }

    private static void runDifferentAlphas(String experimentName, Problem tspProblem, Double beta) {
        List<Double> alphaValues = new ArrayList<>();
        alphaValues.add(2.0);
        alphaValues.add(1.5);
        alphaValues.add(1.0);
        alphaValues.add(2.5);
        alphaValues.add(3.0);

        for (Double alpha : alphaValues) {
            runDifferentGaussians(experimentName + "_alpha" + alpha,
                    tspProblem, beta, alpha);
        }
    }

    private static void runDifferentGaussians(String experimentName, Problem tspProblem, Double beta, Double alpha) {
        List<Double> gaussianValues = new ArrayList<>();
        gaussianValues.add(1.0);
        gaussianValues.add(0.5);
        gaussianValues.add(2.0);

        for (Double gaussianMul : gaussianValues) {

            runExperiments(experimentName + "_gaussian" + gaussianMul,
                    tspProblem, alpha, beta, gaussianMul);
        }
    }

    private static void runExperiments(String experimentName, Problem tspProblem, Double alpha, Double beta, Double gaussianMul) {
        LOGGER.info("Running experiment: " + experimentName);
        for (int i = 0; i < numberOfRepetitions; i++) {
            String experimentId = experimentName + "-" + Integer.toString(i);

            // Independent Ant System
            IndependentAntSystem tspIAS = new IndependentAntSystem(tspProblem);
            tspIAS.setNumberOfAnts(numberOfAnts);
            tspIAS.setNumberOfIterations(numberOfIterations);
            tspIAS.setRho(rho);
            tspIAS.setGaussianMul(gaussianMul);
            tspIAS.setExperimentId(experimentId);
            tspIAS.setAlpha(alpha);
            tspIAS.setBeta(beta);

            ExecutionStats iasES = ExecutionStats.execute(tspIAS, tspProblem);


            // Regular Ant System
            AntSystem tspAS = new AntSystem(tspProblem);
            tspAS.setNumberOfAnts(numberOfAnts);
            tspAS.setNumberOfIterations(numberOfIterations);
            tspAS.setRho(rho);
            tspAS.setExperimentId(experimentId);
            tspAS.setAlpha(alpha);
            tspAS.setBeta(beta);

            ExecutionStats asES = ExecutionStats.execute(tspAS, tspProblem);

            // Results
            iasES.printStats();
            asES.printStats();
        }
    }


}
