
package searchrepositories.filters;

import java.util.ArrayList;
import java.util.List;
import searchrepositories.Model;

public class FilterFactory {
    public static List<Filter> create(String dir, Model parametrs)
    {
        List<Filter> result = new ArrayList<>(); 
        if (parametrs.maskFilter != null && !parametrs.maskFilter.equals(""))
            result.add(new FilterByFileMask(dir, parametrs.maskFilter, parametrs.countMaskFilter));
        if (parametrs.patternFilter != null && !parametrs.patternFilter.equals(""))
            result.add(new FilterByPattern(dir, parametrs.patternFilter, parametrs.countPatternFilter));
        if (parametrs.htmlFilter != null && !parametrs.htmlFilter.equals(""))
            result.add(new FilterHtml(dir,parametrs.htmlFilter,parametrs.countHtmlFilter));
        return result;
    }
}
