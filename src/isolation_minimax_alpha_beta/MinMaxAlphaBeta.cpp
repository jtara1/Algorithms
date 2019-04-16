#include "headers/MinMaxAlphaBeta.h"

#include <limits>
#include <vector>
#include <algorithm>
#include <iterator>
#include <tuple>
#include <iostream>

#include "headers/Board.h"
#include "headers/BoardAction.h"
#include "headers/IndexScoreTuple.h"

MinMaxAlphaBeta::MinMaxAlphaBeta() {
    Reset();
}

//BoardAction MinMaxAlphaBeta::FindAction(Board& state) {
BoardAction MinMaxAlphaBeta::FindAction(Board* state_ptr) {
    this->clock_start = std::clock();
    this->started_search = true;

//    Board state_copy = state;
//    this->state = state;
//    this->state_ptr = &(this->state);

    this->state = *state_ptr;
    this->state_ptr = state_ptr;

    BoardAction action;
    action = AlphaBetaSearch(*(this->state_ptr));

    try {
//        action = AlphaBetaSearch(state);
//        action = AlphaBetaSearch(this->state);
//        action = AlphaBetaSearch(*(this->state_ptr));
//        action = AlphaBetaSearch(*state_ptr);

//    } catch (...) {
    } catch (const std::exception &e) {
        std::cout << e.what() << std::endl;
        int index = std::rand() / ((RAND_MAX + 1u) / (root_actions.size() - 1)); // random index in range of root_actions size
        std::cout << index;
        action = GetBestAction(index);
    }

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
BoardAction MinMaxAlphaBeta::AlphaBetaSearch(Board& board) {
//    Board state = this->state;

    IndexScoreTuple neg_inf = IndexScoreTuple::NegInf();
    IndexScoreTuple inf = IndexScoreTuple::Inf();

    IndexScoreTuple best_value = MaxValue(
        board,
        neg_inf,
        inf,
        true
    );

//    std::cout << best_action << '\n';

    this->best_action = GetBestAction(best_value.index);
    best_action_ptr = &best_action;

    max_depth += max_depth_step;
    if (GetTime() <= max_time) return AlphaBetaSearch(board);

    std::cout << best_value.index << " " << best_value.score << "\n";

//    return best_action;
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
IndexScoreTuple MinMaxAlphaBeta::MaxValue(Board& state, IndexScoreTuple& alpha, IndexScoreTuple& beta, bool is_root_recursion) {
    if (depth >= max_depth || state.IsTerminal()) return { root_actions_index, state.GetScore() };

    IndexScoreTuple value = IndexScoreTuple::NegInf();
    ++(this->depth);

    std::vector<BoardAction> actions = BoardAction::Actions(state);
    if (this->root_actions_ptr == nullptr) {
//        *this->root_actions_ptr = actions;
        this->root_actions = actions;
        root_actions_ptr = &root_actions;
//        root_actions_ptr = new std::vector<BoardAction> (actions);
    }

    int index = -1;
    for (auto it = actions.begin(); it != actions.end(); it++) {
        index += is_root_recursion;
        if (is_root_recursion) this->root_actions_index = index;

        BoardAction action = *it;

        Board next_board = action.Results();
        IndexScoreTuple min_value = MinValue(next_board, alpha, beta);
        value = IndexScoreTuple::Max(value, min_value);

        if (value >= beta)
            return { root_actions_index, state.GetScore() };
        beta = IndexScoreTuple::Max(beta, value);
    }

    return { index, state.GetScore() };
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
IndexScoreTuple MinMaxAlphaBeta::MinValue(Board& state, IndexScoreTuple& alpha, IndexScoreTuple& beta, bool is_root_recursion) {
    if (depth >= max_depth || state.IsTerminal()) return { root_actions_index, state.GetScore() };

    IndexScoreTuple value = IndexScoreTuple::Inf();
    ++(this->depth);

    std::vector<BoardAction> actions = BoardAction::Actions(state, true);
    if (this->root_actions_ptr == nullptr) {
//        *this->root_actions_ptr = actions;
        this->root_actions = actions;
        root_actions_ptr = &root_actions;
//        root_actions_ptr = new std::vector<BoardAction> (actions);
    }

    int index = -1;
    for (auto it = actions.begin(); it != actions.end(); it++) {
        index += is_root_recursion;
        if (is_root_recursion) this->root_actions_index = index;

        BoardAction action = *it;

        Board next_board = action.Results();
        IndexScoreTuple max_value = MaxValue(next_board, alpha, beta);
        value = IndexScoreTuple::Min(value, max_value);

        if (value <= alpha)
            return { root_actions_index, state.GetScore() };
        beta = IndexScoreTuple::Min(beta, value);
    }

    return { index, state.GetScore() };
}

double MinMaxAlphaBeta::GetTime() {
    return (std::clock() - this->clock_start) / (double)CLOCKS_PER_SEC;
}

BoardAction MinMaxAlphaBeta::GetBestAction(int index) {
    if (index < 0) throw std::invalid_argument("index can not be negative for MinMaxAlphaBeta::GetAction method");
    return (*root_actions_ptr).at((size_t)index);
}

void MinMaxAlphaBeta::Reset() {
    state = Board();
    state_ptr = nullptr;

    best_action_ptr = nullptr;
    best_action = BoardAction();

    max_depth = max_depth_default;
    depth = 0;

    root_actions_index = -1;
//    delete root_actions_ptr;
    root_actions_ptr = nullptr;
    root_actions = std::vector<BoardAction>();

    started_search = false;
}
