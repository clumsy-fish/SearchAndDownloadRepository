
package searchrepositories.filters;

import com.ibm.icu.text.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
import org.eclipse.egit.github.core.SearchRepository;
import java.util.regex.*;   
import java.nio.file.*;
import java.nio.file.Files;

public class FilterByPattern implements Filter {
    private final String directory;
    private final String pattern;
    CountFiltration count;
    
    public  FilterByPattern(String path, String p, CountFiltration c)
    {
        directory = path;
        pattern = p;
        count = c;
    }
  
    private int filterCounter(File dir,Pattern p)
    {
        int s=0;
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String children1 : children) {
                File f = new File(dir, children1);
                s+=filterCounter(f,p);
            }
        } else 
        {
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(dir)) ){
                if(dir.canRead()){
                    CharsetDetector cd = new CharsetDetector();
                    cd.setText(bis);
                    CharsetMatch cm = cd.detect();
                    String text = new String(Files.readAllBytes(Paths.get(dir.getAbsolutePath())),
                                            Charset.forName(cm.getName()));
                    Matcher m = p.matcher(text);
                    if(m.find())
                        s = m.groupCount();
                }
            } catch (IOException ex) {}
        } 
        return s;
    }
    
    @Override
    public List<SearchRepository> filtrate(List<SearchRepository> repositories) 
    {
        Pattern p = Pattern.compile(pattern);  
        List<SearchRepository> result = new ArrayList<>();
        for(SearchRepository repo : repositories){
            File f = new File(directory+"\\"+repo.getId()); 
            int i = filterCounter(f,p);
            
            if (count.contains(i)){
               result.add(repo);
            }
        }
        return result;
    }
}
