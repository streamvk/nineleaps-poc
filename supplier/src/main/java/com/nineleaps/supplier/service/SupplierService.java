package com.nineleaps.supplier.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nineleaps.supplier.exception.SupplierNotFoundException;
import com.nineleaps.supplier.model.Supplier;
import com.nineleaps.supplier.repository.SupplierRepository;

@Service
public class SupplierService {
	

	@Autowired
	private SupplierRepository repository;
	
	
	public void saveSupplier(Supplier supplier) {
		repository.save(supplier);
	}
	
	public Supplier findSupplierById(int id) {
		
		Optional<Supplier> sup=repository.findById(id);
		if(sup.isPresent())
		return sup.get();
		else
			throw new SupplierNotFoundException("Supplier not found by id.");
	}
	
	public void updateSupplierDetails(Supplier supplier) {
		repository.save(supplier);
	}
	
	public void deleteSupplierById(int id) {
		repository.deleteById(id);
	}
	
	public void deleteSupplier(Supplier supplier) {
		repository.delete(supplier);
	}
	
	public List<Supplier> getAllSupplier(){
		List<Supplier> list=repository.findAll();
		if(list != null & !list.isEmpty())
			return list;
		else
			throw new SupplierNotFoundException("There is no supplier.");
	}
	
	public boolean isSupplierExists(int id) {
		Optional<Supplier> sup=repository.findById(id);
		if(sup.isPresent())
			return true;
		else
			return false;
	}

}
