package nl.oscar.dpi.donutrater.hiscore.javafx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.oscar.dpi.donutrater.hiscore.service.DonutListener;
import nl.oscar.dpi.donutrater.hiscore.service.ReviewListener;
import nl.oscar.dpi.donutrater.library.domain.Donut;
import nl.oscar.dpi.donutrater.library.domain.DonutReview;
import org.springframework.stereotype.Component;

@Component
public class MainController implements ReviewListener, DonutListener {

    private final ObservableList<DisplayDonut> donuts = FXCollections.observableArrayList();
    private final ObservableList<DisplayReview> reviews = FXCollections.observableArrayList();
    public TableView<DisplayDonut> tblDonut;
    public TableColumn<DisplayDonut, String> colName;
    public TableColumn<DisplayDonut, Integer> colLikes;
    public TableColumn<DisplayDonut, String> colDescription;
    public TableView<DisplayReview> tblReview;
    public TableColumn<DisplayDonut, Long> colClientId;
    public TableColumn<DisplayDonut, String> colDonutId;
    public TableColumn<DisplayDonut, Boolean> colLike;
    public TableColumn<DisplayDonut, Boolean> colSuccess;
    public TableColumn<DisplayDonut, String> colTimestamp;

    @FXML
    public void initialize() {

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLikes.setCellValueFactory(new PropertyValueFactory<>("likes"));

        tblDonut.setItems(donuts);

        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colDonutId.setCellValueFactory(new PropertyValueFactory<>("donutId"));
        colLike.setCellValueFactory(new PropertyValueFactory<>("like"));
        colSuccess.setCellValueFactory(new PropertyValueFactory<>("success"));
        colTimestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        tblReview.setItems(reviews);
    }

    @Override
    public void onNewReview(DonutReview review, Donut donut) {
        Platform.runLater(() -> {
            reviews.add(new DisplayReview(review));

            DisplayDonut displayDonut = new DisplayDonut(donut);

            if (donuts.contains(displayDonut)) {
                donuts.stream().filter(d -> d.equals(displayDonut)).forEach(d -> d.setDonut(donut));
                tblDonut.refresh();
            }
        });
    }

    @Override
    public void onNewDonut(Donut donut) {
        Platform.runLater(() -> {
            donuts.add(new DisplayDonut(donut));
        });
    }
}
