import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import thiagodnf.jacof.aco.AntSystem;
import thiagodnf.jacof.aco.IndependentAntSystem;
import thiagodnf.jacof.problem.Problem;
import thiagodnf.jacof.problem.tsp.TravellingSalesmanProblem;
import thiagodnf.jacof.util.ExecutionStats;

import java.io.IOException;

public class IASRunner {
    /** The class logger*/
    static final Logger LOGGER = Logger.getLogger(IASRunner.class);

    public static void main(String[] args) throws ParseException, IOException {

        String tspInstance = "src/main/resources/problems/tsp/berlin52.tsp";
        String experimentName = "enter-experiment-name-here";

        Problem tspProblem = new TravellingSalesmanProblem(tspInstance);
        Integer numberOfAnts = 100;
        Integer numberOfIterations = 100;
        Double alpha = 1.0;
        Double beta = 3.0;
        Double rho = 0.01;

        int numberOfExperiments = 5;

        for (int i = 0; i < numberOfExperiments; i++) {
            String experimentId = experimentName + "-" + Integer.toString(i);

            // Independent Ant System
            IndependentAntSystem tspIAS = new IndependentAntSystem(tspProblem);
            tspIAS.setExperimentId(experimentId);
            tspIAS.setNumberOfAnts(numberOfAnts);
            tspIAS.setNumberOfIterations(numberOfIterations);
            tspIAS.setAlpha(alpha);
            tspIAS.setBeta(beta);
            tspIAS.setRho(rho);

            ExecutionStats iasES = ExecutionStats.execute(tspIAS, tspProblem);


            // Regular Ant System
            AntSystem tspAS = new AntSystem(tspProblem);
            tspAS.setExperimentId(experimentId);
            tspAS.setNumberOfAnts(numberOfAnts);
            tspAS.setNumberOfIterations(numberOfIterations);
            tspAS.setAlpha(alpha);
            tspAS.setBeta(beta);
            tspAS.setRho(rho);

            ExecutionStats asES = ExecutionStats.execute(tspAS, tspProblem);

            // Results
            iasES.printStats();
            asES.printStats();
        }
    }

}
