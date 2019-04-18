#ifndef TEST_BUILD_AIAGENT_H
#define TEST_BUILD_AIAGENT_H

#include "Agent.h"
#include "MinMaxAlphaBeta.h"

class AIAgent : public Agent {
public:
    AIAgent(bool is_ai_player1 = true);

    void TakeTurn() override;

    MinMaxAlphaBeta min_max;

    std::string name = "AI Agent";
};


#endif //TEST_BUILD_AIAGENT_H
