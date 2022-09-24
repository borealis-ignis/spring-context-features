package org.borealis.scf.service;

import org.borealis.scf.exception.CityException;
import org.borealis.scf.exception.CityNotFoundException;

import java.util.List;

/**
 * @author Sergey Kastalski
 */
public interface CrudService<T, E extends Throwable> {

	T read(long id) throws E, CityException;

	List<T> readAll() throws E;

	T create(T t) throws E;

	T update(T t) throws E, CityNotFoundException;

	boolean delete(long id) throws E;

}