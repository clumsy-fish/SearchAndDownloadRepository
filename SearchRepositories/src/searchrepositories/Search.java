
package searchrepositories;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

public class Search {
    private  String query; 
    public Search(String q)
    {
        query = q;
    }
       
    public List<SearchRepository> searchWithParametrs()
    {
        GitHubClient client = new GitHubClient();
        RepositoryService service = new RepositoryService(client);
        try {
            int countList = 1;
            List<SearchRepository> result = service.searchRepositories(query,countList);
            while ((result.size()== countList*100) && (countList<10))
            {
                countList++;
                result.addAll(service.searchRepositories(query,countList));
            }
            return result;
        } catch (IOException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
