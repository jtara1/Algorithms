from queue import deque

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
    lists = [_sort_small_list(seq[a:b])
             for a, b in zip(range(0, n, 5), range(5, n + 5, 5))]

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
    # final = deque()
    # middle_start = 0
    for val in sequence:
        if val < pivot:
            # middle_start += 1
            # final.appendleft(val)
            left.append(val)
        elif val == pivot:
            # final.insert(middle_start, val)
            middle.append(val)
        else:
            # final.append(val)
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


def _sort_small_list(seq):
    def swap(i, j):
        tmp = seq[i]
        seq[i] = seq[j]
        seq[j] = tmp

    def insert(seq, x):
        if x < seq[1]:
            if x < seq[0]:
                return [x] + seq
            else:
                return seq[:1] + [x] + seq[1:]
        else:
            if len(seq) == 2 or x > seq[2]:
                return seq + [x]
            else:
                return seq[0:2] + [x] + seq[2:]

    def sort_5(seq):
        if seq[0] > seq[1]:
            swap(0, 1)
        if seq[2] > seq[3]:
            swap(2, 3)
        if seq[0] > seq[2]:
            seq = seq[2:4] + seq[0:2] + seq[4:]

        seq2 = insert(seq[:1] + seq[2:4], seq[4])
        return seq2[0:1] + insert(seq2[1:4], seq[1])

    def sort_4(seq):
        if seq[0] > seq[1]:
            swap(0, 1)
        if seq[2] > seq[3]:
            swap(2, 3)
        if seq[0] > seq[2]:
            seq = seq[2:4] + seq[0:2]

        return seq[0:1] + insert(seq[2:4], seq[1])

    def sort_3(seq):
        if seq[0] > seq[1]:
            swap(0, 1)
        return insert(seq[0:2], seq[2])

    def sort_2(seq):
        if seq[0] > seq[1]:
            swap(0, 1)
        return seq

    n = len(seq)
    if n == 1:
        return seq
    elif n == 2:
        return sort_2(seq)
    elif n == 3:
        return sort_3(seq)
    elif n == 4:
        return sort_4(seq)
    return sort_5(seq)


if __name__ == '__main__':
    # n = 15; median = 6
    l = [2, 3, 0, 2, 4, 3, 10, 12, 13, 1, 8, 7, 6, 11, 44]
    print(median(l))
