package com.expleo.project4;

import java.util.ArrayList;

public class Albums {
    private String id;
    private String title;

    public Albums(String id, String title) {
       setId(id);
       setTitle(title);
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
            throw new IllegalArgumentException("Id is empty");
        }

    }

    /**  Check if table exists in the chinook database and returns relevant message
     @param tablename The name used to search for table name
     */
    public static String assertTableExistAs(String tablename)
    {
        return AlbumDA.checkTableExistAs(tablename);
    }

    /**  Check if table exists in the chinook database and returns relevant message
     @param artist -artist object id is used find out if artist has albums
     */
    public static String assertArtistHasAlbums(Artist artist)
    {
        return AlbumDA.checkArtistHasAlbums(artist);
    }

    /**  Returns all albums belonging to a specific artist

     */
    public static ArrayList<Albums> getAllAlbums()
    {
        return AlbumDA.getAllAlbums();
    }

    /**  deletes all artist albums
     @param artistId -artist id is used to find out if the artists are deleted
     */
    public static ArrayList<String> deleteAlbums(String artistId)
    {
        return AlbumDA.deleteAllAlbums(artistId);
    }
    public String getTitle() {


        return title;
    }

    public void setTitle(String title) {
        if(title.length() > 0)
        {
            this.title = title;
        }else
        {
            throw new IllegalArgumentException("Title is empty");
        }

    }
}
