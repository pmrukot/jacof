package thiagodnf.jacof.aco.ant.generators;

import thiagodnf.jacof.aco.ACO;
import thiagodnf.jacof.aco.ant.Ant;

import java.util.Random;

public class IndependentAntsColonyGenerator {
    public static Ant[] generate(int numberOfAnts, ACO aco) {
        Random r = new Random();

        Ant[] ants = new Ant[numberOfAnts];

        for (int i = 0; i < numberOfAnts; i++) {
            ants[i] = new Ant(aco, i);

            ants[i].setAntInitialization(aco.getAntInitialization());
            ants[i].addObserver(aco);

            ants[i].setAlpha(r.nextGaussian() + 2.0);
            ants[i].setBeta(r.nextGaussian() + 3.0);
        }
        return ants;
    }
}