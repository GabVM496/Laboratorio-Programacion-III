package cr.ac.una.unaplanillaws.controller;

import cr.ac.una.unaplanillaws.model.CuentaBancariaDto;
import cr.ac.una.unaplanillaws.service.CuentaBancariaService;
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

@Path("/CuentasBancarias")
public class CuentaBancariaController {

    @EJB
    CuentaBancariaService cuentaBancariaService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCuentaBancaria(@PathParam("id") Long id) {
        try {
            Respuesta respuesta = cuentaBancariaService.getCuentaBancaria(id);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            } else {
                return Response.ok(respuesta.getResultado("CuentaBancaria")).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(CuentaBancariaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo la cuenta bancaria.").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCuentasBancarias() {
        try {
            Respuesta respuesta = cuentaBancariaService.getCuentasBancarias();
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            } else {
                return Response.ok(
                        new GenericEntity<List<CuentaBancariaDto>>(
                                (List<CuentaBancariaDto>) respuesta.getResultado("CuentasBancarias")
                        ) {
                }
                ).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(CuentaBancariaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo las cuentas bancarias.").build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/cuentaBancaria")
    public Response guardarCuentaBancaria(@Valid CuentaBancariaDto cuentaBancariaDto) {
        try {
            Respuesta respuesta = cuentaBancariaService.guardarCuentaBancaria(cuentaBancariaDto);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            } else {
                return Response.ok(respuesta.getResultado("CuentaBancaria")).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(CuentaBancariaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando la cuenta bancaria.").build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/cuentaBancaria/{id}")
    public Response eliminarCuentaBancaria(@PathParam("id") Long id) {
        try {
            Respuesta respuesta = cuentaBancariaService.eliminarCuentaBancaria(id);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            } else {
                return Response.ok().build();
            }
        } catch (Exception ex) {
            Logger.getLogger(CuentaBancariaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error eliminando la cuenta bancaria.").build();
        }
    }
}