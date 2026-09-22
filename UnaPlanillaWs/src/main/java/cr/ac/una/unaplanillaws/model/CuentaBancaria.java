package cr.ac.una.unaplanillaws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "PLAM_CUENTASBANCARIAS", schema = "UNA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaBancaria.findAll", query = "SELECT c FROM CuentaBancaria c"),
    @NamedQuery(name = "CuentaBancaria.findByCbeId", query = "SELECT c FROM CuentaBancaria c WHERE c.cbeId = :cbeId")
})
public class CuentaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PLAM_CUENTASBANCARIAS_CBE_ID_GENERATOR", sequenceName = "una.PLAM_CUENTASBANCARIAS_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAM_CUENTASBANCARIAS_CBE_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "CBE_ID")
    private Long cbeId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CBE_AGENCIA")
    private Long cbeAgencia;

    @Column(name = "CBE_ADICIONAL")
    private Long cbeAdicional;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CBE_NUMEROCUENTA")
    private Long cbeNumerocuenta;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CBE_TIPO")
    private String cbeTipo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CBE_PRINCIPAL")
    private String cbePrincipal;

    @Version
    @Basic(optional = false)
    @NotNull
    @Column(name = "CBE_VERSION")
    private Long cbeVersion;

    @JoinColumn(name = "BAN_ID", referencedColumnName = "BAN_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Banco banId;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado empId;

    public CuentaBancaria() {
    }

    public CuentaBancaria(Long cbeId) {
        this.cbeId = cbeId;
    }

    public CuentaBancaria(CuentaBancariaDto cuentaBancariaDto) {
        this.cbeId = cuentaBancariaDto.getId();
        actualizar(cuentaBancariaDto);
    }

    public void actualizar(CuentaBancariaDto cuentaBancariaDto) {
        this.cbeAgencia = cuentaBancariaDto.getAgencia();
        this.cbeAdicional = cuentaBancariaDto.getAdicional();
        this.cbeNumerocuenta = cuentaBancariaDto.getNumeroCuenta();
        this.cbeTipo = cuentaBancariaDto.getTipo();
        this.cbePrincipal = (cuentaBancariaDto.getPrincipal() != null && cuentaBancariaDto.getPrincipal()) ? "S" : "N";
        this.cbeVersion = cuentaBancariaDto.getVersion();
    }

    public Long getCbeId() {
        return cbeId;
    }

    public void setCbeId(Long cbeId) {
        this.cbeId = cbeId;
    }

    public Long getCbeAgencia() {
        return cbeAgencia;
    }

    public void setCbeAgencia(Long cbeAgencia) {
        this.cbeAgencia = cbeAgencia;
    }

    public Long getCbeAdicional() {
        return cbeAdicional;
    }

    public void setCbeAdicional(Long cbeAdicional) {
        this.cbeAdicional = cbeAdicional;
    }

    public Long getCbeNumerocuenta() {
        return cbeNumerocuenta;
    }

    public void setCbeNumerocuenta(Long cbeNumerocuenta) {
        this.cbeNumerocuenta = cbeNumerocuenta;
    }

    public String getCbeTipo() {
        return cbeTipo;
    }

    public void setCbeTipo(String cbeTipo) {
        this.cbeTipo = cbeTipo;
    }

    public String getCbePrincipal() {
        return cbePrincipal;
    }

    public void setCbePrincipal(String cbePrincipal) {
        this.cbePrincipal = cbePrincipal;
    }

    public Long getCbeVersion() {
        return cbeVersion;
    }

    public void setCbeVersion(Long cbeVersion) {
        this.cbeVersion = cbeVersion;
    }

    public Banco getBanId() {
        return banId;
    }

    public void setBanId(Banco banId) {
        this.banId = banId;
    }

    public Empleado getEmpId() {
        return empId;
    }

    public void setEmpId(Empleado empId) {
        this.empId = empId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cbeId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CuentaBancaria other = (CuentaBancaria) obj;
        return Objects.equals(this.cbeId, other.cbeId);
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillaws.model.CuentaBancaria[ cbeId=" + cbeId + " ]";
    }
}