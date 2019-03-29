#ifndef TEST_BUILD_AIAGENT_H
#define TEST_BUILD_AIAGENT_H


#include "Agent.h"
#include "MinMaxAlphaBeta.h"

class AIAgent : public Agent {
public:
    AIAgent(State state);

    void TakeTurn();

    MinMaxAlphaBeta min_max;
};


#endif //TEST_BUILD_AIAGENT_H
