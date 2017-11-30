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
    return _median(seq, seq, (len(seq) - 1) // 2, True)


def _median(original_seq, seq, index, use_median_as_index=False):
    """

    :param seq:
    :param index: aka i
    :return:
    """
    n = len(original_seq)
    seq_n = len(seq)
    if seq_n == 0:
        raise Exception('seq: {} has length of 0'.format(seq))
    elif seq_n == 1:
        return seq[0]

    left, mid, right, pivot_index = _partition(
        seq,
        _split_sort(seq, index, use_median_as_index))

    median_index = (n - 1) // 2

    # median found since k == i
    if pivot_index == median_index:
        return mid[0]
    # median is in right list
    elif pivot_index < median_index:
        print(median_index - pivot_index - 1)
        return _median(original_seq, right, median_index - pivot_index - 1)
    # median is in left list
    else:
        return _median(original_seq, left, index, True)


def _split_sort(seq, index, use_median_as_index=False):
    """

    :param seq:
    :param index: aka k
    :return:
    """
    n = len(seq)
    if n == 1:
        return seq[0]

    def get_value(sequence):
        print(use_median_as_index, index)
        return sequence[
            (len(sequence) - 1) // 2 if use_median_as_index else index]

    # break into groups of 5 sorting each
    lists = [sorted(seq[a:b]) for a, b in zip(range(0, n, 5), range(5, n+5, 5))]

    # collect median of each and call this method on list of medians
    return _split_sort(
        [get_value(l) for l in lists],
        index,
        use_median_as_index)


def _partition(sequence, pivot_value):
    """

    :param sequence:
    :param pivot_value:
    :return:
    """
    left = []
    middle = []
    right = []
    for val in sequence:
        if val < pivot_value:
            left.append(val)
        elif val == pivot_value:
            middle.append(val)
        else:
            right.append(val)
    return left, middle, right, len(left)

if __name__ == '__main__':
    # print(median([]))
    print(median([0]))
    l = [0, 1, 5, 4, 6, 7]
    print(median(l))  # median = 4 (size = 6)
    print(sort_and_pick_median(l))

