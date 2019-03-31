#include "headers/Agent.h"

Agent::Agent(Board* state) {
    SetBoard(state);
}

void Agent::SetBoard(Board* state) {
    this->state_pointer = state;
}

Agent::Agent() {

}
