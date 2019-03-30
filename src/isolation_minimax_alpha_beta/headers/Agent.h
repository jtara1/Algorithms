#ifndef TEST_BUILD_AGENT_H
#define TEST_BUILD_AGENT_H

#include "Board.h"

class Agent {
public:
    Agent();
    Agent(Board &state);

    virtual void TakeTurn();

    void SetBoard(Board &state);

    bool is_player1;

private:
    Board state;
};


#endif //TEST_BUILD_AGENT_H
