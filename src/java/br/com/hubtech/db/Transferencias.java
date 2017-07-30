
package br.com.hubtech.db;


import br.com.hubtech.model.Transferencia;

/**
 *
 * @author Ricardo
 */
public class Transferencias extends Tabela<String, Transferencia>{
    
    private static Transferencias transferencias;
    private Long id = new Long(0);
    
    private Transferencias(){}
    
    public static Transferencias getInstance()
    {
        if(transferencias == null)
            transferencias = new Transferencias();
        
        return transferencias;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return ++id;
    }
}
