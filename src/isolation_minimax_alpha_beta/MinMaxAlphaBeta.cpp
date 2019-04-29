#include <limits>
#include <vector>
#include <algorithm>
#include <iterator>
#include <tuple>
#include <iostream>

#include "headers/MinMaxAlphaBeta.h"
#include "headers/Board.h"
#include "headers/BoardAction.h"
#include "headers/IndexScoreTuple.h"

MinMaxAlphaBeta::MinMaxAlphaBeta(bool is_ai_player1, float time_limit_sec) {
    std::random_device rd;
    mt = std::mt19937(rd());

    max_time = time_limit_sec;
    this->is_ai_player1 = is_ai_player1;
    Reset();
}

BoardAction MinMaxAlphaBeta::FindAction(Board* state_ptr) {
    this->clock_start = std::clock();
    this->started_search = true;

    this->state = *state_ptr;
    this->state_ptr = &(this->state);

    bool pick_randomly = (*state_ptr).GetTurnCount() <= 4;

    std::vector<BoardAction> quick_actions = BoardAction::Actions(state, false, !pick_randomly);
    if (quick_actions.empty()) throw std::invalid_argument("could not find any quick actions");

    if (pick_randomly) {
        BoardAction action = quick_actions.at((size_t)GetRandomIndex(quick_actions.size()));
        Reset();
        return action;
    }

    this->first_available_action = quick_actions.at(0); // get first action that's found that's legal

    BoardAction action = AlphaBetaSearch(*(this->state_ptr));
    std::cout << "is player 1: " << (is_ai_player1 ? "yes" : "no") << " depth: " << depth << " score: " << action.Results().GetScore() << '\n';
    std::cout << "actions index: " << root_actions_index << " root actions size: " << (*root_actions_ptr).size() << "\n";

    // reset to defaults
    Reset();

    return action;
}

// implementations
/*
 * function ALPHA-BETA-SEARCH(state_pointer) returns an action
        v ←MAX-VALUE(state_pointer,−∞,+∞)
        return the action in ACTIONS(state_pointer) with value v
 */
BoardAction MinMaxAlphaBeta::AlphaBetaSearch(Board board) {
    IndexScoreTuple neg_inf = IndexScoreTuple::NegInf();
    IndexScoreTuple inf = IndexScoreTuple::Inf();

    IndexScoreTuple best_value;
    if (is_ai_player1) best_value = MaxValue(
            board,
            neg_inf,
            inf,
            true
        );
    else best_value = MinValue(
            board,
            neg_inf,
            inf,
            true
        );


    this->best_action = GetRootAction(best_value.index);
    best_action_ptr = &best_action;

    max_depth += max_depth_step;
    if (GetTime() + this->time_limit_padding < max_time) {
        root_actions_index = -1;
        return AlphaBetaSearch(board);
    }

    return *best_action_ptr;
}

/*
 * function MAX-VALUE(state_pointer, α, β) returns a utility value
        if TERMINAL-TEST(state_pointer) then return UTILITY(state_pointer)
        v ← −∞
        for each a in ACTIONS(state_pointer) do
            v ← MAX(v, MIN-VALUE(RESULT(s,a), α, β))
            if v ≥ β then return v
            α←MAX(α, v)
        return v
 */
IndexScoreTuple MinMaxAlphaBeta::MaxValue(Board state, IndexScoreTuple& alpha, IndexScoreTuple& beta, bool is_root_recursion) {
    if (depth >= max_depth || state.IsTerminal()) return { root_actions_index, state.GetScore() };

    IndexScoreTuple value = IndexScoreTuple::NegInf();
    ++(this->depth);

    std::vector<BoardAction> actions = BoardAction::Actions(state);
    if (this->root_actions_ptr == nullptr) {
        this->root_actions = actions;
        root_actions_ptr = &root_actions;
    }

    for (auto it = actions.begin(); it != actions.end(); it++) {
        root_actions_index += is_root_recursion;

        BoardAction action = *it;

        Board next_board = action.Results();
        IndexScoreTuple min_value = MinValue(next_board, alpha, beta);
        value = IndexScoreTuple::Max(value, min_value);

        if (value >= beta) return value;
        alpha = IndexScoreTuple::Max(alpha, value);
    }

    return value;
}

/*
 * function MIN-VALUE(state_pointer, α, β) returns a utility value
        if TERMINAL-TEST(state_pointer) then return UTILITY(state_pointer)
        v ← +∞
        for each a in ACTIONS(state_pointer) do
            v ← MIN(v, MAX-VALUE(RESULT(s,a) , α, β))
            if v ≤ α then return v
            β ← MIN(β, v)
        return v
 */
IndexScoreTuple MinMaxAlphaBeta::MinValue(Board state, IndexScoreTuple& alpha, IndexScoreTuple& beta, bool is_root_recursion) {
    if (depth >= max_depth || state.IsTerminal()) return { root_actions_index, state.GetScore() };

    IndexScoreTuple value = IndexScoreTuple::Inf();
    ++(this->depth);

    std::vector<BoardAction> actions = BoardAction::Actions(state);
    if (this->root_actions_ptr == nullptr) {
        this->root_actions = actions;
        root_actions_ptr = &root_actions;
    }

    for (auto it = actions.begin(); it != actions.end(); it++) {
        root_actions_index += is_root_recursion;

        BoardAction action = *it;

        Board next_board = action.Results();
        IndexScoreTuple max_value = MaxValue(next_board, alpha, beta);
        value = IndexScoreTuple::Min(value, max_value);

        if (value <= alpha) return value;
        beta = IndexScoreTuple::Min(beta, value);
    }

    return value;
}

double MinMaxAlphaBeta::GetTime() {
    return (std::clock() - this->clock_start) / (double)CLOCKS_PER_SEC;
}

BoardAction MinMaxAlphaBeta::GetRootAction(int index, bool previous_call_failed) {
    try {
        if (previous_call_failed) index = GetRandomIndex((int)root_actions.size()); // random index in range of root_actions size

        bool in_range = index >= 0 && index < (*root_actions_ptr).size();

        if (!in_range && !previous_call_failed) {
//            std::cout << "warning: attempt choosing random action; index = " << index << " root actions size: " << (*root_actions_ptr).size() << std::endl;
            return GetRootAction(index, true); // attempt to pick a random root action
        } else if (!in_range) { // not in range and previous method call failed
//            std::cout << "warning: quickest action chosen; index = " << index << " root actions size: " << (*root_actions_ptr).size() <<std::endl;
            return first_available_action; // could not create root actions
        }

        return (*root_actions_ptr).at((size_t)index);
    } catch (const std::out_of_range &e) {
        return first_available_action;
    }
}

void MinMaxAlphaBeta::Reset() {
    state = Board();
    state_ptr = nullptr;

    best_action_ptr = nullptr;
    best_action = BoardAction();

    max_depth = max_depth_default;
    depth = 0;

    root_actions_index = -1;
    root_actions_ptr = nullptr;
    root_actions = std::vector<BoardAction>();

    started_search = false;
}

int MinMaxAlphaBeta::GetRandomIndex(int upper_limit) {
    return std::rand() / ((RAND_MAX + 1u) / upper_limit);
//    std::uniform_real_distribution<double> dist(0.0, (float)upper_limit);
//
//    return (int)dist(mt);
}
