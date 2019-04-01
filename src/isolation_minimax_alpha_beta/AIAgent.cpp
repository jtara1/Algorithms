#include <iostream>
#include "headers/AIAgent.h"
#include "headers/MinMaxAlphaBeta.h"

AIAgent::AIAgent() : Agent() {

}

void AIAgent::TakeTurn() {
    std::string input;
    char temp;

    min_max.AlphaBetaSearch(*state_pointer);
    std::cout << "done\n";
    std::cin >> temp;
}
