#ifndef TEST_BUILD_AGENT_H
#define TEST_BUILD_AGENT_H


#include "State.h"

class Agent {
public:
    Agent(State state);

    State TakeTurn();

    void SetState(State &state);

    State state;
};


#endif //TEST_BUILD_AGENT_H
