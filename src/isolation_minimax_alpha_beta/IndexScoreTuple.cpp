#include "headers/IndexScoreTuple.h"

IndexScoreTuple::IndexScoreTuple(int index, float score) {
    this->index = index;
    this->score = score;
}

bool IndexScoreTuple::operator<=(const IndexScoreTuple &tuple) const {
    return score <= tuple.score;
}

bool IndexScoreTuple::operator>=(const IndexScoreTuple &tuple) const {
    return score >= tuple.score;
}

IndexScoreTuple& IndexScoreTuple::Max(IndexScoreTuple &a, IndexScoreTuple &b) {
    return a >= b ? a : b;
}

IndexScoreTuple& IndexScoreTuple::Min(IndexScoreTuple &a, IndexScoreTuple &b) {
    return a <= b ? a : b;
}
