package br.com.dbc.data.analysis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dbc.data.analysis.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>{

}
