package br.com.dbc.data.analysis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dbc.data.analysis.models.Salesman;

public interface SalesmanRepository extends JpaRepository<Salesman, String>{

}
