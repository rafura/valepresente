
package br.com.hubtech.servico;

import br.com.hubtech.dao.ContaDao;
import br.com.hubtech.dao.PessoaDao;
import br.com.hubtech.model.Conta;
import br.com.hubtech.model.Pessoa;
import br.com.hubtech.model.SituacaoConta;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
@Path("conta")
public class ContaResource {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        return "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><root></root>";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/criarcontamatriz/{nome}/{cpforcnpj}")
    public String criaContaMatriz(@PathParam("nome") String nome, @PathParam("cpforcnpj") Long cpfOrCnpJ)
    {
        PessoaDao pessoaDao = new PessoaDao();
        Pessoa pessoa = pessoaDao.get(cpfOrCnpJ);
        
        if(pessoa == null)
            return "Pessoa fisicxa ou juridica nao localizada";
        
        Conta conta = new Conta().setId(nome).setDataCriacao(Calendar.getInstance()).setSituacaoConta(SituacaoConta.BLOQUEADA);
        ContaDao dao = new ContaDao();
        conta.setPessoa(pessoa);
        pessoa.setConta(conta);
        dao.persist(conta);
        
        return "Conta criada com sucesso";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/criarcontafilial/{nomeContaMatriz}/{nomeContaFilial}")
    public String criaContaFilial(@PathParam("nomeContaMatriz") String nomeContaMatriz, @PathParam("nomeContaFilial") String nomeContaFilial) 
    {
        ContaDao dao = new ContaDao();
        Conta contaMatriz = get(nomeContaMatriz);
        
        if(contaMatriz == null)
            return "Conta Matriz " + nomeContaMatriz + " nao localizada";
        
        if(contaMatriz.getSituacaoConta() != SituacaoConta.ATIVA)
            return "Conta Matriz " + nomeContaMatriz + " nao esta ativada";
        
        Conta contaFilial = new Conta().setId(nomeContaFilial).setDataCriacao(Calendar.getInstance()).setSituacaoConta(SituacaoConta.BLOQUEADA);
        contaFilial.setParent(contaMatriz);
        contaMatriz.addChidren(contaFilial);
        return "Conta filial criada com sucesso";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/consultarconta/{nome}")
    public String consultarConta(@PathParam("nome") String nomeContaMatriz) 
    {
        Conta conta = get(nomeContaMatriz);
        if(conta == null)
            return "Conta nao localizada";
        
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        sb.append("<br>conta:" + conta.getId());
        sb.append("<br>saldo:" + conta.getBalance());
        sb.append("<br>eh matriz:" + (conta.getChildren().size() > 0));
        sb.append("<br>data criacao:" + sdf.format(conta.getDataCriacao().getTime()));
        
        if(conta.getPessoa() != null)
            sb.append("<br>Titular:" + conta.getPessoa().getNome());
        
        if(conta.getParent() != null)
            sb.append("<br>Conta matriz:" + conta.getParent().getId());
        else
            sb.append("<br>Conta matriz: Nao ha");
            
        sb.append("<br>Situacao:" + conta.getSituacaoConta());
        sb.append("<p>");
        
        return sb.toString();
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/listarcontas/")
    public String listarContas() 
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ContaDao dao = new ContaDao();
        Collection<Conta> lista = dao.list();
        StringBuilder sb = new StringBuilder();
        for(Conta conta: lista)
        {
            sb.append("<br>conta:" + conta.getId());
            sb.append("<br>saldo:" + conta.getBalance());
            sb.append("<br>eh matriz:" + (conta.getChildren().size() > 0));
            sb.append("<br>data criacao:" + sdf.format(conta.getDataCriacao().getTime()));
            sb.append("<br>eh filial:" + (conta.getParent() != null));
            sb.append("Titular:" + conta.getPessoa().getNome());
            sb.append("<br>Situacao:" + conta.getSituacaoConta());
            sb.append("<p>");
        }
        
        return sb.toString();
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/alterarconta/{nome}/{situacao}")
    public String alterarConta(@PathParam("nome") String nomeContaMatriz, @PathParam("situacao") String situacao) 
    {
        Conta conta = get(nomeContaMatriz);
        
        if(conta == null)
            return "Conta " + nomeContaMatriz + " nao localizada";
        
        SituacaoConta situacaoConta;
        try{
            situacaoConta = SituacaoConta.valueOf(situacao.toUpperCase());
        } catch(Exception E) {
            return "Situacao informada invalida";
        }
            
        conta.setSituacaoConta(situacaoConta);
        return "Conta alterada com sucesso";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/excluirConta/{nome}")
    public String excluirConta(@PathParam("nome") String nomeContaMatriz) 
    {
        Conta conta = get(nomeContaMatriz);
        
        if(conta == null)
            return "Conta " + nomeContaMatriz + " nao localizada";
        
        if(conta.getChildren().size() > 0)
            return "Conta " + nomeContaMatriz + " possui contas filiais.";
        
        if(conta.getBalance().doubleValue() > 0)
            return "Conta " + nomeContaMatriz + " ainda possui saldo.";
        
        Conta parent = conta.getParent();
        
        if(parent != null){
            parent.getChildren().remove(conta.getId());
        } else {
            ContaDao contas = new ContaDao();
            conta = contas.delete(nomeContaMatriz);
            Pessoa pessoa = conta.getPessoa();
            pessoa.setConta(null);
        }
        
        return "Conta " + nomeContaMatriz + " excluida com sucesso.";
    }
    
    private Conta get( String nome)
    {
        ContaDao contas = new ContaDao();
        Collection<Conta> lista= contas.list();
        
        for(Conta conta: lista)
        {
            if(conta.getId().equals(nome))
                return conta;
            
            Conta c = get(conta, nome);
            if(c != null)
                return c;
        }
        
        return null;
    }
    
    
    private Conta get(Conta conta, String nome)
    {
        if(conta.getChildren().size() > 0)
        {
           Collection<Conta> children = conta.getChildren().values();
           for(Conta c: children)
           {
               Conta cc = get(c, nome);
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
