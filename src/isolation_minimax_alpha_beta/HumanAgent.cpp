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

HumanAgent::HumanAgent(Board* state) : HumanAgent() {
    SetBoard(state);
}

void HumanAgent::TakeTurn() {
    std::string input;

    while (true) {
        std::cout << "enter action player " << (is_player1 ? 1 : 2) << ": ";
        std::cin >> input;
        // take input eg: D4 -> index for board_pointer array, i = 27
        int row = rows.at(char(tolower(input[0])));
        int col = input[1] - '0' - 1;

        // move player piece from spot to D4 if legal
        if (state_pointer->IsLegalMove(row, col)) {
            // do move with board_pointer
            state_pointer->MovePlayer(row, col);
            break;
        } else {
            std::cout << "illegal move\n";
        }
    }
}