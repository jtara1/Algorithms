import functools


class MemoizeMakeChange:
    def __init__(self, func, n, denominations):
        self.func = func
        self.n = n
        self.denominations = denominations
        self.cache = [[] for _ in range(len(denominations))]
        functools.update_wrapper(self, func)

    def __call__(self, n, denominations):
        try:
            return [self.cache[i][n] for i in range(len(self.denominations))]
        except IndexError:
            return self._extend_cache(n)

    def _extend_cache(self, change):
        for amount in range(len(self.cache), change + 1):
            coins = self.func(amount, self.denominations)
            for index, coins_for_denom in enumerate(coins):
                self.cache[index] = coins_for_denom


def make_change(n, denominations):
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


if __name__ == '__main__':
    denom = [1, 5, 10, 25]
    print(make_change(76, denom))
