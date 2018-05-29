import random
from src.operating_system_components.memory_mangement_unit.mmu_queue import MMUQueue
from src.operating_system_components.memory_mangement_unit.page_table_page import PageTablePage


class MMUNotRecentlyUsed(MMUQueue):
    def write(self, virtual_address):
        print('Write is called with virtual address: {}'.format(
            hex(virtual_address)))

        page_table_index, offset = self._partition_virtual_address(virtual_address)
        self.page_table[page_table_index].modified = 1

        self._translate_virtual_address(virtual_address)
        # print('.. write to page frame: {}'.format(self.page_table[page_table_index]))

    def _page_frame_replacement(self, virtual_page_index):
        # get pages that have been read (consequently, not modified), but
        # have been allocated a page frame
        pages = list(
            filter(
                lambda page: page.modified == 0 and
                             page.page_frame_index is not None,
                self.page_table
            )
        )

        # all pages had modified = 1 or none had been allocated a page frame
        if not pages:
            pages = list(
                filter(
                    lambda page: page.modified == 1 and
                                 page.page_frame_index is not None,
                    self.page_table
                )
            )

            # there is no page in page table w/ page_frame_index value
            if not pages:
                msg = 'can\'t replace a page if all pages in table' \
                      ' do not have a page_frame_index'
                raise Exception(msg)

        replace_index = random.randrange(len(pages))
        replaced_page = pages[replace_index]
        pframe_index = replaced_page.page_frame_index

        print('.. write to disk. replaced this: {}'.format(replaced_page))

        # remove the old page from the page frame and page table
        # it would have value from page frame written to disk
        for i, page in enumerate(self.page_table):
            if page == pages[replace_index]:
                self.page_table[i] = PageTablePage()

        return self._store_in_frame(virtual_page_index, pframe_index)
