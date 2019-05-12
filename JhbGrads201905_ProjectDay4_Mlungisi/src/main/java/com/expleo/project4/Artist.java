package com.expleo.project4;

import java.util.ArrayList;

public class Artist {
    private String name;
    private String id;

    public Artist(String id, String name) {
        setName(name);
        setId(id);
    }



    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        if(name.length() > 0)
        {
            this.name = name;

        }else
        {
            throw new IllegalArgumentException(" artist name is empty");

        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(id.length() > 0)
        {
            this.id = id;
        }else
        {
            throw new IllegalArgumentException(" artist id is empty");

        }

    }

    //Invoking the ArtistDA methods:

    /**  Check if table exists in the chinook database and returns relevant message
     @param tablename The name used to search for table name
     */
    public static String assertTableExistAs(String tablename)
    {
        return ArtistDA.checkTableExistAs(tablename);
    }

    /**  Check if artist name exists in the chinook database and returns relevant message
     @param name The artist name used to search for artist name in the artist table
     */
    public static String assertArtistNameAs(String name)
    {
        return ArtistDA.checkArtistNameExistAs(name);
    }

    /**  return the artist object with Id
     */
    public static Artist getObjArtist()
    {
        return ArtistDA.getObjArtist();
    }

    /**  deletes all artist albums
     @param artist -artist object- id is used to find out if the artists are deleted
     */
    public static ArrayList<String> deleteArtist(Artist artist)
    {

        return ArtistDA.deleteArtist(artist);
    }
}
