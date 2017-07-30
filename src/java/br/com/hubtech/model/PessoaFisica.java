package br.com.hubtech.model;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ricardo
 */
@XmlRootElement
public class PessoaFisica extends Pessoa
{
    private Calendar dataNascimento;
    
    /**
     * @return the cpf
     */
    @XmlElement
    public Long getCpf() {
        return getId();
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(Long cpf) {
        this.setId(cpf);
    }
    
    /**
     * @return the dataNascimento
     */
    @XmlElement
    public Calendar getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(Calendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
}
