#include "AIAgent.h"
#include "MinMaxAlphaBeta.h"

AIAgent::AIAgent(State state) : Agent(state) {
    min_max = MinMaxAlphaBeta();
}

void AIAgent::TakeTurn() {
    State new_state = min_max.AlphaBetaSearch(state);
}
