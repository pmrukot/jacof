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
        int numberOfExperiments = 5;

        for(int i = 0; i < numberOfExperiments; i++){
            String experimentId = "enter-id-here" + Integer.toString(i);

            String tspInstance = "src/main/resources/problems/tsp/oliver30.tsp";
            Problem tspProblem = new TravellingSalesmanProblem(tspInstance);
            Integer numberOfAnts = 50;
            Integer numberOfiterations = 100;
            Double ASAlpha = 2.0;
            Double ASBeta = 3.0;
            Double Rho = 0.01;

            IndependentAntSystem tspIAS = new IndependentAntSystem(tspProblem);
            tspIAS.setExperimentId(experimentId);
            tspIAS.setNumberOfAnts(numberOfAnts);
            tspIAS.setNumberOfIterations(numberOfiterations);
            tspIAS.setRho(Rho);

            ExecutionStats iasES = ExecutionStats.execute(tspIAS, tspProblem);

            AntSystem tspAS = new AntSystem(tspProblem);
            tspAS.setExperimentId(experimentId);
            tspAS.setNumberOfAnts(numberOfAnts);
            tspAS.setNumberOfIterations(numberOfiterations);
            tspAS.setAlpha(ASAlpha);
            tspAS.setBeta(ASBeta);
            tspAS.setRho(Rho);

            ExecutionStats asES = ExecutionStats.execute(tspAS, tspProblem);

            iasES.printStats();
            asES.printStats();
        }
    }

}
