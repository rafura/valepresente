
package br.com.hubtech.model;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ricardo
 */
@XmlRootElement
public class Transferencia {
    
    private Conta contaOrigem;
    private Conta contaDestino;
    private BigDecimal valor;
    private String id;

    /**
     * @return the contaOrigem
     */
    @XmlElement
    public Conta getContaOrigem() {
        return contaOrigem;
    }

    /**
     * @param contaOrigem the contaOrigem to set
     */
    public void setContaOrigem(Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    /**
     * @return the contaDestino
     */
    @XmlElement
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
    @XmlElement
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
    @XmlElement
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
