package cr.ac.una.unaplanillaws.controller;

import cr.ac.una.unaplanillaws.model.BancoDto;
import cr.ac.una.unaplanillaws.service.BancoService;
import cr.ac.una.unaplanillaws.util.CodigoRespuesta;
import cr.ac.una.unaplanillaws.util.Respuesta;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/Bancos")
public class BancoController {

    @EJB
    BancoService bancoService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBanco(@PathParam("id") Long id) {
        try {
            Respuesta respuesta = bancoService.getBanco(id);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            } else {
                return Response.ok(respuesta.getResultado("Banco")).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(BancoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el banco.").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBancos() {
        try {
            Respuesta respuesta = bancoService.getBancos();
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            } else {
                return Response.ok(
                        new GenericEntity<List<BancoDto>>(
                                (List<BancoDto>) respuesta.getResultado("Bancos")
                        ) {
                }
                ).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(BancoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo los bancos.").build();
        }
    }
    
    @GET
    @Path("/{nombre}/{rebajo}/{estado}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBancos(@PathParam("nombre") String nombre, @PathParam("rebajo") String rebajo, @PathParam("estado") String estado) {
        try {
            Respuesta respuesta = bancoService.getBancos(nombre, rebajo, estado);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue())
                        .entity(respuesta.getMensaje()).build();
            }
            return Response.ok(
                    new GenericEntity<List<BancoDto>>(
                            (List<BancoDto>) respuesta.getResultado("Bancos")
                    ) {}
            ).build();
        } catch (Exception ex) {
            Logger.getLogger(BancoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue())
                    .entity("Error obteniendo los bancos.").build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/banco")
    public Response guardarBanco(@Valid BancoDto bancoDto) {
        try {
            Respuesta respuesta = bancoService.guardarBanco(bancoDto);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            } else {
                return Response.ok(respuesta.getResultado("Banco")).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(BancoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el banco.").build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/banco/{id}")
    public Response eliminarBanco(@PathParam("id") Long id) {
        try {
            Respuesta respuesta = bancoService.eliminarBanco(id);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            } else {
                return Response.ok().build();
            }
        } catch (Exception ex) {
            Logger.getLogger(BancoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error eliminando el banco.").build();
        }
    }
}