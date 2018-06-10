package nl.oscar.dpi.donutrater.client.boot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJms
@ComponentScan("nl.oscar.dpi.donutrater.client")
@EnableScheduling
public class ClientApplication extends Application {

    private ConfigurableApplicationContext context;
    private Parent rootNode;

    @Override
    public void init() throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ClientApplication.class);

        context = builder.run(getParameters().getRaw().toArray(new String[0]));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client.fxml"));
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
