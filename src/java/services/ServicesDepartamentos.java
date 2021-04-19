package services;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Departamento;
import repositories.RepositoryDepartamentos;

@Path("/departamentos")
public class ServicesDepartamentos {

    RepositoryDepartamentos repo;

    public ServicesDepartamentos() {
        this.repo = new RepositoryDepartamentos();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDepartamentos() throws SQLException {
        List<Departamento> departamentos = this.repo.getDepartamento();
        Gson converter = new Gson();
        String json = converter.toJson(departamentos);
        return json;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String findDepartamento(@PathParam("id") String id) throws SQLException {
        int iddept = Integer.parseInt(id);
        Departamento dept = this.repo.findDepartamento(iddept);
        Gson converter = new Gson();
        String json = converter.toJson(dept);
        return json;
    }

    //metodo para insertar (post)
    //tenemos dos formas de realizar la accion para insertar
    //1) enviar los parametros uno a uno
    //@path("/post/{iddept}/{nombre}/{localidad}")
    //2 enviar el objeto completo en elo formato json
    //@path("/post"){idDepartamento : 10}
    //cuando hacemos put post o delute , no se devuelve nada
    //pero tenemos que enviar infonmacion al servidor para saber si
    //se ha
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/post")
    public Response insertarDepartamento(String deptjson) throws SQLException {
        Gson converter = new Gson();
        //debemos convertir el String json a la clase Departamento
        //lo que se llama desearializar
        Departamento dept
                = converter.fromJson(deptjson, Departamento.class);
        //con  el departamento, ya tenemos los datos e insertamos
        this.repo.insertarDepartamento(dept.getIdDepartamento(), dept.getNombre(),
                dept.getLocalidad());
        //devolvemos una respuesta correcta
        return Response.status(200).build();

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/put")
    public Response modificarDepartamento(String deptjson) throws SQLException {
        Gson converter = new Gson();
        //debemos convertir el String json a la clase Departamento
        //lo que se llama desearializar
        Departamento dept
                = converter.fromJson(deptjson, Departamento.class);
        //con  el departamento, ya tenemos los datos e insertamos
        this.repo.modificarDepartamento(dept.getIdDepartamento(), dept.getNombre(),
                dept.getLocalidad());
        //devolvemos una respuesta correcta
        return Response.status(200).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response eliminarDepartamento(@PathParam("id") String id) throws SQLException {
        int iddept = Integer.parseInt(id);
        System.out.println(iddept);
        this.repo.eliminarDepartamento(iddept);
        return Response.status(200).build();

    }
}
