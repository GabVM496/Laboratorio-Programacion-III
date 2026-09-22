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
    @NamedQuery(name = "Banco.findByBanId", query = "SELECT b FROM Banco b WHERE b.banId = :banId"),
    @NamedQuery(name = "Banco.findByBanNombre", query = "SELECT b FROM Banco b WHERE b.banNombre = :banNombre")
})
public class Banco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PLAM_BANCOS_BAN_ID_GENERATOR", sequenceName = "una.PLAM_BANCOS_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAM_BANCOS_BAN_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "BAN_ID")
    private Long banId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "BAN_NOMBRE")
    private String banNombre;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "BAN_REBAJOCOMISION")
    private String banRebajocomision;

    @Basic(optional = false)
    @NotNull
    @Column(name = "BAN_COMISIONTRAN")
    private Long banComisiontran;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "BAN_ESTADO")
    private String banEstado;

@Version
@Column(name = "BAN_VERSION")
private Long banVersion;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "banId", fetch = FetchType.LAZY)
    private List<CuentaBancaria> cuentaBancariaList = new ArrayList<>();

    public Banco() {
    }

    public Banco(Long banId) {
        this.banId = banId;
    }

    public Banco(BancoDto bancoDto) {
        this.banId = bancoDto.getId();
        actualizar(bancoDto);
    }

    public void actualizar(BancoDto bancoDto) {
        this.banNombre = bancoDto.getNombre();
        this.banRebajocomision = bancoDto.getRebajoComision();
        this.banComisiontran = bancoDto.getComision();
        this.banEstado = (bancoDto.getActivo() != null && bancoDto.getActivo()) ? "A" : "I";
    }

    public Long getBanId() {
        return banId;
    }

    public void setBanId(Long banId) {
        this.banId = banId;
    }

    public String getBanNombre() {
        return banNombre;
    }

    public void setBanNombre(String banNombre) {
        this.banNombre = banNombre;
    }

    public String getBanRebajocomision() {
        return banRebajocomision;
    }

    public void setBanRebajocomision(String banRebajocomision) {
        this.banRebajocomision = banRebajocomision;
    }

    public Long getBanComisiontran() {
        return banComisiontran;
    }

    public void setBanComisiontran(Long banComisiontran) {
        this.banComisiontran = banComisiontran;
    }

    public String getBanEstado() {
        return banEstado;
    }

    public void setBanEstado(String banEstado) {
        this.banEstado = banEstado;
    }

    public Long getBanVersion() {
        return banVersion;
    }

    public void setBanVersion(Long banVersion) {
        this.banVersion = banVersion;
    }

    @XmlTransient
    public List<CuentaBancaria> getCuentaBancariaList() {
        return cuentaBancariaList;
    }

    public void setCuentaBancariaList(List<CuentaBancaria> cuentaBancariaList) {
        this.cuentaBancariaList = cuentaBancariaList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(banId);
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
        return Objects.equals(this.banId, other.banId);
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillaws.model.Banco[ banId=" + banId + " ]";
    }
}