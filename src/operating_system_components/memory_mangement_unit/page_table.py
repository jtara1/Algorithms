from memory_mangement_unit.page_table_page import PageTablePage


class PageTable(list):
    def __init(self, *args):
        super(PageTable, self).__init__(*args)


if __name__ == '__main__':
    t = PageTable()
    print(t[0].present)
