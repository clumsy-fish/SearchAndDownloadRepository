/* аргуметы : 
    1) файл с параметрами запроса. В файл param.txt после ':' дописываем значения параметров,
        по которым мы хотим найти репозиторий. Поля, в которые пишутся дата или числа,
        можно использовать сравнения (типо >,<= b т.д.)
    2) файл куда будем записывать результат. Результат представлен в виде нумерованого списка USERNAME/REPOSITORY
    */

package searchrepo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

public class SearchRepo {
    /*Функция ищет репы по 11 критериям (как мне показалось самым важным)
      если нужно, то можно добавить любые из представленных здесь 
       https://github.com/search/advanced 
    */
    public static List<SearchRepository> searchRepo(File file) throws IOException{
        GitHubClient client = new GitHubClient();
        RepositoryService service = new RepositoryService(client);
          
        StringBuilder query = new StringBuilder();
        Map <String,String> param = new HashMap <>();
        BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
        String[] par;
        
        par=in.readLine().split(":",2);
        if(par[1].trim().length()!=0){
            query.append(par[1].trim()).append(' ');
            param.put("in","name");//будет искать заданые query только по имени
        }
        par=in.readLine().split(":",2);
        if(par[1].trim().length()!=0)
            param.put("user",par[1].trim());
        par=in.readLine().split(":",2);
        if(par[1].trim().length()!=0)
            param.put("repo",par[1].trim());    
        par=in.readLine().split(":",2);
        if(par[1].trim().length()!=0)
            param.put("created",par[1].trim());
        par=in.readLine().split(":",2);
        if(par[1].trim().length()!=0)
            param.put("language",par[1].trim());
        par=in.readLine().split(":",2);
        if(par[1].trim().length()!=0)
            param.put("stars",par[1].trim());
        par=in.readLine().split(":",2);
        if(par[1].trim().length()!=0)
            param.put("forks",par[1].trim());
        par=in.readLine().split(":",2);
        if(par[1].trim().length()!=0)
            param.put("size",par[1].trim());
        par=in.readLine().split(":",2);
        if(par[1].trim().length()!=0)
            param.put("pushed",par[1].trim());
        par=in.readLine().split(":",2);
        if(par[1].trim().length()!=0)
            param.put("fork",par[1].trim());
        par=in.readLine().split(":",2);
        if(par[1].trim().length()!=0)
            param.put("topic",par[1].trim());
        in.close();
          
        List<SearchRepository> list;
        if (query.length()!=0){
            for (Map.Entry<String, String> p : param.entrySet())
		query.append(p.getKey()).append(':').append(p.getValue()).append(' ');
            list = service.searchRepositories(query.toString()); 
        }
        else
            list = service.searchRepositories(param);
        return list;
    }
        
    public static void main(String[] args) {
        try {
            File file = new File(args[0]);
            File result =new File(args[1]);
            BufferedWriter out = new BufferedWriter(new FileWriter(result.getAbsoluteFile()));
            List<SearchRepository> list = searchRepo(file);
            if(list.isEmpty()) 
               System.out.println("nothing found");
            else{
                Integer i=1;
                for (SearchRepository s : list){
                   out.write(i+") "+s.getId()+'\n');
                    ++i;
                }
            }
            out.close(); 
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
