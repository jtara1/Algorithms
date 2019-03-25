#include "State.h"

State::State() = default;

bool State::IsTerminal() {
    return false;
}

float State::GetValue() {
    return 0;
}

std::vector <Action> State::Actions() {
    return std::vector<Action>();
}
