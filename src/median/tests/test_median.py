from random import randint
from src.median import median, sort_and_pick_median


def test_with_random_values():
    length = 10
    for i in range(1, 4):
        length *= 10
        print('length = {}'.format(length))

        l = [randint(1, 100) for _ in range(length)]
        print(l)
        assert(median(l) == sort_and_pick_median(l))
