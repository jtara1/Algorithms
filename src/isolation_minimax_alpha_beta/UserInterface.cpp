#include <utility>

#include <iostream>

#include "headers/UserInterface.h"
#include "headers/Agent.h"
#include "headers/AIAgent.h"


UserInterface::UserInterface() {
    this->board = Board();
    board_pointer = &(this->board);

    bool ai_goes_first = shouldAIGoFirst();
    float time_limit_per_turn_for_ai = askForTimeLimitForAITurn();

    agent1 = AIAgent(ai_goes_first, time_limit_per_turn_for_ai);
    agent2 = HumanAgent();

    agent_pointers[0] = &agent1;
    agent_pointers[1] = &agent2;

    agent1.SetBoard(board_pointer);
    agent2.SetBoard(board_pointer);

    agent1.is_player1 = ai_goes_first;
    agent2.is_player1 = !ai_goes_first;

    is_agent1_turn = ai_goes_first;
}

UserInterface::UserInterface(Agent *agent1, Agent *agent2, bool agent1_goes_first, Board board) {
    this->board = std::move(board);
    board_pointer = &(this->board);

    agent_pointers[0] = agent1;
    agent_pointers[1] = agent2;
    agent1->SetBoard(board_pointer);
    agent2->SetBoard(board_pointer);

    agent1->is_player1 = true;
    agent2->is_player1 = false;

    is_agent1_turn = agent1_goes_first;
}

UserInterface::UserInterface(Agent *agent1, Agent *agent2, bool agent1_goes_first) : UserInterface(agent1, agent2, agent1_goes_first, Board()) {

}

void UserInterface::Start() {
    char temp;

    while (!(board_pointer->IsTerminal())) {
        std::cout << board_pointer->Repr() << '\n';
//        std::cin >> temp;

        agent_pointers[!is_agent1_turn]->TakeTurn();
        is_agent1_turn = !is_agent1_turn;
    }

    std::cout << board_pointer->Repr() << '\n';
    std::cout << "game over\n";
    std::cout << "winner is Player " << board_pointer->GetWinnerRepr() << '\n';

    std::cin >> temp;
}

bool UserInterface::shouldAIGoFirst() {
    std::cout << "who goes first, [c]omputer or [o]pponent ?\n";

    char response;
    std::cin >> response;
    response = char(tolower(response));

    if (response == 'c') return true;
    if (response == 'o') return false;

    std::cout << "invalid response for who goes first\n";
    return shouldAIGoFirst();
}

float UserInterface::askForTimeLimitForAITurn() {
    std::cout << "enter time limit (seconds) per turn of AI: ";

    std::string response;
    std::cin >> response;

    double time_limit = ::atof(response.c_str());

    if (time_limit <= 0 || time_limit >= 100) {
        std::cout << "enter number for time limit in range (0, 100)\n";
        return askForTimeLimitForAITurn();
    }

    return (float)(time_limit - 0.01); // todo: define 0.01 in MinMaxAlphaBeta class
}

