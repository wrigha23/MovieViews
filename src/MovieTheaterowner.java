

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.PreparedStatement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MovieTheaterowner {

	public  MovieTheaterowner(){
		
	}
	
	public void homepage() throws Exception{
		try{
			Connection connect=getConnection();
			PreparedStatement movies=(PreparedStatement) connect.prepareStatement("CREATE TABLE IF NOT EXISTS MoviesPlaying(MovieTherateID int,MovieTherate varchar(50),MovieName varchar(50), MovieDetail varchar(200), MoviePrice float, MovieRating varchar(10), runtime int,showtimes varchar(100),NumberOfSeats int, PRIMARY KEY(MovieName))");
			movies.executeUpdate();
			Stage MTHomepage=new Stage();
		MTHomepage.setTitle("Movie Theater Homepage");
		BorderPane Task= new BorderPane();
		HBox hbox=new HBox();
		hbox.setPadding(new Insets(15,12,15,12));
		hbox.setSpacing(10);
		
		Button addM=new Button("Add");
		Button MHistory=new Button("History");
		Button removeM=new Button("Remove");
		
		addM.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				Stage newMovies=new Stage();
				newMovies.setTitle("Add movies to therater");
				GridPane MoviesInfo=new GridPane();
				BorderPane MovieCollect=new BorderPane();
				VBox vbox= new VBox();
				vbox.setPadding(new Insets(10));
				vbox.setSpacing(10);
				MoviesInfo.setVgap(10);
				MoviesInfo.setHgap(10);
				
				//Image moviedefault=new Image("8db7dba3dd148002f989781c0b59bb3.jpg");
				Label MovieName=new Label("Movie name:");
				Label MovieDesricpt=new Label("Movie descrption:");
				Label MoviePrice=new Label("Ticket Price:");
				Label MovieRating=new Label("Rating");
				Label Runtime=new Label("Runtime");
				
				Label MovieImage= new Label ("Insert Movie poster here:");
				
				TextField MovieTitle=new TextField();
				TextField MovieInfo=new TextField();
				TextField MovieCost=new TextField();
				TextField RT= new TextField();
				Button addm=new Button("Add movie");
				
				ComboBox Rating=new ComboBox();
				ObservableList Ratings=FXCollections.observableArrayList("G","PG","PG-13","R");
				Rating.setItems(Ratings);
				
				MoviesInfo.add(MovieName,0,0);
				MoviesInfo.add(MovieTitle, 1, 0);
				MoviesInfo.add(MovieDesricpt,0,1);
				MoviesInfo.add(MovieInfo, 1, 1);
				MoviesInfo.add(MoviePrice, 0, 2);
				MoviesInfo.add(MovieCost, 1, 2);
				MoviesInfo.add(MovieRating, 0, 4);
				MoviesInfo.add(Rating, 1, 4);
				MoviesInfo.add(Runtime, 0,3);
				MoviesInfo.add(RT, 1, 3);
				
				MoviesInfo.add(addm, 3, 3);
				
				//this method prompt the user to renter their password
				addm.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				
				//gets the movie information that was enterd
				String getMovieTitle=MovieTitle.getText();
				String getMovieInfo=MovieDesricpt.getText();
				String getMoviePrice=MovieCost.getText();
				String getRunTime=RT.getText();
				
				
				//creates a new stage that gets the show time and seat size
				
				Stage ShowTime=new Stage();
				ShowTime.setTitle(getMovieTitle+" ShowTimes");
				GridPane grid= new GridPane();
				
				Label DailyShowTimes=new Label("Daily Showtimes");
				Label NumberOfSeats=new Label("Theater size");
				
				TextField DST=new TextField();
				TextField NS=new TextField();
				
				Button add= new Button("Add Me");
				
				add.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent e){
			String getShowTime=DST.getText();
			String getSeatNumber=NS.getText();
			
			
			Stage verfi=new Stage();
			verfi.setTitle("Save Movie");
			FlowPane root= new FlowPane();
			root.setPadding(new Insets(11,12,13,14));
			root.setHgap(20);
			root.setVgap(20);
			
			
			
			
			Label renter= new Label("Enter your password");
			TextField location=new TextField();
			Button enter =new Button("enter");
			
			enter.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent e){
					//searchs the database for the password 
					String password=location.getText();
					//System.out.println(password);
					
					
				//	java.sql.PreparedStatement state;
					try {
						
						try {
							System.out.println("i am here");
							PreparedStatement state = connect.prepareStatement("SELECT * FROM movieowner");
							ResultSet output=state.executeQuery();
							System.out.println("i have done database qurey");
							ArrayList<String> myList=new ArrayList<String>();
							//
							while(output.next()){
								System.out.println("i am in the while loop");
								if(password.equals(output.getString("password"))){
									System.out.println("i have found a password that match");
									int ID=output.getInt(1);
									String name=output.getString(2);
									String MT=output.getString(4);
									String MTaddress=output.getString(5);
									
									//places the informatin enterd into the database
									String place="INSERT INTO moviesplaying(MovieTherateID,MovieTherate,MovieName,MovieDetail,MoviePrice,MovieRating,runtime,showtimes,NumberOfSeats ) VALUES('"+ID+"','"+MT+"','"+getMovieTitle+"','"+getMovieInfo+"','"+getMoviePrice+"','"+Rating.getValue()+"','"+getRunTime+"','"+getShowTime+"','"+getSeatNumber+"')";
									java.sql.PreparedStatement insertvalue=connect.prepareStatement(place);
									insertvalue.executeUpdate();
									System.out.println("enterd into database");

								}
								
								
							}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
								
								
							}
						catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					
					
					
					
				
				}}
				);
				
					root.getChildren().add(location);
					root.getChildren().add(renter);
					root.getChildren().add(enter);
					Scene scene= new Scene(root,500,500);
					verfi.setScene(scene);
					verfi.show();
					
					
					
					
					
					
					
					//String insert="INSERT INTO moviesplaying"
		}
				});
			
			
		
				
				grid.add(DailyShowTimes, 0, 0);
				grid.add(DST, 1, 0);
				grid.add(NumberOfSeats, 0, 1);
				grid.add(NS, 1, 1);
				grid.add(add, 3, 3);
				
				Scene mt=new Scene(grid,500,500);
				ShowTime.setScene(mt);
				ShowTime.show();
				
				
				
				
				
				
				
			
				
				
				
				
				
				
				
				//String insert="INSERT INTO moviesplaying"
				
			}
			});
				
				//vbox.getChildren().add(new ImageView(moviedefault));
				//vbox.getChildren().add(MovieImage);
				//find out how to allow user to insert pic
				
				
				MovieCollect.setCenter(MoviesInfo);
				MovieCollect.setRight(vbox);
				
				Scene scene= new Scene(MovieCollect,500,500);
				newMovies.setScene(scene);
				newMovies.show();
				
				
				
			}
		});
		
		hbox.getChildren().add(addM);
		hbox.getChildren().add(MHistory);
		hbox.getChildren().add(removeM);
		
		Task.setTop(hbox);
		
		Scene scene = new Scene(Task,500,500);
		MTHomepage.setScene(scene);
		MTHomepage.show();
		}
		catch (Exception e){
			System.out.println(e);
		}
		
		
		
		
	}
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
		catch (Exception e){
			System.out.println(e);
		}
		return null;
	}
	
	
	
}
