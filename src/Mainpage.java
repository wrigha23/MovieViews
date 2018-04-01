import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Mainpage extends Application {
	@Override
	public void start (Stage mainpage) throws Exception{
	try{	Connection conn=getConnection();
		
		PreparedStatement create=conn.prepareStatement("CREATE TABLE IF NOT EXISTS Customer(ID int,firstname varchar(50),lastname varchar(50),username varchar(50),password varchar(50), address varchar (75), PRIMARY KEY(ID))  ");
		PreparedStatement customer=conn.prepareStatement("CREATE TABLE IF NOT EXISTS Movieowner(ID int,firstname varchar(50), lastname varchar(50), MTname varchar(50), MTaddress varchar(50), city varchar(50), username varchar(50), password varchar(50), PRIMARY KEY(ID,MTname))");
		
		create.executeUpdate();
		customer.executeUpdate();
		
		//sets title
		mainpage.setTitle("MovieViews");
		//creates a pane for the scene
		GridPane root= new GridPane();
		
		//root.setAlignment(Pos.CENTER);

			//-fx-Background-color:red;
		
		root.setHgap(10);
		root.setVgap(5);
		root.setStyle("-fx-background-color:darkred");
		
		//creates log in button
		Button login= new Button("Log in!");
		Label search=new Label("Search:");
		TextField serachbox=new TextField("Enter movie or address");
		Button searchbt=new Button("Search");
		search.setStyle("-fx-color:blue; -fx-font-size:20;");
		
		login.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				mainpage.hide();
				try {
					Login();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println(e1);
				}
			}
		});
		
		Scene scene= new Scene(root,500,500, Color.RED);
		root.add(search, 0, 0);
		root.add(serachbox, 2, 0);
		root.add(searchbt, 4, 0);
		root.add(login,6,0);
		mainpage.setScene(scene);
		mainpage.show();
	}
	catch (Exception e){
		System.out.println(e);
	}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);

	}
	
	//lets the user chose whether they are a customer or a movie theater owner
private void typeofuser(){
	Stage register=new Stage();
	register.setTitle("Register");
	GridPane flow=new GridPane();
	flow.setVgap(15);
	flow.setHgap(15);
	
	//flow.setAlignment(Pos.CENTER);
	Label pick=new Label("Choose user type");
	
	Button customer=new Button("Customer");
	Button MT= new Button("Movie Therater");
	flow.add(pick, 5, 0);
	flow.add(customer, 5, 1);
	flow.add(MT, 5, 2);
	
	customer.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent e){
			try {
				CustomerResigster();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	});
	
	MT.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent e){
			try {
				MovietheaterRegister();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	});
	
	
	Scene rsceen=new Scene(flow,500,500);
	register.setScene(rsceen);
	register.show();
	
	
	
	
	
}



private void Login() throws Exception{
	Stage loginpage= new Stage();
	loginpage.setTitle("MovieView Sign In");
	
	FlowPane root= new FlowPane();
	
	root.setPadding(new Insets(11,12,13,14));
	root.setHgap(20);
	root.setVgap(20);
	
	TextField username= new TextField();
	TextField password=new TextField();
	
	Label user= new Label("Username:");
	Label pass= new Label("Password:");
	
	//ceartes log in buttons
	Button login= new Button("Log in");
	Button cancel= new Button("cancel");
	Button newm= new Button("Register");
	
	//creates customer table
	Connection conn=getConnection();
	//PreparedStatement create=conn.prepareStatement("CREATE TABLE IF NOT EXISTS Customer(ID int NOT NULL,firstname varchar(50),lastname varchar(50),username varchar(50),password varchar(50), city varchar(75), address varchar (75), PRIMARY KEY(ID)) ");
	//create.executeUpdate();
	System.out.println("Table created");
	
	login.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent e){
			String usern=username.getText();
			//System.out.println(usern);
			String pass=password.getText();
			
			try {
				PreparedStatement search=conn.prepareStatement("SELECT username,password FROM Customer");
				PreparedStatement searchMT=conn.prepareStatement("SELECT username,password FROM movieowner");
				ResultSet result=search.executeQuery();
				ResultSet resultMT=searchMT.executeQuery();
				ArrayList<String> array= new ArrayList<String>();
				while((result.next())&&(resultMT.next())){
					if(usern.equals(result.getString("username"))&& pass.equals(result.getString("password"))){
						System.out.println("Logged in");
						
					}
					else if(usern.equals(resultMT.getString("username"))&&pass.equals(resultMT.getString("password"))) {
						MovieTheaterowner MTO=new MovieTheaterowner();
						try {
							MTO.homepage();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					else{
						System.out.println("incorrect password enterd");
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			loginpage.hide();
		}
	});
	
	newm.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent e){
			loginpage.hide();
			typeofuser();
			
		}
	});
	
	cancel.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent e){
			loginpage.hide();
			try {
				start(loginpage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println(e1);
			}
		}
	});
	
	root.getChildren().addAll(user,username);
	root.getChildren().addAll(pass, password);
	root.getChildren().addAll(login,newm,cancel);
	
	
	Scene scene= new Scene(root, 300, 200);
	
	loginpage.setScene(scene);
	loginpage.show();
		
	
	
	
}


