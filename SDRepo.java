
package javaapplication14;

import java.io.*;
import java.util.*;
import java.util.Map.*;
import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.client.*;
import org.eclipse.egit.github.core.service.*;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.*;

public class SDRepo {

    public static List<SearchRepository> searchRepo() throws IOException{
        GitHubClient client = new GitHubClient();
        RepositoryService service = new RepositoryService(client);
          
        StringBuilder query = new StringBuilder();
        Map <String,String> param = new HashMap <>();
        Scanner sc = new Scanner(System.in);
        String in;
           
        System.out.println("the search keywords:");
        in=sc.nextLine();
        if(in.compareTo("")!=0)
        {
            query.append(in).append(' ');
            param.put("in","name");//будет искать заданые query только по имени
        }
        System.out.println("owners:");
        in=sc.nextLine();
        if(in.length()!=0)
            param.put("user",in);
        System.out.println("repositories(USERNAME/REPOSITORY):");
            in=sc.nextLine();
        if(in.length()!=0)
            param.put("repo",in);    
        System.out.println("created(YYYY-MM-DD): (you can use greater than, less than, and range qualifiers)");
        in=sc.nextLine();
        if(in.length()!=0)
            param.put("created",in);
        System.out.println("language:");
        in=sc.nextLine();
        if(in.length()!=0)
            param.put("language",in);
        System.out.println("number of stars: (you can use greater than, less than, and range qualifiers)");
        in=sc.nextLine();
        if(in.length()!=0)
            param.put("stars",in);
        System.out.println("number of forks: (you can use greater than, less than, and range qualifiers)");
        in=sc.nextLine();
        if(in.length()!=0)
            param.put("forks",in);
        System.out.println("size(in kilobytes): (you can use greater than, less than, and range qualifiers)");
        in=sc.nextLine();
        if(in.length()!=0)
            param.put("size",in);
        System.out.println("pushed(YYYY-MM-DD): (you can use greater than, less than, and range qualifiers)");
        in=sc.nextLine();
        if(in.length()!=0)
            param.put("pushed",in);
        System.out.println("Filters whether forked repositories should be included (true) or only forked repositories should be returned (only)");
        in=sc.nextLine();
        if(in.length()!=0)
            param.put("fork",in);
        System.out.println("private or public:");
        in=sc.nextLine();
        if(in.length()!=0)
            param.put("is",in);
        System.out.println("topic:");
        in=sc.nextLine();
        if(in.length()!=0)
            param.put("topic",in);
          
        List<SearchRepository> list;
        if (query.length()!=0){
            for (Entry<String, String> p : param.entrySet())
		query.append(p.getKey()).append(':').append(p.getValue()).append(' ');
            list = service.searchRepositories(query.toString()); 
        }
        else
            list = service.searchRepositories(param);
        
        return list;
    }
    
    public static void cloneRepo(SearchRepository repo,String dir) throws GitAPIException
    {
       CloneCommand com =Git.cloneRepository();
       com.setURI(repo.getUrl()+".git");
       com.setDirectory(new File(dir+repo.getId()));
       com.call();
    }
    
    public static void main(String[] args) {
        try {
         
            List<SearchRepository> list= searchRepo();
            
            if(list.isEmpty()) 
                System.out.println("nothing found");
            else{
                Integer i=1;
                for (SearchRepository s : list){
                    System.out.println(i+") "+s.getName()+ " owner:"+ s.getOwner());
                    ++i;
                }
                Scanner sc = new Scanner(System.in);
                System.out.println("enter the directory for download");
                String dir= sc.nextLine();
                System.out.println("what is the repository going to download?(number)");
                Integer n = sc.nextInt();
                cloneRepo(list.get(n-1),dir);
            }
       
       } catch (IOException | GitAPIException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
}
