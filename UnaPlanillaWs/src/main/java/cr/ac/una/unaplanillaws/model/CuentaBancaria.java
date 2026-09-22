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
    @NamedQuery(name = "CuentaBancaria.findById", query = "SELECT c FROM CuentaBancaria c WHERE c.id = :id")
})
public class CuentaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PLAM_CUENTASBANCARIAS_CBE_ID_GENERATOR", sequenceName = "una.PLAM_CUENTASBANCARIAS_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAM_CUENTASBANCARIAS_CBE_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "CBE_ID")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CBE_AGENCIA")
    private Long agencia;

    @Column(name = "CBE_ADICIONAL")
    private Long adicional;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CBE_NUMEROCUENTA")
    private Long numeroCuenta;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CBE_TIPO")
    private String tipo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CBE_PRINCIPAL")
    private String principal;

    @Version
    @Basic(optional = false)
    @NotNull
    @Column(name = "CBE_VERSION")
    private Long version;

    @JoinColumn(name = "BAN_ID", referencedColumnName = "BAN_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Banco banco;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado empleado;

    public CuentaBancaria() {
    }

    public CuentaBancaria(Long id) {
        this.id = id;
    }

    public CuentaBancaria(CuentaBancariaDto cuentaBancariaDto) {
        this.id = cuentaBancariaDto.getId();
        actualizar(cuentaBancariaDto);
    }

    public void actualizar(CuentaBancariaDto cuentaBancariaDto) {
        this.agencia = cuentaBancariaDto.getAgencia();
        this.adicional = cuentaBancariaDto.getAdicional();
        this.numeroCuenta = cuentaBancariaDto.getNumeroCuenta();
        this.tipo = cuentaBancariaDto.getTipo();
        this.principal = (cuentaBancariaDto.getPrincipal() != null && cuentaBancariaDto.getPrincipal()) ? "S" : "N";
        this.version = cuentaBancariaDto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgencia() {
        return agencia;
    }

    public void setAgencia(Long agencia) {
        this.agencia = agencia;
    }

    public Long getAdicional() {
        return adicional;
    }

    public void setAdicional(Long adicional) {
        this.adicional = adicional;
    }

    public Long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(Long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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
        CuentaBancaria other = (CuentaBancaria) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillaws.model.CuentaBancaria[ id=" + id + " ]";
    }
}