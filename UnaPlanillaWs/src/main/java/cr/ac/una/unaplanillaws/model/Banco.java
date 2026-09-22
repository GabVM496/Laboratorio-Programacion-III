/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.unaplanillaws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author USUARIO UNA PZ
 */
@Entity
@Table(name = "PLAM_BANCOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Banco.findAll", query = "SELECT b FROM Banco b"),
    @NamedQuery(name = "Banco.findByBanId", query = "SELECT b FROM Banco b WHERE b.banId = :banId"),
    @NamedQuery(name = "Banco.findByBanNombre", query = "SELECT b FROM Banco b WHERE b.banNombre = :banNombre"),
    @NamedQuery(name = "Banco.findByBanRebajocomision", query = "SELECT b FROM Banco b WHERE b.banRebajocomision = :banRebajocomision"),
    @NamedQuery(name = "Banco.findByBanComisiontran", query = "SELECT b FROM Banco b WHERE b.banComisiontran = :banComisiontran"),
    @NamedQuery(name = "Banco.findByBanEstado", query = "SELECT b FROM Banco b WHERE b.banEstado = :banEstado"),
    @NamedQuery(name = "Banco.findByBanVersion", query = "SELECT b FROM Banco b WHERE b.banVersion = :banVersion")})
public class Banco implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "BAN_ID")
    private BigDecimal banId;
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
    private BigInteger banComisiontran;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "BAN_ESTADO")
    private String banEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BAN_VERSION")
    private BigInteger banVersion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "banId", fetch = FetchType.LAZY)
    private List<CuentaBancaria> cuentaBancariaList;

    public Banco() {
    }

    public Banco(BigDecimal banId) {
        this.banId = banId;
    }

    public Banco(BigDecimal banId, String banNombre, String banRebajocomision, BigInteger banComisiontran, String banEstado, BigInteger banVersion) {
        this.banId = banId;
        this.banNombre = banNombre;
        this.banRebajocomision = banRebajocomision;
        this.banComisiontran = banComisiontran;
        this.banEstado = banEstado;
        this.banVersion = banVersion;
    }

    public BigDecimal getBanId() {
        return banId;
    }

    public void setBanId(BigDecimal banId) {
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

    public BigInteger getBanComisiontran() {
        return banComisiontran;
    }

    public void setBanComisiontran(BigInteger banComisiontran) {
        this.banComisiontran = banComisiontran;
    }

    public String getBanEstado() {
        return banEstado;
    }

    public void setBanEstado(String banEstado) {
        this.banEstado = banEstado;
    }

    public BigInteger getBanVersion() {
        return banVersion;
    }

    public void setBanVersion(BigInteger banVersion) {
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
        int hash = 0;
        hash += (banId != null ? banId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Banco)) {
            return false;
        }
        Banco other = (Banco) object;
        if ((this.banId == null && other.banId != null) || (this.banId != null && !this.banId.equals(other.banId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillaws.model.Banco[ banId=" + banId + " ]";
    }
    
}
