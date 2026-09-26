package cr.ac.una.unaplanillaws.service;

import cr.ac.una.unaplanillaws.model.Banco;
import cr.ac.una.unaplanillaws.model.BancoDto;
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
public class BancoService {

    private static final Logger LOG = Logger.getLogger(BancoService.class.getName());

    @PersistenceContext(unitName = "UnaPlanillaWsPU")
    private EntityManager em;

    public Respuesta getBanco(Long id) {
        try {
            Query qryBanco = em.createNamedQuery("Banco.findByBanId", Banco.class);
            qryBanco.setParameter("banId", id);

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Banco", new BancoDto((Banco) qryBanco.getSingleResult()));

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existe un banco con el código ingresado.", "getBanco NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el banco.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el banco.", "getBanco NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el banco.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el banco.", "getBanco " + ex.getMessage());
        }
    }

    public Respuesta getBancos() {
        try {
            Query query = em.createNamedQuery("Banco.findAll", Banco.class);
            List<Banco> bancos = (List<Banco>) query.getResultList();
            List<BancoDto> bancosDto = new ArrayList<>();
            for (Banco banco : bancos) {
                bancosDto.add(new BancoDto(banco));
            }
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Bancos", bancosDto);
        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen bancos registrados.", "getBancos NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar los bancos.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar los bancos.", "getBancos " + ex.getMessage());
        }
    }
    
    public Respuesta getBancos(String nombre, String rebajo, String estado) {
        try {
            Query query = em.createNamedQuery("Banco.findByNombreRebajoEstado", Banco.class);
            query.setParameter("nombre", nombre);
            query.setParameter("rebajo", rebajo);
            query.setParameter("estado", estado);

            List<Banco> bancos = query.getResultList();
            List<BancoDto> bancosDto = new ArrayList<>();
            for (Banco banco : bancos) {
                bancosDto.add(new BancoDto(banco));
            }
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Bancos", bancosDto);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar los bancos.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,
                    "Ocurrió un error al consultar los bancos.", "getBancos " + ex.getMessage());
        }
    }

    public Respuesta guardarBanco(BancoDto bancoDto) {
        try {
            Banco banco;
            if (bancoDto.getId() != null && bancoDto.getId() > 0) {
                banco = em.find(Banco.class, bancoDto.getId());
                if (banco == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encontró el banco a modificar.", "guardarBanco NoResultException");
                }
                banco.actualizar(bancoDto);
                banco = em.merge(banco);
            } else {
                banco = new Banco(bancoDto);
                em.persist(banco);
            }
            em.flush();
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Banco", new BancoDto(banco));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el banco.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar el banco.", "guardarBanco " + ex.getMessage());
        }
    }

    public Respuesta eliminarBanco(Long id) {
        try {
            Banco banco;
            if (id != null && id > 0) {
                banco = em.find(Banco.class, id);
                if (banco == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO,
                            "No se encontró el banco a eliminar.", "eliminarBanco NoResultException");
                }
                if (banco.getCuentaBancariaList() != null && !banco.getCuentaBancariaList().isEmpty()) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,
                            "No se puede eliminar el banco porque tiene cuentas bancarias asociadas.",
                            "eliminarBanco tiene cuentas asociadas");
                }
                em.remove(banco);
            } else {
                return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO,
                        "Debe cargar el banco a eliminar.", "eliminarBanco NoResultException");
            }
            em.flush();
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,
                        "No se puede eliminar el banco porque tiene relaciones con otros registros.",
                        "eliminarBanco " + ex.getMessage());
            }
            LOG.log(Level.SEVERE, "Ocurrió un error al eliminar el banco.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,
                    "Ocurrió un error al eliminar el banco.", "eliminarBanco " + ex.getMessage());
        }
    }
    
}