#ifndef TEST_BUILD_MINMAXALPHABETA_H
#define TEST_BUILD_MINMAXALPHABETA_H


#include "State.h"

class MinMaxAlphaBeta {
public:
    State AlphaBetaSearch(State state);
    float MinValue(State state, float alpha, float beta);
    float MaxValue(State state, float alpha, float beta);

};


#endif //TEST_BUILD_MINMAXALPHABETA_H
