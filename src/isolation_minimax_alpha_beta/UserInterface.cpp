#include <utility>

#include <iostream>

#include "headers/UserInterface.h"
#include "headers/Agent.h"
#include "headers/AIAgent.h"

/* make a UI, board, 1 Human Agent or AI Agent, and 1 AI Agent */
UserInterface::UserInterface() {
    this->board = Board();
    board_pointer = &(this->board);

    int ai_goes_first = shouldAIGoFirst();
    float time_limit_per_turn_for_ai = askForTimeLimitForAITurn();

    agent1 = AIAgent((bool)ai_goes_first, time_limit_per_turn_for_ai);
    agent2 = HumanAgent();
    ai_agent2 = AIAgent(!(bool)ai_goes_first, time_limit_per_turn_for_ai);

    Agent* agent2_ptr;

    if (ai_goes_first == 2) agent2_ptr = &ai_agent2;
    else agent2_ptr = &agent2;

    agent_pointers[0] = &agent1;
    agent_pointers[1] = agent2_ptr;

    agent1.SetBoard(board_pointer);
    agent2_ptr->SetBoard(board_pointer);

    agent1.is_player1 = (bool)ai_goes_first;
    agent2_ptr->is_player1 = !(bool)ai_goes_first;

    is_agent1_turn = (bool)ai_goes_first;
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

        agent_pointers[!is_agent1_turn]->TakeTurn();
        is_agent1_turn = !is_agent1_turn;
    }

    std::cout << board_pointer->Repr() << '\n';
    std::cout << "game over\n";
    std::cout << "winner is Player " << board_pointer->GetWinnerRepr() << '\n';

    std::cin >> temp;
}

int UserInterface::shouldAIGoFirst() {
    std::cout << "who goes first, [c]omputer or [o]pponent,\nor watch AI vs AI [p]lay ?\n";

    char response;
    std::cin >> response;
    response = char(tolower(response));

    if (response == 'c') return 1;
    if (response == 'o') return 0;
    if (response == 'p') return 2;

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

    return (float)time_limit; // todo: define 0.01 in MinMaxAlphaBeta class
}

