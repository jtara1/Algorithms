def mergesort(array):
    """Returns a sorted copy of the array/list given using
    the merge sort algorithm. Uses the less than op. (__lt__)
    for comparison
    """
    n = len(array)                                      # k0
    # base case
    if n <= 1:                                          # k1
        return array                                    # k2

    left = mergesort(array[:n // 2])                    # log(n)
    right = mergesort(array[n // 2:])                   # log(n)
    return _merge(left, right)                          # n - 1


def _merge(left_list, right_list):
    """Merge two lists with the result being sorted using __lt__
    operator / method
    """
    final = []                                          # k3

    # index1, index2 for left and right lists respectively
    i1, i2 = 0, 0                                       # k4
    for _ in range(len(left_list) + len(right_list)):   # log(n)
        # get value from left, if none avail, extend final w/ remaining values
        try:
            left = left_list[i1]                        # k5
        except IndexError:
            final.extend(right_list[i2:])               # k6
            break

        # get value from right, if none avail, extend final w/ remaining values
        try:
            right = right_list[i2]                      # k7
        except IndexError:
            final.extend(left_list[i1:])                # k8
            break

        # compare the values to have the smaller value come first
        if left < right:                                # k9
            final.append(left)                          # k10
            i1 += 1                                     # k11
        else:
            final.append(right)                         # k12
            i2 += 1                                     # k13

    return final                                        # k14


def time_it(func):
    """Decorator that prints the time the decorated function took to run"""
    def run(**kwargs):
        import time
        start = time.time()
        func(**kwargs)
        print(time.time() - start)
    return run


@time_it
def test(sort_func=mergesort, mutates=False, size=15):
    """Some quick tests for the algorithm"""
    import random
    a = []
    for _ in range(size):
        a.append(random.randrange(0, 20))

    # a = [3, 2, 1, 0]
    # a = []
    print(a)
    if mutates:
        sort_func(a)
    else:
        a = sort_func(a)
    print(a)


if __name__ == '__main__':
    l = [3, 2, 10, 0, 1, 4, 8, 5, 9, 11, 20, 13, 19, 6, 12, 7, 17, 16, 15, 14]
    median_index = (len(l)-1) // 2
    print(mergesort(l)[median_index])
    # test(size=10)
