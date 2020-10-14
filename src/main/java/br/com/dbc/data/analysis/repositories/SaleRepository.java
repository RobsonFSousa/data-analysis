package br.com.dbc.data.analysis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dbc.data.analysis.models.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long>{

}
