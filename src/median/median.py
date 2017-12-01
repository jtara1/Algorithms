def sort_and_pick_median(seq):
    """Standard and most apparent way or algorithm to find median of some
    sequence or list O(nlog(n))

    :param seq: list, string, tuple, or other type
    :return: the value of the median of the sequence
    """
    i = (len(seq) - 1) // 2
    return sorted(seq)[i]


def median(seq):
    """Efficient algorithm O(n) to find the median of sequence that implements
    __iter__ and __len__

    :param seq: list, string, tuple, or other type
    :return: the value of the median of the sequence
    """
    # print('median is: {}'.format(sort_and_pick_median(seq)))
    return _median(seq, (len(seq) - 1) // 2)


def _median_of_medians(seq):
    """Reduces a sequence into its median of medians
    Breaks sequence of values into lists of length 5 sorting each group.
    Pick the median of each group and recursively call _median_of_medians(lists)

    :param seq: collection, list, tuple, etc. of values
    :return: the median of the medians (scalar / single value)
    :rtype: whatever the value type of the elements in the argument seq are
    """
    n = len(seq)
    if n == 1:
        return seq[0]
    elif n == 0:
        raise Exception("length of list is 0")

    def get_value(sequence):
        length = len(sequence)
        return sequence[(length - 1) // 2]

    # break into groups of 5 sorting each
    lists = [sorted(seq[a:b]) for a, b in zip(range(0, n, 5), range(5, n+5, 5))]

    # collect median of each and call this method on list of medians
    return _median_of_medians([get_value(seq) for seq in lists])


def _partition(sequence, pivot):
    """Iterate through the sequence once, putting elements less than the pivot
    in the left list, equal in the middle, and greater than in the right.

    :param sequence: collection, list, tuple, etc. of values
    :param pivot: the value in which the sequence will pivot around
    :return: the new_sequence (left+middle+right), first index of middle,
        and last index of middle
    :rtype: tuple of (list, int, int)
    """
    left = []
    middle = []
    right = []
    for val in sequence:
        if val < pivot:
            left.append(val)
        elif val == pivot:
            middle.append(val)
        else:
            right.append(val)
    return left + middle + right, len(left), len(sequence) - len(right) - 1


def _median(seq, ith_index):
    pivot = _median_of_medians(seq)
    seq, start_i, end_i = _partition(seq, pivot)
    if ith_index < start_i:
        return _median(seq[:start_i], ith_index)
    elif ith_index > end_i:
        return _median(seq[end_i + 1:], ith_index - end_i - 1)
    else:
        return pivot


if __name__ == '__main__':
    # n = 15; median = 6
    l = [2, 3, 0, 2, 4, 3, 10, 12, 13, 1, 8, 7, 6, 11, 44]
    print(median(l))

