
package mydrawapp;

import java.io.BufferedReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import java.io.InputStreamReader;
import java.io.Reader;

/*
 * By:Arifuddin Ahmad
 * SN:1019399
 * 
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        //Initialise objects
        Reader parseReader = new InputStreamReader(System.in);
        Group  root = new Group();
        Parser parser = new Parser(parseReader);


        parser.parse(getUserInput("Use Demo Instructions:(Y/N): "),getUserInput("Got to Step-Through mode(Y/N): "));//parse instructions
        root.getChildren().add(parser.getGroup());//Draw objects taken from instructions onto the screen
        
        //Draw scene
        Scene scene = new Scene(root, parser.getWidth(),parser.getHeight());
        primaryStage.setTitle("Draw App - JavaFX Version");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    //Get user true or false input
    private boolean getUserInput(String msg)
    {
        boolean isStepThrough = false;  
        InputStreamReader inputStreamReader = new InputStreamReader(System.in); 
        BufferedReader reader = new BufferedReader(inputStreamReader); 
        while(true)
        {
            try
            {
                System.out.print(msg);
                String userInput = reader.readLine().toUpperCase();
                if(userInput.compareTo("Y")==0)
                {
                    isStepThrough = true;
                    return isStepThrough;
                }else if(userInput.compareTo("N")==0){
                    isStepThrough = false;
                    return isStepThrough;
                }
            }catch(IOException e)
            {
                System.err.println("Input/Output Error");
            }
        }
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}

