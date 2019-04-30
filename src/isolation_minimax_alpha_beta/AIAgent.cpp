#include <iostream>

#include "headers/AIAgent.h"
#include "headers/MinMaxAlphaBeta.h"

AIAgent::AIAgent(bool is_ai_player1, float time_limit_sec) : Agent() {
    min_max = MinMaxAlphaBeta(is_ai_player1, time_limit_sec);

    this->is_ai_player1 = is_ai_player1;
    this->time_limit_sec = time_limit_sec;
}

void AIAgent::TakeTurn() {
    std::cout << "begin AIAgent turn\n";
    MinMaxAlphaBeta min_max = MinMaxAlphaBeta(is_ai_player1, time_limit_sec);
    BoardAction action = min_max.FindAction(state_pointer);

    std::cout << min_max.GetTime() << " s\n";
    std::cout << action << '\n'; // print out where we are moving to

    BoardAction::Results(*state_pointer, action, true); // mutate shared ref to board
}
