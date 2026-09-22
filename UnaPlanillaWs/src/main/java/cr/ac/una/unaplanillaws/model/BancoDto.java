package cr.ac.una.unaplanillaws.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;

public class BancoDto {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 80)
    private String nombre;

    private Boolean cobraComision;

    @NotNull
    private Long comision;

    private Boolean activo;

    private Long version;

    private Boolean modificado;

    public BancoDto() {
        this.modificado = false;
    }

    public BancoDto(Banco banco) {
        this();
        this.id = banco.getId();
        this.nombre = banco.getNombre();
        this.cobraComision = banco.getRebajoComision() != null && banco.getRebajoComision().equals("E");
        this.comision = banco.getComisionTransferencia();
        this.activo = banco.getEstado() != null && banco.getEstado().equals("A");
        this.version = banco.getVersion();
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

    public Boolean getCobraComision() {
        return cobraComision;
    }

    public void setCobraComision(Boolean cobraComision) {
        this.cobraComision = cobraComision;
    }

    public Long getComision() {
        return comision;
    }

    public void setComision(Long comision) {
        this.comision = comision;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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
        int hash = 5;
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
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "BancoDto{" + "id=" + id + ", nombre=" + nombre + '}';
    }
}