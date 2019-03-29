#include "Agent.h"

Agent::Agent(State state) {
    SetState(state);
}

State Agent::TakeTurn() {
    return State();
}

void Agent::SetState(State &state) {
    this->state = state;
}
