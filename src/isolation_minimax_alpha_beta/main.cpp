#include <iostream>
#include <limits>
#include <vector>
#include <algorithm>
#include <iterator>

//#include "headers/BoardAction.h"
//#include "headers/State.h"
//#include "headers/Board.h"
#include "headers/UserInterface.h"
#include "headers/HumanAgent.h"
#include "headers/AIAgent.h"

int main() {
//    Board initBoard = Board();
//    BoardAction action = AlphaBetaSearch<BoardAction>(initBoard);
//    std::cout << action << std::endl;

//    Board* board = new Board();
    HumanAgent a1 = HumanAgent();
    HumanAgent a2 = HumanAgent();

//    UserInterface ui = UserInterface(a1, a2, true);
    UserInterface ui = UserInterface(&a1, &a2, true);
    ui.Start();

    char temp;
    std::cin >> temp;
    return 0;
}
