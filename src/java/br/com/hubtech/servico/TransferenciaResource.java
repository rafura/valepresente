
package br.com.hubtech.servico;

import br.com.hubtech.dao.ContaDao;
import br.com.hubtech.dao.TransferenciaDao;
import br.com.hubtech.model.Conta;
import br.com.hubtech.model.SituacaoConta;
import br.com.hubtech.model.Transferencia;
import java.math.BigDecimal;
import java.util.Collection;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Ricardo
 */
@Path("transferencia")
public class TransferenciaResource {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        return "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><root></root>";
    }
    
    @GET
    @Produces( { MediaType.TEXT_PLAIN })
    @Path("/transferencia/{contaorigem}/{contadestino}/{valor}")
    public String transferencia(@PathParam("contaorigem") String contaOrigem,
            @PathParam("contadestino") String contaDestino,
            @PathParam("valor") String valor)
    {
        BigDecimal valorAporte;
        try{
            valorAporte = BigDecimal.valueOf(Double.parseDouble(valor));
        }
        catch(NumberFormatException e)
        {
            return "Valor invalido";
        }
        
        Conta cOrigem = getConta(contaOrigem);
        if(cOrigem == null)
            return "conta " + contaOrigem + " não localizada";
        
        if(cOrigem.getBalance().doubleValue() < valorAporte.doubleValue())
            return "Saldo insuficiente";
        
        Conta cDestino = null;
        for(Conta conta: cOrigem.getChildren().values())
        {
            cDestino = getConta(conta, contaDestino);
            if(cDestino != null)
                break;
        }
        
        if(cDestino == null)
            return "conta " + cDestino + " não localizada";
        
        if(cOrigem.getSituacaoConta() != SituacaoConta.ATIVA)
            return "conta " + contaOrigem + " não Ativa";
        
        if(cDestino.getSituacaoConta() != SituacaoConta.ATIVA)
            return "conta " + contaDestino + " não Ativa";
        
        if(cDestino.getChildren().size() > 0)
            return "conta " + contaOrigem + " não é uma conta filial";
        
        Transferencia transferencia = criaTransferencia(cOrigem, cDestino, valorAporte);
        TransferenciaDao dao = new TransferenciaDao();
        dao.persist(transferencia);
        
        cOrigem.setBalance(cOrigem.getBalance().subtract(valorAporte));
        cDestino.setBalance(cDestino.getBalance().add(valorAporte));
        
        return "Transferencia efetuada com sucesso";
    }
    
    @GET
    @Produces( { MediaType.TEXT_PLAIN })
    @Path("/aporte/{contadestino}/{valor}")
    public String aporte(@PathParam("contadestino") String contaDestino,
            @PathParam("valor") String valor)
    {
        try
        {
            Conta cDestino = getConta(contaDestino);
            if(cDestino == null)
                return "conta " + contaDestino + " não localizada";

            if(cDestino.getParent() != null)
                return "conta informada " + contaDestino + " não é uma conta Matriz";

            if(cDestino.getSituacaoConta() != SituacaoConta.ATIVA)
                return "conta informada " + contaDestino + " não Ativada";

            BigDecimal valorAporte = BigDecimal.valueOf(Double.parseDouble(valor));
            
            Transferencia transferencia = criaTransferencia(cDestino, valorAporte);
            TransferenciaDao dao = new TransferenciaDao();
            String id = dao.persist(transferencia);

            cDestino.setBalance(cDestino.getBalance().add(valorAporte));

            return "Aporte realizado com sucesso. ID " + id;
        }
        catch(NumberFormatException e)
        {
            return "Valor invalido";
        }
        catch(Exception e)
        {
            return e.getMessage();
        }
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/estorno/{id}/")
    public String estorno(@PathParam("id") String id)
    {
        TransferenciaDao dao = new TransferenciaDao();
        Transferencia transferencia = dao.get(id);
        
        if(transferencia == null)
            return "nao localizada";
        
        Conta cDesctino = getConta(transferencia.getContaDestino().getId());
        cDesctino.setBalance(cDesctino.getBalance().subtract(transferencia.getValor()));
        
        if(transferencia.getContaOrigem() != null)
        {
            Conta cOrigem = getConta(transferencia.getContaOrigem().getId());
            cOrigem.setBalance(cOrigem.getBalance().add(transferencia.getValor()));
        }
        
        dao.delete(id);
        
        return "Estorno realizado com sucesso";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/list/")
    public String list()
    {
        TransferenciaDao dao = new TransferenciaDao();
        Collection<Transferencia> list = dao.list();
        
        StringBuilder sb = new StringBuilder();
        for(Transferencia transferencia: list)
        {
            sb.append("<br>ID:" + transferencia.getId());
            
            if(transferencia.getContaOrigem() != null)
                sb.append("<br>Conta Origem:" + transferencia.getContaOrigem().getId());
            
            sb.append("<br>ContaDestino:" + transferencia.getContaDestino().getId());
            sb.append("<br>Vaor:" + transferencia.getValor().doubleValue());
            sb.append("<p>");
        }
        
        return sb.toString();
    }
    
    private Transferencia criaTransferencia(Conta origem, Conta destino, BigDecimal valor)
    {
        Transferencia transferencia = new Transferencia();
        transferencia.setContaOrigem(origem);
        transferencia.setContaDestino(destino);
        transferencia.setValor(valor);
        return transferencia;
    }
    
    private Transferencia criaTransferencia(Conta destino, BigDecimal valor)
    {
        Transferencia transferencia = new Transferencia();
        transferencia.setContaDestino(destino);
        transferencia.setValor(valor);
        return transferencia;
    }
    
    private Conta getConta( String nome)
    {
        ContaDao dao =  new ContaDao();
        Collection<Conta> lista= dao.list();
        
        for(Conta conta: lista)
        {
            Conta c = getConta(conta, nome);
            if(c != null)
                return c;
        }
        
        return null;
    }
    
    private Conta getConta(Conta conta, String nome)
    {
        if(conta.getChildren().size() > 0)
        {
           Collection<Conta> children = conta.getChildren().values();
           for(Conta c: children)
           {
               Conta cc = getConta(c, nome);
               if( cc != null)
                   return cc;
           }
        }
        
        if(conta.getId().equals(nome))
            return conta;
        else
            return null;
    }
}
