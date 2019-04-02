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

IndexScoreTuple IndexScoreTuple::Inf() {
    return { -1, std::numeric_limits<float>::infinity() };
//    if (IndexScoreTuple::inf_score_ptr == nullptr) {
//        IndexScoreTuple::inf_score = IndexScoreTuple(-1, std::numeric_limits<float>::infinity());
//        IndexScoreTuple::inf_score_ptr = &IndexScoreTuple::inf_score;
//    }
//
//    return inf_score;
}

IndexScoreTuple IndexScoreTuple::NegInf() {
    return { -1, -std::numeric_limits<float>::infinity() };
//    if (IndexScoreTuple::neg_inf_score_ptr == nullptr) {
//        IndexScoreTuple::neg_inf_score = IndexScoreTuple(-1, -std::numeric_limits<float>::infinity());
//        IndexScoreTuple::neg_inf_score_ptr = &IndexScoreTuple::inf_score;
//    }
//
//    return neg_inf_score;
}
