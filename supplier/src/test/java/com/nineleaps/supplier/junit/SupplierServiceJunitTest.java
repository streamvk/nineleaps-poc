package com.nineleaps.supplier.junit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nineleaps.supplier.model.Supplier;
import com.nineleaps.supplier.repository.SupplierRepository;
import com.nineleaps.supplier.service.SupplierService;


public class SupplierServiceJunitTest {
	
	@Mock
	private SupplierRepository repository;
	
	@InjectMocks
	private SupplierService supplierService;
	
	@Before 
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void createSupplierTest() {
		Supplier supplier = new Supplier(1, "Gal Gadot", "galgadot@gmail.com");
		supplierService.saveSupplier(supplier);
		verify(repository,times(1)).save(supplier);
		
	}
	
	@Test
	public void getSupplierByIdTest() {
		
		Supplier supplier = new Supplier(1, "Gal Gadot", "galgadot@gmail.com");
		Optional<Supplier> supOp= Optional.of(supplier);
		when(repository.findById(1)).thenReturn(supOp);
		Supplier sup= supplierService.findSupplierById(1);
		assertEquals("Gal Gadot", sup.getName());
	}
	
	@Test
	public void getAllSupplierTest() {
		
		List<Supplier> supplierList=new ArrayList<>();
		supplierList.add(new Supplier(1, "Gal Gadot", "galgadot@gmail.com"));
		supplierList.add(new Supplier(2, "Emma Jackson", "emma@gmail.com"));
		supplierList.add(new Supplier(3, "Jennifer Lawrance", "jennifer@gmail.com"));
		supplierList.add(new Supplier(4, "Dakota Jhonson", "dakota@gmail.com"));
		
		when(repository.findAll()).thenReturn(supplierList);
		
		List<Supplier> supList=supplierService.getAllSupplier();
		assertEquals(4, supList.size());
		verify(repository,times(1)).findAll();
	}
	
	@Test
	public void deleteSupplierByIdTest() {
		
		supplierService.deleteSupplierById(1);
		verify(repository,times(1)).deleteById(1);
	}
	
	@Test
	public void deleteSupplier() {
		Supplier supplier = new Supplier(1, "Gal Gadot", "galgadot@gmail.com");
		supplierService.deleteSupplier(supplier);
		verify(repository,times(1)).delete(supplier);
	}

}
