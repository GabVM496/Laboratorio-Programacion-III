package cr.ac.una.unaplanilla.service;

import cr.ac.una.unaplanilla.model.BancoDto;
import cr.ac.una.unaplanilla.util.Request;
import cr.ac.una.unaplanilla.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BancoService {

    public Respuesta getBancos() {
        try {
            Request request = new Request("Bancos");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<BancoDto> bancosDto = (List<BancoDto>) request.readEntity(new GenericType<List<BancoDto>>() { });

            return new Respuesta(true, "", "", "Bancos", bancosDto);
        } catch (Exception ex) {
            Logger.getLogger(BancoService.class.getName()).log(Level.SEVERE, "Error obteniendo bancos.", ex);
            return new Respuesta(false, "Error obteniendo bancos.", "getBancos " + ex.getMessage());
        }
    }

    public Respuesta getBanco(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("Bancos", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            BancoDto banco = (BancoDto) request.readEntity(BancoDto.class);

            return new Respuesta(true, "", "", "Banco", banco);
        } catch (Exception ex) {
            Logger.getLogger(BancoService.class.getName()).log(Level.SEVERE, "Error obteniendo el banco [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el banco.", "getBanco " + ex.getMessage());
        }
    }
    
    public Respuesta getBancos(String nombre, String rebajo, String estado) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("nombre", nombre);
            parametros.put("rebajo", rebajo);
            parametros.put("estado", estado);
            Request request = new Request("Bancos", "/{nombre}/{rebajo}/{estado}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<BancoDto> bancos = (List<BancoDto>) request.readEntity(new GenericType<List<BancoDto>>() {});
            return new Respuesta(true, "", "", "Bancos", bancos);
        } catch (Exception ex) {
            Logger.getLogger(BancoService.class.getName()).log(Level.SEVERE, "Error obteniendo bancos.", ex);
            return new Respuesta(false, "Error obteniendo bancos.", "getBancos " + ex.getMessage());
        }
    }

    public Respuesta guardarBanco(BancoDto bancoDto) {
        try {
            Request request = new Request("Bancos/banco");
            request.post(bancoDto);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            BancoDto banco = (BancoDto) request.readEntity(BancoDto.class);

            return new Respuesta(true, "", "", "Banco", banco);
        } catch (Exception ex) {
            Logger.getLogger(BancoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el banco.", ex);
            return new Respuesta(false, "Ocurrio un error al guardar el banco.", "guardarBanco " + ex.getMessage());
        }
    }

    public Respuesta eliminarBanco(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("Bancos/banco", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(BancoService.class.getName()).log(Level.SEVERE, "Error eliminando el banco", ex);
            return new Respuesta(false, "Error eliminando el banco.", "eliminarBanco " + ex.getMessage());
        }
    }
}