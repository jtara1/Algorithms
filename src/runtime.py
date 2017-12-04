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


def runtime(func, args, json_file=None):
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
    total_t = 0

    for count, arg in enumerate(args, start=1):
        val, t = func_t(*arg)
        times.append(t)
        total_t += t

    average_time = total_t / float(count)
    # try:
    #     with open(json_file, 'r') as f:
    #         json_data = json.load(f)
    # except (FileNotFoundError, IOError):
    #     json_data = []
    # json_data.extend(times)

    json_data = times
    with open(json_file, 'w') as file:
        json.dump(json_data, file)

    # try:
    #     with open(csv_file, 'r') as f:
    #         csv_data = json.load(f)
    # except (FileNotFoundError, IOError):
    #     csv_data = ''

    with open(csv_file, 'w') as file:
        file.write(',\n'.join(str(numb) for numb in times))

    return average_time, times


def average_of_averages(file_name):
    with open(file_name, 'r') as f:
        data = json.load(f)

    total = 0
    for index in range(0, len(data), 2):
        total += data[index]
    average = total / len(data) / 2.0
    # print('average of averages: {}'.format(average))
    return average

if __name__ == '__main__':
    def counter(end=10000):
        for i in range(end):
            print(i)
    runtime(counter, [(i,) for i in range(50)])
    # path1 = 'C:\\Users\\James\\Documents\\_Github-Projects\\Algorithms\\src\\make_change\\tests\\make_change.json'
    # path2 = 'C:\\Users\\James\\Documents\\_Github-Projects\\Algorithms\\src\\make_change\\tests\\make_change_no_memoization.json'
    # print(average_of_averages(path1))
    # print(average_of_averages(path2))
