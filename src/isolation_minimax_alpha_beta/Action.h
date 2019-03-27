#ifndef PROJECT_ACTION_H
#define PROJECT_ACTION_H

#include <vector>

class State;

class Action {
public:
    // attrs
    int start;
    int end;

    // constructor
    Action(int start, int end);

    // methods
    State Results();
};

#endif //PROJECT_ACTION_H
