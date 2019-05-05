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

        if (input.size() != 2) {
            std::cout << "invalid input. was given: " << input << "\nenter a letter for the row and number for the column\n";
            continue;
        }

        // take input eg: D4 -> index for board_pointer array, i = 27
        int row = char(tolower(input.at(0))) - 'a';
        int col = input.at(1) - '0' - 1;

        int next_pos = state_pointer->CoordsToBoardIndex(row, col);

        std::cout << "player is at position: " << state_pointer->GetPlayerPos() << '\n';
        std::cout << "attempting to move to row, col: " << row << ", " << col << "; next pos: " << next_pos << '\n';

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