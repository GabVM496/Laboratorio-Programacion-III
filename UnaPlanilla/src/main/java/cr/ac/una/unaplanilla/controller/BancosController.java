package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.BancoDto;
import cr.ac.una.unaplanilla.service.BancoService;
import cr.ac.una.unaplanilla.util.FlowController;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

public class BancosController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXTextField txtId;
    @FXML
    private MFXTextField txtNombreBanco;
    @FXML
    private MFXTextField txtComision;
    @FXML
    private MFXComboBox<String> cmbRebajoComision;
    @FXML
    private MFXCheckbox chkActivo;
    @FXML
    private MFXButton btnNuevo;
    @FXML
    private MFXButton btnBuscar;
    @FXML
    private MFXButton btnEliminar;
    @FXML
    private MFXButton btnGuardar;

    private BancoDto bancoDto;
    private ObjectProperty<BancoDto> bancoProperty = new SimpleObjectProperty<>();
    private List<Node> requeridos = new ArrayList<>();

    @Override
    public void initialize() {
        Platform.runLater(() -> { cargarValoresDefecto(); });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtNombreBanco.delegateSetTextFormatter(Formato.getInstance().letrasFormat(80));
        txtComision.delegateSetTextFormatter(Formato.getInstance().twoDecimalFormat());
        bancoDto = new BancoDto();
        bindBanco();
        indicarRequeridos();
    }

    private void cargarValoresDefecto() {
        bancoDto = new BancoDto();
        bancoDto.setActivo(Boolean.TRUE);
        bancoDto.setRebajoComision("E");
        bancoProperty.setValue(bancoDto);
        txtNombreBanco.clear();
        txtComision.clear();
        chkActivo.setSelected(true);
        cmbRebajoComision.getSelectionModel().selectFirst();
        txtId.clear();
        txtId.requestFocus();
    }

    private void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombreBanco, txtComision));
    }

    private void bindBanco() {
        try {
            bancoProperty.addListener((obs, oldVal, newVal) -> {
                if (oldVal != null) {
                    txtId.textProperty().unbind();
                    txtNombreBanco.textProperty().unbindBidirectional(oldVal.getNombreProperty());
                    txtComision.textProperty().unbindBidirectional(oldVal.getComisionProperty());
                    chkActivo.selectedProperty().unbindBidirectional(oldVal.getActivoProperty());
                }
                if (newVal != null) {
                    if (newVal.getIdProperty().get() != null && !newVal.getIdProperty().get().isBlank()) {
                        txtId.textProperty().bind(newVal.getIdProperty());
                    }
                    txtNombreBanco.textProperty().bindBidirectional(newVal.getNombreProperty());

                    txtComision.textProperty().bindBidirectional(newVal.getComisionProperty(), new StringConverter<Number>() {
                        @Override
                        public String toString(Number object) {
                            return (object == null || object.doubleValue() == 0.0) ? "" : String.valueOf(object);
                        }

                        @Override
                        public Number fromString(String string) {
                            if (string == null || string.isBlank()) {
                                return 0.0;
                            }
                            try {
                                return Double.valueOf(string);
                            } catch (NumberFormatException e) {
                                return 0.0;
                            }
                        }
                    });

                    chkActivo.selectedProperty().bindBidirectional(newVal.getActivoProperty());

                    cmbRebajoComision.setValue("E".equals(newVal.getRebajoComision()) ? "Empleado" : "Empresa");
                }
            });
        } catch (Exception ex) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al realizar el bindeo", getStage(),
                    "Ocurrió un error al realizar el bindeo.");
        }
    }

    @FXML
    private void onKeyPressedTxtId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtId.getText().isBlank()) {
            cargarBanco(Long.valueOf(txtId.getText()));
        }
    }

    @FXML
    private void onKeyPressedTxtNombreBanco(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtNombreBanco.getText().isBlank()) {
        }
    }

    private void cargarBanco(Long id) {
        try {
            BancoService bancoService = new BancoService();
            Respuesta respuesta = bancoService.getBanco(id);
            if (respuesta.getEstado()) {
                this.bancoDto = (BancoDto) respuesta.getResultado("Banco");
                this.bancoProperty.setValue(this.bancoDto);
                cmbRebajoComision.setValue("E".equals(this.bancoDto.getRebajoComision()) ? "Empleado" : "Empresa");
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Banco", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(BancosController.class.getName()).log(Level.SEVERE, "Error buscando el banco.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Banco", getStage(), "Ocurrió un error buscando el banco.");
        }
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";

        for (Node node : requeridos) {
            if (node instanceof MFXTextField && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXTextField) node).getFloatingText();
                } else {
                    invalidos += ", " + ((MFXTextField) node).getFloatingText();
                }
                validos = false;
            }
        }

        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }

    @FXML
    private void onActionCbxRebajoComision(ActionEvent event) {
        if (bancoDto != null) {
            bancoDto.setRebajoComision("Empleado".equals(cmbRebajoComision.getValue()) ? "E" : "M");
        }
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar Banco", getStage(), "¿Esta seguro que desea limpiar el registro?")) {
            cargarValoresDefecto();
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        BusquedaController busquedaController = (BusquedaController) FlowController.getInstance().getController("BusquedaView");
        busquedaController.busquedaBancos();
        FlowController.getInstance().goViewInWindowModal("BusquedaView", getStage(), true);
        BancoDto ban = (BancoDto) busquedaController.getResultado();
        if (ban != null) {
            cargarBanco(ban.getId());
        }
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try {
            if (this.bancoDto.getId() == null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Banco", getStage(), "Favor consultar el banco a eliminar.");
            } else {
                BancoService bancoService = new BancoService();
                Respuesta respuesta = bancoService.eliminarBanco(this.bancoDto.getId());
                if (respuesta.getEstado()) {
                    cargarValoresDefecto();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar Banco", getStage(), "El banco se eliminó correctamente.");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Banco", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BancosController.class.getName()).log(Level.SEVERE, "Error eliminando el banco.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Banco", getStage(), "Ocurrió un error eliminando el banco.");
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            bancoDto.setNombre(txtNombreBanco.getText());

            String invalidos = validarRequeridos();
            if (!invalidos.isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar banco", getStage(), invalidos);
            } else {
                BancoService bancoService = new BancoService();
                Respuesta respuesta = bancoService.guardarBanco(bancoDto);
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar banco", getStage(), respuesta.getMensaje());
                } else {
                    this.bancoDto = (BancoDto) respuesta.getResultado("Banco");
                    this.bancoProperty.set(this.bancoDto);
                    cmbRebajoComision.setValue("E".equals(this.bancoDto.getRebajoComision()) ? "Empleado" : "Empresa");
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Banco", getStage(), "El banco se guardó correctamente.");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BancosController.class.getName()).log(Level.SEVERE, "Error guardando el banco.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Banco", getStage(), "Ocurrio un error guardando el banco.");
        }
    }
}