
package br.com.hubtech.dao;


import br.com.hubtech.db.Transferencias;
import br.com.hubtech.model.Transferencia;
import java.util.Collection;

/**
 *
 * @author Ricardo
 */
public class TransferenciaDao {
    
    public String persist(Transferencia transferencia)
    {
        Transferencias transferencias = Transferencias.getInstance();
        String id = String.valueOf(transferencias.getId());
        transferencia.setId(id);
        transferencias.addOrUpdate(id, transferencia);
        return id;
    }
    
    public Transferencia get(String id)
    {
        Transferencias transferencias = Transferencias.getInstance();
        return transferencias.get(id);
    }
    
    public Transferencia delete(String id)
    {
        Transferencias transferencias = Transferencias.getInstance();
        return transferencias.delete(id);
    }
    
    public Collection<Transferencia> list()
    {
        Transferencias transferencias = Transferencias.getInstance();
        return transferencias.getList();
    }
}
