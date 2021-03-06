import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import thiagodnf.jacof.aco.AntSystem;
import thiagodnf.jacof.problem.Problem;
import thiagodnf.jacof.problem.tsp.TravellingSalesmanProblem;
import thiagodnf.jacof.util.ExecutionStats;

import java.io.IOException;

public class ASRunner {

	/** The class logger*/
	static final Logger LOGGER = Logger.getLogger(ASRunner.class);

	public static void main(String[] args) throws ParseException, IOException {

		String instance = "src/main/resources/problems/tsp/oliver30.tsp";

		Problem problem = new TravellingSalesmanProblem(instance);

		AntSystem aco = new AntSystem(problem);
        aco.setNumberOfAnts(50);
        aco.setNumberOfIterations(100);
        aco.setAlpha(2.0);
        aco.setBeta(3.0);
        aco.setRho(0.01);

		ExecutionStats es = ExecutionStats.execute(aco, problem);
		es.printStats();
	}

}
