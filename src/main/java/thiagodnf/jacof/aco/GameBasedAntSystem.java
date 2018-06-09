package thiagodnf.jacof.aco;

import com.opencsv.CSVReader;
import thiagodnf.jacof.aco.ant.GameBasedAnt;
import thiagodnf.jacof.aco.ant.exploration.GameBasedSelection;
import thiagodnf.jacof.aco.ant.initialization.AnAntAtEachVertex;
import thiagodnf.jacof.aco.graph.initialization.ASInitialization;
import thiagodnf.jacof.aco.rule.globalupdate.deposit.FullDeposit;
import thiagodnf.jacof.aco.rule.globalupdate.evaporation.FullEvaporation;
import thiagodnf.jacof.problem.Problem;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameBasedAntSystem extends ACO {

    private String csvFileName;

    public GameBasedAntSystem(Problem problem) {
        super(problem);
    }

    public GameBasedAntSystem(Problem problem, String csvFileName) {
        super(problem);
        this.csvFileName = csvFileName;
    }

    public String getCsvFileName() {
        return csvFileName;
    }

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    @Override
    public void build() {
        setGraphInitialization(new ASInitialization(this));
        setAntInitialization(new AnAntAtEachVertex(this));
        setAntExploration(new GameBasedSelection(this));

        // Global Update Pheromone Rule
        getEvaporations().add(new FullEvaporation(this, rho));
        getDeposits().add(new FullDeposit(this));
    }


    @Override
    protected void initializeAnts() {
        LOGGER.debug("Initializing the ants");

        List<String[]> antLines = new ArrayList<>();

        LOGGER.info(csvFileName);
        try (
                Reader reader = Files.newBufferedReader(Paths.get(csvFileName));
                CSVReader csvReader = new CSVReader(reader);
        ) {
            antLines = csvReader.readAll();
            LOGGER.info("size: " + antLines.size());

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        setNumberOfAnts(antLines.size());
        this.ants = new GameBasedAnt[numberOfAnts];

        LOGGER.info("CSV-based colony:");
        for (int i = 0; i < numberOfAnts; i++) {
            GameBasedAnt newAnt = new GameBasedAnt(this, i);

            newAnt.setAntInitialization(getAntInitialization());
            newAnt.addObserver(this);

            newAnt.setShortestPathProbability(Double.parseDouble(antLines.get(i)[0]));
            newAnt.setBestPheromonePathProbability(Double.parseDouble(antLines.get(i)[1]));
            newAnt.setRandomChooseProbability(Double.parseDouble(antLines.get(i)[2]));
//            LOGGER.info(newAnt);

            ants[i] = newAnt;
        }
    }

    @Override
    public String toString() {
        return GameBasedAntSystem.class.getSimpleName();
    }
}
