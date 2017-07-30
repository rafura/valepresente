
package br.com.hubtech.dao;

import br.com.hubtech.db.Pessoas;
import br.com.hubtech.model.Pessoa;
import java.util.Collection;

/**
 *
 * @author Ricardo
 */
public class PessoaDao {

    public void persist(Pessoa pessoa)
    {
        Pessoas pessoas = Pessoas.getInstance();
        pessoas.addOrUpdate(pessoa.getId(), pessoa);
    }
    
    public <T> T get(Long id)
    {
        Pessoas pessoas = Pessoas.getInstance();
        return (T) pessoas.get(id);
    }
    
    public <T> T delete(Long id)
    {
        Pessoas pessoas = Pessoas.getInstance();
        return (T) pessoas.delete(id);
    }
    
    public <T extends Pessoa> Collection<T> list()
    {
        Pessoas pessoas = Pessoas.getInstance();
        return (Collection<T>) pessoas.getList();
    }
}