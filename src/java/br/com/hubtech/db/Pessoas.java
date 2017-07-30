
package br.com.hubtech.db;

import br.com.hubtech.model.Pessoa;

/**
 *
 * @author Ricardo
 */
public class Pessoas extends Tabela<Long, Pessoa>{

    private static Pessoas pessoas;
    
    private Pessoas(){}
    
    public static Pessoas getInstance()
    {
        if(pessoas == null)
            pessoas = new Pessoas();
        
        return pessoas;
    }
}