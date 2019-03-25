#ifndef PROJECT_STATE_H
#define PROJECT_STATE_H

#include <vector>

#include "Action.h"

class State {
public:
    State();

    bool IsTerminal();
    float GetValue();
    std::vector<Action> Actions();
};

#endif //PROJECT_STATE_H
