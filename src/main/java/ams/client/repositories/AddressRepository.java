package ams.client.repositories;

import ams.client.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends CrudRepository<Address, UUID> {

    List <Address> findAddressByzipCode(String zipCode);
}
