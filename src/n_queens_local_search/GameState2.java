package n_queens_local_search;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameState2 extends GameState {
    // attrs
    /* current column index that needs a queen placed here */
    private int columnIndex = 0;

    public static HashSet<String> solutionAttempts = new HashSet<String>();

    // get and set
    @Override
    public int getMaxFitness() {
        return 0;
    }

    public int getColumnIndex() { return columnIndex; }
    public void setColumnIndex(int value) { columnIndex = value; }

    public Set<String> getSolutionAttempts() { return Collections.synchronizedSet(solutionAttempts); }

    // static

    // constructors
    public GameState2(int size) {
        super(new Tile[size]);

//        getColumns()[0] = new Tile(random.nextInt(size), 0);
        getColumns()[0] = new Tile(0, 0);
        columnIndex = 0;
    }

    public GameState2(int size, Tile... rows) {
        super(rows);

        columnIndex = 0;
        for (int i = 1; i < size; ++i) {
            if (rows[i] == null) break;
            ++columnIndex;
        }
        this.size = size;
    }

    public GameState2(int size, HashSet<String> solutionAttempts, Tile... rows) {
        this(size, rows);
//        this.solutionAttempts = solutionAttempts;
    }

    public GameState2(int size, int... rowIndices) {
        super(rowIndices);
        this.size = size;
    }

    // methods
    @Override
    public State getRandomNeighbor() {
        return getRandomNeighbor(this);
    }

    private State getRandomNeighbor(GameState2 state2) {
        state2.setColumnIndex(state2.getColumnIndex() + 1);
        if (state2.getColumnIndex() >= state2.getSize()) return this;

        for (int i = 0; i < state2.getSize(); ++i) {
            GameState2 newState = (GameState2)state2.createNewState(state2.getColumnIndex(), i);

            if (!state2.getSolutionAttempts().contains(newState.toString()) && newState.isSubSolution()) {
                newState.getSolutionAttempts().add(newState.toString());

//                System.out.println(newState);
//                System.out.println(state2.getSolutionAttempts().size() + " " + state2.getColumnIndex());
                return newState;
            }

        }

        if (state2.backtrack()) return null;
        return state2.getRandomNeighbor();
    }

    private boolean backtrack() {
        if (columnIndex >= 1) {
            columnIndex -= 2;
            getColumns()[columnIndex + 1] = null;
            return false;
        } else {
            System.out.println("did all possible backtracking");
            return true;
        }
    }

    @Override
    public State createNewState(int col, int newRow) {
        GameState state_pointer = (GameState)super.createNewState(col, newRow);
        return new GameState2(size, solutionAttempts, state_pointer.getColumns());
    }

    @Override
    public float temperatureScheduling(int step) {
        return super.temperatureScheduling(step);
    }

    @Override
    public int energy() {
        if (energy != null) return energy;

        int queenAttacks = queensOnSameLines(rows, false);
        if (isSolution()) energy = -Integer.MAX_VALUE;
        else energy = queenAttacks;
//        else energy = queenAttacks + getReducedCostForQueensOnLOfAnotherQueen();

        energy = energy == 0 ? -1 : energy;
        return energy;
    }

    @Override
    public int fitness() {
        return 0;
    }

    @Override
    public State reproduce(State state_pointer) {
        return null;
    }

    @Override
    public State mutate() {
        return null;
    }

    @Override
    public boolean isSolution() {
        return columnIndex == (size - 1) && super.isSolution();
    }

    public boolean isSubSolution() {
//        return columnIndex != (size - 1) && super.isSolution();
        return super.isSolution();
    }

    @Override
    public int hashCode() {
        return super.toString().hashCode();
    }
}
