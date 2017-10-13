def mergesort(array):
    """Returns a sorted copy of the array/list given using
    the merge sort algorithm. Uses the less than op. (__lt__)
    for comparison
    """
    n = len(array)
    # base case
    if n == 1:
        return array

    return _merge(
        mergesort(array[:int(n / 2)]),
        mergesort(array[int(n / 2):]))


def _merge(left_list, right_list):
    """Merge two lists with the result being sorted using __lt__
    operator / method
    """
    final = []

    # index1, index2 for left and right lists respectively
    i1, i2 = 0, 0
    for _ in range(len(left_list) + len(right_list)):
        # get value from left, if none avail, append value from right
        try:
            left = left_list[i1]
        except IndexError:
            final.append(right_list[i2])
            i2 += 1
            continue

        # get value from right, if none avail, append value from left
        try:
            right = right_list[i2]
        except IndexError:
            final.append(left)
            i1 += 1
            continue

        # compare the values to have the smaller value come first
        if left < right:
            final.append(left)
            i1 += 1
        else:
            final.append(right)
            i2 += 1

    return final


if __name__ == '__main__':
    # testing
    import random
    a = []
    for _ in range(21):
        a.append(random.randrange(0, 20))

    # a = [4, 2, 5, 1, 42]
    print(a)
    a = mergesort(a)
    print(a)
