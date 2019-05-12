package com.expleo.project4;

import java.sql.*;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.Matchers.greaterThan;

/*
 *Albums data access class to store all albums methods related to chinnok sqlite artist table
 */
public class ArtistDA {

    private static Connection connection;
    private static PreparedStatement pstmt;
    private static String tableName;
    private static Artist objArtist;
    private static String artistName;
    private static int counter = 0;


    static String getTableName() {
        return tableName;
    }

     static Artist getObjArtist() {
        return objArtist;
    }

    /*
    *Check if table exists in the chinook database and returns relevant message

    *@param mytableName - is the table to be checked if it exist in the chinook database
    * return will return a message
     */
    static String checkTableExistAs(String myTableName)
    {

        String message = "";
        if(ChinookConnector.isExited() == false)
        {
            try
            {
                //get the connection from chinnok connector
                connection = ChinookConnector.getConnection();

                pstmt = connection.prepareStatement("SELECT count(*) as count FROM " +
                        "sqlite_master WHERE type='table' AND name =?");
                pstmt.setString(1, myTableName);
                ResultSet rsNumberOfTables = pstmt.executeQuery();
                int numberOfTables = -1;

                while (rsNumberOfTables.next()) {
                    numberOfTables = Integer.parseInt(rsNumberOfTables.getString("count"));
                }

                try {
                    //System.out.println("");
                    //assert if number of tables is greater than 0
                    assertThat( numberOfTables, greaterThan(0));
                    tableName = myTableName;
                    message = "\n"+tableName + " :table exist in the chinook database";
                } catch (AssertionError e) {

                    message = "\n"+"Invalid table name: " + myTableName + " :table does not exist in the chinook database!";
                    ChinookConnector.setExit(true);
                }


            }catch(SQLException ex)
            {
                message += "\nCould not connect"+ ex.getMessage();
                ChinookConnector.setExit(true);
            }
        }


        return message;


    }

    /*
     *Check if table exists in the chinook database and returns relevant message


     *@param artist - is the artist name vavue to be checked if it exist in the chinook database
     * return will return a message
     */
    static String checkArtistNameExistAs(String myArtistName)
    {

        String message = "";
        int numberOfRows = 0;
        String ArtistId ="";
        Artist objArtist;

       //check if exits from table name
        if(ChinookConnector.isExited() == false)
        {
            try
            {
                //get the connection from chinnok connector
                connection = ChinookConnector.getConnection();

                pstmt = connection.prepareStatement("SELECT * FROM " +getTableName()+
                        " WHERE Name =?");
                pstmt.setString(1, myArtistName);
                ResultSet rsNumberOfRows = pstmt.executeQuery();


                while (rsNumberOfRows.next()) {
                    //numberOfRows = Integer.parseInt(rsNumberOfRows.getString("count"));
                    ArtistId = rsNumberOfRows.getString("ArtistId");
                    numberOfRows++;

                }
   artistName = myArtistName;
                try {
                    //System.out.println("");
                    //assert if number of tables is greater than 0
                    assertThat( numberOfRows, greaterThan(0));
                    //tableName = myTableName;
                    //set the id and name object for later use
                    objArtist = new Artist(ArtistId, artistName);
                    setArtist(objArtist) ;
                    message += "\n"+artistName + " : artist name exist in the "+getTableName()+ " table";
                } catch (AssertionError e) {

                    message += "\n"+artistName + " : artist name does not exist in the "+getTableName()+ " table";
                    ChinookConnector.setExit(true);
                }catch (IllegalArgumentException ex)
                {
                    message += "\n"+ex.getMessage();
                }


            }catch(SQLException ex)
            {
                message += "\nCould not connect"+ ex.getMessage();
            }
        }


        return message;


    }

    static ArrayList<String> deleteArtist(Artist artist)
    {

        String message = "";
        int numberOfRows = 0;
        String ArtistId ="";
        ArrayList<String>arMessages = new ArrayList<>();

        //check if exits from table name
        if(ChinookConnector.isExited() == false)
        {
            try
            {
                //get the connection from chinnok connector
                connection = ChinookConnector.getConnection();



                    pstmt = connection.prepareStatement("DELETE FROM " +getTableName()+
                            " WHERE ArtistId =?");
                    pstmt.setString(1, artist.getId());
                    pstmt.executeUpdate();

                pstmt = connection.prepareStatement("SELECT * FROM " +getTableName()+
                        " WHERE ArtistId =?");
                pstmt.setString(1, artist.getId());
                ResultSet rsNumberOfRowsDeleted = pstmt.executeQuery();

                while (rsNumberOfRowsDeleted.next()) {

                    numberOfRows++;

                }


                try {
                    //System.out.println("");
                    //assert if number of tables is greater than 0
                    assertThat(numberOfRows, equalTo(0));
                    //tableName = myTableName;
                    //set the id and name object for later use

                        message = "Artist with Id:"+artist.getId()+" and name: "+artist.getName()+ "was deleted successfully";
                        arMessages.add(message);


                } catch (AssertionError e) {

                    message = "Artist with Id:"+artist.getId()+" and name: "+artist.getName()+ "was not deleted";
                    arMessages.add(message);
                    ChinookConnector.setExit(true);
                }catch (IllegalArgumentException ex)
                {
                    message = ex.getMessage()+"\n";
                    arMessages.add(message);
                    ChinookConnector.setExit(true);
                }


            }catch(SQLException ex)
            {
                message += "Could not connect"+ ex.getMessage()+"\n";
                arMessages.add(message);
                ChinookConnector.setExit(true);
            }
        }


        return arMessages;


    }


    private static void setArtist(Artist myobjArtist) {
        if(myobjArtist != null)
        {
          objArtist = myobjArtist;
        }else
        {
            System.out.println("IT IS NULL---------------------");
        }

    }




}
