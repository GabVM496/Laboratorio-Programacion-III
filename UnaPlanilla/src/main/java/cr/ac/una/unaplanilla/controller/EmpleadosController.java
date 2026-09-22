package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.BancoDto;
import cr.ac.una.unaplanilla.model.CuentaBancariaDto;
import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.service.BancoService;
import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.util.BindingUtils;
import cr.ac.una.unaplanilla.util.FlowController;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

public class EmpleadosController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TabPane tbpControlEmpleadosPane;
    @FXML
    private MFXTextField txtId;
    @FXML
    private MFXTextField txtNombre;
    @FXML
    private MFXTextField txtPApellido;
    @FXML
    private MFXTextField txtSApellido;
    @FXML
    private MFXTextField txtCedula;
    @FXML
    private MFXRadioButton rdbMasculino;
    @FXML
    private ToggleGroup tggGenero;
    @FXML
    private MFXRadioButton rdbFemenino;
    @FXML
    private MFXCheckbox chkAdministrador;
    @FXML
    private MFXCheckbox chkActivo;
    @FXML
    private MFXDatePicker dtpFIngreso;
    @FXML
    private MFXDatePicker dtpFSalida;
    @FXML
    private MFXTextField txtCorreo;
    @FXML
    private MFXTextField txtUsuario;
    @FXML
    private MFXPasswordField txtClave;
    @FXML
    private MFXButton btnNuevo;
    @FXML
    private MFXButton btnBuscar;
    @FXML
    private MFXButton btnEliminar;
    @FXML
    private MFXButton btnGuardar;
    @FXML
    private Tab tbpControlEmpleados;
    @FXML
    private Tab tbpCuentaBancaria;
    @FXML
    private MFXTextField txtNumeroAgencia;
    @FXML
    private MFXTextField txtNumeroCuenta;
    @FXML
    private MFXComboBox<BancoDto> cmbBanco;
    @FXML
    private CheckBox chkCuentaPrincipal;
    @FXML
    private MFXRadioButton rdbCuentaCorriente;
    @FXML
    private MFXRadioButton rdbCuentaAhorros;
    @FXML
    private ToggleGroup tggTipoCuenta;
    @FXML
    private TableView<CuentaBancariaDto> tbvCuentas;
    @FXML
    private TableColumn<CuentaBancariaDto, Long> tbcNumeroCuenta;
    @FXML
    private TableColumn<CuentaBancariaDto, String> tbcNombreBanco;
    @FXML
    private TableColumn<CuentaBancariaDto, Boolean> tbcEliminar;
    @FXML
    private MFXButton btnAgregarCuenta;

    private EmpleadoDto empleadoDto;
    private ObjectProperty<EmpleadoDto> empleadoProperty = new SimpleObjectProperty<>();
    private CuentaBancariaDto cuentaBancariaDto;
    private ObjectProperty<CuentaBancariaDto> cuentaBancariaProperty = new SimpleObjectProperty<>();
    private List<Node> requeridos = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rdbMasculino.setUserData("M");
        rdbFemenino.setUserData("F");

        if (rdbCuentaCorriente != null && rdbCuentaAhorros != null) {
            rdbCuentaCorriente.setUserData("C");
            rdbCuentaAhorros.setUserData("A");
        }

        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtNombre.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
        txtPApellido.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtSApellido.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtCedula.delegateSetTextFormatter(Formato.getInstance().cedulaFormat(40));
        txtCorreo.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(80));
        txtUsuario.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtClave.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(8));

        if (txtNumeroAgencia != null) txtNumeroAgencia.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        if (txtNumeroCuenta != null) txtNumeroCuenta.delegateSetTextFormatter(Formato.getInstance().integerFormat());

        cargarBancos(); 

        this.empleadoDto = new EmpleadoDto();
        bindEmpleado();

        this.cuentaBancariaDto = new CuentaBancariaDto();
        bindCuentaBancaria();

        cargarValoresDefecto();
        indicarRequeridos();

        cmbBanco.setConverter(new StringConverter<BancoDto>() {
            @Override
            public String toString(BancoDto banco) {
                return banco != null ? banco.getNombre() : "";
            }
            @Override
            public BancoDto fromString(String string) {
                return null; 
            }
        });

        tbcNumeroCuenta.setCellValueFactory((cd) -> cd.getValue().getNumeroCuentaProperty());
        
        tbcNombreBanco.setCellValueFactory((cd) -> {
            Long bancoId = cd.getValue().getBancoId();
            if (bancoId != null && cmbBanco.getItems() != null) {
                for (BancoDto b : cmbBanco.getItems()) {
                    if (b.getId().equals(bancoId)) {
                        return new SimpleStringProperty(b.getNombre());
                    }
                }
            }
            return new SimpleStringProperty("");
        });
        
        tbcEliminar.setCellValueFactory((cd) -> new SimpleBooleanProperty(cd.getValue() != null));
        tbcEliminar.setCellFactory((cd) -> new ButtonCell());

        tbvCuentas.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                this.cuentaBancariaDto = newValue;
                this.cuentaBancariaProperty.setValue(this.cuentaBancariaDto);
                if (newValue.getBancoId() != null && cmbBanco != null) {
                    for (int i = 0; i < cmbBanco.getItems().size(); i++) {
                        if (cmbBanco.getItems().get(i).getId().equals(newValue.getBancoId())) {
                            cmbBanco.getSelectionModel().selectIndex(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void initialize() {
    }

    private void cargarBancos() {
        try {
            BancoService bancoService = new BancoService();
            Respuesta respuesta = bancoService.getBancos();
            if (respuesta.getEstado()) {
                List<BancoDto> bancos = (List<BancoDto>) respuesta.getResultado("Bancos");
                cmbBanco.setItems(FXCollections.observableArrayList(bancos));
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Bancos", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error cargando bancos.", ex);
        }
    }

    private void cargarValoresDefecto() {
        this.empleadoDto = new EmpleadoDto();
        this.empleadoDto.setActivo(Boolean.TRUE);
        this.empleadoDto.setAdministrador(Boolean.FALSE);
        this.empleadoDto.setFechaIngreso(LocalDate.now());
        this.empleadoDto.setGenero("M");
        this.empleadoProperty.setValue(this.empleadoDto);
        validarAdministrador();
        txtId.clear();
        txtId.requestFocus();
        limpiarCuentaBancaria();
        cargarCuentasBancarias();
    }

    private void limpiarCuentaBancaria() {
        tbvCuentas.getSelectionModel().clearSelection();
        this.cuentaBancariaDto = new CuentaBancariaDto();
        this.cuentaBancariaProperty.setValue(this.cuentaBancariaDto);
        if (cmbBanco != null) {
            cmbBanco.getSelectionModel().clearSelection();
        }
    }

    private void cargarCuentasBancarias() {
        tbvCuentas.getItems().clear();
        tbvCuentas.setItems(this.empleadoDto.getCuentasBancariasList());
        tbvCuentas.refresh();
    }

    private void bindEmpleado() {
        try {
            empleadoProperty.addListener((obs, oldVal, newVal) -> {
                if (oldVal != null) {
                    txtId.textProperty().unbind();
                    txtNombre.textProperty().unbindBidirectional(oldVal.getNombreProperty());
                    txtPApellido.textProperty().unbindBidirectional(oldVal.getPrimerApellidoProperty());
                    txtSApellido.textProperty().unbindBidirectional(oldVal.getSegundoApellidoProperty());
                    txtCedula.textProperty().unbindBidirectional(oldVal.getCedulaProperty());
                    chkAdministrador.selectedProperty().unbindBidirectional(oldVal.getAdministradorProperty());
                    txtCorreo.textProperty().unbindBidirectional(oldVal.getCorreoProperty());
                    txtUsuario.textProperty().unbindBidirectional(oldVal.getUsuarioProperty());
                    txtClave.textProperty().unbindBidirectional(oldVal.getClaveProperty());
                    dtpFIngreso.valueProperty().unbindBidirectional(oldVal.getFechaIngresoProperty());
                    dtpFSalida.valueProperty().unbindBidirectional(oldVal.getFechaSalidaProperty());
                    chkActivo.selectedProperty().unbindBidirectional(oldVal.getActivoProperty());
                    BindingUtils.unbindToggleGroupToProperty(tggGenero, oldVal.getGeneroProperty());
                }
                if (newVal != null) {
                    if (newVal.getIdProperty().get() != null && !newVal.getIdProperty().get().isBlank()) {
                        txtId.textProperty().bind(newVal.getIdProperty());
                    }
                    txtNombre.textProperty().bindBidirectional(newVal.getNombreProperty());
                    txtPApellido.textProperty().bindBidirectional(newVal.getPrimerApellidoProperty());
                    txtSApellido.textProperty().bindBidirectional(newVal.getSegundoApellidoProperty());
                    txtCedula.textProperty().bindBidirectional(newVal.getCedulaProperty());
                    chkAdministrador.selectedProperty().bindBidirectional(newVal.getAdministradorProperty());
                    txtCorreo.textProperty().bindBidirectional(newVal.getCorreoProperty());
                    txtUsuario.textProperty().bindBidirectional(newVal.getUsuarioProperty());
                    txtClave.textProperty().bindBidirectional(newVal.getClaveProperty());
                    dtpFIngreso.valueProperty().bindBidirectional(newVal.getFechaIngresoProperty());
                    dtpFSalida.valueProperty().bindBidirectional(newVal.getFechaSalidaProperty());
                    chkActivo.selectedProperty().bindBidirectional(newVal.getActivoProperty());
                    BindingUtils.bindToggleGroupToProperty(tggGenero, newVal.getGeneroProperty());
                }
            });
        } catch (Exception ex) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al realizar el bindeo", getStage(), "Ocurrió un error al realizar el bindeo.");
        }
    }

    private void bindCuentaBancaria() {
        try {
            StringConverter<Long> longConverter = new StringConverter<Long>() {
                @Override
                public String toString(Long object) {
                    return object == null ? "" : object.toString();
                }
                @Override
                public Long fromString(String string) {
                    if (string == null || string.isBlank()) return null;
                    try { return Long.valueOf(string); } catch (NumberFormatException e) { return null; }
                }
            };

            StringConverter<Integer> intConverter = new StringConverter<Integer>() {
                @Override
                public String toString(Integer object) {
                    return object == null ? "" : object.toString();
                }
                @Override
                public Integer fromString(String string) {
                    if (string == null || string.isBlank()) return null;
                    try { return Integer.valueOf(string); } catch (NumberFormatException e) { return null; }
                }
            };

            cuentaBancariaProperty.addListener((obs, oldVal, newVal) -> {
                if (oldVal != null) {
                    txtNumeroCuenta.textProperty().unbindBidirectional(oldVal.getNumeroCuentaProperty());
                    txtNumeroAgencia.textProperty().unbindBidirectional(oldVal.getAgenciaProperty());
                    chkCuentaPrincipal.selectedProperty().unbindBidirectional(oldVal.getPrincipalProperty());
                    if (tggTipoCuenta != null) {
                        BindingUtils.unbindToggleGroupToProperty(tggTipoCuenta, oldVal.getTipoProperty());
                    }
                }
                if (newVal != null) {
                    txtNumeroCuenta.textProperty().bindBidirectional(newVal.getNumeroCuentaProperty(), longConverter);
                    txtNumeroAgencia.textProperty().bindBidirectional(newVal.getAgenciaProperty(), intConverter);
                    chkCuentaPrincipal.selectedProperty().bindBidirectional(newVal.getPrincipalProperty());
                    if (tggTipoCuenta != null) {
                        BindingUtils.bindToggleGroupToProperty(tggTipoCuenta, newVal.getTipoProperty());
                    }
                }
            });
        } catch (Exception ex) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al realizar el bindeo", getStage(), "Ocurrió un error al realizar el bindeo en cuentas.");
        }
    }

    private void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombre, txtCedula, txtPApellido, dtpFIngreso));
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof MFXTextField && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank())) {
                invalidos += validos ? ((MFXTextField) node).getFloatingText() : "," + ((MFXTextField) node).getFloatingText();
                validos = false;
            } else if (node instanceof MFXPasswordField && (((MFXPasswordField) node).getText() == null || ((MFXPasswordField) node).getText().isBlank())) {
                invalidos += validos ? ((MFXPasswordField) node).getFloatingText() : "," + ((MFXPasswordField) node).getFloatingText();
                validos = false;
            } else if (node instanceof MFXDatePicker && ((MFXDatePicker) node).getValue() == null) {
                invalidos += validos ? ((MFXDatePicker) node).getFloatingText() : "," + ((MFXDatePicker) node).getFloatingText();
                validos = false;
            } else if (node instanceof MFXComboBox && ((MFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                invalidos += validos ? ((MFXComboBox) node).getFloatingText() : "," + ((MFXComboBox) node).getFloatingText();
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }

    private void validarAdministrador() {
        if (chkAdministrador.isSelected()) {
            requeridos.addAll(Arrays.asList(txtUsuario, txtClave));
            txtUsuario.setDisable(false);
            txtClave.setDisable(false);
        } else {
            requeridos.removeAll(Arrays.asList(txtUsuario, txtClave));
            txtUsuario.clear();
            txtUsuario.setDisable(true);
            txtClave.clear();
            txtClave.setDisable(true);
        }
    }

    @FXML
    private void onKeyPressedTxtId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtId.getText().isBlank()) {
            cargarEmpleado(Long.valueOf(txtId.getText()));
        }
    }

    private void cargarEmpleado(Long id) {
        try {
            EmpleadoService empleadoService = new EmpleadoService();
            Respuesta respuesta = empleadoService.getEmpleado(id);
            if (respuesta.getEstado()) {
                this.empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
                this.empleadoProperty.setValue(this.empleadoDto);
                validarAdministrador();
                validarRequeridos();
                cargarCuentasBancarias();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Empleado", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error buscando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Empleado", getStage(), "Ocurrió un error buscando el empleado.");
        }
    }

    @FXML
    private void onActionChkAdministrador(ActionEvent event) {
        validarAdministrador();
    }

    @FXML
    private void onActionBtnAgregarCuenta(ActionEvent event) {
        if (cmbBanco.getSelectionModel().getSelectedItem() == null) {
            new Mensaje().showModal(Alert.AlertType.WARNING, "Agregar Cuenta", getStage(), "Debe seleccionar un banco.");
            return;
        }

        if (txtNumeroCuenta.getText() == null || txtNumeroCuenta.getText().isBlank()) {
            new Mensaje().showModal(Alert.AlertType.WARNING, "Agregar Cuenta", getStage(), "Debe ingresar el número de cuenta.");
            return;
        }

        this.cuentaBancariaDto.setBancoId(cmbBanco.getSelectionModel().getSelectedItem().getId());

        boolean exists = tbvCuentas.getItems().contains(this.cuentaBancariaDto);

        if (exists) {
            this.cuentaBancariaDto.setModificado(true);
            tbvCuentas.refresh();
            limpiarCuentaBancaria();
        } else {
            if (tbvCuentas.getItems().stream().anyMatch(c -> c.getNumeroCuenta().equals(this.cuentaBancariaDto.getNumeroCuenta()))) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Agregar Cuenta", getStage(), "El número de cuenta ya existe en la lista.");
                return;
            }
            this.cuentaBancariaDto.setModificado(true);
            tbvCuentas.getItems().add(this.cuentaBancariaDto); 
            tbvCuentas.refresh();
            limpiarCuentaBancaria();
        }
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (tbpCuentaBancaria.isSelected()) {
            limpiarCuentaBancaria();
        } else if (new Mensaje().showConfirmation("Limpiar Empleado", getStage(), "¿Esta seguro que desea limpiar el registro?")) {
            cargarValoresDefecto();
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        BusquedaController busquedaController = (BusquedaController) FlowController.getInstance().getController("BusquedaView");
        busquedaController.busquedaEmpleados();
        FlowController.getInstance().goViewInWindowModal("BusquedaView", getStage(), true);
        EmpleadoDto emp = (EmpleadoDto) busquedaController.getResultado();
        if (emp != null) {
            cargarEmpleado(emp.getId());
        }
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try {
            if (this.empleadoDto.getId() == null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Empleado", getStage(), "Favor consultar el empleado a eliminar.");
            } else {
                EmpleadoService empleadoService = new EmpleadoService();
                Respuesta respuesta = empleadoService.eliminarEmpleado(this.empleadoDto.getId());
                if (respuesta.getEstado()) {
                    cargarValoresDefecto();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar Empleado", getStage(), "El empleado se eliminó correctamente.");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Empleado", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error eliminando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Empleado", getStage(), "Ocurrió un error eliminando el empleado.");
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), invalidos);
            } else {
                EmpleadoService empleadoService = new EmpleadoService();
                Respuesta respuesta = empleadoService.guardarEmpleado(empleadoDto);
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), respuesta.getMensaje());
                } else {
                    this.empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
                    this.empleadoProperty.set(this.empleadoDto);
                    validarAdministrador();
                    validarRequeridos();
                    cargarCuentasBancarias();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Empleado", getStage(), "El empleado se guardó correctamente.");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error guardando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Empleado", getStage(), "Ocurrio un error guardando el empleado.");
        }
    }

    @FXML
    private void selectionChangeTabCuenta(Event event) {
        if (tbpCuentaBancaria.isSelected()) {
            if (this.empleadoDto.getId() == null) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Cuentas Bancarias", getStage(),
                        "Debe cargar un empleado antes de gestionar cuentas bancarias.");
                tbpControlEmpleadosPane.getSelectionModel().select(tbpControlEmpleados);
            }
        }
    }

    @FXML
    private void onKeyPressedTxtIdEmpleado(KeyEvent event) {
    }

    private class ButtonCell extends TableCell<CuentaBancariaDto, Boolean> {
        final Button cellButton = new Button();

        ButtonCell() {
            cellButton.setPrefWidth(500);
            cellButton.getStyleClass().add("jfx-btnimg-tbveliminar");

            cellButton.setOnAction((ActionEvent t) -> {
                CuentaBancariaDto cuenta = (CuentaBancariaDto) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                empleadoDto.getCuentasBancariasEliminadas().add(cuenta);
                tbvCuentas.getItems().remove(cuenta);
                tbvCuentas.refresh();
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }
}