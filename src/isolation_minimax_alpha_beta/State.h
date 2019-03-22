#ifndef PROJECT_STATE_H
#define PROJECT_STATE_H

#include <vector>

public class State {
public:
    bool IsSolution();
    float GetValue();
    std::vector<Action> Actions();
};

#endif //PROJECT_STATE_H
