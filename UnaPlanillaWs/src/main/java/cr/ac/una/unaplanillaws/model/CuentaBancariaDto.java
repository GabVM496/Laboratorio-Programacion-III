package cr.ac.una.unaplanillaws.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;

public class CuentaBancariaDto {

    private Long id;

    @NotNull
    private Long agencia;

    private Long adicional;

    @NotNull
    private Long numeroCuenta;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 1)
    private String tipo;

    private Boolean principal;

    private Long version;

    private Boolean modificado;

    private BancoDto banco;

    public CuentaBancariaDto() {
        this.modificado = false;
    }

    public CuentaBancariaDto(CuentaBancaria cuentaBancaria) {
        this();
        this.id = cuentaBancaria.getCbeId() != null ? cuentaBancaria.getCbeId().longValue() : null;
        this.agencia = cuentaBancaria.getCbeAgencia() != null ? cuentaBancaria.getCbeAgencia().longValue() : null;
        this.adicional = cuentaBancaria.getCbeAdicional() != null ? cuentaBancaria.getCbeAdicional().longValue() : null;
        this.numeroCuenta = cuentaBancaria.getCbeNumerocuenta() != null ? cuentaBancaria.getCbeNumerocuenta().longValue() : null;
        this.tipo = cuentaBancaria.getCbeTipo();
        this.principal = cuentaBancaria.getCbePrincipal() != null && cuentaBancaria.getCbePrincipal().equals("S");
        this.version = cuentaBancaria.getCbeVersion() != null ? cuentaBancaria.getCbeVersion().longValue() : null;
        if (cuentaBancaria.getBanId() != null) {
            this.banco = new BancoDto(cuentaBancaria.getBanId());
        }
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

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public BancoDto getBanco() {
        return banco;
    }

    public void setBanco(BancoDto banco) {
        this.banco = banco;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CuentaBancariaDto other = (CuentaBancariaDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "CuentaBancariaDto{" + "id=" + id + ", numeroCuenta=" + numeroCuenta + ", tipo=" + tipo + '}';
    }
}