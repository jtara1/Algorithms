#include <iostream>

#include "headers/AIAgent.h"
#include "headers/MinMaxAlphaBeta.h"


void AIAgent::TakeTurn() {
    BoardAction action = min_max.FindAction(state_pointer);

    std::cout << min_max.GetTime() << " s\n";
    std::cout << action << '\n';

    BoardAction::Results(*state_pointer, action, true); // mutate shared ref to board
}

AIAgent::AIAgent(bool is_ai_player1) {
    min_max = MinMaxAlphaBeta(is_ai_player1);
}
