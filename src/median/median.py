def median(seq):
    return _median2(seq, (len(seq) - 1) // 2)


def _median2(seq, index):
    """

    :param seq:
    :return:
    """
    n = len(seq)
    if n == 0:
        raise Exception('seq: {} has length of 0'.format(seq))
    elif n == 1:
        return seq[0]
        # partitioned, partition_index = partition(seq, value)
    left, mid, right, partition_index = partition(
        seq,
        _median(seq))

    median_index = (n - 1) // 2
    if partition_index == median_index:
        return mid[partition_index]
    # median is in right list
    elif partition_index < median_index:
        return _median2(partition_index[partition_index:],
                        median_index - partition_index - 1)
    # median is in left list
    else:
        return _median2(partition_index[partition_index:],
                        index)


def _median(seq, index):
    """

    :param seq:
    :param index:
    :return:
    """
    n = len(seq)
    if n == 1:
        return seq[0]

    def middle_value(sequence):
        return sequence[(len(sequence) - 1) // 2]

    # break into groups of 5
    lists = [seq[a:b].sort() for a, b in zip(range(0, n, 5), range(5, n + 5, 5))]
    # sort each group
    # for seq in lists:
    #     seq.sort()
    # collect median of each and call this method on list of medians
    return _median([middle_value(l) for l in lists])


def partition(seq, value):
    """

    :param seq:
    :param value:
    :return:
    """
    left = []
    middle = []
    right = []
    for val in seq:
        if val < value:
            final.append(val)
        elif val == value:
            middle.append(val)
        else:
            right.append(val)
    return left, middle, right, len(left)

if __name__ == '__main__':
    # print(median([]))
    print(median([0]))
    print(median([0, 1, 5, 4, 6, 7]))