import static java.awt.PageAttributes.MediaType.C;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.scene.control.ScrollPane;
import sec6_db.Student;
import sec6_db.dbConn;

public class db_207 extends Application {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ObservableList<Student> data;
    TableView<Student> table;

    private VBox vBoxMainLayout = new VBox();
    private Scene scene;
    private Button themeToggleButton = new Button("Dark Mode");
    private final String LIGHT_THEME_PATH = "/sec6_db/light-theme.css";
    private final String DARK_THEME_PATH = "/sec6_db/dark-theme.css";

    @Override
    public void start(Stage primaryStage) {

        Button btn1 = new Button();
        btn1.setText("Add");

        Button btn2 = new Button();
        btn2.setText("Clear");

        Button btnclear = new Button();
        btnclear.setText("Clear table view");

        Text txt1 = new Text("Add New Student");

        Label l1 = new Label("ID");
        l1.getStyleClass().add("labelstyle");
        Label l2 = new Label("Name");
        l2.getStyleClass().add("labelstyle");
        Label l3 = new Label("CGPA");
        l3.getStyleClass().add("labelstyle");
        Label l4 = new Label("Major");
        l4.getStyleClass().add("labelstyle");
        Label l5 = new Label("Minor");
        l5.getStyleClass().add("labelstyle");

        TextField t1 = new TextField();
        TextField t2 = new TextField();
        TextField t3 = new TextField();
        TextField t4 = new TextField();
        TextField t5 = new TextField();

        GridPane g1 = new GridPane();
        g1.add(txt1, 0, 0, 2, 1);
        g1.add(btnclear, 2, 0);
        g1.add(themeToggleButton, 3, 0);
        g1.add(l1, 0,1);
        g1.add(t1, 1,1);
        g1.add(l2, 0,2);
        g1.add(t2, 1,2);
        g1.add(l3, 0,3);
        g1.add(t3, 1,3);
        g1.add(l4, 0,4);
        g1.add(t4, 1,4);
        g1.add(l5, 0,5);
        g1.add(t5, 1,5);

        g1.add(btn1, 0,6);
        g1.add(btn2, 1,6);

        g1.setVgap(10);
        g1.setHgap(10);
        g1.setAlignment(Pos.CENTER);
        g1.setPadding(new Insets(20));

        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No rows to display"));

        TableColumn c1 = new TableColumn("ID");
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn c2 = new TableColumn("Name");
        c2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn c3 = new TableColumn("CGPA");
        c3.setCellValueFactory(new PropertyValueFactory<>("cgpa"));

        TableColumn c4 = new TableColumn("Major");
        c4.setCellValueFactory(new PropertyValueFactory<>("major"));

        TableColumn c5 = new TableColumn("Minor");
        c5.setCellValueFactory(new PropertyValueFactory<>("minor"));

        table.getColumns().addAll(c1, c2, c3, c4, c5);
        VBox v = new VBox(table);
        v.setPadding(new Insets(20));

        try {
            show();
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        Label l6 = new Label("Update Student's Major by ID: ");
        Label l7 = new Label("ID");
        TextField t6 = new TextField();
        Label l8 = new Label("Major");
        TextField t7 = new TextField();
        Button btn3 = new Button("Update");

        GridPane g2 = new GridPane();

        g2.add(l6, 0,0,2,1);
        g2.add(l7,0,1);
        g2.add(t6, 1,1);
        g2.add(l8,0,2);
        g2.add(t7, 1,2);
        g2.add(btn3,0,3,2,1);

        g2.setVgap(10);
        g2.setHgap(10);
        g2.setPadding(new Insets(20));

        Label l15 = new Label("Update Student's Minor by ID: ");
        Label l16 = new Label("ID");
        TextField t15 = new TextField();
        Label l17 = new Label("Minor");
        TextField t16 = new TextField();
        Button btn6 = new Button("Update");

        GridPane g3 = new GridPane();

        g3.add(l15,0,0,2,1);
        g3.add(l16,0,1);
        g3.add(t15, 1,1);
        g3.add(l17,0,2);
        g3.add(t16, 1,2);
        g3.add(btn6,0,3,2,1);

        g3.setVgap(10);
        g3.setHgap(10);
        g3.setPadding(new Insets(20));

        Label l9 = new Label("Delete Student by ID: ");
        Label l10 = new Label("ID");
        TextField t8 = new TextField();
        Button btn4 = new Button("Delete");

        GridPane g4 = new GridPane();

        g4.add(l9,0,0,2,1);
        g4.add(l10,0,1);
        g4.add(t8, 1,1);
        g4.add(btn4,0,2,2,1);

        g4.setVgap(10);
        g4.setHgap(10);
        g4.setPadding(new Insets(20));

        Label l11 = new Label("Average CGPA:  ");
        Button btn5 = new Button("Avg");
        Label l12 = new Label();

        GridPane g5 = new GridPane();

        g5.add(l11,0,0,2,1);
        g5.add(btn5,0,1);
        g5.add(l12,0,2);
      
        g5.setVgap(10);
        g5.setHgap(10);
        g5.setPadding(new Insets(20));

        g1.getStyleClass().add("custom-grid-pane");
        g2.getStyleClass().add("custom-grid-pane");
        g3.getStyleClass().add("custom-grid-pane");
        g4.getStyleClass().add("custom-grid-pane");
        g5.getStyleClass().add("custom-grid-pane");
        VBox vbox = new VBox(g2, g3, g4, g5);
  vbox.getStyleClass().add("custom-grid-pane");
        btn1.setOnAction((ActionEvent event) -> {

            conn = dbConn.DBConnection();
            String sql = "Insert into students (id, name, cgpa, major,minor) Values(?,?,?,?,?)";

            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, t1.getText());
                pst.setString(2, t2.getText());
                pst.setString(3, t3.getText());
                pst.setString(4, t4.getText());
                pst.setString(5, t5.getText());
                int i = pst.executeUpdate();
                if (i == 1) {
                    System.out.println("Data is inserted");
                }
                pst.close();
                conn.close();
                show();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        });

        btn2.setOnAction((ActionEvent event) -> {

            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");
            t5.setText("");
        });

        btn3.setOnAction((ActionEvent event) -> {

            String id = t6.getText();
            String m = t7.getText();

            String sql = "Update students set major = ? where id = ?";
            conn = dbConn.DBConnection();
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, m);
                pst.setString(2, id);
                pst.executeUpdate();
                pst.close();
                conn.close();
                show();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }

        });

        btn6.setOnAction((ActionEvent event) -> {

            String c = t15.getText();
            String d = t16.getText();

            String sql = "Update students set minor = ? where id = ?";
            conn = dbConn.DBConnection();
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, d);
                pst.setString(2, c);
                pst.executeUpdate();
                pst.close();
                conn.close();
                show();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }

        });

        btn4.setOnAction(e -> {
            String id = t8.getText();
            String sql = "Delete from students where id = ?";

            conn = dbConn.DBConnection();
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, id);
                pst.executeUpdate();
                pst.close();
                conn.close();
                show();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        });

        btn5.setOnAction(e -> {
            String sql = "select avg (cgpa) from students"; // avg, sum, count, min, max

            conn = dbConn.DBConnection();
            try {
                pst = conn.prepareStatement(sql);
                res = pst.executeQuery();
                if (res.next()) {
                    l11.setText(Double.toString(res.getDouble(1)));
                }
                pst.close();
                conn.close();

            } catch (Exception ex) {
                System.out.println(ex.toString());
            }

        });
        btnclear.setOnAction(e -> {
            String sql = "Delete from students";

            conn = dbConn.DBConnection();
            try {
                pst = conn.prepareStatement(sql);
                // pst.setString(1);
                pst.executeUpdate();
                pst.close();
                conn.close();
                show();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        });
         
        // Container for grid and other buttons
        VBox vBoxControls = new VBox(10); // spacing between nodes
        vBoxControls.getChildren().addAll( g2, g3, g4, g5,v);
        vBoxControls.setPadding(new Insets(10));

        // Container that will be scrolled
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBoxControls);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPannable(true);

        scrollPane.prefHeightProperty().bind(primaryStage.heightProperty());
        
        InputStream testStream = getClass().getResourceAsStream("/sec6_db/dark-theme.css");
    if (testStream == null) {
        System.err.println("Failed to load the light-theme.css stylesheet. File does not exist.");
    } else {
        System.out.println("Successfully located the light-theme.css stylesheet.");
        try {
            testStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        vBoxMainLayout.getChildren().addAll(g1, scrollPane); // Adding g1, table, scrollPane to your main layout
        vBoxMainLayout.getStyleClass().add("custom-grid-pane");
        themeToggleButton.setOnAction(event -> toggleTheme());

        
        scene = new Scene(vBoxMainLayout, 1000, 500);
        applyCssTheme("light-theme.css"); // Apply the light theme

        primaryStage.setTitle("DB Connection");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Listener for width changes to hide ScrollPane bars when the window is large enough
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            boolean wideEnough = Double.compare(newValue.doubleValue(), vBoxControls.getWidth()) > 0;
            scrollPane.setHbarPolicy(wideEnough ? ScrollPane.ScrollBarPolicy.NEVER : ScrollPane.ScrollBarPolicy.AS_NEEDED);
        });

        // Listener for height changes to hide ScrollPane bars when the window is tall enough
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            boolean tallEnough = Double.compare(newValue.doubleValue(), vBoxControls.getHeight()) >0;
            scrollPane.setVbarPolicy(tallEnough ? ScrollPane.ScrollBarPolicy.NEVER : ScrollPane.ScrollBarPolicy.AS_NEEDED);
        });
    }

    public void show() throws SQLException {

        data = FXCollections.observableArrayList();
        conn = dbConn.DBConnection();

        pst = conn.prepareStatement("select * from students");
        res = pst.executeQuery();

        while (res.next()) {
            data.add(new Student(res.getInt(1), res.getString(2), res.getDouble(3), res.getString(4),res.getString(5)));

        }
        pst.close();
        conn.close();
        table.setItems(data);
    }
    public void clear() throws SQLException {

        data = FXCollections.observableArrayList();
        conn = dbConn.DBConnection();

        pst = conn.prepareStatement("Delete from students");
        res = pst.executeQuery();

        while (res.next()) {
            data.remove(new Student(res.getInt(1), res.getString(2), res.getDouble(3), res.getString(4),res.getString(5)));

        }
        pst.close();
        conn.close();
        table.setItems(data);
    }
    private void toggleTheme() {
    if ("Dark Mode".equals(themeToggleButton.getText())) {
        applyCssTheme("dark-theme.css"); 
        themeToggleButton.setText("Light Mode");
    } else if ("Light Mode".equals(themeToggleButton.getText())) {
        applyCssTheme("light-theme.css"); 
        themeToggleButton.setText("Dark Mode");
    }
}

private void applyCssTheme(String path) {
    scene.getStylesheets().clear();
    InputStream stylesheetStream = getClass().getResourceAsStream("/sec6_db/" + path);
    if (stylesheetStream == null) {
        System.err.println("Cannot load stylesheet at " + path + ". Make sure the file is in the correct directory.");
    } else {
        try {
            String stylesheet = getClass().getResource("/sec6_db/" + path).toExternalForm();
            scene.getStylesheets().add(stylesheet);
        } finally {
            try {
                stylesheetStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}