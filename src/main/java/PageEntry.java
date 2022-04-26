import java.util.Comparator;
import java.util.Objects;

public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    public String getPdfName() {
        return pdfName;
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(PageEntry o) {

        return Comparator.comparing((PageEntry p) -> p.count)
                .thenComparing(p -> p.page)
                .compare(o, this);
       // return o.getCount() - this.count;
    }

    @Override
    public String toString() {
        return "PageEntry{" +
                "pdfName='" + pdfName + '\'' +
                ", page=" + page +
                ", count=" + count +
                '}';
    }
/*
    Класс, описывающий один элемент результата одного поиска.
    Он состоит из имени пдф-файла, номера страницы и количества раз, которое встретилось это слово на ней

       {
    "pdfName": "Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf",
    "page": 12,
    "count": 6
  },
     */

    // ???
}
