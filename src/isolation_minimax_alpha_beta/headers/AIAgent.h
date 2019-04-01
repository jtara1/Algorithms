#ifndef TEST_BUILD_AIAGENT_H
#define TEST_BUILD_AIAGENT_H

#include "Agent.h"
#include "MinMaxAlphaBeta.h"

class AIAgent : public Agent {
public:
    AIAgent();

    void TakeTurn() override;

    MinMaxAlphaBeta min_max;

    std::string name = "AI Agent";
};


#endif //TEST_BUILD_AIAGENT_H
