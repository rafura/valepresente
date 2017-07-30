
package br.com.hubtech.servico;

import br.com.hubtech.dao.PessoaDao;
import br.com.hubtech.model.Pessoa;
import br.com.hubtech.model.PessoaFisica;
import br.com.hubtech.model.PessoaJuridica;
import java.text.ParseException;
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
@Path("pessoa")
public class PessoaResource {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        return "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><root></root>";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/criarpessoafisica/{cpf}/{datanascimento}/{nome}/")
    public String criarPessoaFisica(@PathParam("cpf") Long cpf,
            @PathParam("datanascimento") String datanascimento,
            @PathParam("nome") String nome)
    {
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setCpf(cpf);
        pessoaFisica.setNome(nome);
        
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        Calendar dataNascimento = Calendar.getInstance();
        
        try {
            dataNascimento.setTime(sdf.parse(datanascimento));
            pessoaFisica.setDataNascimento(dataNascimento);
        } catch (ParseException ex) {
            return ex.getMessage();
        }
     
        PessoaDao dao = new PessoaDao();
        dao.persist(pessoaFisica);
        return "Pessoa Fisica criada com sucesso";
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/criarpessoajuridica/{cnpj}/{razaosocial}/{nomefantasia}")
    public PessoaJuridica criarPessoaJuridica(@PathParam("cnpj") Long cnpj,
            @PathParam("razaosocial") String razaosocial,
            @PathParam("nomefantasia") String nomefantasia)
    {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setCnpj(cnpj);
        pessoaJuridica.setRazaoSocial(razaosocial);
        pessoaJuridica.setNomeFantasia(nomefantasia);
        
        PessoaDao dao = new PessoaDao();
        dao.persist(pessoaJuridica);
        return pessoaJuridica;
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/alterarpesoafisica/{cpf}/{datanascimento}/{nome}")
    public String alterarPessoaFisica(@PathParam("cpf") Long cpf,
            @PathParam("datanascimento") String datanascimento,
            @PathParam("nome") String nome)
    {
        PessoaDao dao = new PessoaDao();
        Pessoa pessoa = dao.get(cpf);
        
        if(pessoa == null)
            return "CPF nao localizado";
        
        PessoaFisica pf;
        if(pessoa instanceof PessoaFisica)
            pf = (PessoaFisica)pessoa;
        else
            return "cpf informado nao eh de pesso fisica";
        
        pessoa.setNome(nome);

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        Calendar dataNascimento = Calendar.getInstance();
        try {
            dataNascimento.setTime(sdf.parse(datanascimento));
            ((PessoaFisica) pessoa).setDataNascimento(dataNascimento);
        } catch (ParseException ex) {
            return ex.getMessage();
        }

        dao.persist(pessoa);
        return "Alteracao realizada com sucesso";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/alterarpesoajuridica/{cnpj}/{razaosocial}/{nomefantasia}")
    public String alterarPessoaJuridica(@PathParam("cnpj") Long cnpj,
            @PathParam("razaosocial") String razaosocial,
            @PathParam("nomefantasia") String nomefantasia)
    {
        
        PessoaDao dao = new PessoaDao();
        Pessoa pessoa = dao.get(cnpj);
        
        if(pessoa == null)
            return "CPF nao localizado";
        
        PessoaJuridica pj;
        if(pessoa instanceof PessoaJuridica)
            pj = (PessoaJuridica) pessoa;
        else
            return "cpnj informado nao eh pesso juridica";
        
        pj.setRazaoSocial(razaosocial);
        pj.setNomeFantasia(nomefantasia);
        
        dao.persist(pj);
        return "Alteracao realizada com sucesso";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/listapessoas/")
    public  String listaPessoas()
    {
        PessoaDao dao = new PessoaDao();
        Collection<Pessoa> lista = dao.list();
        StringBuilder sb = new StringBuilder();
        
        for(Pessoa pessoa: lista)
        {
            sb.append("Nome:" + pessoa.getNome());
            sb.append("<br>ID:" + pessoa.getId());
            
            if(pessoa instanceof PessoaFisica)
                sb.append("<br>Tipo: Fisica");
            else
                sb.append("<br>Tipo: Juridica");
            
            sb.append("<p>");
        }
        
        return sb.toString();
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/excluir/{id}")
    public String excluir(@PathParam("id") String id )
    {
        long cpfOrCnpj;
        try{
            cpfOrCnpj = Long.parseLong(id);
        }catch(Exception e){
            return "CPF Ou CNPJ invalidos";
        }
        
        PessoaDao dao = new PessoaDao();
        Pessoa pessoa = dao.get(cpfOrCnpj);
        
        if(pessoa == null)
            return "Registro " + id + " não localizado";
            
        if(pessoa.getConta() != null)
            return "Pessoa fisica ou juridica ainda possui conta associada";
        
        dao.delete(cpfOrCnpj);    
        return "Registro " + id + " excluido com sucesso";
    }
}
