
package br.com.hubtech.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ricardo
 * @param <K>
 * @param <T>
 */
public class Tabela<K,T>{
    
    private final Map<K, T> registros = new HashMap<>();
    
    public void addOrUpdate(K k, T t)
    {
        registros.put(k, t);
    }
    
    public T get(K k)
    {
        return registros.get(k);
    }
    
    public T delete(K k)
    {
        return registros.remove(k);
    }
    
    public Collection<T> getList()
    {
        return registros.values();
    }
}
