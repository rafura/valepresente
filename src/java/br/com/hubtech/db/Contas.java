
package br.com.hubtech.db;

import br.com.hubtech.model.Conta;


/**
 *
 * @author Ricardo
 */
public class Contas extends Tabela<String, Conta>{

    private static Contas contas;
    
    private Contas(){}
    
    public static Contas getInstance()
    {
        if(contas == null)
            contas = new Contas();
        
        return contas;
    }
}
