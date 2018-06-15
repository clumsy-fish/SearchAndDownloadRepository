
package searchrepositories;

import java.io.File;
import java.util.List;
import org.eclipse.egit.github.core.SearchRepository;

public class Delete {
    private final String directory;
    public Delete (String path)
    {
        directory = path;
    }
    
    private void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String children1 : children) {
                File f = new File(dir, children1);
                deleteDirectory(f);
            }
            dir.delete();
        } else dir.delete();
    }
    
    public void deleteRepositories(List<SearchRepository> repositories)
    {
         for(SearchRepository repo : repositories)
         {
              deleteDirectory(new File(directory+"\\"+repo.getId()));
              File userDir = new File(directory+"\\"+repo.getOwner());
              userDir.delete();
         }
    }
}
