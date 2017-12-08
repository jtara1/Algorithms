from multiprocessing import Process, Pool, Queue
from src import TimeIt
import json
from os.path import dirname, join
import functools


# class CreateProcess:
#     def __init__(self, func):
#         self.func = func
#         functools.update_wrapper(self, func)
#         self.queue = Queue
#
#     def __call__(self, *args):
#         proc = Process(target=self.func, args=args)
#         proc.start()
#         # with Pool(4) as pool:
#         #     results = pool.starmap(self.func, args)
#         # return results


def _format_lines(times, average_time, max_time, min_time, input_values,
                  output_values, func_name, include_header=True):
    """Generator to format each line that gets saved in comma separated value
    file given the input, output, and runtime data of some function.
    """
    # headers for columns of values
    if include_header:
        yield 'call number, {} runtime, inp, out,' \
              ' min_time, max_time, average_time\n'.format(func_name)
    yield '1, {}, {}, {}, {}, {}, {}\n'\
        .format(times[0], input_values[0][0], output_values[0],
                min_time, max_time, average_time)

    count = 2
    for time, in_value, out_value \
            in zip(times[1:], input_values[1:], output_values[1:]):
        yield '{}, {}, {}, {}\n'\
            .format(count, time, in_value[0], out_value)
        count += 1


def runtime(func, args, format_line=_format_lines, json_file=None):
    """Measure the amount of time a function takes to run
    Get the average runtime by running func for each arg in args

    :param func: function we measuring the runtime of
    :param args: a list of tuples of args passed to the function
    :param json_file: the file in which the average time and list of times the
        program took to run are saved in (defaults to func.__name__ + '.json'
    :return: average time, list of time of each call
    :rtype: tuple of (float, list of floats)
    """
    json_file = join(dirname(__file__), func.__name__ + '.json') \
        if json_file is None else json_file
    csv_file = json_file.replace('.json', '.csv')

    func_t = TimeIt(func, print_after_stop=False)
    times = []
    return_values = []
    total_t = 0

    for count, arg in enumerate(args, start=1):
        val, t = func_t(*arg)
        return_values.append(val)
        times.append(t)
        total_t += t

    average_time = total_t / float(count)
    max_time = max(times)
    min_time = min(times)

    with open(csv_file, 'w') as file:
        for line in format_line(times, average_time, max_time, min_time, args,
                              return_values, func.__name__, True):
            # print(line)
            file.write(line)

    return average_time, times


if __name__ == '__main__':
    def counter(end=10000):
        total = 0
        for i in range(end):
            total += i
            print(i)
        return total
    runtime(counter, [(i,) for i in range(50)], _format_lines)
