/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.unaplanillaws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author USUARIO UNA PZ
 */
@Entity
@Table(name = "PLAM_CUENTASBANCARIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaBancaria.findAll", query = "SELECT c FROM CuentaBancaria c"),
    @NamedQuery(name = "CuentaBancaria.findByCbeId", query = "SELECT c FROM CuentaBancaria c WHERE c.cbeId = :cbeId"),
    @NamedQuery(name = "CuentaBancaria.findByCbeAgencia", query = "SELECT c FROM CuentaBancaria c WHERE c.cbeAgencia = :cbeAgencia"),
    @NamedQuery(name = "CuentaBancaria.findByCbeAdicional", query = "SELECT c FROM CuentaBancaria c WHERE c.cbeAdicional = :cbeAdicional"),
    @NamedQuery(name = "CuentaBancaria.findByCbeNumerocuenta", query = "SELECT c FROM CuentaBancaria c WHERE c.cbeNumerocuenta = :cbeNumerocuenta"),
    @NamedQuery(name = "CuentaBancaria.findByCbeTipo", query = "SELECT c FROM CuentaBancaria c WHERE c.cbeTipo = :cbeTipo"),
    @NamedQuery(name = "CuentaBancaria.findByCbePrincipal", query = "SELECT c FROM CuentaBancaria c WHERE c.cbePrincipal = :cbePrincipal"),
    @NamedQuery(name = "CuentaBancaria.findByCbeVersion", query = "SELECT c FROM CuentaBancaria c WHERE c.cbeVersion = :cbeVersion")})
public class CuentaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CBE_ID")
    private BigDecimal cbeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CBE_AGENCIA")
    private BigInteger cbeAgencia;
    @Column(name = "CBE_ADICIONAL")
    private BigInteger cbeAdicional;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CBE_NUMEROCUENTA")
    private BigInteger cbeNumerocuenta;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "CBE_VERSION")
    private BigInteger cbeVersion;
    @JoinColumn(name = "BAN_ID", referencedColumnName = "BAN_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Banco banId;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado empId;

    public CuentaBancaria() {
    }

    public CuentaBancaria(BigDecimal cbeId) {
        this.cbeId = cbeId;
    }

    public CuentaBancaria(BigDecimal cbeId, BigInteger cbeAgencia, BigInteger cbeNumerocuenta, String cbeTipo, String cbePrincipal, BigInteger cbeVersion) {
        this.cbeId = cbeId;
        this.cbeAgencia = cbeAgencia;
        this.cbeNumerocuenta = cbeNumerocuenta;
        this.cbeTipo = cbeTipo;
        this.cbePrincipal = cbePrincipal;
        this.cbeVersion = cbeVersion;
    }

    public BigDecimal getCbeId() {
        return cbeId;
    }

    public void setCbeId(BigDecimal cbeId) {
        this.cbeId = cbeId;
    }

    public BigInteger getCbeAgencia() {
        return cbeAgencia;
    }

    public void setCbeAgencia(BigInteger cbeAgencia) {
        this.cbeAgencia = cbeAgencia;
    }

    public BigInteger getCbeAdicional() {
        return cbeAdicional;
    }

    public void setCbeAdicional(BigInteger cbeAdicional) {
        this.cbeAdicional = cbeAdicional;
    }

    public BigInteger getCbeNumerocuenta() {
        return cbeNumerocuenta;
    }

    public void setCbeNumerocuenta(BigInteger cbeNumerocuenta) {
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

    public BigInteger getCbeVersion() {
        return cbeVersion;
    }

    public void setCbeVersion(BigInteger cbeVersion) {
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
        int hash = 0;
        hash += (cbeId != null ? cbeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuentaBancaria)) {
            return false;
        }
        CuentaBancaria other = (CuentaBancaria) object;
        if ((this.cbeId == null && other.cbeId != null) || (this.cbeId != null && !this.cbeId.equals(other.cbeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillaws.model.CuentaBancaria[ cbeId=" + cbeId + " ]";
    }
    
}
