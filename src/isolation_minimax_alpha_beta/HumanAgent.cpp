#include <iostream>
#include <map>

#include "headers/HumanAgent.h"

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

HumanAgent::HumanAgent(Board &state) : Agent(state) {
    HumanAgent();
    SetBoard(state);
}

void HumanAgent::TakeTurn() {
    std::string input;
    std::cout << "enter action: ";
    std::cin >> input;

    // take input eg: D4
    char col = input[0];
    char row = input[1];

    // move player piece from spot to D4 if legal

}