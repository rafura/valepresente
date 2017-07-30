
package br.com.hubtech.model;

import java.math.BigDecimal;

/**
 *
 * @author Ricardo
 */

public class Aporte {
    
    private Conta contaDestino;
    private BigDecimal valor;
    private String id;

    /**
     * @return the contaDestino
     */
    public Conta getContaDestino() {
        return contaDestino;
    }

    /**
     * @param contaDestino the contaDestino to set
     */
    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }

    /**
     * @return the valor
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
}
