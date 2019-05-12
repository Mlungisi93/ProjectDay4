package com.expleo.project4.StepDefs;

import com.expleo.project4.Steps.commentSteps;
import cucumber.api.java.en.*;
import net.thucydides.core.annotations.Steps;

public class commentStepDefs {
    //constant variables to initialise my program
 private final String DBNAME = "chinook";
 private final String TABLENAME = "artists";
 private final String ARTISTNAME = "AC/DC";
 //private final String ARTISTNAME = "Milton Nascimento & Bebeto"; // artist who has no albums
 private final String TABLENAME2 = "albums";
 private final String JSONDBNAME = "db";
 private final String URL = "http://localhost:3000";



    @Steps
    commentSteps RESTSSteps;

    @Given("^That chinook database artist name with albums and Blog exist$")
    public void that_chinook_database_artist_name_with_albums_and_Blog_exist() {

         //First Check if chinook.db exist
      RESTSSteps.checkAllInformation(DBNAME, TABLENAME, ARTISTNAME,TABLENAME2, JSONDBNAME,URL);

    }

    @When("^I transfer artist name and album titles to blog as comment$")
    public void i_transfer_artist_name_and_album_titles_to_blog_as_comment(){
        // Write code here that turns the phrase above into concrete actions
       RESTSSteps.transferArtistNameAndAlbumTitle();
    }

    @Then("^Blog comment should be added successfully$")
    public void blog_comment_should_be_added_successfully(){
        // Write code here that turns the phrase above into concrete actions
        RESTSSteps.verifyReturnCodeAS(201);

    }

    @Then("^artist and related albums should be deleted$")
    public void artist_and_related_albums_should_be_deleted()  {
        // Write code here that turns the phrase above into concrete actions
RESTSSteps.deleteAllAlbumsAndArtist();

    }
}
