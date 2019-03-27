#ifndef PROJECT_STATE_H
#define PROJECT_STATE_H

#include <vector>

#include "Action.h"

class State {
public:
    State();

    virtual bool IsTerminal();

    /**
     * Higher the value, the better the state is for the AI
     * Includes the estimated cost + cost to an AI's goal state
     * @return
     */
    virtual float GetValue();

    virtual std::vector<Action> Actions();
};

#endif //PROJECT_STATE_H
