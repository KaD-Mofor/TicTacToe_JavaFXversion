package TicTacToe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class TicTacToe extends Application {

    private char whoseTurn = 'X'; //To indicate which player is playing
    private Cell[][] cell = new Cell[3][3];
    private Label lblStatus = new Label("X's turn to play");

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane pane = new GridPane(); //Pane to hold nine cells
        for (int i =0; i < 3; i++)
            for (int j =0; j < 3; j++)
                pane.add(cell[i][j] = new Cell(), j, i);

        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(lblStatus); //The winner text is placed at the bottom
        borderPane.setCenter(pane);  //The game is centered

        Scene scene = new Scene(borderPane, 250, 170);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    //Determine if cells are occupied
    public boolean isFull(){
        for (int i =0; i < 3; i++)
            for (int j =0; j < 3; j++)
                if(cell[i][j].getToken() == ' ')
                    return false;
        return true;
    }

    //Determine if player with token wins
    public boolean isWon(char token){
        for (int i=0; i<3; i++)                         //Check the rows
            if (cell[i][0].getToken() == token
            && cell[i][1].getToken() == token
            && cell[i][2].getToken() == token){
                return true;
            }
        for (int j=0; j<3; j++)                        //check the columns
            if (cell[0][j].getToken() == token
                    && cell[1][j].getToken() == token
                    && cell[2][j].getToken() == token){
                return true;
            }
        if (cell[0][0].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][2].getToken() == token){
            return true;
        }
        if (cell[0][2].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][0].getToken() == token){
            return true;
        }
        return false;
    }

//inner class for a cell
    public class Cell extends Pane {
    private char token = ' '; //token used for this cell

    public Cell() {
        setStyle("-fx-border-color: black");
        this.setPrefSize(500, 500);
        this.setOnMouseClicked(mouseEvent -> handleMouseClick());
    }

    /**
     * Return token
     */
    public char getToken() {
        return token;
    }

    /**
     * Set a new token
     */
    public void setToken(char c) {
        this.token = c;

        if (token == 'X') {
            Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
            line1.endXProperty().bind(this.widthProperty().subtract(10));
            line1.endYProperty().bind(this.heightProperty().subtract(10));
            Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
            line2.startYProperty().bind(this.heightProperty().subtract(10));
            line2.endXProperty().bind(this.widthProperty().subtract(10));

            this.getChildren().addAll(line1, line2);
        } else {
            Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2,
                    this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);
            ellipse.centerXProperty().bind(this.widthProperty().divide(2));
            ellipse.centerYProperty().bind(this.heightProperty().divide(2));
            ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
            ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
            ellipse.setStroke(Color.BLACK);
            ellipse.setFill(Color.WHITE);

            getChildren().add(ellipse);      //Add ellipse to the pane
        }
    }

    //Handle a mouse click event
    private void handleMouseClick() {
        //if cell is empty and game is not over
        if (token == ' ' && whoseTurn != ' ') {
            setToken(whoseTurn);   //Set token in the cell

            //Check game status
            if (isWon(whoseTurn)) {
                lblStatus.setText(whoseTurn + " won! The game is over.");
                whoseTurn = ' '; //Game over
            } else if (isFull()) {
                lblStatus.setText("Draw! The game is over.");
                whoseTurn = ' ';       //Game over
            }
            else {
                //Change the turn
                whoseTurn = (whoseTurn == 'X') ? '0' : 'X';
                //Display whose turn
                lblStatus.setText(whoseTurn + "'s turn");
            }
        }
    }


    //public static void main(String[] args) {
      //  launch(args);
   // }
  }
}
