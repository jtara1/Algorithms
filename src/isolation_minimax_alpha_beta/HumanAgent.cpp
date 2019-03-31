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

    // take input eg: D4 -> index for board_pointer array, i = 27
    int row = rows.at(tolower(input[0]));
    int col = input[1] - '0';

    while (true) {
        // move player piece from spot to D4 if legal
        if (state.IsLegalMove(row, col)) {
            // do move with board_pointer
            state.MovePlayer(row, col);
            break;
        } else {
            std::cout << "illegal move\n";
            break;
        }
    }
}