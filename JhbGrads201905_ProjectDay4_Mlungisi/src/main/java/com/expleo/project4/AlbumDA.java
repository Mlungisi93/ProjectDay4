package com.expleo.project4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.expleo.project4.ArtistDA.getTableName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

/*
*Albums data access class to store all albums methods related to chinnok sqlite albums table
 */
public class AlbumDA {

    private static Connection connection;
    private static PreparedStatement pstmt;
    private static String tableName;

    private static ArrayList<Albums> arArtistAlbums = new ArrayList<>();
    public static String getTableName() {
        return tableName;
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
     *Check if artist has albums in the in the chinook database and returns relevant message

     *@param artist - is the artist object to be checked if artist has albums in the chinook database
     * return will return a message
     */
    static String checkArtistHasAlbums(Artist artist)
    {
         Albums objAlbums;
        String message = "";
        int numberOfRows = 0;
        String ArtistId ="";

        //check if exits from table name
        if(ChinookConnector.isExited() == false)
        {
            try
            {
                //get the connection from chinnok connector
                connection = ChinookConnector.getConnection();

                pstmt = connection.prepareStatement("SELECT * FROM " +getTableName()+
                        " WHERE ArtistId =?");
                pstmt.setString(1, artist.getId());
                ResultSet rsNumberOfRows = pstmt.executeQuery();


                while (rsNumberOfRows.next()) {

                    numberOfRows++;

                }


                try {
                    //System.out.println("");
                    //assert if number of tables is greater than 0
                    assertThat(numberOfRows, greaterThan(0));
                    //tableName = myTableName;
                    //set the id and name object for later use
                    message += artist.getName() +String.format(" artist has %d album%s", numberOfRows, numberOfRows !=1 ? "s": "");

                     rsNumberOfRows = pstmt.executeQuery();
                    while (rsNumberOfRows.next()) {
                        objAlbums = new Albums(rsNumberOfRows.getString("AlbumId"), rsNumberOfRows.getString("Title"));
                     arArtistAlbums.add(objAlbums);

                    }

                } catch (AssertionError e) {

                    message += "No Albums found for "+artist.getName()+" artist";
                    ChinookConnector.setExit(true);
                }catch (IllegalArgumentException ex)
                {
                    message += "\n"+ex.getMessage();
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

    static ArrayList<String> deleteAllAlbums(String artistId)
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


                for (int i = 0; i < arArtistAlbums.size() ; i++) {

                    pstmt = connection.prepareStatement("DELETE FROM " +getTableName()+
                            " WHERE AlbumId =?");
                    pstmt.setString(1, arArtistAlbums.get(i).getId());
                    pstmt.executeUpdate();

                }



                    pstmt = connection.prepareStatement("SELECT * FROM " +getTableName()+
                            " WHERE ArtistId =?");
                    pstmt.setString(1, artistId);
                    ResultSet rsNumberOfRows = pstmt.executeQuery();



                while (rsNumberOfRows.next()) {

                    numberOfRows++;

                }


                try {
                    //System.out.println("");
                    //assert if number of tables is greater than 0
                    assertThat(numberOfRows, equalTo(0));
                    //tableName = myTableName;
                    //set the id and name object for later use
                    for (int i = 0; i < arArtistAlbums.size(); i++) {
                        message = "Album with Id:"+arArtistAlbums.get(i).getId()+" and title: "+arArtistAlbums.get(i).getTitle()+ " was deleted";
                       arMessages.add(message);
                    }


                } catch (AssertionError e) {

                    for (int i = 0; i < arArtistAlbums.size(); i++) {
                        message = "Album with Id:"+arArtistAlbums.get(i).getId()+" and title: "+arArtistAlbums.get(i).getTitle()+ " was not deleted";
                        arMessages.add(message);

                    }
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

    public static ArrayList<Albums> getAllAlbums() {
        return arArtistAlbums;
    }
}
