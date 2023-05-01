package fys.fyspersistance;


import fys.fysmodel.Identifiable;
import fys.fyspersistance.exceptions.RepositoryException;

import java.io.Serializable;

public interface Repository<ID extends Serializable, E extends Identifiable<ID>> {

    /**
     * Method to find an entity in the repository by id.
     * @param id the id of the entity
     * @return the entity with the given id
     * @throws RepositoryException if the entity with the given id does not exist
     */
    E findById(ID id);

    /**
     * Method to get all entities from the repository.
     * @return a list of all entities
     */
    Iterable<E> findAll();

    /**
     * Method to add an entity to the repository.
     * @param entity the entity to be added
     * @return a list of all entities
     * @throws RepositoryException if the entity already exists
     */
    void add(E entity);

    /**
     * Method to remove an entity from the repository.
     * @param id the id of the entity to be removed
     * @throws RepositoryException if the entity does not exist
     */
    void remove(ID id);

    void remove(E entity);

    /**
     * Method to get all entities from the repository.
     * @return a list of all entities
     * @throws RepositoryException if the entity does not exist
     */
    void modify(E entity);
}
