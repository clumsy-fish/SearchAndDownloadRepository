
package searchrepositories;

import java.io.File;
import java.util.List;
import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;

public class Download {
    private final String directory;
    private final List<SearchRepository> repositories;
    
    public Download(String path, List<SearchRepository> repo)
    {
        directory = path;
        repositories = repo;
    }
    
    private void cloneRepo(String idRepo) throws GitAPIException
    {
      try {
        Git git = Git.cloneRepository()
                          .setURI("https://github.com/"+idRepo+".git")
                          .setDirectory(new File(directory+"\\"+idRepo))
                          .call(); 
        git.close();
      }
      catch (Exception e){}
    }
     
    public void downloadRepositories()
    {
        repositories.forEach((rep) -> {
            try {
                cloneRepo(rep.getId());
            } catch (GitAPIException ex) {}
        });
    }
     
}