private void CustomerResigster(){
	Stage register= new Stage();
	register.setTitle("New member");
	
	GridPane root=new GridPane();
	root.setHgap(5);
	root.setVgap(5);
	Scene scene= new Scene(root,500,500);
	
	TextField firstname=new TextField ();
	TextField Lastname=new TextField();
	TextField username=new TextField();
	TextField password= new TextField();
	TextField newpass= new TextField();
	TextField address= new TextField();
	
	Label firstn=new Label("First Name:");
	Label lastn= new Label("Last Name");
	Label usern= new Label ("User Name:");
	Label pass= new Label ("Password");
	Label repass= new Label("re enter password");
	Label add= new Label("Address");
	Label city=new Label("city");
	Button enter= new Button("Enter");
	
	ComboBox citybox =new ComboBox();
	ObservableList cities=FXCollections.observableArrayList("Saint Petersbutg","Orlando","Miami","Daytona Beach"
			,"Jacksonville","Fort Lauderdale","Naples","Sarasota","Tallahassee");
	citybox.setItems(cities);
	
	root.add(firstn,0,0);
	root.add(firstname, 1, 0);
	root.add(lastn, 0, 1);
	root.add(Lastname, 1, 1);
	root.add(usern, 0, 2);
	root.add(username, 1, 2);
	root.add(pass, 0, 3);
	root.add(password, 1, 3);
	root.add(repass, 0, 4);
	root.add(newpass, 1, 4);
	root.add(city, 0, 5);
	root.add(add, 0, 6);
	root.add(citybox,1,5);
	root.add(address, 1, 6);
	
	
	root.add(enter,1,7);
	enter.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent e){
			String fname= firstname.getText();
			String lname=Lastname.getText();
			String uname=username.getText();
			String pword= password.getText();
			String pword2= newpass.getText();
			String street=address.getText();
			//String town=(String) citybox.getValue();
			int custID;
			
			//creates a random customer id
			
			
			
			System.out.println(pword);
			System.out.println(pword2);
			
			//checks to make sure both passwords match
			
			if(pword.equals(pword2)){
				try {
					
					//checks to make sure username and password arent already in the database
					Connection conn=getConnection();
					String current="SELECT username, password FROM Customer  ";
					PreparedStatement statement=conn.prepareStatement(current);
					ResultSet result=statement.executeQuery();
					
					Random rand= new Random();
					custID=rand.nextInt(2000)+1;
					ArrayList<String> array= new ArrayList<String>();
					while(result.next()){
						//Random rand= new Random();
						//custID=rand.nextInt(2000)+1;
						//checks to see if username exist in database
						if(uname.equals(result.getString("username"))){
							
						}
						//checks to see if password exist in dtatabase
						else if(pword.equals(result.getString("password"))){
							System.out.println("Password already exist");
						}
						else{
							//places userinformation into the database
							System.out.println("Username and password is not in database");
							try{
							PreparedStatement insertinfo=conn.prepareStatement("INSERT INTO customer(ID,firstname,lastname,username,password,city,address) VALUES("+custID+",'"+fname+"','"+lname+"','"+uname+"','"+pword+"','"+citybox.getValue()+"','"+street+"')");
							insertinfo.executeUpdate();
							
							Label correct=new Label("Account created");
							root.getChildren().add(correct);
							Login();
							register.hide();
							}
							catch (Exception e3){
								System.out.println(e3);
								
							}
							
							
						}
						
					}
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//PreparedStatement statement=conn.prepareStatement("SELECT");
			}
			
			else {
				System.out.println("passwords do not match");
			}
			
			
		}
	});
	
	register.setScene(scene);
	register.show();
	
	
	
}


