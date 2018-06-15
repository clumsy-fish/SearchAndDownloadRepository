package searchrepositories;

import searchrepositories.filters.FilterFactory;
import searchrepositories.filters.Filter;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.egit.github.core.SearchRepository;
// 1) путь к файлу с параметрами поиска
// 2) путь к директории куда будут загружены репы
public class SearchRepositories {

    public static void main(String[] args) {
        Model parametrs = new Model(args[0]);
        String directory = args[1];
        Search githubSearcher = new Search(parametrs.gitHubFilter);
        List<SearchRepository> repo = githubSearcher.searchWithParametrs();
      
        Download gitDownload = new Download(directory,repo);
        gitDownload.downloadRepositories();
        
        List<SearchRepository> repos = new ArrayList<>(repo);
        
        for(Filter filter : FilterFactory.create(directory, parametrs))
        {
            repo = filter.filtrate(repo);
        }
        repos.removeAll(repo);
        Delete deleteservice = new Delete(directory);
        deleteservice.deleteRepositories(repos);
    }
}
