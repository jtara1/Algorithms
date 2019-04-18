#ifndef TEST_BUILD_INDEXSCORETUPLE_H
#define TEST_BUILD_INDEXSCORETUPLE_H

#include <limits>

class IndexScoreTuple {
public:
    IndexScoreTuple() = default;
    IndexScoreTuple(int index, float score);

    static IndexScoreTuple* inf_score_ptr;
    static IndexScoreTuple* neg_inf_score_ptr;
    static IndexScoreTuple inf_score;
    static IndexScoreTuple neg_inf_score;

    static IndexScoreTuple Inf();
    static IndexScoreTuple NegInf();

    bool operator<=(const IndexScoreTuple& tuple) const;
    bool operator>=(const IndexScoreTuple& tuple) const;

    static IndexScoreTuple& Max(IndexScoreTuple& a, IndexScoreTuple& b);
    static IndexScoreTuple& Min(IndexScoreTuple& a, IndexScoreTuple& b);

    float score;
    int index;
};

#endif //TEST_BUILD_INDEXSCORETUPLE_H
