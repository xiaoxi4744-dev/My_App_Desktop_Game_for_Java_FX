import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
//OOP
import javafx.beans.property.*;
//sql
import javafx.collections.*;
import java.sql.*;

import javax.swing.Action;

//OOP
class Students {
    private StringProperty id;
    private StringProperty f_name;
    private StringProperty l_name;
    private StringProperty email;
    private StringProperty birthday;
    private StringProperty lv;

    public Students(String id, String f_name, String l_name, String email, String birthday,String lv){
        this.id = new SimpleStringProperty(id);
        this.f_name = new SimpleStringProperty(f_name);
        this.l_name = new SimpleStringProperty(l_name);
        this.email = new SimpleStringProperty(email);
        this.birthday = new SimpleStringProperty(birthday);
        this.lv = new SimpleStringProperty(lv);
    }
    public StringProperty idProperty() {return id;} //ส่งค่าออกไป
    public StringProperty f_nameProperty() {return f_name;}
    public StringProperty l_nameProperty() {return l_name;}
    public StringProperty emailProperty() {return email;}
    public StringProperty birthdayProperty() {return birthday;}
    public StringProperty lvProperty() {return lv;}
}

public class Appscore extends Application {

    Stage stage; //Global
    TableView<Students> table = new TableView<>();
    public static void main(String[] args) {
        launch(args);
    }
//GUI1 ############################################################################################################################################################################
    @Override
        public void start(Stage stage)throws Exception{

        this.stage = stage;
        StackPane root1Pane = new StackPane();

        Label title = new Label("Login");
        title.setFont(new Font("Arial",22));

        TextField user = new TextField();
        user.setPromptText("Username");
        user.getStyleClass().add("Login-Field");

        PasswordField pass = new PasswordField();
        pass.setPromptText("Password");
        pass.getStyleClass().add("Login-Field");

        Button bt = new Button("Login");
        bt.getStyleClass().add("login-btn");
        bt.setOnAction(e ->{
            if(checkLoingAdmin(user.getText(),pass.getText())){
                System.out.println("Login Success");
                showmain();
            }else{
                System.out.println("Login Field");
                //แจ้งเตือน
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Username หรือ Password ผิด!");
                alert.showAndWait();
            }
        });

        Button bt1 = new Button("Regiter");
        bt1.getStyleClass().add("login-btn");
        bt1.setOnAction( e ->{
            System.out.println("สมัครแล้ว!");
            Regiter();
        });

        Rectangle re = new Rectangle();
        re.setWidth(260);
        re.setHeight(240);
        re.setArcWidth(30); //กำหนดความโค้ง
        re.setArcHeight(30); //กำหนดความโค้ง
        re.setFill(Color.web("#c6c6c6"));
        re.getStyleClass().add("re-control");
        
        HBox BT = new HBox(10);
        BT.setPadding(new Insets(10));
        BT.setAlignment(Pos.CENTER);

        VBox window = new VBox(15);
        window.setPadding(new Insets(10));
        window.setAlignment(Pos.CENTER);
        window.setFillWidth(false);

        BT.getChildren().addAll(bt,bt1);
        window.getChildren().addAll(title,user,pass,BT);

        root1Pane.getChildren().addAll(re, window);
        root1Pane.getStyleClass().add("bg-scene1");//พื้นหลัง

        Scene scene1 = new Scene(root1Pane,1000,600);

        //CSS
        scene1.getStylesheets().add(getClass().getResource("Sy.css").toExternalForm());

        stage.setTitle("Login");
        stage.setScene(scene1);
        stage.show();
    }
//GUI2 ############################################################################################################################################################################
    public void showmain(){
        //ปุ่ม
        TextField sch = new TextField();
        sch.setPromptText("Search (ID)");
        sch.getStyleClass().add("search");

        Button btsch = new Button("🔍");
        btsch.getStyleClass().add("login-btn");
        btsch.setOnAction(e ->{
            SELECTDATA(sch.getText()); //รับค่ามาจาก sch 
        });

        Button res = new Button("Reset");
        res.getStyleClass().add("login-btn");
        res.setOnAction(e ->{LoadData();});

        Label lb = new Label("NWK SCORE ADMIN");
        lb.getStyleClass().add("NWKTEXT");

        TextField Id = new TextField();
        Id.setPromptText("ID");
        Id.getStyleClass().add("sc");

        TextField fnameField = new TextField();
        fnameField.setPromptText("First Name");
        fnameField.getStyleClass().add("User-Textfield");

        TextField lnameField = new TextField();
        lnameField.setPromptText("Last Name");
        lnameField.getStyleClass().add("User-Textfield");

        TextField BD = new TextField();
        BD.setPromptText("Birthday");
        BD.getStyleClass().add("User-Textfield");

        TextField Email = new TextField();
        Email.setPromptText("email");
        Email.getStyleClass().add("User-Textfield\"");

        TextField Lv = new TextField();
        Lv.setPromptText("LV");
        Lv.getStyleClass().add("sc");

        Button btaddBT = new Button("ADD");
        btaddBT.getStyleClass().add("login-btn");
        btaddBT.setOnAction(e -> {
            addStudents(
                fnameField.getText(),
                lnameField.getText(),
                Email.getText(),
                BD.getText()
                
            );
            LoadData();
        });

        Button DeleteBT = new Button("DELETE(ID)");
        DeleteBT.getStyleClass().add("login-btn");
        DeleteBT.setOnAction(e -> {
            DELETEstudens(
                Id.getText()
            );
            LoadData();
        });

        Button UPDATEBT = new Button("UPDATE");
        UPDATEBT.getStyleClass().add("login-btn");
        UPDATEBT.setOnAction(e -> {
            
            UPDATEStudents(
                Email.getText(),
                Id.getText()
                );
            
            LoadData();
        });

        table = new TableView<>();

        //สร้างตาราง
        TableColumn<Students,String> idcl = new TableColumn<>("ID");
        idcl.setCellValueFactory(data -> data.getValue().idProperty());

        TableColumn<Students,String> fcl = new TableColumn<>("First Name");
        fcl.setCellValueFactory(data -> data.getValue().f_nameProperty());

        TableColumn<Students,String> lcl = new TableColumn<>("Last Name");
        lcl.setCellValueFactory(data -> data.getValue().l_nameProperty());

        TableColumn<Students,String> email = new TableColumn<>("Email");
        email.setCellValueFactory(data -> data.getValue().emailProperty());

        TableColumn<Students,String> bd = new TableColumn<>("Birthday");
        bd.setCellValueFactory(data -> data.getValue().birthdayProperty());

        TableColumn<Students,String> lv = new TableColumn<>("LV");
        lv.setCellValueFactory(data -> data.getValue().lvProperty());

        //ขยายตาราง
        idcl.setPrefWidth(100);
        fcl.setPrefWidth(200);
        lcl.setPrefWidth(200);
        email.setPrefWidth(200);
        bd.setPrefWidth(100);
        lv.setPrefWidth(70);

        table.getColumns().addAll(idcl,fcl,lcl,email,bd,lv);

        //โหลดข้อมูล
        LoadData();

        //จัดตำแหน่ง
        HBox BTseach = new HBox(10);
        BTseach.setPadding(new Insets(10));
        BTseach.setAlignment(Pos.CENTER);

        HBox BT = new HBox(10);
        BT.setPadding(new Insets(10));
        BT.setAlignment(Pos.CENTER);

        HBox BTAC = new HBox(10);
        BTAC.setPadding(new Insets(10));
        BTAC.setAlignment(Pos.CENTER);

        VBox tb = new VBox(10);
        tb.setPadding(new Insets(10));
        tb.setAlignment(Pos.CENTER);
        tb.setFillWidth(false);

        VBox Em = new VBox(10);
        Em.setPadding(new Insets(10));
        Em.setAlignment(Pos.CENTER);
        Em.setFillWidth(false);

        VBox Action = new VBox(10);
        Action.setPadding(new Insets(10));
        Action.setAlignment(Pos.CENTER);
        Action.setFillWidth(false);

        BTseach.getChildren().addAll(sch,btsch,res); //Nodeค้นหา
        BT.getChildren().addAll(Id,fnameField,lnameField,Lv); //Node TextField
        Em.getChildren().addAll(BD,Email);

        BTAC.getChildren().addAll(btaddBT,DeleteBT,UPDATEBT); //NodeAction
        Action.getChildren().addAll(BTAC); //จัดระเบียบ

        tb.getChildren().addAll(BTseach,lb,BT,Em,Action,table);//จัดระเบียบ

        Scene scene2 = new Scene(tb,1000,600);

        //CSS
        scene2.getStylesheets().add(getClass().getResource("Sy.css").toExternalForm());

        stage.setTitle("Mian");
        stage.setScene(scene2);
        stage.show();
    }

//GUI3 ############################################################################################################################################################################
    public void Regiter(){

        StackPane root = new StackPane();

        Label lb = new Label("Regiter");

        root.getChildren().add(lb);
        root.getStyleClass().add("bg-scene3");



        Scene scene3 = new Scene(root,1000,600);
        scene3.getStylesheets().add(getClass().getResource("Sy.css").toExternalForm());

        stage.setTitle("Regiter");
        stage.setScene(scene3);
        stage.show();
    }