private void MovietheaterRegister(){
	Stage MT= new Stage();
	MT.setTitle("Movie Theater registion");
	
	GridPane grid=new GridPane();
	Label firstn=new Label("First name:");
	Label lastn=new Label("last name:");
	Label MovieTherater=new Label("Movie Therater name:");
	Label MTaddress=new Label("Movie Therater address:");
	Label city=new Label("city:");
	Label usern=new Label("username:");
	Label passw=new Label("Password:");
	Label repass=new Label("re-enter password:");
	
	Button enter=new Button("Enter");
	Button cancel=new Button("Cancel");
	
	TextField fn=new TextField();
	TextField ln=new TextField();
	TextField MTname=new TextField();
	TextField MTlocation=new TextField();
	TextField MTFL=new TextField();
	TextField username=new TextField();
	TextField password=new TextField();
	TextField reenterpass=new TextField();
	
	ComboBox FLcity=new ComboBox();
	ObservableList cities=FXCollections.observableArrayList("Saint Petersbutg","Orlando","Miami","Daytona Beach"
			,"Jacksonville","Fort Lauderdale","Naples","Sarasota","Tallahassee");
	FLcity.setItems(cities);
	
	grid.add(firstn,0,0);
	grid.add(fn, 1, 0);
	grid.add(lastn,0,1);
	grid.add(ln, 1, 1);
	grid.add(MovieTherater, 0, 2);
	grid.add(MTname, 1, 2);
	grid.add(MTaddress, 0, 3);
	grid.add(MTlocation, 1, 3);
	grid.add(city, 0, 4);
	grid.add(FLcity, 1, 4);
	grid.add(usern, 0, 5);
	grid.add(username, 1, 5);
	grid.add(passw, 0, 6);
	grid.add(password,1,6);
	grid.add(repass, 0, 7);
	grid.add(reenterpass, 1, 7);
	
	grid.add(enter, 3, 8);
	grid.add(cancel, 4, 8);
	
	enter.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent e){
			
			//checks to make sure that passwords are the same
			String Name= fn.getText();
			String LName=ln.getText();
			String MovieName=MTname.getText();
			String MovieState=MTlocation.getText();
			String user=username.getText();
			String password1=password.getText();
			String password2=reenterpass.getText();
			int MTID;
			Random rand= new Random();
			MTID=rand.nextInt(2000)+1;
			try {
				if(password1.equals(password2)){
					//checks to see if username, password, and movie address already exist
					Connection conn=getConnection();
					String check="SELECT username, password, MTaddress FROM Movieowner";
					PreparedStatement checktable=conn.prepareStatement(check);
					ResultSet outcome=checktable.executeQuery();
					
					ArrayList<String> arrayOutcome= new ArrayList<String>();
					while(outcome.next()){
						if(user.equals(outcome.getString("username"))){
							System.out.println("Usrename already exist");
							
						}
						else if(password1.equals(outcome.getString("password"))){
							System.out.println("Password already exist");
						}
						else if (MovieState.equals(outcome.getString("MTaddress"))){
							System.out.println("This movie therate already exist ");
						}
						//adds movie to the database
						else{
							//System.out.print("i am here");
							String insert= "INSERT INTO Movieowner(ID,firstname,lastname,MTname,MTaddress,city, username, password) VALUES("+MTID+",'"+Name+"','"+LName+"','"+MovieName+"','"+MovieState+"','"+FLcity.getValue()+"','"+user+"','"+password1+"')";
							PreparedStatement insertTable=conn.prepareStatement(insert);
							insertTable.executeUpdate();
							//System.out.println("added to database");
							//MovieTheaterowner MTO=new MovieTheaterowner();
							//MTO.homepage();
						}
					}
		
				
				
				
				
				}
				else{
					System.out.print("Password are not the same");
				}
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println(e1);
			}
		}
	});
	
	Scene scene=new Scene(grid,500,500);
	MT.setScene(scene);
	MT.show();
	
}
//connects to movieviews database
public static Connection getConnection() throws Exception{
	try{
		String driver="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://127.0.0.1:3306/movieview";
		String username="root";
		String password="Yondrese11";
		Class.forName(driver);
		
		Connection conn=DriverManager.getConnection(url,username,password);
		System.out.println("Connect");
		
		return conn;
		
	}
	catch(Exception e){
		System.out.println(e);
	}
	return null;
}




}