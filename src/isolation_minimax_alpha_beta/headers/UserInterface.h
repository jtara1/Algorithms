#ifndef TEST_BUILD_USERINTERFACE_H
#define TEST_BUILD_USERINTERFACE_H


#include "Agent.h"

class UserInterface {
public:
    UserInterface(Agent &agent1, Agent &agent2, bool agent1_goes_first);
    UserInterface(Agent* agent1, Agent* agent2, bool agent1_goes_first);

    void Start();

    std::array<Agent,2> agents;
    std::array<Agent*, 2> agent_pointers;
    bool is_agent1_turn;
    Board board;
};


#endif //TEST_BUILD_USERINTERFACE_H
