#ifndef PROJECT_STATE_H
#define PROJECT_STATE_H

#include <vector>

#include "Action.h"

class State {
public:
    State();

    virtual bool IsTerminal();

    virtual float GetValue();

    virtual std::vector<Action> Actions();
};

#endif //PROJECT_STATE_H
