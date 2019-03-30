#include <iostream>
#include <map>

#include "headers/HumanAgent.h"
#include "headers/BoardAction.h"

std::map<char, int> HumanAgent::rows;

HumanAgent::HumanAgent() : Agent() {
    if (HumanAgent::rows.empty()) {
        HumanAgent::rows['a'] = 0;
        HumanAgent::rows['b'] = 1;
        HumanAgent::rows['c'] = 2;
        HumanAgent::rows['d'] = 3;
        HumanAgent::rows['e'] = 4;
        HumanAgent::rows['f'] = 5;
        HumanAgent::rows['g'] = 6;
        HumanAgent::rows['h'] = 7;
    }

}

HumanAgent::HumanAgent(Board &state) : HumanAgent() {
    SetBoard(state);
}

void HumanAgent::TakeTurn() {
    std::string input;
    std::cout << "enter action: ";
    std::cin >> input;

    // take input eg: D4 -> index for board array, i = 27
    char col = char(tolower(input[0]));
    char row = input[1] - '0';

    // move player piece from spot to D4 if legal
    // if it's a legal move
    if (state.IsLegalMove(row, col)) {
        // create the aciton for this move
        BoardAction action = BoardAction()
        // do move with board
    }
}