#include <iostream>

#include "headers/UserInterface.h"
#include "headers/Agent.h"

UserInterface::UserInterface(Agent *agent1, Agent *agent2, bool agent1_goes_first) {
    this->board = Board();
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
}
