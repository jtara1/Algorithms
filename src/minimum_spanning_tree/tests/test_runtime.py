from src.minimum_spanning_tree import prim, kruskal, Graph
from src import runtime


def test_sparse_graph():
    print('\ntest_sparse_graph')
    # sizes = [100, 500]
    sizes = [100, 500, 1000, 5000]
    # sizes = [100, 500, 1000, 5000, 10000]
    args = [(Graph.create_graph(size, dense=False),) for size in sizes]
    runtime(kruskal,
            args=args,
            format_lines=_format_lines)
    runtime(prim,
            args=args,
            format_lines=_format_lines)

def test_dense_graph():
    print('\ntest_sparse_graph')
    # sizes = [100, 500]
    sizes = [100, 500, 1000, 5000, 10000]
    args = [(Graph.create_graph(size, dense=True),) for size in sizes]
    runtime(kruskal,
            args=args,
            format_lines=_format_lines)
    runtime(prim,
            args=args,
            format_lines=_format_lines)


def _format_lines(times, average_time, max_time, min_time, input_values,
                  output_values, func_name, include_header=True):
    """Generator to format each line that gets saved in comma separated value
    file given the input, output, and runtime data of some function.
    """
    # headers for rows of values
    if include_header:
        yield 'call number, {} runtime, input, output,' \
              ' min_time, max_time, average_time\n'.format(func_name)
    yield '1, {}, {}, {}, {}, {}, {}\n'\
        .format(times[0], len(input_values[0]), '',
                min_time, max_time, average_time)

    count = 2
    for time, in_value, out_value \
            in zip(times[1:], input_values[1:], output_values[1:]):
        yield '{}, {}, {}, {}\n'\
            .format(count, time, len(in_value), '')
        count += 1

if __name__ == '__main__':
    test_sparse_graph()
    # test_dense_graph()
