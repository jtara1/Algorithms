#ifndef TEST_BUILD_INDEXSCORETUPLE_H
#define TEST_BUILD_INDEXSCORETUPLE_H

#include <limits>

class IndexScoreTuple {
public:
    IndexScoreTuple(int index, float score);

    static IndexScoreTuple Inf();
    static IndexScoreTuple NegInf();

    bool operator<=(const IndexScoreTuple& tuple) const;
    bool operator>=(const IndexScoreTuple& tuple) const;

    static IndexScoreTuple& Max(IndexScoreTuple& a, IndexScoreTuple& b);
    static IndexScoreTuple& Min(IndexScoreTuple& a, IndexScoreTuple& b);

    float score;
    int index;

private:
    static IndexScoreTuple inf_score = IndexScoreTuple(-1, std::numeric_limits<float>::infinity());
    static IndexScoreTuple neg_inf_score = IndexScoreTuple(-1, -std::numeric_limits<float>::infinity());
};

#endif //TEST_BUILD_INDEXSCORETUPLE_H
