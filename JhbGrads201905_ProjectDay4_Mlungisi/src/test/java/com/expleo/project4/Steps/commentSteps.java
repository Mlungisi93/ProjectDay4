package com.expleo.project4.Steps;

import com.expleo.project4.*;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;


public class commentSteps {
    private JsonObject jsonObject;
    private RequestSpecification request;
    private String  url;
    private Response response; //ws
    ArrayList<Albums> arAlbums;
    ArrayList<String> arMessage =new ArrayList<>();
    ArrayList<String> arComments = new ArrayList<>();



    public void verifyReturnCodeAS(int returnCode) {
        String message="";
     if(ChinookConnector.isExited() == false)
     {
         request.then().statusCode(returnCode);
         //System.out.println("verifyReturnCode: StatusCode="+response.getStatusCode());
         try {
             assertThat(response.getStatusCode(), equalTo(returnCode));
             message = "Blog comments added successfully";

         }catch (AssertionError ex)
         {
             message = "Failed to add Blog comments";
         }

     }

     if(message.length() > 0)
     {
         reportMessage(message);
     }

    }


    /*
     *Call This method from ChinookConnector class and
     * @param databaseName - pass the database name
     * return a relevant message
     */

    public void checkAllInformation(String databaseName,String tableName, String artistname, String table2Name, String jsonDBName, String url) {

        checkAllInformationHigher(databaseName, tableName, artistname, table2Name,jsonDBName,url);

            }

    @Step("{0}")
    public void reportMessage(String message)
    {

    }

    public void checkAllInformationHigher(String databaseName,String tableName, String artistname, String table2Name, String jsonDBName, String url) {

        /*
         *Call This method from ChinookConnector class
         * return a relevant message
         */
        String message = ChinookConnector.checkChinookFileExist(databaseName); // check if chinook.db exist

        reportMessage(message);
        message = ChinookConnector.assertConnectionToChinook();
        if (message.length() > 0) { //check if connection to chinook is successful
            reportMessage(message);
            //message +=ChinookConnector.assertConnectionToChinook();
            /*
             *Call This method from Artist class and
             * @param tableName - pass the table name
             * return a message to say does it exist or not
             */
            message = JSONDBConnector.checkJSONDBFileExist(jsonDBName);// TODO check if db.json exits
            if (message.length() > 0) {
                reportMessage(message);
                 message = JSONDBConnector.CheckForJSONConnection(url); // check url
                if(message.length() > 0)
                {
                    reportMessage(message);
                    message = Artist.assertTableExistAs(tableName); //check if  artist table Name exist
                    if (message.length() > 0) {
                        //message +=Artist.assertTableExistAs(tableName);
                        reportMessage(message);
                        message = Artist.assertArtistNameAs(artistname);//check if  artist name exist
                        if (message.length() > 0) {
                            reportMessage(message);


                            message = Albums.assertTableExistAs(table2Name); //check if albums table exist in the chinook database

                            if (message.length() > 0) {
                                reportMessage(message);
                            }
                            message = Albums.assertArtistHasAlbums(Artist.getObjArtist());//check if artist name has albums
                            if (message.length() > 0) {
                                reportMessage(message);

                            }

                            // TODO check if connection to db.json is started
                        }
                    }
                }




            }


        }
    }




    /*
     *Call This method from Artist class and
     * @param artistName - pass the table name
     * return a message to say does it exist or not
     */
    public String checkArtistExistAs(String artistname) {
        return Artist.assertArtistNameAs(artistname);
    }

    public void transferArtistNameAndAlbumTitle() {
         String message;
         submitNewComment();
        for (int i = 0; i < arMessage.size(); i++) {
            message = arMessage.get(i);
            if(message.length() > 0)
            {
                reportMessage(message);
            }

        }

    }


    public void submitNewComment() {
        jsonObject = JSONDBConnector.getJsonObject();
        request =JSONDBConnector.getRequest();
        url = JSONDBConnector.getUrl();
        String message ="";
        arAlbums = Albums.getAllAlbums();
        Artist myArtist = Artist.getObjArtist();
        String comment ="";
        if(ChinookConnector.isExited() == false)
        {

            for(int i = 0; i < arAlbums.size(); i++)
            {
                comment = myArtist.getName() +" - "+arAlbums.get(i).getTitle();
                try {
                    assertThat(comment.length(), greaterThan(0));
                      arComments.add(comment);
                    jsonObject.addProperty("body", comment);
                    jsonObject.addProperty("postId", "1");
                    request = request.body(jsonObject).when();
                    response = request.post(url + "/comments");
                    message = " "+ comment+ " transfered to Blog successfully \n";
                    arMessage.add(message);
                }catch (AssertionError ex)
                {
                    message = comment+ " did not transfer successfully";
                    ChinookConnector.setExit(true);
                }


            }
        }

    }


    public void deleteAllAlbumsAndArtist() {
        if (ChinookConnector.isExited() == false) {
            arMessage = new ArrayList<>();
            Artist myArtist = Artist.getObjArtist();
            arMessage = Albums.deleteAlbums(myArtist.getId());
       ArrayList<String>arArtist =new ArrayList<>();
       arArtist = Artist.deleteArtist(myArtist);
       if(arArtist.size() > 0)
       {
           arMessage.add(arArtist.get(0));
       }

            for (int i = 0; i < arMessage.size(); i++) {

                reportMessage(arMessage.get(i));
            }

            }


        }


    }

