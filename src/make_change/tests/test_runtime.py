from src.make_change import make_change, make_change_no_memoization
from src.make_change.make_change import MemoizeMakeChange
from src import runtime
from random import randint


def test_runtime_quick(pre_build_cache=True):
    denominations = [1, 5, 10, 25]
    # do all func calls so __cache is completely filled out (for change <= 99)
    if pre_build_cache:
        make_change(99, denominations)  # $50
    else:
        MemoizeMakeChange.clear_cache(denominations)
    calls = 10000

    args = [(randint(1, 100), denominations) for _ in range(calls)]
    runtime(make_change, args)
    runtime(make_change_no_memoization, args)


def test_runtime_slow(pre_build_cache=True):
    # the 4 coins, $1, $5, $10, $20 USD liquid currency denominations
    denominations = [1, 5, 10, 25, 100, 500, 1000, 2000]
    # do all func calls so __cache is completely filled out (for change <= 5000)
    if pre_build_cache:
        make_change(5000, denominations)  # output for <= $50 is in __cache
    else:
        MemoizeMakeChange.clear_cache(denominations)
    calls = 1000

    # random amount between [0.01, 50] USD == [1, 5000]
    args = [(randint(1, 5001), denominations) for _ in range(calls)]
    avg1, _ = runtime(make_change, args)
    avg2, _ = runtime(make_change_no_memoization, args)
    print(avg1, avg2)


if __name__ == '__main__':
    test_runtime_slow(False)
    # test_runtime_slow(True)
