
package searchrepositories.filters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.apache.tools.ant.DirectoryScanner;
import org.eclipse.egit.github.core.SearchRepository;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class FilterHtml implements Filter {
    private final String directory;
    private final String selector;
    CountFiltration count;
    
    public FilterHtml(String path, String cssQuery, CountFiltration c)
    {    
        directory = path;
        selector = cssQuery;
        count = c;
    }

@Override
public List<SearchRepository> filtrate(List<SearchRepository> repositories) {
    List<SearchRepository> result = new ArrayList<>();
    for(SearchRepository repo : repositories)
    {
        try{
            String repoPath = directory+"\\"+repo.getId();
            DirectoryScanner scanner = new DirectoryScanner();
            scanner.setIncludes(new String[]{"**/*.html"});
            scanner.setBasedir(repoPath);
            scanner.setCaseSensitive(false);
            scanner.scan();
            String[] files = scanner.getIncludedFiles();
            int s = 0;
            for (String file : files)
            {
                try {
                    File htmlFile = new File(repoPath+"\\"+file);  
                    Document doc = Jsoup.parse(htmlFile, "UTF-8");
                    Elements el = doc.select(selector);
                    if (!el.isEmpty())
                        s += el.size();
                } catch (IOException ex) {}
            }
            if (count.contains(s))
                result.add(repo);
        }catch(Exception e){}
    }
    return result;
}
    
}
