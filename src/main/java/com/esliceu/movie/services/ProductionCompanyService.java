package com.esliceu.movie.services;

import com.esliceu.movie.DAO.ProductionCompanyDAO;
import com.esliceu.movie.models.ProductionCompany;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductionCompanyService {
    @Autowired
    ProductionCompanyDAO productionCompanyDAO;

    public Page<ProductionCompany> getPaginatedCompanys(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productionCompanyDAO.findAll(pageable);
    }

    public String saveCompany(String companyName) {
        ProductionCompany existingCompany = productionCompanyDAO.findByCompanyName(companyName);

        if (existingCompany != null) {
            return "El nombre ya está en uso. Por favor, elija otro.";
        } else {
            // Si no existe, guardar la nueva company
            ProductionCompany productionCompany = new ProductionCompany();
            productionCompany.setCompanyName(companyName);
            productionCompanyDAO.save(productionCompany);
            return null;
        }
    }

    public void deleteCompany(Integer companyId) {
        ProductionCompany productionCompany = productionCompanyDAO.findById(companyId).get();
        if (productionCompany != null) {
            productionCompanyDAO.delete(productionCompany);
        }
    }

    public ProductionCompany getCompanyById(Integer companyId) {
        return productionCompanyDAO.findById(companyId).get();
    }

    public String updateCompanyNameById(Integer companyId, String companyName) {
        ProductionCompany existingCompany = productionCompanyDAO.findByCompanyName(companyName);
        if (existingCompany != null){
            return "El nombre ya está en uso. Por favor, elija otro.";
        }
        Optional<ProductionCompany> optionalProductionCompany = productionCompanyDAO.findById(companyId);

        ProductionCompany productionCompany = optionalProductionCompany.get();
        productionCompany.setCompanyName(companyName);
        productionCompanyDAO.save(productionCompany);

        return null;
    }

    public String getCompanyJson() {
        List<ProductionCompany> companys = productionCompanyDAO.findAll();
        List<String> names = companys.stream()
                .map(c -> c.getCompanyName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public List<ProductionCompany> findByName(String companyName) {
        List<ProductionCompany> companyList = new ArrayList<>();
        ProductionCompany productionCompany = productionCompanyDAO.findByCompanyName(companyName);
        companyList.add(productionCompany);
        return companyList;
    }
}
