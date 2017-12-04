import functools
from os.path import join, dirname
import json


class MemoizeMakeChange:
    # file path to where func calls are serialized (str should be formatted)
    json_file_path = join(dirname(__file__), 'make_change_{denominations}.json')

    def __init__(self, func):
        """Save the output of calls to make_change func to save time when future
        calls are made to the func
        self.cache is a 2D list where each row is a denomination and the column
        index is the amount of change needed

        e.g.: for denom = [1, 5, 10, 25], we need to get numb of coins for $0.06
        (change = 6), our self.cache will be built to equal
         [[0, 1, 2, 3, 4, 0, 1],
          [0, 0, 0, 0, 0, 1, 1],
          [0, 0, 0, 0, 0, 0, 0],
          [0, 0, 0, 0, 0, 0, 0]]
        take the column of values from column at index 6 and we get [1, 1, 0, 0]
        which means we need 1 penny, 1 nickle, 0 dimes, 0 quarters as the
        smallest amount of coins to make up 6 change in total

        Note: make_change func will determine a single column in self.cache
        while this class will handle the caching, calling, and serialization

        :param func: make_change function (func this object is wrapping)
        """
        self.cache = None
        self.func = func
        functools.update_wrapper(self, func)  # this object now wraps the func

    def __call__(self, n, denominations):
        """Overloading () operator
        Attempts to load output if there is no self.cache loaded yet

        :param n: amount of change
        :param denominations: list of denominations
        :return: self.func(n, denominations) (# of coins of each denomination)
        :rtype: list of integers
        """
        if self.cache is None:
            # load serialization of cached function calls
            self._deserialize(denominations)

        try:
            return [self.cache[i][n] for i in range(len(denominations))]
        # we haven't cached values for this amount of change or larger amounts
        except IndexError:
            return self._extend_cache(n, denominations)
        # self.cache is None or non-index-able type (no self.cache.__get__(val))
        except TypeError:
            self.cache = [[] for _ in range(len(denominations))]
            return self.__call__(n, denominations)

    def _extend_cache(self, change, denominations):
        """Call self.func(change, denominations) for chg range [n+1 to change+1)
        saving each self.func output in column chg of self.cache
        Serializes self.cache after all calls to self.func complete

        :param change: amount of change (as integer)
        :param denominations: list of denominations of coins / bills from least
            to greatest
        :return: self.func(change, denominations)
        :rtype: list of integers
        """
        for amount in range(len(self.cache[0]), change + 1):
            coins = self.func(amount, denominations)
            for index, coins_for_denom in enumerate(coins):
                self.cache[index].append(coins_for_denom)

        self._serialize(denominations)
        return coins

    def _serialize(self, denominations):
        file_path = MemoizeMakeChange.json_file_path.format(
            denominations=denominations)
        with open(file_path, 'w') as file:
            json.dump(self.cache, file)

    def _deserialize(self, denominations):
        file_path = MemoizeMakeChange.json_file_path.format(
            denominations=denominations)
        try:
            with open(file_path, 'r') as file:
                self.cache = json.load(file)
        except (FileNotFoundError, IOError):  # there is no serialized cache
            self.cache = None


def make_change_no_memoization(n, denominations):
    """Returns the smallest amount of coins of the given denomination to total
    n, the amount of change.

    e.g.: for n = 6, denominations = [1, 5, 10, 25]
    return [1, 1, 0, 0]

    :param n: amount of change (as integer)
    :param denominations: list of denominations of coins / bills from least
            to greatest
    :return: number of coins of each denomination
    :rtype: list of integers
    """
    def find_next_coin():
        for i in range(len(denominations) - 1, -1, -1):
            if total + denominations[i] <= n:
                return i
        raise Exception("no solution")

    total = 0
    change = [0] * len(denominations)

    while total < n:
        index = find_next_coin()
        change[index] += 1
        total += denominations[index]

    return change


# memoized version of make_change_no_memoization
make_change = MemoizeMakeChange(make_change_no_memoization)
make_change.__name__ = "make_change"


if __name__ == '__main__':
    denom = [1, 5, 10, 25]
    # print(make_change(76, denom))
    # print(make_change(24, denom))
    print(make_change_no_memoization(76, denom))
    print(make_change_no_memoization(24, denom))
