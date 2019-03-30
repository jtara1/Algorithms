#ifndef TEST_BUILD_HUMANAGENT_H
#define TEST_BUILD_HUMANAGENT_H


#include <map>
#include "Agent.h"

class HumanAgent : public Agent {
public:
    static std::map<char, int> rows;

    HumanAgent();
    HumanAgent(Board &state);

    void TakeTurn() override;
};


#endif //TEST_BUILD_HUMANAGENT_H
