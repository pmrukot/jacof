package thiagodnf.jacof.aco;

import thiagodnf.jacof.aco.ant.Ant;
import thiagodnf.jacof.aco.ant.exploration.IndependentPseudoRandomProportionalRule;
import thiagodnf.jacof.aco.ant.initialization.AnAntAtEachVertex;
import thiagodnf.jacof.aco.ant.selection.RouletteWheel;
import thiagodnf.jacof.aco.graph.initialization.ASInitialization;
import thiagodnf.jacof.aco.rule.globalupdate.deposit.FullDeposit;
import thiagodnf.jacof.aco.rule.globalupdate.evaporation.FullEvaporation;
import thiagodnf.jacof.problem.Problem;

import java.util.Random;

public class IndependentAntSystem extends ACO {

    public IndependentAntSystem(Problem problem) {
        super(problem);
    }

    @Override
    public void build() {
        setGraphInitialization(new ASInitialization(this));
        setAntInitialization(new AnAntAtEachVertex(this));

        setAntExploration(new IndependentPseudoRandomProportionalRule(this, new RouletteWheel()));

        // Global Update Pheromone Rule
        getEvaporations().add(new FullEvaporation(this, rho));
        getDeposits().add(new FullDeposit(this));
    }


    @Override
    protected void initializeAnts() {
        LOGGER.debug("Initializing the ants");

        Random r = new Random();

        this.ants = new Ant[numberOfAnts];

        for (int i = 0; i < numberOfAnts; i++) {
            ants[i] = new Ant(this, i);

            ants[i].setAntInitialization(getAntInitialization());
            ants[i].addObserver(this);

            ants[i].setAlpha(r.nextGaussian() + 2.0);
            ants[i].setBeta(r.nextGaussian() + 3.0);
        }
    }

    @Override
    public String toString() {
        return IndependentAntSystem.class.getSimpleName();
    }
}
