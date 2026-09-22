package cr.ac.una.unaplanilla.model;

import jakarta.json.bind.annotation.JsonbTransient;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CuentaBancariaDto {

    private StringProperty id;
    private StringProperty bancoId;
    private StringProperty empleadoId;
    private ObjectProperty<Integer> agencia;
    private ObjectProperty<Integer> adicional;
    private ObjectProperty<Long> numeroCuenta;
    private ObjectProperty<String> tipo;
    private BooleanProperty principal;
    private Long version;
    private Boolean modificado;
    //TODO

    public CuentaBancariaDto() {
        this.id = new SimpleStringProperty("");
        this.bancoId = new SimpleStringProperty("");
        this.empleadoId = new SimpleStringProperty("");
        this.agencia = new SimpleObjectProperty<>();
        this.adicional = new SimpleObjectProperty<>();
        this.numeroCuenta = new SimpleObjectProperty<>();
        this.tipo = new SimpleObjectProperty("C");
        this.principal = new SimpleBooleanProperty(false);
        this.modificado = false;
    }

    public Long getId() {
        if (this.id.get() != null && !this.id.get().isBlank()) {
            return Long.valueOf(this.id.get());
        } else {
            return null;
        }
    }

    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public Long getBancoId() {
        if (this.bancoId.get() != null && !this.bancoId.get().isBlank()) {
            return Long.valueOf(this.bancoId.get());
        } else {
            return null;
        }
    }

    public void setBancoId(Long bancoId) {
        this.bancoId.set(bancoId.toString());
    }

    public Long getEmpleadoId() {
        if (this.empleadoId.get() != null && !this.empleadoId.get().isBlank()) {
            return Long.valueOf(this.empleadoId.get());
        } else {
            return null;
        }
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId.set(empleadoId.toString());
    }

    public Integer getAgencia() {
        return agencia.get();
    }

    public void setAgencia(Integer agencia) {
        this.agencia.set(agencia);
    }

    public Integer getAdicional() {
        return adicional.get();
    }

    public void setAdicional(Integer adicional) {
        this.adicional.set(adicional);
    }

    public Long getNumeroCuenta() {
        return numeroCuenta.get();
    }

    public void setNumeroCuenta(Long numeroCuenta) {
        this.numeroCuenta.set(numeroCuenta);
    }

    public String getTipo() {
        return tipo.get();
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    public Boolean getPrincipal() {
        return principal.get();
    }

    public void setPrincipal(Boolean principal) {
        this.principal.set(principal);
    }

    @JsonbTransient
    public StringProperty getIdProperty() {
        return id;
    }

    @JsonbTransient
    public StringProperty getBancoIdProperty() {
        return bancoId;
    }

    @JsonbTransient
    public StringProperty getEmpleadoIdProperty() {
        return empleadoId;
    }

    @JsonbTransient
    public ObjectProperty<Integer> getAgenciaProperty() {
        return agencia;
    }

    @JsonbTransient
    public ObjectProperty<Integer> getAdicionalProperty() {
        return adicional;
    }

    @JsonbTransient
    public ObjectProperty<Long> getNumeroCuentaProperty() {
        return numeroCuenta;
    }

    @JsonbTransient
    public ObjectProperty<String> getTipoProperty() {
        return tipo;
    }

    @JsonbTransient
    public BooleanProperty getPrincipalProperty() {
        return principal;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        return Objects.equals(this.id.get(), other.id.get());
    }

    @Override
    public String toString() {
        return "CuentaBancariaDto{" + "id=" + id + ", bancoId=" + bancoId + ", empleadoId=" + empleadoId + '}';
    }

}