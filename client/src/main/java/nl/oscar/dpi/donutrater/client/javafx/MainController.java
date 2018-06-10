package nl.oscar.dpi.donutrater.client.javafx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.oscar.dpi.donutrater.client.service.DonutService;
import nl.oscar.dpi.donutrater.library.domain.Donut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class MainController {

    private final ObservableList<DisplayDonut> donuts = FXCollections.observableArrayList();
    public TableColumn<DisplayDonut, String> colName;
    public TableColumn<DisplayDonut, Integer> colLikes;
    public TableColumn<DisplayDonut, String> colDescription;
    @FXML
    private Button btnSubmitDonut;
    @FXML
    private TextField txtDonutName;
    @FXML
    private TextArea txtDonutDescription;
    @FXML
    private TableView<DisplayDonut> tblDonut;
    @FXML
    private Button btnLikeDonut;
    @FXML
    private Button btnDislikeDonut;
    @Autowired
    private DonutService service;

    @FXML
    public void initialize() {
        btnSubmitDonut.disableProperty()
                .bind(txtDonutName.textProperty().isEmpty()
                        .or(txtDonutDescription.textProperty().isEmpty()));

        btnLikeDonut.disableProperty().bind(tblDonut.getSelectionModel().selectedItemProperty().isNull());
        btnDislikeDonut.disableProperty().bind(tblDonut.getSelectionModel().selectedItemProperty().isNull());

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLikes.setCellValueFactory(new PropertyValueFactory<>("likes"));
/*
        donuts.add(new DisplayDonut(new Donut("Henk", "Ingrid")));*/
        tblDonut.setItems(donuts);

        btnSubmitDonut.setOnAction(this::sendDonut);
        btnLikeDonut.setOnAction(this::likeDonut);
        btnDislikeDonut.setOnAction(this::dislikeDonut);

        new Thread(this::initDonuts).start();
    }

    private void sendDonut(ActionEvent event) {
        service.addDonut(txtDonutName.getText(), txtDonutDescription.getText());

        txtDonutDescription.clear();
        txtDonutName.clear();
    }

    private void likeDonut(ActionEvent event) {
        reviewDonut(true);
    }

    private void dislikeDonut(ActionEvent event) {
        reviewDonut(false);
    }

    private void reviewDonut(boolean like) {
        Donut donut = tblDonut.getSelectionModel().getSelectedItem().getDonut();

        service.reviewDonut(donut, like);
    }

    @JmsListener(destination = "update_donut", containerFactory = "myFactory")
    public void updateDonut(Donut donut) {
        Platform.runLater(() -> {
            DisplayDonut displayDonut = new DisplayDonut(donut);

            if (donuts.contains(displayDonut)) {
                donuts.stream().filter(d -> d.equals(displayDonut)).forEach(d -> d.setDonut(donut));
                tblDonut.refresh();
            } else {
                donuts.add(displayDonut);
            }
        });
    }

    private void initDonuts() {
        Collection<Donut> initialDonuts = service.initDonuts().getDonuts();

        Platform.runLater(() -> {
            donuts.addAll(initialDonuts.stream().map(DisplayDonut::new).collect(Collectors.toList()));
        });
    }
}
