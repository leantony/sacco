/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacco.classes;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Antony
 * @param <E>
 */
public interface DatabaseInterface<E> extends AutoCloseable {

    boolean Update(E value) throws SQLException;

    boolean Delete(long ID) throws SQLException;

    List<E> select() throws SQLException;

    List<E> select(long ID) throws SQLException;

    long Add(E value) throws SQLException;
}
