package cr.ac.una.unaplanilla.service;

import cr.ac.una.unaplanilla.model.CuentaBancariaDto;
import cr.ac.una.unaplanilla.util.Request;
import cr.ac.una.unaplanilla.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuentaBancariaService {

    public Respuesta getCuentasBancarias() {
        try {
            Request request = new Request("CuentasBancarias");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<CuentaBancariaDto> cuentasBancariasDto = (List<CuentaBancariaDto>) request.readEntity(new GenericType<List<CuentaBancariaDto>>() { });

            return new Respuesta(true, "", "", "CuentasBancarias", cuentasBancariasDto);
        } catch (Exception ex) {
            Logger.getLogger(CuentaBancariaService.class.getName()).log(Level.SEVERE, "Error obteniendo cuentas bancarias.", ex);
            return new Respuesta(false, "Error obteniendo cuentas bancarias.", "getCuentasBancarias " + ex.getMessage());
        }
    }

    public Respuesta getCuentaBancaria(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("CuentasBancarias", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            CuentaBancariaDto cuentaBancaria = (CuentaBancariaDto) request.readEntity(CuentaBancariaDto.class);

            return new Respuesta(true, "", "", "CuentaBancaria", cuentaBancaria);
        } catch (Exception ex) {
            Logger.getLogger(CuentaBancariaService.class.getName()).log(Level.SEVERE, "Error obteniendo la cuenta bancaria [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo la cuenta bancaria.", "getCuentaBancaria " + ex.getMessage());
        }
    }

    public Respuesta guardarCuentaBancaria(CuentaBancariaDto cuentaBancariaDto) {
        try {
            Request request = new Request("CuentasBancarias/cuentaBancaria");
            request.post(cuentaBancariaDto);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            CuentaBancariaDto cuentaBancaria = (CuentaBancariaDto) request.readEntity(CuentaBancariaDto.class);

            return new Respuesta(true, "", "", "CuentaBancaria", cuentaBancaria);
        } catch (Exception ex) {
            Logger.getLogger(CuentaBancariaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar la cuenta bancaria.", ex);
            return new Respuesta(false, "Ocurrio un error al guardar la cuenta bancaria.", "guardarCuentaBancaria " + ex.getMessage());
        }
    }

    public Respuesta eliminarCuentaBancaria(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("CuentasBancarias/cuentaBancaria", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(CuentaBancariaService.class.getName()).log(Level.SEVERE, "Error eliminando la cuenta bancaria", ex);
            return new Respuesta(false, "Error eliminando la cuenta bancaria.", "eliminarCuentaBancaria " + ex.getMessage());
        }
    }
}