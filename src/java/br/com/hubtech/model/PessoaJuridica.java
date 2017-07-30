
package br.com.hubtech.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ricardo
 */
@XmlRootElement
public class PessoaJuridica extends Pessoa
{
    private String nomeFantasia;

    /**
     * @return the cnpj
     */
    @XmlElement
    public Long getCnpj() {
        return getId();
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(Long cnpj) {
        this.setId(cnpj);
    }

    /**
     * @return the razaoSocial
     */
    @XmlElement
    public String getRazaoSocial() {
        return getNome();
    }

    /**
     * @param razaoSocial the razaoSocial to set
     */
    public void setRazaoSocial(String razaoSocial) {
        this.setNome(razaoSocial);
    }

    /**
     * @return the nomeFantasia
     */
    @XmlElement
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    /**
     * @param nomeFantasia the nomeFantasia to set
     */
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
}
