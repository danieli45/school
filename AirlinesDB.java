import java.sql.*;
import java.util.Scanner;

public class Project2DB {

	// Daniel Isaac Chavez
	// Dr. Blythe
	//November 30th 2018
	
	//----------------------------------------------------------------------------------------------------------------
	
	   //  Database credentials
	   static final String USER = "username";
	   static final String PASS = "password";
	   public static void main(String[] args) 
	   {
		   Scanner kbd = new Scanner(System.in);
		   boolean quit = false;
		   
		    Connection conn; // the actual mysql server connection
		    Statement stmt;
		    ResultSet rs;
		    //Connection conn = null;
		    //Statement stmt = null;
		   
	   		// strings for mysql hostname, userid, ...
			String db_host;
			String db_user;
			String db_password;
			String db_name;
		   
			// get user credentials and mysql server info
			System.out.print("Enter MySQL database hostname (or IP adress):");
			db_host = kbd.nextLine();

			System.out.print("Enter MySQL database username:");
			db_user = kbd.nextLine();

			System.out.print("Enter MySQL database password:");
			db_password=kbd.nextLine(); 

			// could also prompt for this, if desired
			db_name=db_user;
			
			db_host = "jdbc:mysql://" + db_host+ "/" + db_name;
			
		   // most mysql calls can cause exceptions,so we'll try to catch them. 
		   try
		   {
			   // set up the client (this machine) to use mysql
			   System.out.print("Initializing client DB subsystem ...");
			   Class.forName("com.mysql.jdbc.Driver");
			   System.out.println("Done.");
			   			   
			   // go out and connect to the mysql server
			   System.out.print("Connecting to remote DB ...");
			   conn = DriverManager.getConnection(db_host,db_user, db_password);
			   System.out.println("DB connection established.");
			   
			   while(!quit)
			   {
			   stmt = conn.createStatement();
               Scanner scn = new Scanner(System.in);
               System.out.println("Enter a command: ");
               String first = scn.next();
               
               if(first.equals("a") || first.equals("A"))
               {
            	   String second = scn.next(); // Get the user second command
        		   
            	   switch(second)
            	   {
            	   case "c": 
            		   String cCode = scn.next();
            		   String cName = scn.next();
            		   
            		   //Creates a table if it doesn't exist and prompt the user for the information.
            		   stmt = conn.createStatement();  
            		   String createCityTable = "Create Table if not exists City_T(CityCode varChar(3) NOT NULL, CityName Char(25) NOT NULL, PRIMARY KEY(CityCode))";
            		   stmt.executeUpdate(createCityTable);
            		   //Inserts City Code and City Name into City_T"
            		   String addCity = "INSERT INTO City_T(CityCode,CityName) \n" +
            		                    "VALUES(";
            		   addCity += "\"" + cCode + "\",";
            		   addCity += "\"" + cName + "\");";
            		   try {
            		   stmt.executeUpdate(addCity);
            		   } catch(SQLException e) {
            			   System.out.println("somethnig wrong with exetuion of query");
            		   }
            		   System.out.println("Done");
            		   break;
            		   
            	   case "a":
            		    
            		   String aCode = scn.next();
            		   String aName = scn.nextLine();
            		   //Creates a table if it doesn't exist and prompt the user for the information.
            		   stmt = conn.createStatement(); 
            		   String createAirlinesTable = "Create Table if not exists Airlines_T(AirlineCode Char(3) NOT NULL, AirlineName Char(20) NOT NULL, PRIMARY KEY(AirlineCode))";
            		   stmt.executeUpdate(createAirlinesTable);
            		  
            		   //Insert Airline Code and Airline Name into Airline_T
            		    String addAirline = "INSERT INTO Airlines_T(AirlineCode,AirlineName) " +
            		                        "VALUES(";
    		            addAirline += "\"" + aCode + "\",";
    		            addAirline += "\"" + aName + "\")";

            		    stmt.executeUpdate(addAirline);
            		    System.out.println("Done");
            		   break;
            		   
            	   case "f": 
            		   
            		   String airCode = scn.next();
            		   String depCity = scn.next();
            		   String desCity = scn.next();
            		   int Cost = scn.nextInt();
            		   
            		   //Creates a table if it doesn't exist and prompt the user for the information.	   
            		   stmt = conn.createStatement(); 
            		   String createFlightsTable = "Create Table if not exists Flights_T(AirlineCode Char(3) NOT NULL, DepCityCode Char(3) NOT NULL, DesCityCode Char(3) NOT NULL, Cost INT, PRIMARY KEY(AirlineCode,DepCityCode,DesCityCode), FOREIGN KEY (AirlineCode) REFERENCES Airlines_T(AirlineCode), FOREIGN KEY (DepCityCode) REFERENCES City_T(CityCode), FOREIGN KEY (DesCityCode) REFERENCES City_T(CityCode))";
            		   stmt.executeUpdate(createFlightsTable);
            		             		   
            		  try
            		  { 
               		   //Inserts flight information into the Flight table    
            			  
            		   String addFlight = "INSERT INTO Flights_T(AirlineCode, DepCityCode, DesCityCode, Cost)" +
		                                   " VALUES(";
            		   addFlight += "\"" + airCode + "\" ,";
   		               addFlight += "\"" + depCity + "\" ,";
   		               addFlight += "\"" + desCity + "\" ,";
   		               addFlight += "\"" + Cost + "\" )";
        		       stmt.executeUpdate(addFlight);
        		       System.out.println("Done");
            		  }
            		  catch(SQLException se)
            		  {
                         //IF flight already exists updates the Cost to the new Cost provided.        			
            			  stmt = conn.createStatement(); 

            			  String updateFlight = "Update Flights_T "
            			  		                 + "SET Cost = ";
   		                  updateFlight += "\"" + Cost + "\""
   		                  		+ " WHERE AirlineCode = ";
   		                  updateFlight += "\"" + airCode + "\""
   		                  		+ " AND depCityCode = ";
   		                  updateFlight += "\"" + depCity + "\""
   		                  		+ " AND desCityCode = ";
   		                  updateFlight += "\"" + desCity + "\"";
   		                  stmt.executeUpdate(updateFlight);
              		      System.out.println("Done");
            		  }
            		   break;
            	   default:
            			   System.out.println("You insert a wrong command (" + second +") is not a command available"); 
            		   break;
            	   }  
               }
               else if(first.equals("l") || first.equals("L"))
               {
            	   String second = scn.next();
            	   switch(second)
            	   {
            	   case "c":
            		   //List all cities in the table
            		   stmt = conn.createStatement();
            		   String ListCities = "SELECT * FROM City_T ORDER BY CityCode";
            		   System.out.print("Sending Query: ");
   		               System.out.println(ListCities);	
   		               rs = stmt.executeQuery(ListCities);
   		               
   		               while(rs.next())
   		     	        {	     		   
   		     		       // display requested attributes
   		     		       System.out.println(rs.getString("CityCode")+ " " + rs.getString("CityName"));
   		     	        }
            		   break;
            		   
            	   case "a":            		 
            		   //List all airlines on the table  
            		   stmt = conn.createStatement();
            		   String ListAirlines = "SELECT * FROM Airlines_T ORDER BY AirlineCode";	
           		       rs = stmt.executeQuery(ListAirlines);
           		       
           		       while(rs.next())
		     	        {	     		   
		     		       // display requested attributes
		     		       System.out.println(rs.getString("AirlineCode")+ " " + rs.getString("AirlineName"));
		     	        }
            		   break;
            		   
            	   case "f":
            		   
            		   //List the flights available
            		   
            		   stmt = conn.createStatement();
            		   String ViewFlights = "Create VIEW if not exists List As Select * From City_T, Flights_T WHERE City_T.CityCode = DesCityCode";
           		       stmt.execute(ViewFlights);
           		       stmt.close();   
           		       
           		       stmt = conn.createStatement();
           		       String ListFlights = "SELECT AirlineName, DepCityCode, DesCityCode, Cost FROM List,Airlines_T "
           		    		   + " WHERE List.AirlineCode = Airlines_T.AirlineCode"
           		       		   + " ORDER BY DepCityCode ASC, DesCityCode ASC, Cost ASC, AirlineName ASC";   
           		       rs = stmt.executeQuery(ListFlights);
           		       
           		       while(rs.next())
		     	        {	     		   
		     		       // display requested attributes
		     		       System.out.println(rs.getString("AirlineName")+ ": " + rs.getString("DepCityCode") + " --> " + rs.getString("DesCityCode") + " $" + rs.getInt("Cost"));
		     	        }
            		   break;
            	   default:
            			System.out.println("You insert a wrong command (" + second +") is not a command available"); 
            	   }
               }
               
               else if(first.equals("f") || first.equals("F"))
               {
            	  //Find the flight according to the information the user provides. 

        		   String depCity = scn.next();
            	   String desCity = scn.next();
        		   String connection = scn.next();
        		      		  
        		   if(connection.equals("1"))
        		   {
        			   //Print flights with 1 connection
            		   stmt = conn.createStatement();
            		  
            		   String viewArriveFlight = "Create View if not exists arrival As  Select AirlineCode As aircode, depCityCode as dep, desCityCode As des, Cost as price From Flights_T;";
            		   stmt.execute(viewArriveFlight);
            		   stmt.close();
            		   
            		   //SELECT * FROM Flights_T, Arrivals WHERE Flights_T.depCityCode =  "hou" AND Arrivals.DesCityCode = "chi" AND Arrivals.depCityCode = Flights_t.desCityCode;

            		      stmt = conn.createStatement();
            		      String findFlight = "SELECT * FROM Flights_T, arrival"; 
            		      findFlight += " WHERE Flights_T.depCityCode = ";
		                  findFlight += "\"" + depCity + "\""
		                  		+ " AND Arrival.des = ";
		                  findFlight += "\"" + desCity + "\""
		                  + " AND Arrival.dep =  Flights_T.desCityCode";
		                   //findFlight += " ORDER BY Cost";
            		       rs = stmt.executeQuery(findFlight);
           		       
           		           while(rs.next())
		     	        {	     		   
		     		       // display requested attributes
		     		       System.out.print(rs.getString("DepCityCode")+ " --> " + rs.getString("DesCityCode")+ " : " + rs.getString("AirlineCode") + " $" + rs.getInt("Cost") + "; ");
		     	           System.out.print(rs.getString("dep") + " --> " + rs.getString("des")+ " : " + rs.getString("aircode") + " $" + rs.getInt("price"));
		     	            int cost1 = rs.getInt("Cost");
		     	           int price1 = rs.getInt("price");
                           double total = cost1 + price1;
                           System.out.println(", for a total cost of $" + total);
		     	        }     
           		   }
        		        
        		   //Prints flights with no connections
        		   else if(connection.equals("0"))
        		    {
        			  
            		   stmt = conn.createStatement();
            		   
            		   
            		     //Create VIEW if not exists finder as Select DepCityCode, DesCityCode FROM FLIGHTS_T; 
            		   
            		   String findFlight = "SELECT DepCityCode, DesCityCode,AirlineCode,Cost FROM Flights_T";
            		              findFlight += " WHERE depCityCode = ";
        		                  findFlight += "\"" + depCity + "\""
        		                  		+ " AND desCityCode = ";
        		                  findFlight += "\"" + desCity + "\"";
        		                  findFlight += " ORDER BY Cost";
    		               
            		       rs = stmt.executeQuery(findFlight);
 
           		           while(rs.next())
		     	          {	     		   
		     		       // display requested attributes
		     		       System.out.println(rs.getString("DepCityCode")+ " --> " + rs.getString("DesCityCode") + " : " + rs.getString("AirlineCode") + " $" + rs.getInt("Cost"));
		     		       
		     	          }
           		           System.out.println("No more flights found");
        		   }
			   
        		   else
        		   {
        			   System.out.println("Wrong Input for Connections!!");
        			   System.out.println("Try (1) or (0)");
        		   }
            	   
               }
               else if(first.equals("d") || first.equals("D"))
               {
    
            	   String airCode = scn.next();
        		   String depCity = scn.next();
        		   String desCity = scn.next();
        		   
              	   //Deletes the information on the FLights_T provided by User
            	   
            	   stmt = conn.createStatement();
            	   scn = new Scanner(System.in);

        		   String deleteFlight = "DELETE FROM Flights_T " +
        		                          "WHERE Flights_T.AirlineCode = ";
        		                          deleteFlight += "\"" + airCode + "\" ";
        		                          deleteFlight += "AND Flights_T.DepCityCode = ";
        		                          deleteFlight += "\"" + depCity + "\" ";
        		                          deleteFlight += "AND Flights_T.DesCityCode = ";
        		                          deleteFlight +="\""+ desCity + "\"";
        	         stmt.executeUpdate(deleteFlight);   
        	         System.out.println("Done");		                                              
               }         	   
        	   else if(first.equals("Q") || first.equals("q"))
        	   {
        		   //Quits the program
        		   quit = true;
        		   System.out.println("BYE!");
        		   System.exit(0);    		   
               }
               else
               {
            	   //Alerts the user that the command he chose is not available
            	   System.out.println("You insert a wrong command (" + first +") is not a command available"); 
               }         	   
	     	   stmt.close();   
		   }
			 //closes db connection   
			   conn.close();
		   } // close While Loop
		   
		   catch(SQLException se)
		   {
			   System.out.println("WARNING! INPUT ERROR!");
			   System.out.println("Duplicated Value! Please try a different Input!"); 
			   //Handle errors for JDBC
			   se.printStackTrace();
		   }
		   catch(Exception e)
		   {
			   // Handle any other exceptions
			   System.out.println("Exception"+e);
		   }
	   }
}