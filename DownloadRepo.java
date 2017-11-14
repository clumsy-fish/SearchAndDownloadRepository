/* аргуметы : 
    1) файл со списком репозиториев, полученных в результате поиска с помощью SearchRepo
    2) директория в которую будем скачивать
    3) номер репозитория из списка
*/
package downloadrepo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

public class DownloadRepo {
     public static void cloneRepo(String idRepo,String dir) throws GitAPIException
    {
       CloneCommand com =Git.cloneRepository();
       com.setURI("https://github.com/"+idRepo+".git");
       com.setDirectory(new File(dir+idRepo));
       com.call();
    }
    
    public static void main(String[] args) {
        BufferedReader in = null;
         try {
             File file = new File(args[0]);
             in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
             String directory = args[1];
             Integer numb = Integer.parseInt(args[2]);
             for(int i=1; i!=numb; ++i)
                in.readLine();
             String[] p=in.readLine().split(" ");
             cloneRepo(p[1],directory);
         } catch (FileNotFoundException ex) {
             System.err.println(ex.getMessage());
         } catch (IOException | GitAPIException ex) {
             System.err.println(ex.getMessage());
         } finally {
             try {
                 in.close();
             } catch (IOException ex) {
                 System.err.println(ex.getMessage());
             }
         }
    }
    
}
