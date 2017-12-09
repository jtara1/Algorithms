# import sys
# from os.path import join, dirname
# sys.path.append(dirname(__file__))

from src.make_change import make_change, make_change_no_memoization
from src.make_change.make_change import MemoizeMakeChange
from src import runtime
from random import randint


def test_runtime_small_denoms(pre_build_cache=True, calls=100):
    print('\ntest_runtime of make_change algorithms')
    denominations = [1, 5, 10, 25]
    # do all func calls so __cache is completely filled out (for change <= 100)
    if pre_build_cache:
        make_change(100, denominations)  # $1.00
    else:
        MemoizeMakeChange.clear_cache(denominations)

    args = [(randint(1, 101), denominations) for _ in range(calls)]
    print(type(make_change))
    print(type(make_change_no_memoization))
    runtime(make_change, args, format_line)
    runtime(make_change_no_memoization, args, format_line)


def test_runtime_large_denoms(pre_build_cache=True):
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
    avg2, _ = runtime(make_change_no_memoization, args,
                      format_lines=format_line)
    avg1, _ = runtime(make_change, args, format_line)
    print(avg1, avg2)


def format_line(times, average_time, max_time, min_time, input_values,
                output_values, func_name, include_header=True):
    """Generator to format each line that gets saved in comma separated value
    file given the input, output, and runtime data of some function.
    """
    # headers for columns of values
    if include_header:
        yield 'call number, {} runtime, input, output,' \
              ' min_time, max_time, average_time\n'.format(func_name)
    yield '1, {}, {}, {}, {}, {}, {}\n'\
        .format(times[0], input_values[0][0], '',
                min_time, max_time, average_time)

    count = 2
    for time, in_value, out_value \
            in zip(times[1:], input_values[1:], output_values[1:]):
        yield '{}, {}, {}, {}\n'\
            .format(count, time, in_value[0], '')
        count += 1


if __name__ == '__main__':
    # test_runtime_slow(False)
    # test_runtime_slow(True)
    # test_runtime_small_denoms(False)
    test_runtime_small_denoms(True, calls=100000)
