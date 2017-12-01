from math import floor


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
    print('median is: {}'.format(sort_and_pick_median(seq)))
    # return _median(seq, seq, (len(seq) - 1) // 2, True)
    return _median2(seq, (len(seq) - 1) // 2)


def _median(original_seq, seq, ith_index, use_median_as_index=False):
    """

    :param seq:
    :param index: aka i
    :return:
    """
    print('seq = {}'.format(seq))
    n = len(original_seq)
    seq_n = len(seq)
    if seq_n == 0:
        raise Exception('seq: {} has length of 0'.format(seq))
    elif seq_n <= 5:
        return _split_sort(seq, ith_index, True)

    if not use_median_as_index and ith_index > 4:
        return _median(original_seq, _split_sort(seq, ith_index, True), True)
    left, mid, right, pivot_index = _partition(
        original_seq,
        _split_sort(seq, ith_index, use_median_as_index))

    median_index = (n - 1) // 2

    # median found since k == i
    if pivot_index == median_index:
        return mid[0]
    # median is in right list
    elif pivot_index < ith_index:
        new_ith_index = median_index - pivot_index - 1
        print('next ith_index = {}'.format(new_ith_index))
        return _median(original_seq, right, new_ith_index, False)
    # median is in left list
    else:
        return _median(original_seq, left, ith_index, True)


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
        try:
            length = len(sequence)
            # get median
            if use_median_as_index:
                return sequence[(length - 1) // 2]
            # avoid IndexError
            elif length <= index:
                pass
            else:
                return sequence[index]
        except IndexError as e:
            raise e
            # return sequence[(len(sequence) - 1) // 2]

    # break into groups of 5 sorting each
    lists = [sorted(seq[a:b]) for a, b in zip(range(0, n, 5), range(5, n+5, 5))]

    # collect median of each and call this method on list of medians
    return _split_sort(
        [get_value(seq) for seq in lists],
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


def _median_of_medians(seq):
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


def _partition2(sequence, pivot):
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


def _median2(seq, ith_index):
    pivot = _median_of_medians(seq)
    seq, start_i, end_i = _partition2(seq, pivot)
    if ith_index < start_i:
        return _median2(seq[:start_i], ith_index)
    elif ith_index > end_i:
        return _median2(seq[end_i+1:], ith_index - end_i)
    else:
        return pivot


if __name__ == '__main__':
    # print(median([]))
    # print(median([0]))
    # l = [0, 1, 5, 4, 6, 7]
    l = [2, 3, 0, 2, 4, 3, 10, 12, 13, 1, 8, 7, 6, 11, 44]
    print(median(l))

