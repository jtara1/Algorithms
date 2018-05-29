import math
from memory_mangement_unit.page_table import PageTable
from memory_mangement_unit.page_table_page import PageTablePage


class MMUQueue:
    def __init__(self, virtual_address_bits=16, pages=64):
        self.page_table = PageTable((PageTablePage() for _ in range(64)))
        self.page_frames = [False] * 2**3
        self.counter = 0

    def extended(self, value, size):
        """Unsigned extension of some number to the designated size

        :param value:
        :param size:
        :return:
        """
        diff = size - len(value)
        return '0' * diff + value

    def read(self, virtual_address):
        print('Read is called with virtual address:  {}'.format(
            hex(virtual_address)))
        table_index, _ = self._partition_virtual_address(virtual_address)
        self.page_table[table_index].last_referenced = self.counter
        return self._translate_virtual_address(virtual_address)

    def _translate_virtual_address(self, virtual_address):
        """Breaks a virtual address down into the page_table_index and
        the offset for the page frame. Then selects and stores the page
        in a frame.

        :param virtual_address:
        :return:
        """
        self.counter += 1

        page_table_index, offset = self._partition_virtual_address(virtual_address)

        frame_index = self._select_frame_and_store(page_table_index)
        frame_index = bin(frame_index)

        physical_address = frame_index + offset[2:]

        print('.. this becomes physical address: {}{}'
              .format(' ' * 4, hex(eval(physical_address))))

        # upper bits for page frame index, lower bits for offset
        return physical_address[:2 + 3], '0b' + physical_address[2 + 5:]

    def _partition_virtual_address(self, v_addr):
        """

        :param v_addr:
        :return: tuple of int and str. page table index (decimal) and \n
            offset (binary)
        """
        # convert an int of base 10 to base 2 (type str, without 0b)
        bits = self.extended(bin(v_addr)[2:], 16)

        page_table_index = eval('0b' + bits[:6])
        return page_table_index, '0b' + bits[6:]

    def _select_frame_and_store(self, virtual_page_index):
        """Checks if page with this index for page table is already present.
        If not present, tries to select the first page frame available.
        Then none are available, then it calls the replacement algorithm
        method, _page_frame_replacement

        :param virtual_page_index:
        :return:
        """
        page = self.page_table[virtual_page_index]
        # page was already loaded into memory
        if page.present:
            return page.page_frame_index

        print('.. page fault')
        # find empty spot and load it in that frame
        for index, in_use in enumerate(self.page_frames):
            if not in_use:
                self.page_frames[index] = True
                return self._store_in_frame(virtual_page_index, index)

        # no unused page frame in memory
        print('.. there\'s no open page frame, replacing a page')

        return self._page_frame_replacement(virtual_page_index)

    def _store_in_frame(self, virtual_page_index, frame_index):
        """Puts a virtual page from page table into a page frame in memory.

        :param virtual_page_index:
        :param frame_index:
        :return:
        """
        page = self.page_table[virtual_page_index]
        page.page_frame_index = frame_index
        page.present = 1

        page.time_when_loaded = self.counter

        print('.. stored in page frame {}: {}'.format(frame_index, page))

        return frame_index

    def _page_frame_replacement(self, virtual_page_index):
        """Algorithm for determining which page frame will be replaced

        :param virtual_page_index:
        :return:
        """
        oldest_time = math.inf
        oldest_page = None
        oldest_index = None
        for index, page in enumerate(self.page_table):
            if page.time_when_loaded is not None and page.time_when_loaded < oldest_time:
                oldest_page = page
                oldest_time = page.time_when_loaded
                oldest_index = index

        print('.. write to disk. replaced this: {}'.format(oldest_page))
        self.page_table[oldest_index] = PageTablePage()

        return self._store_in_frame(
            virtual_page_index, oldest_page.page_frame_index)
