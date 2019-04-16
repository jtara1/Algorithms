#include <iostream>

#include "headers/AIAgent.h"
#include "headers/MinMaxAlphaBeta.h"

AIAgent::AIAgent() : Agent() {

}

void AIAgent::TakeTurn() {
    std::string input;
    char temp;

    BoardAction action = min_max.FindAction(state_pointer);

    std::cout << min_max.GetTime() << " s\n";

    std::cout << action << '\n';
    BoardAction::Results(*state_pointer, action, true);
}
