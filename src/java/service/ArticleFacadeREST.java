/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Article;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author remi
 */
@Stateless

@Path("article")
public class ArticleFacadeREST extends AbstractFacade<Article> {

    @PersistenceContext
    private EntityManager em;

    public ArticleFacadeREST() {
        super(Article.class);
    }


    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, "application/json", "application/xml"})
    public String create(MultivaluedMap<String, String> inFormParams) {
        System.out.println("DANS CREATE");
        Article a = new Article(inFormParams.getFirst("titre"), inFormParams.getFirst("content"));
        super.create(a);
        return "/BlogWS2015/resources/article/" + a.getId();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, "application/json"})
    @Produces({"application/json"})
    public Article edit(MultivaluedMap<String, String> inFormParams) {
        Article a = super.find(Long.parseLong(inFormParams.getFirst("id")));
        a.setContent(inFormParams.getFirst("content"));
        a.setTitre(inFormParams.getFirst("titre"));
        super.edit(a);
        return a;
    }

    @DELETE
    @Path("{id}")
    public String remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
        return "" + id;
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Article find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Article> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Article> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        int[] range = new int[]{from, to};
        Query q = em.createQuery("select a from Article a ORDER BY a.time ASC", Article.class);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @java.lang.Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
