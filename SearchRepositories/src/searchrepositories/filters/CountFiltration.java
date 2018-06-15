
package searchrepositories.filters;

public class CountFiltration {
    private Integer from;
    private Integer to;
    
    public CountFiltration(String parse)
    {
        try{
        if (parse.contains(".."))
        {
             String[] interval = parse.replace('.',' ').split("  ");
             from = Integer.parseInt(interval[0]);
             to = Integer.parseInt(interval[1]);
             return;
        }
        if (parse.contains("<"))
        {
             String value = parse.replace("<","");
             from = null;
             to = Integer.parseInt(value);
             return;
        }
        if (parse.contains(">"))
        {
             String value = parse.replace(">","");
             from = Integer.parseInt(value);
             to = null;
             return;
        }
        from = Integer.parseInt(parse);
        to = from;
        }
        catch(NumberFormatException e)
        {
            from = null;
            to = null;
        }
    }
    
    public boolean contains(int c)
    {//=5
        if(c==0)
            return false;
        else{ 
            if (from != null && to == null)
               return c>=from;
            if (to != null && from == null)
               return c<=to;
            if (to != null && from != null)
               return c<=to && c>=from;
        } 
        return true;
    }
}
