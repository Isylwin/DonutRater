package nl.oscar.dpi.donutrater.hiscore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableJms
@ComponentScan("nl.oscar.dpi.donutrater.hiscore")
@EnableJpaRepositories("nl.oscar.dpi.donutrater.hiscore")
@EntityScan("nl.oscar.dpi.donutrater")
@SpringBootApplication
@EnableScheduling
public class HiscoreApplication extends Application {

    private ConfigurableApplicationContext context;
    private Parent rootNode;

    @Override
    public void init() throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(HiscoreApplication.class);

        context = builder.run(getParameters().getRaw().toArray(new String[0]));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hiscore.fxml"));
        loader.setControllerFactory(context::getBean);
        rootNode = loader.load();
    }

    @Override
    public void stop() {
        context.close();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(rootNode));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
