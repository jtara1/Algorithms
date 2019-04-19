#ifndef TEST_BUILD_USERINTERFACE_H
#define TEST_BUILD_USERINTERFACE_H


#include "Agent.h"
#include "HumanAgent.h"
#include "AIAgent.h"

class UserInterface {
public:
    UserInterface();
    UserInterface(Agent* agent1, Agent* agent2, bool agent1_goes_first);
    UserInterface(Agent* agent1, Agent* agent2, bool agent1_goes_first, Board board);

    bool shouldAIGoFirst();
    float askForTimeLimitForAITurn();

    void Start();

    std::array<Agent*, 2> agent_pointers;
    bool is_agent1_turn;
    Board board;
    Board* board_pointer;

private:
    AIAgent agent1;
    HumanAgent agent2;
};


#endif //TEST_BUILD_USERINTERFACE_H
