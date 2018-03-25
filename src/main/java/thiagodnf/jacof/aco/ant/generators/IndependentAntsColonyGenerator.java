package thiagodnf.jacof.aco.ant.generators;

import thiagodnf.jacof.aco.ACO;
import thiagodnf.jacof.aco.ant.Ant;
import thiagodnf.jacof.aco.ant.initialization.AnAntAtEachVertex;

import java.util.Random;


public class IndependentAntsColonyGenerator {
    public static Ant[] generate(int numberOfAnts, ACO aco) {
        Ant[] ants = new Ant[numberOfAnts];
        Random r = new Random();
        for (int id = 0; id < numberOfAnts; id++) {
            Ant ant = new Ant(aco, id);

            ant.setAlpha(r.nextGaussian() + 2.0);
            ant.setBeta(r.nextGaussian() + 3.0);

            // ant.setAntExploration(new AntTypeBasedExploration(aco, new RouletteWheel()));
            // ant.setAntLocalUpdate();
            ant.setAntInitialization(new AnAntAtEachVertex(aco));
            ant.addObserver(aco);
            ants[id] = ant;
        }
        return ants;
    }
}