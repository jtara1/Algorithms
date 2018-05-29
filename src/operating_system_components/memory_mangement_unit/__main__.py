from memory_mangement_unit.mmu_queue import MMUQueue
from memory_mangement_unit.mmu_not_recently_used import MMUNotRecentlyUsed
from memory_mangement_unit.mmu_least_recently_used import MMULeastRecentlyUsed


def phase1():
    print('-' * 10 + 'phase1' + '-' * 10)
    mmu = MMUQueue()

    # same 6 upper bits, same page frame & phys addr
    mmu.read(0x43b7)
    print()
    mmu.read(0x43b7)

    # keep the lower bits the same since mmu doesn't care about those
    for i in range(10):
        print()
        mmu.read(eval('0x{}3b7'.format(i)))

    print()
    mmu.read(0x43b7)


def phase2():
    print('-' * 10 + 'phase2' + '-' * 10)
    mmu = MMUNotRecentlyUsed()

    mmu.read(0x03b7)

    # keep the lower bits the same since mmu doesn't care about those
    # after no page frames avail, replaces the non-modified frame (0x43b7)
    for i in range(1, 10):
        print()
        mmu.write(eval('0x{}3b7'.format(i)))


def phase3():
    print('-' * 10 + 'phase3' + '-' * 10)
    mmu = MMULeastRecentlyUsed()

    mmu.write(0x03b7)
    print()

    [mmu.read(0x13b7) for _ in range(2)]

    # keep the lower bits the same since mmu doesn't care about those
    # after no page frames avail, replaces the non-modified frame (0x43b7)
    for i in range(1, 5):
        print()
        mmu.read(eval('0x{}3b7'.format(i)))

    [mmu.read(0x13b7) for _ in range(2)]

    for i in range(5, 10):
        print()
        mmu.read(eval('0x{}3b7'.format(i)))


if __name__ == '__main__':
    # for phase in [phase1, phase2, phase3]:
    #     phase()
    #     print()
    # phase1()
    # phase2()
    phase3()