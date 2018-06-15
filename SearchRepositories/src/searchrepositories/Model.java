package searchrepositories;

import searchrepositories.filters.CountFiltration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {
    public String gitHubFilter;
    
    public String maskFilter;
    public CountFiltration countMaskFilter;
    
    public String patternFilter;
    public CountFiltration countPatternFilter;
    
    public String htmlFilter;
    public CountFiltration countHtmlFilter;
    
    public Model(String path)
    {
        File parametrs = new File(path);
        if (keys.isEmpty())
            setKeys();
        //читаем файл  
        StringBuilder queryString = new StringBuilder();
        try {
           BufferedReader in = new BufferedReader(new FileReader(parametrs.getAbsoluteFile()));
           String[] pair;
           String line;
           HashMap<String,String> parse = new HashMap<>();
           while ((line = in.readLine())!= null)
           {
                pair=line.split(":",2);
                if(pair.length==2)
                    parse.put(pair[0].trim(),pair[1].trim());
           }
           in.close();
           //парсер для своих фильтров
           
           maskFilter = parse.get("file mask");
           countMaskFilter = new CountFiltration(parse.get("count files with mask"));
           
           patternFilter = parse.get("regular");
           countPatternFilter = new CountFiltration(parse.get("count files"));
           
           htmlFilter = parse.get("html");
           
           //парсер параметров для github
            for (Map.Entry<String, String> param : keys.entrySet())
            {
                String key = param.getKey();
                String tag = param.getValue();
                if(parse.containsKey(key))
                {
                    String value = parse.get(key);
                    if(value.length() != 0)
                        queryString.append(tag).append(value).append(' ');
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
             gitHubFilter = queryString.toString();
        }
    }
    
    private static void setKeys()
    {
        keys.put("the search keywords","");
        keys.put("owners","user:");
        keys.put("repositories(USERNAME/REPOSITORY)","repo:");
        keys.put("created(YYYY-MM-DD)","created:");
        keys.put("language","language:");
        keys.put("number of stars","stars:");
        keys.put("number of forks","forks:");
        keys.put("size(in kilobytes)","size:");
        keys.put("pushed(YYYY-MM-DD)","pushed:");
        keys.put("Filters whether forked repositories should be included (true)or only forked repositories should be returned (only)","fork:");
        keys.put("topic","topic:");
        keys.put("code with this extension","extension:");
        keys.put("In this path ","path:");
        keys.put("With this many comments","comments:");
        keys.put("Opened by the author","author:");
        keys.put("Mentioning the users","mentions");
        keys.put("Assigned to the users","assignee:");
        keys.put("Updated before the date","updated:");
        keys.put("Users full name","fullname:");
        keys.put("From this location","location:");
        keys.put("user with this many followers","followers:");
        keys.put("user with this many public repositories","repos:");
    }
      private static final HashMap<String,String> keys = new HashMap<>();
    
}
