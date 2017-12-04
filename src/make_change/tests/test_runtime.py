from src.make_change import make_change, make_change_no_memoization
from src import runtime
from random import randint


def test_runtime():
    denominations = [1, 5, 10, 25]
    # do all func calls so cache is completely filled out (for change <= 99)
    # make_change(99, denominations)
    calls = 10000

    args = [(randint(1, 100), denominations) for _ in range(calls)]
    runtime(make_change, args)
    runtime(make_change_no_memoization, args)
