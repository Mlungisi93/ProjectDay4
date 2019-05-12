/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.expleo.project4;

/**
 * Raised when the data contains errors
 * @author S Ribeiro
 */
public class DataException extends Exception
{

    /**
     * Creates a new instance of <code>DataException</code> without detail
     * message.
     */
    public DataException()
    {
    }

    /**
     * Constructs an instance of <code>DataException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public DataException(String msg)
    {
        super(msg);
    }
}
