package cr.ac.una.unaplanillaws.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmpleadoDto {

    private Long id;
    @NotNull(message = "El nombre del empleado no debe ser nulo.")
    @NotEmpty(message = "El nombre del empleado no debe estar vacío.")
    @Size(min = 1, max = 30, message = "El nombre de la persona debe tener una longitud entre 1 y 30 caracteres.")
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String cedula;
    private String genero;
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "El correo no cumple con el formato válido.")
    private String correo;
    private Boolean administrador;
    private String usuario;
    private String clave;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private Boolean activo;
    private Long version;
    private Boolean modificado;
    private LocalDateTime fecha;
    
    private List<CuentaBancariaDto> cuentasBancariasList;
    private List<CuentaBancariaDto> cuentasBancariasEliminadas;

    public EmpleadoDto() {
        this.modificado = false;
        this.fecha = LocalDateTime.now();
        this.cuentasBancariasList = new ArrayList<>();
        this.cuentasBancariasEliminadas = new ArrayList<>();
    }

    public EmpleadoDto(Empleado empleado) {
        this();
        this.id = empleado.getId();
        this.nombre = empleado.getNombre();
        this.primerApellido = empleado.getPrimerApellido();
        this.segundoApellido = empleado.getSegundoApellido();
        this.cedula = empleado.getCedula();
        this.genero = empleado.getGenero();
        this.correo = empleado.getCorreo();
        this.administrador = empleado.getAdministrador().equals("S");
        this.usuario = empleado.getUsuario();
        this.clave = empleado.getClave();
        this.fechaIngreso = empleado.getFechaIngreso();
        if (empleado.getFechaSalida()!= null) {
            this.fechaSalida = empleado.getFechaSalida();
        } else {
            this.fechaSalida = null;
        }
        this.activo = empleado.getEstado().equals("A");
        this.version = empleado.getVersion();
        this.fecha = LocalDateTime.now();
        
        if (empleado.getCuentaBancariaList() != null) {
            for (CuentaBancaria cuentaBancaria : empleado.getCuentaBancariaList()) {
                this.cuentasBancariasList.add(new CuentaBancariaDto(cuentaBancaria));
            }
        }
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
    public String getPrimerApellido() { 
        return primerApellido; 
    }
    public void setPrimerApellido(String primerApellido) { 
        this.primerApellido = primerApellido; 
    }
    public String getSegundoApellido() { 
        return segundoApellido; 
    }
    public void setSegundoApellido(String segundoApellido) { 
        this.segundoApellido = segundoApellido; 
    }
    public String getCedula() { 
        return cedula; 
    }
    public void setCedula(String cedula) { 
        this.cedula = cedula; 
    }
    public String getGenero() { 
        return genero; 
    }
    public void setGenero(String genero) { 
        this.genero = genero; 
    }
    public String getCorreo() { 
        return correo; 
    }
    public void setCorreo(String correo) { 
        this.correo = correo; 
    }
    public Boolean getAdministrador() { 
        return administrador; 
    }
    public void setAdministrador(Boolean administrador) { 
        this.administrador = administrador; 
    }
    public String getUsuario() { 
        return usuario; 
    }
    public void setUsuario(String usuario) { 
        this.usuario = usuario; 
    }
    public String getClave() { 
        return clave; 
    }
    public void setClave(String clave) { 
        this.clave = clave; 
    }
    public LocalDate getFechaIngreso() { 
        return fechaIngreso; 
    }
    public void setFechaIngreso(LocalDate fechaIngreso) { 
        this.fechaIngreso = fechaIngreso; 
    }
    public LocalDate getFechaSalida() { 
        return fechaSalida; 
    }
    public void setFechaSalida(LocalDate fechaSalida) { 
        this.fechaSalida = fechaSalida; 
    }
    public Boolean getActivo() { 
        return activo; 
    }
    public void setActivo(Boolean activo) { 
        this.activo = activo; 
    }
    public Boolean getModificado() { 
        return modificado; 
    }
    public void setModificado(Boolean modificado) { 
        this.modificado = modificado; 
    }
    public LocalDateTime getFecha() { 
        return fecha; 
    }
    public void setFecha(LocalDateTime fecha) { 
        this.fecha = fecha; 
    }
    public Long getVersion() { 
        return version; 
    }
    public void setVersion(Long version) { 
        this.version = version; 
    }

    public List<CuentaBancariaDto> getCuentasBancariasList() {
        return cuentasBancariasList;
    }

    public void setCuentasBancariasList(List<CuentaBancariaDto> cuentasBancariasList) {
        this.cuentasBancariasList = cuentasBancariasList;
    }

    public List<CuentaBancariaDto> getCuentasBancariasEliminadas() {
        return cuentasBancariasEliminadas;
    }

    public void setCuentasBancariasEliminadas(List<CuentaBancariaDto> cuentasBancariasEliminadas) {
        this.cuentasBancariasEliminadas = cuentasBancariasEliminadas;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        final EmpleadoDto other = (EmpleadoDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "EmpleadoDto{" + "id=" + id + ", nombre=" + nombre + ", primerApellido=" + primerApellido + ", segundoApellido=" + segundoApellido + ", cedula=" + cedula + '}';
    }
}