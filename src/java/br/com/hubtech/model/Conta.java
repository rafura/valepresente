
package br.com.hubtech.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ricardo
 */
@XmlRootElement
public class Conta {
    
    private String id;
    private Calendar dataCriacao;
    private SituacaoConta situacaoConta;
    private Conta parent;
    private final Map<String, Conta> children = new HashMap<>();
    private BigDecimal balance = new BigDecimal(0);
    private Pessoa pessoa;
    
    /**
     * @return the id
     */
    @XmlElement
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     * @return 
     */
    public Conta setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return the dataCriacao
     */
    public Calendar getDataCriacao() {
        return dataCriacao;
    }

    /**
     * @param dataCriacao the dataCriacao to set
     * @return 
     */
    public Conta setDataCriacao(Calendar dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    /**
     * @return the parent
     */
    @XmlElement
    public Conta getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Conta parent) {
        this.parent = parent;
    }

    /**
     * @return the situacaoConta
     */
    @XmlElement
    public SituacaoConta getSituacaoConta() {
        return situacaoConta;
    }

    /**
     * @param situacaoConta the situacaoConta to set
     * @return 
     */
    public Conta setSituacaoConta(SituacaoConta situacaoConta) {
        this.situacaoConta = situacaoConta;
        return this;
    }
    
    public void addChidren(Conta conta)
    {
        getChildren().put(conta.getId(), conta);
    }

    /**
     * @return the children
     */
    @XmlElement
    @XmlElementWrapper
    public Map<String, Conta> getChildren() {
        return children;
    }

    /**
     * @return the balance
     */
    @XmlElement
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
        balance.add(balance);
    }

    /**
     * @return the pessoa
     */
    @XmlElement
    public Pessoa getPessoa() {
        return pessoa;
    }

    /**
     * @param pessoa the pessoa to set
     */
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    
}
