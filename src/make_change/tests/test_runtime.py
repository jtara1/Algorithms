from src.make_change import make_change, make_change_no_memoization
from src import TimeIt
from random import randint
from multiprocessing import Pool
from functools import reduce
import csv


def test_runtime():
    make_change(99, [1, 5, 10, 25])
    # wrap our algorithms with TimeIt object so they're timed
    make_change_t = TimeIt(make_change)
    make_change_no_memoization_t = TimeIt(make_change_no_memoization)

    denominations = [1, 5, 10, 25]
    make_change_args = [(randint(1, 100), denominations) for _ in range(100)]

    def run(func):
        with Pool(5) as pool:
            # result = pool.map(make_change_t, change_amounts)
            result = pool.starmap(func, make_change_args)

        t = reduce(lambda x, y: x[1] if isinstance(x, tuple) else x + y[1],
                   result)
        print("Total time of {}: {} seconds".format(func.__name__, t))
        return t

    t1 = run(make_change_t)
    t2 = run(make_change_no_memoization_t)

    with open(make_change_t.__name__ + ".csv", 'a+') as file:
        writer = csv.writer(file)
        writer.writerow([t1])

    with open(make_change_no_memoization_t.__name__ + ".csv", 'a+') as file:
        writer = csv.writer(file)
        writer.writerow([t2])
