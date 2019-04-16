#include <iostream>

#include "headers/UserInterface.h"
#include "headers/Agent.h"

UserInterface::UserInterface(Agent *agent1, Agent *agent2, bool agent1_goes_first) {
//    this->board = Board();
/*
     *      A # # # # # # # #
            B - - - # # - # #
            C - - # - # X # #
            D - - - # # - # #
            E - - - O # # # #
            F - - - - - # - -
            G - - - - - - - -
            H - - - - - - - #
     */
//    std::array<char, BOARD_AREA> board_repr = {
//            '#', '#', '#', '#', '#', '#', '#', '#',
//            '-', '-', '-', '#', '#', '-', '#', '#',
//            '-', '-', '#', '-', '#', 'X', '#', '#',
//            '-', '-', '-', '#', '#', '-', '#', '#',
//            '-', '-', '-', 'O', '#', '#', '#', '#',
//            '-', '-', '-', '-', '-', '#', '-', '-',
//            '-', '-', '-', '-', '-', '-', '-', '-',
//            '-', '-', '-', '-', '-', '-', '-', '#'
//    };
//
//    this->board = Board(board_repr, 21, 35);

//    this->board = Board();

    // #: 35, -: 45, O: 79, X: 88
    std::array<char, BOARD_AREA> board_repr = {
            35, 35, 35, 35, 35, 35, 35, 79,
            45, 45, 45, 45, 45, 45, 45, 88,
            45, 45, 35, 45, 35, 45, 35, 35,
            45, 45, 45, 35, 35, 45, 35, 35,
            45, 45, 45, 45, 35, 35, 35, 35,
            45, 45, 45, 45, 45, 35, 45, 35,
            45, 45, 45, 45, 45, 45, 45, 35,
            45, 45, 45, 45, 45, 45, 45, 35
    };

    this->board = Board(board_repr, 15, 7, true);


    board_pointer = &board;

    agent_pointers[0] = agent1;
    agent_pointers[1] = agent2;
    agent1->SetBoard(board_pointer);
    agent2->SetBoard(board_pointer);

    agent1->is_player1 = true;
    agent2->is_player1 = false;

    is_agent1_turn = agent1_goes_first;
}

void UserInterface::Start() {
    while (!(board_pointer->IsTerminal())) {
        std::cout << board_pointer->Repr() << '\n';
        agent_pointers[!is_agent1_turn]->TakeTurn();
        is_agent1_turn = !is_agent1_turn;
    }

    char temp;

    std::cout << board_pointer->Repr() << '\n';
    std::cout << "game over\n";
    std::cout << "winner is Player " << board_pointer->GetWinnerRepr() << '\n';

    std::cin >> temp;
}
