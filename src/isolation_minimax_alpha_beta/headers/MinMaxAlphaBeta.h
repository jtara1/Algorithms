#ifndef TEST_BUILD_MINMAXALPHABETA_H
#define TEST_BUILD_MINMAXALPHABETA_H


//#include "State.h"
#include "Board.h"

class MinMaxAlphaBeta {
public:
    Board AlphaBetaSearch(Board state);
    float MinValue(Board state, float alpha, float beta);
    float MaxValue(Board state, float alpha, float beta);

};


#endif //TEST_BUILD_MINMAXALPHABETA_H
