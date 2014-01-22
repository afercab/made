package com.velonuboso.made.gui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    

    private Stage stage = null;
    private Configurator configurator = null;
    private MadeLogger madeLogger = null;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        
        this.stage = stage;
        this.stage.centerOnScreen();
        
        madeLogger = MadeLogger.getInstance();
        madeLogger.configure(stage);
        
        configurator = Configurator.getInstance();
        try{
            configurator.autoConfigure();
        }catch(Exception ex){
            madeLogger.error(ex.getMessage(), ex);
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
        Parent root = (Parent) loader.load();
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
        
        MainController mainController = loader.getController();
        mainController.setStage(stage);
        mainController.setup();
    }

    public Stage getStage() {
        return stage;
    }

    public Configurator getConfigurator() {
        return configurator;
    }

    
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("AAAAAaaaaa");
        launch(args);
    }

}