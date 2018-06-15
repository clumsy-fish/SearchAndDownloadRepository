package searchrepositories.filters;

import java.util.List;
import org.eclipse.egit.github.core.SearchRepository;

public interface Filter {
    // Аргумент список репов для фильтрации
    // Возвращает список прощедших проверку
    List<SearchRepository> filtrate(List<SearchRepository> repo);
}
