package gui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Constraints;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import model.entities.Person;

public class ViewController implements Initializable {

	@FXML
	private TextField txtName;
	@FXML
	private TextField txtCpf;
	@FXML
	private TextField txtTel;
	@FXML
	private TextField txtAdress;
	@FXML
	private TextField txtNumber;
	@FXML
	private TextField txtCity;
	@FXML
	private TextField txtState;

	@FXML
	private Button clean;
	@FXML
	private Button save;

	@FXML
	private TableView<Person> tableView;
	@FXML
	private TableColumn<Person, String> colName;
	@FXML
	private TableColumn<Person, String> colCpf;
	@FXML
	private TableColumn<Person, String> colTel;
	@FXML
	private TableColumn<Person, String> colAdress;
	@FXML
	private TableColumn<Person, Void> colAction;

	private ObservableList<Person> obsList;

	public void onBtCleanAction() {
		txtName.setText(null);
		txtCpf.setText(null);
		txtTel.setText(null);
		txtAdress.setText(null);
		txtNumber.setText(null);
		txtCity.setText(null);
		txtState.setText(null);
	}

	public void onBtSaveAction() {
		Person p = new Person();
		p.setName(String.valueOf(txtName.getText()));
		p.setCpf(Long.valueOf(txtCpf.getText()));
		p.setTel(Long.valueOf(txtTel.getText()));
		p.setAdress(String.valueOf(txtAdress.getText()));
		p.setNumber(Long.valueOf(txtNumber.getText()));
		p.setCity(String.valueOf(txtCity.getText()));
		p.setState(String.valueOf(txtState.getText()));
		onBtCleanAction();
		obsList.add(p);
		tableView.refresh();
	}

	private void addButtonToTable() {
	    Callback<TableColumn<Person, Void>, TableCell<Person, Void>> cellFactory = new Callback<TableColumn<Person, Void>, TableCell<Person, Void>>() {
	        @Override
	        public TableCell<Person, Void> call(final TableColumn<Person, Void> param) {
	            final TableCell<Person, Void> cell = new TableCell<Person, Void>() {

	                private final Button btnEdit = new Button("Editar");
	                private final Button btnDelete = new Button("Excluir");

	                {
	                    btnEdit.setOnAction(event -> {
	                        Person person = getTableView().getItems().get(getIndex());
	                        onEditPerson(person); 
	                    });

	                    btnDelete.setOnAction(event -> {
	                    	Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza que deseja excluir?");
	                    	Person person = getTableView().getItems().get(getIndex());
	                    	if (result.get() == ButtonType.OK) {
	                    		obsList.remove(person); 	                    		
	                    	}
	                        
	                    });
	                }

	                @Override
	                public void updateItem(Void item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (empty) {
	                        setGraphic(null);
	                    } else {
	                        HBox hBox = new HBox(10); 
	                        hBox.getChildren().addAll(btnEdit, btnDelete);
	                        setGraphic(hBox); 
	                    }
	                }
	            };
	            return cell;
	        }
	    };

	    colAction.setCellFactory(cellFactory); 
	}

	private void onEditPerson(Person person) {
		txtName.setText(person.getName());
		txtCpf.setText(String.valueOf(person.getCpf()));
		txtTel.setText(String.valueOf(person.getTel()));
		txtAdress.setText(person.getAdress());
		txtNumber.setText(String.valueOf(person.getNumber()));
		txtCity.setText(person.getCity());
		txtState.setText(person.getState());
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtCpf);
		Constraints.setTextFieldMaxLength(txtName, 70);
		Constraints.setTextFieldInteger(txtTel);
		Constraints.setTextFieldMaxLength(txtAdress, 120);
		Constraints.setTextFieldInteger(txtNumber);
		Constraints.setTextFieldMaxLength(txtCity, 120);
		Constraints.setTextFieldMaxLength(txtState, 120);

		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
		colAdress.setCellValueFactory(new PropertyValueFactory<>("adress"));

		obsList = FXCollections.observableArrayList();
		tableView.setItems(obsList);
		
		 addButtonToTable();
		 addButtonToTable();
	}

}
