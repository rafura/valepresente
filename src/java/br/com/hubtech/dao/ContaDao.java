
package br.com.hubtech.dao;

import br.com.hubtech.db.Contas;
import br.com.hubtech.model.Conta;
import java.util.Collection;

/**
 *
 * @author Ricardo
 */
public class ContaDao {

    public void persist(Conta conta)
    {
        Contas contas = Contas.getInstance();
        contas.addOrUpdate(conta.getId(), conta);
    }
    
    public Conta get(String nome)
    {
        Contas contas = Contas.getInstance();
        return contas.get(nome);
    }
    
    public Collection<Conta> list()
    {
        Contas contas = Contas.getInstance();
        return contas.getList();
    }
    
    public Conta delete(String nome)
    {
        Contas contas = Contas.getInstance();
        return contas.delete(nome);
    }
    
}
