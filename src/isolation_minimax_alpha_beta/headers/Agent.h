#ifndef TEST_BUILD_AGENT_H
#define TEST_BUILD_AGENT_H

#include <string>

#include "Board.h"

class Agent {
public:
    Agent() = default;
    Agent(Board* state);

    virtual void TakeTurn() = 0;

    void SetBoard(Board* state);

    bool is_player1;

    std::string name = "Agent";

protected:
    Board* state_pointer;
};


#endif //TEST_BUILD_AGENT_H
