package cr.ac.una.unaplanillaws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PLAM_BANCOS", schema = "UNA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Banco.findAll", query = "SELECT b FROM Banco b"),
    @NamedQuery(name = "Banco.findById", query = "SELECT b FROM Banco b WHERE b.id = :id"),
    @NamedQuery(name = "Banco.findByNombre", query = "SELECT b FROM Banco b WHERE b.nombre = :nombre")
})
public class Banco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PLAM_BANCOS_BAN_ID_GENERATOR", sequenceName = "una.PLAM_BANCOS_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAM_BANCOS_BAN_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "BAN_ID")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "BAN_NOMBRE")
    private String nombre;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "BAN_REBAJOCOMISION")
    private String rebajoComision;

    @Basic(optional = false)
    @NotNull
    @Column(name = "BAN_COMISIONTRAN")
    private Long comisionTransferencia;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "BAN_ESTADO")
    private String estado;

    @Version
    @Basic(optional = false)
    @NotNull
    @Column(name = "BAN_VERSION")
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "banco", fetch = FetchType.LAZY)
    private List<CuentaBancaria> cuentasBancarias = new ArrayList<>();

    public Banco() {
    }

    public Banco(Long id) {
        this.id = id;
    }

    public Banco(BancoDto bancoDto) {
        this.id = bancoDto.getId();
        actualizar(bancoDto);
    }

    public void actualizar(BancoDto bancoDto) {
        this.nombre = bancoDto.getNombre();
        this.rebajoComision = (bancoDto.getCobraComision() != null && bancoDto.getCobraComision()) ? "E" : "M";
        this.comisionTransferencia = bancoDto.getComision();
        this.estado = (bancoDto.getActivo() != null && bancoDto.getActivo()) ? "A" : "I";
        this.version = bancoDto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRebajoComision() {
        return rebajoComision;
    }

    public void setRebajoComision(String rebajoComision) {
        this.rebajoComision = rebajoComision;
    }

    public Long getComisionTransferencia() {
        return comisionTransferencia;
    }

    public void setComisionTransferencia(Long comisionTransferencia) {
        this.comisionTransferencia = comisionTransferencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @XmlTransient
    public List<CuentaBancaria> getCuentasBancarias() {
        return cuentasBancarias;
    }

    public void setCuentasBancarias(List<CuentaBancaria> cuentasBancarias) {
        this.cuentasBancarias = cuentasBancarias;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Banco other = (Banco) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillaws.model.Banco[ id=" + id + " ]";
    }
}