    //database
public void LoadData(){
    ObservableList<Students> list = FXCollections.observableArrayList(); //สร้างArray เก็บข้อมูล

    try {
   Connection conn = connect();

    //loopเอาข้อมูลมาใส่เก็บไว้ใน Array
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM data_store");

    while(rs.next()){
        list.add(new Students(
            rs.getString("id"), //ตัวแปรในdatabase
            rs.getString("f_name"),
            rs.getString("l_name"),
            rs.getString("email"),
            rs.getString("birthday"),
            rs.getString("lv")
        ));
    }

    table.setItems(list);
    conn.close();
        

    } catch (Exception e) {
        e.printStackTrace();
        }
    }

    //Add ข้อมูล 
public void addStudents(String f_name, String l_name, String email, String birthday){
    try {
        Connection conn = connect();

        String sql = "INSERT INTO data_store(f_name,l_name,email,birthday) VALUES (?,?,?,?)";
        PreparedStatement add = conn.prepareStatement(sql);

        add.setString(1,f_name);
        add.setString(2,l_name);
        add.setString(3,email);
        add.setString(4,birthday);

        add.executeUpdate();

        conn.close();

    } catch (Exception e) {
        e.printStackTrace();
        }
    }
    //DELETE ข้อมูล
public void DELETEstudens(String id){
    try {
        Connection conn = connect();

        String sql = "DELETE FROM data_store WHERE id=?";
        PreparedStatement dt = conn.prepareStatement(sql);

        dt.setString(1, id);

        dt.executeUpdate();

        conn.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    //UPDATE ข้อมูล
    public void UPDATEStudents(String email,String id){
    try {
        Connection conn = connect();

        String sql = "UPDATE data_store SET email=? WHERE id =?";
        PreparedStatement add = conn.prepareStatement(sql);
    
        add.setString(1, email);
        add.setString(2, id);

        int rows = add.executeUpdate();
        System.out.println("Updated rows = " + rows);

        conn.close();

    } catch (Exception e) {
        e.printStackTrace();
        }
    }
    //SELECT DATA
    public void SELECTDATA(String id){
        ObservableList<Students> list = FXCollections.observableArrayList();// สร้างArray

        try {
        Connection conn = connect();

        String sql = "SELECT * FROM data_store WHERE id = ?";
        PreparedStatement sel = conn.prepareStatement(sql);

        sel.setString(1, id);
        ResultSet rs = sel.executeQuery();

         while(rs.next()){
            list.add(new Students(
                rs.getString("id"), //ตัวแปรในdatabase
                rs.getString("f_name"),
                rs.getString("l_name"),
                rs.getString("email"),
                rs.getString("birthday"),
                rs.getString("lv")
            ));
        }

        table.setItems(list);

        conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //DATABASE connection
    public Connection connect() throws Exception {
    return DriverManager.getConnection(
        Config.getURL(),
        Config.USER,
        Config.PASS
    );
    }
    //checkLoing
    public boolean checkLoingAdmin(String username,String passwaord){
        try {
            Connection conn = connect();

            String sql = "SELECT * FROM admin WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,username);
            ps.setString(2,passwaord);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                conn.close();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}