import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {

    private final Map<String, List<PageEntry>> indexWords = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {

        List<File> lstFiles = new ArrayList<>();
        for (File file : pdfsDir.listFiles()) {
            if (file.isFile())
                lstFiles.add(file);
        }

        for (File doc : lstFiles) {
            var pdf = new PdfDocument(new PdfReader(doc));
            var page_count = pdf.getNumberOfPages();

            for (int i = 1; i <= page_count; i++) {
                PdfPage page = pdf.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page);
                var words = text.split("\\P{IsAlphabetic}+");

                Map<String, Integer> freqs = new HashMap<>();
                for (var word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    freqs.put(word.toLowerCase(), freqs.getOrDefault(word, 0) + 1);
                }

                //for (Map.Entry<String, Integer> kv : freqs.entrySet()) {
                for (String word : freqs.keySet()) {
                    PageEntry pageEntry = new PageEntry(pdf.getDocumentInfo().getTitle(), i, freqs.get(word));
                    List<PageEntry> pageEntries;
                    if (!indexWords.containsKey(word)) {
                        pageEntries = new ArrayList<>();
                    } else {
                        pageEntries = indexWords.get(word);
                    }
                    pageEntries.add(pageEntry);
                    indexWords.put(word, pageEntries);
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        // тут реализуйте поиск по слову
        word = word.toLowerCase(Locale.ROOT);
        List<PageEntry> pageEntries = indexWords.get(word);
        Collections.sort(pageEntries);
        return pageEntries;
    }
}
