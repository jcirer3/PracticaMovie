package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Person;
import com.esliceu.movie.models.ProductionCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionCompanyDAO extends JpaRepository<ProductionCompany, Integer> {
    Page<ProductionCompany> findAll(Pageable pageable);
    ProductionCompany findByCompanyName(String companyName);
}
