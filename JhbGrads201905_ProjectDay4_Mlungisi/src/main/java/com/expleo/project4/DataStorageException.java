/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.expleo.project4;

/**
 * Raised when problems occurred with the data storage
 * @author S Ribeiro
 */
public class DataStorageException extends Exception
{

    /**
     * Creates a new instance of <code>DataStorageException</code> without
     * detail message.
     */
    public DataStorageException()
    {
    }

    /**
     * Constructs an instance of <code>DataStorageException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DataStorageException(String msg)
    {
        super(msg);
    }
}
