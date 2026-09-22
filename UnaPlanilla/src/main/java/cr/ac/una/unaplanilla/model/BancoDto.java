package cr.ac.una.unaplanilla.model;

import jakarta.json.bind.annotation.JsonbTransient;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BancoDto {

    private StringProperty id;
    private StringProperty nombre;
    private ObjectProperty<String> rebajoComision;
    private DoubleProperty comisionTransferencia;
    private BooleanProperty activo;
    private Long version;
    private Boolean modificado;

    public BancoDto() {
        this.id = new SimpleStringProperty("");
        this.nombre = new SimpleStringProperty("");
        this.rebajoComision = new SimpleObjectProperty("E");
        this.comisionTransferencia = new SimpleDoubleProperty(0.0);
        this.activo = new SimpleBooleanProperty(true);
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

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getRebajoComision() {
        return rebajoComision.get();
    }

    public void setRebajoComision(String rebajoComision) {
        this.rebajoComision.set(rebajoComision);
    }

    public Double getComisionTransferencia() {
        return comisionTransferencia.get();
    }

    public void setComisionTransferencia(Double comisionTransferencia) {
        this.comisionTransferencia.set(comisionTransferencia);
    }

    public Boolean getActivo() {
        return activo.get();
    }

    public void setActivo(Boolean activo) {
        this.activo.set(activo);
    }

    @JsonbTransient
    public StringProperty getIdProperty() {
        return id;
    }

    @JsonbTransient
    public StringProperty getNombreProperty() {
        return nombre;
    }

    @JsonbTransient
    public ObjectProperty<String> getRebajoComisionProperty() {
        return rebajoComision;
    }

    @JsonbTransient
    public DoubleProperty getComisionTransferenciaProperty() {
        return comisionTransferencia;
    }

    @JsonbTransient
    public BooleanProperty getActivoProperty() {
        return activo;
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
        final BancoDto other = (BancoDto) obj;
        return Objects.equals(this.id.get(), other.id.get());
    }

    @Override
    public String toString() {
        return "BancoDto{" + "id=" + id + ", nombre=" + nombre + '}';
    }

}