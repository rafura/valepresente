
package br.com.hubtech.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ricardo
 */
@XmlRootElement
public abstract class Pessoa
{
    private Long id;
    private String nome;
    private Conta conta;

    /**
     * @return the id
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    @XmlElement
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     * @return 
     */
    public Pessoa setNome(String nome) {
        this.nome = nome;
        return this;
    }

    /**
     * @return the conta
     */
    @XmlElement
    public Conta getConta() {
        return conta;
    }

    /**
     * @param conta the conta to set
     */
    public void setConta(Conta conta) {
        this.conta = conta;
    }
    
    
}
