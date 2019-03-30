from random import randint
from src.median import median, sort_and_pick_median
from src import runtime


def test_runtime():
    print('\ntest_median_runtime')
    args = []
    for exp in range(2, 7):
        size = 10 ** exp
        args.append([randint(-1000, 1000) for _ in range(size)])

    runtime(median, args=args, format_lines=_format_lines)
    assert(args == args)
    runtime(sort_and_pick_median, args=args, format_lines=_format_lines)


def _format_lines(times, average_time, max_time, min_time, input_values,
                  output_values, func_name, include_header=True):
    """Generator to format each line that gets saved in comma separated value
    file given the input, output, and runtime data of some function.
    """
    # headers for rows of values
    if include_header:
        yield 'call number, {} runtime, inp, out,' \
              ' min_time, max_time, average_time\n'.format(func_name)
    yield '1, {}, {}, {}, {}, {}, {}\n'\
        .format(times[0], len(input_values[0]), output_values[0],
                min_time, max_time, average_time)

    count = 2
    for time, in_value, out_value \
            in zip(times[1:], input_values[1:], output_values[1:]):
        yield '{}, {}, {}, {}\n'\
            .format(count, time, len(in_value), out_value)
        count += 1


if __name__ == '__main__':
    test_runtime()
