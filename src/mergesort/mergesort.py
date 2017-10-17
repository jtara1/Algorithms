def mergesort(array):
    """Returns a sorted copy of the array/list given using
    the merge sort algorithm. Uses the less than op. (__lt__)
    for comparison
    """
    n = len(array)                                      # k0
    # base case
    if n <= 1:                                          # k1
        return array                                    # k2

    return _merge(                                      # n - 1
        mergesort(array[:n // 2]),                      # log(n)
        mergesort(array[n // 2:]))                      # log(n)


def _merge(left_list, right_list):
    """Merge two lists with the result being sorted using __lt__
    operator / method
    """
    final = []                                          # k3

    # index1, index2 for left and right lists respectively
    i1, i2 = 0, 0                                       # k4
    for _ in range(len(left_list) + len(right_list)):   # n
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
    def run():
        import time
        start = time.time()
        func()
        print(time.time() - start)
    return run


@time_it
def test():
    """Some quick tests for the algorithm"""
    import random
    a = []
    for _ in range(15):
        a.append(random.randrange(0, 20))

    # a = [3, 2, 1, 0]
    # a = []
    print(a)
    a = mergesort(a)
    print(a)


if __name__ == '__main__':
    test()
