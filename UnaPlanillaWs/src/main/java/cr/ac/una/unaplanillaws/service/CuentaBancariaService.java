package cr.ac.una.unaplanillaws.service;

import cr.ac.una.unaplanillaws.model.CuentaBancaria;
import cr.ac.una.unaplanillaws.model.CuentaBancariaDto;
import cr.ac.una.unaplanillaws.util.CodigoRespuesta;
import cr.ac.una.unaplanillaws.util.Respuesta;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class CuentaBancariaService {

    private static final Logger LOG = Logger.getLogger(CuentaBancariaService.class.getName());

    @PersistenceContext(unitName = "UnaPlanillaWsPU")
    private EntityManager em;

    public Respuesta getCuentaBancaria(Long id) {
        try {
            Query qryCuenta = em.createNamedQuery("CuentaBancaria.findByCbeId", CuentaBancaria.class);
            qryCuenta.setParameter("cbeId", id);

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "CuentaBancaria", new CuentaBancariaDto((CuentaBancaria) qryCuenta.getSingleResult()));

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existe una cuenta bancaria con el código ingresado.", "getCuentaBancaria NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar la cuenta bancaria.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar la cuenta bancaria.", "getCuentaBancaria NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar la cuenta bancaria.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar la cuenta bancaria.", "getCuentaBancaria " + ex.getMessage());
        }
    }

    public Respuesta getCuentasBancarias() {
        try {
            Query query = em.createNamedQuery("CuentaBancaria.findAll", CuentaBancaria.class);
            List<CuentaBancaria> cuentas = (List<CuentaBancaria>) query.getResultList();
            List<CuentaBancariaDto> cuentasDto = new ArrayList<>();
            for (CuentaBancaria cuenta : cuentas) {
                cuentasDto.add(new CuentaBancariaDto(cuenta));
            }
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "CuentasBancarias", cuentasDto);
        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen cuentas bancarias registradas.", "getCuentasBancarias NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar las cuentas bancarias.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar las cuentas bancarias.", "getCuentasBancarias " + ex.getMessage());
        }
    }

    public Respuesta guardarCuentaBancaria(CuentaBancariaDto cuentaBancariaDto) {
        try {
            CuentaBancaria cuenta;
            if (cuentaBancariaDto.getId() != null && cuentaBancariaDto.getId() > 0) {
                cuenta = em.find(CuentaBancaria.class, cuentaBancariaDto.getId());
                if (cuenta == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encontró la cuenta bancaria a modificar.", "guardarCuentaBancaria NoResultException");
                }
                cuenta.actualizar(cuentaBancariaDto);
                cuenta = em.merge(cuenta);
            } else {
                cuenta = new CuentaBancaria(cuentaBancariaDto);
                em.persist(cuenta);
            }
            em.flush();
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "CuentaBancaria", new CuentaBancariaDto(cuenta));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar la cuenta bancaria.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar la cuenta bancaria.", "guardarCuentaBancaria " + ex.getMessage());
        }
    }

    public Respuesta eliminarCuentaBancaria(Long id) {
        try {
            CuentaBancaria cuenta;
            if (id != null && id > 0) {
                cuenta = em.find(CuentaBancaria.class, id);
                if (cuenta == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encontró la cuenta bancaria a eliminar.", "eliminarCuentaBancaria NoResultException");
                }
                em.remove(cuenta);
            } else {
                return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "Debe cargar la cuenta bancaria a eliminar.", "eliminarCuentaBancaria NoResultException");
            }
            em.flush();
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "No se puede eliminar la cuenta bancaria porque tiene relaciones con otros registros.", "eliminarCuentaBancaria " + ex.getMessage());
            }
            LOG.log(Level.SEVERE, "Ocurrio un error al eliminar la cuenta bancaria.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al eliminar la cuenta bancaria.", "eliminarCuentaBancaria " + ex.getMessage());
        }
    }
}