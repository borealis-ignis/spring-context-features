package org.borealis.scf.service;

/**
 * @author Sergey Kastalski
 */
public interface EntityConverter<T, D> {

	T convertDbo(D dbo);

	D convertDto(T dto);

}
