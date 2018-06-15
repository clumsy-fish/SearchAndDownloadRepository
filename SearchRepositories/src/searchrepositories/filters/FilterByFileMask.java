package searchrepositories.filters;

import java.util.*;
import org.apache.tools.ant.DirectoryScanner;
import org.eclipse.egit.github.core.SearchRepository;

public class FilterByFileMask  implements Filter{
    private final String directory;
    private final String fileMask;
    CountFiltration count;
    
    public FilterByFileMask(String path, String mask ,CountFiltration c)
    {
        directory = path;
        fileMask = mask;
        count = c;
    }
   
    @Override
    public List<SearchRepository> filtrate(List<SearchRepository> repositories) {
        List<SearchRepository> result = new ArrayList<>();
        for(SearchRepository repo : repositories)
        {
            try{
                DirectoryScanner scanner = new DirectoryScanner();
                scanner.setIncludes(new String[]{fileMask});
                scanner.setBasedir(directory+"\\"+repo.getId());
                scanner.setCaseSensitive(false);
                scanner.scan();
                String[] files = scanner.getIncludedFiles();
              
                if (count.contains(files.length)){
                    result.add(repo);
                }
            }
            catch(IllegalStateException e) {}
        }
        return result;
    }   
}
