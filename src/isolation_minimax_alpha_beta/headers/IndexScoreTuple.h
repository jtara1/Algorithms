#ifndef TEST_BUILD_INDEXSCORETUPLE_H
#define TEST_BUILD_INDEXSCORETUPLE_H

class IndexScoreTuple {
public:
    IndexScoreTuple(int index, float score);

    bool operator<=(const IndexScoreTuple& tuple) const;
    bool operator>=(const IndexScoreTuple& tuple) const;

    static IndexScoreTuple& Max(IndexScoreTuple& a, IndexScoreTuple& b);
    static IndexScoreTuple& Min(IndexScoreTuple& a, IndexScoreTuple& b);

    float score;
    int index;
};

#endif //TEST_BUILD_INDEXSCORETUPLE_H
