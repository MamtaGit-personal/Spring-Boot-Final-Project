package dealer.customer.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dealer.customer.controller.model.DealerData;
import dealer.customer.controller.model.DealerData.DealerCustomer;
import dealer.customer.controller.model.DealerData.DealerVehicle;
import dealer.customer.dao.CustomerDao;
import dealer.customer.dao.DealerDao;
import dealer.customer.dao.VehicleDao;
import dealer.customer.entity.Customer;
import dealer.customer.entity.Dealer;
import dealer.customer.entity.Vehicle;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DealerCustomerService {
	
	@Autowired
	private DealerDao dealerDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private VehicleDao vehicleDao;
	
	/*************************************************************************
	 *                Dealer - Create, Delete, find or Update
	************************************************************************/
	
	/*************************************************************************
	 *                Create a Dealer
	************************************************************************/
	@Transactional(readOnly = false)
	public DealerData saveDealer(DealerData dealerData) {
		
		Dealer dealer = new Dealer();
		setFieldsInDealer(dealer, dealerData);
		return new DealerData(dealerDao.save(dealer));
	}
	
	/*******************************************************************
	 * setFieldsInDealer() method copies all the fields from 
	 * DealerData to Dealer's respective fields
	 *******************************************************************/
	private void setFieldsInDealer(Dealer dealer, DealerData dealerData) {
		dealer.setDealerId(dealerData.getDealerId());
		dealer.setDealerName(dealerData.getDealerName());
		dealer.setDealerState(dealerData.getDealerState());
		dealer.setDealerAddress(dealerData.getDealerAddress());
		dealer.setDealerCity(dealerData.getDealerCity());
		dealer.setDealerZip(dealerData.getDealerZip());
		dealer.setDealerPhone(dealerData.getDealerPhone());
	}
	
	/*******************************************************************
	 * updateDealer() - updates DealerData 
	 *******************************************************************/
	@Transactional(readOnly = false)
	public DealerData updateDealer(DealerData dealerData) {

		Long dealerId = dealerData.getDealerId();
		log.info("The dealerId = {}", dealerId); 
		
		Dealer dealer = findDealerById(dealerId);
		setFieldsInDealer(dealer, dealerData);
		return new DealerData(dealerDao.save(dealer));
	}
	
	/*******************************************************************
	 * findDealerById() -finds dealer for a given dealerId
	*******************************************************************/
	private Dealer findDealerById(Long dealerId) {
		
		return dealerDao.findById(dealerId)
			.orElseThrow( () -> new NoSuchElementException(
			"Dealer with ID=" + dealerId + " was NOT found."));
	}
	
	/*******************************************************************
	 * retrieveDealerById() method finds dealer for 
	 * the given dealerId
	*******************************************************************/
	@Transactional(readOnly = true)
	public DealerData retrieveDealerById(Long dealerId) {
		Dealer dealer = findDealerById(dealerId);
		DealerData dealerData = new DealerData(dealer);
		return dealerData;
	}
	
	/*******************************************************************
	 * Retrieves all Dealers
	*******************************************************************/
	@Transactional(readOnly = true)
	public List<DealerData> retrieveAllDealers() {
		List<Dealer> dealers = dealerDao.findAll();
		List<DealerData> result = new LinkedList<>();
		
		for(Dealer dealer : dealers) {
			result.add(new DealerData(dealer));
		}
		return result;
	}

	
	/*******************************************************************
	 * Delete a dealer for a given dealerId
	*******************************************************************/
	@Transactional(readOnly = false)
	public void deleteDealerById(Long dealerId) {
		Dealer dealer = findDealerById(dealerId);
		dealerDao.delete(dealer);
	}
	
	/*************************************************************************
	 *                 Customer - Create, Delete, find or Update
	************************************************************************/
	/*************************************************************************
	 *                 Create a Customer
	 ************************************************************************/
	@Transactional(readOnly = false)
	public DealerCustomer saveCustomer(Long dealerId, DealerCustomer dealerCustomer) {
		
		Dealer dealer = findDealerById(dealerId);
		
		Customer customer = new Customer();
		setFieldsInCustomer(customer, dealerCustomer);
		
		customer.getDealers().add(dealer);
		dealer.getCustomers().add(customer);
		
		Customer savedCustomer = customerDao.save(customer);
		return new DealerCustomer(savedCustomer);
	}
	
	/*************************************************************************/
	
	private void setFieldsInCustomer(Customer customer, DealerCustomer dealerCustomer) 
	{
		customer.setCustomerId(dealerCustomer.getCustomerId());
		customer.setCustomerFirstName(dealerCustomer.getCustomerFirstName());
		customer.setCustomerLastName(dealerCustomer.getCustomerLastName());
		customer.setCustomerEmail(dealerCustomer.getCustomerEmail());
		customer.setCustomerPhone(dealerCustomer.getCustomerPhone());
	}

	/*************************************************************************
	 *                Find a customer by Customer Id
	 ************************************************************************/
	@Transactional(readOnly = true)
	public DealerCustomer retrieveCustomerById(Long customerId) {
		
		Customer customer = findCustomerById(customerId);
		return new DealerCustomer(customer);
	}
	
	/*************************************************************************/
	private Customer findCustomerById(Long customerId) {
		
		return customerDao.findById(customerId)
				.orElseThrow( () -> new NoSuchElementException(
						"Customer with ID=" + customerId + " was NOT found."));
	}

	/*************************************************************************
	 *                Find all customers
	************************************************************************/
	@Transactional(readOnly = true)
	public List<DealerCustomer> retrieveAllCustomers() {
		
		List<Customer> customers = customerDao.findAll();
		List<DealerCustomer> result = new LinkedList<>();
		
		for(Customer customer : customers) {
			result.add(new DealerCustomer(customer));
		}
		return result;
		
	}
	
	
	/*********************************************************************
	 *                  Update a customer 
	*********************************************************************/
	
	@Transactional(readOnly = false)
	public DealerCustomer updateCustomerForGivenCustomerId(Long customerId,
			DealerCustomer dealerCustomer) {

		Customer updateCustomer = findCustomerById(customerId);
		setFieldsInCustomer(updateCustomer, dealerCustomer);
		
		/*******************************************************************/	
		List<Dealer> dealers = dealerDao.findAll();
		for(Dealer dealer : dealers) {
			Set<Customer> customers = dealer.getCustomers();
			for(Customer customer : customers) {
				if(customer.getCustomerId() == customerId) {
					setFieldsInCustomer(customer, dealerCustomer);
					customerDao.save(customer);
				}
			}
		} 
		
		/*******************************************************************/
		
		return new DealerCustomer(customerDao.save(updateCustomer));
	}
	
	/*********************************************************************
	 *                  Delete a customer - Need to Work
	*********************************************************************/
	@Transactional(readOnly = false)
	public void deleteCustomerByCustomerId(Long customerId) {
		Customer deleteCustomer = findCustomerById(customerId);
		
		/*********************  Need to Work below  ********************/
		List<Dealer> dealers = dealerDao.findAll();
		for(Dealer dealer : dealers) {
			Set<Customer> customers = dealer.getCustomers();
			for(Customer customer : customers) {
				if(customer.getCustomerId() == customerId) {
					customerDao.delete(customer);
				}
			}
		} 
		/*********************  Need to Work above  ********************/
		
		customerDao.delete(deleteCustomer);
	}

	/***********************************************************************
	 *                 Vehicle - Create, Delete, find or Update
	************************************************************************/
	
	/*************************************************************************
	 *                        Create a Vehicle
	 ************************************************************************/
	
	@Transactional(readOnly = false)
	public DealerVehicle saveVehicle(Long dealerId, DealerVehicle dealerVehicle) {
		
		Dealer dealer = findDealerById(dealerId);
		Vehicle vehicle = new Vehicle();
		
		setFieldsInVehicle(vehicle, dealerVehicle);
		
		vehicle.setDealer(dealer);
		dealer.getVehicles().add(vehicle);
		
		Vehicle savedVehicle = vehicleDao.save(vehicle);
		return new DealerVehicle(savedVehicle);
	}
	
	/************************************************************************/
	private void setFieldsInVehicle(Vehicle vehicle, DealerVehicle dealerVehicle) {
		vehicle.setVehicleId(dealerVehicle.getVehicleId());
		vehicle.setVehicleMake(dealerVehicle.getVehicleMake());
		vehicle.setVehicleModel(dealerVehicle.getVehicleModel());
		vehicle.setVehicleYear(dealerVehicle.getVehicleYear());
		vehicle.setVehicleExtColor(dealerVehicle.getVehicleExtColor());
		vehicle.setVehicleIntColor(dealerVehicle.getVehicleIntColor());
		
	}
	
	/***********************************************************************
	 *                        Update a Vehicle
	************************************************************************/
	
	@Transactional(readOnly = false)
	public DealerVehicle updateVehicleById(Long vehicleId, DealerVehicle dealerVehicle) {
		
		Vehicle updateVehicle = findVehiclerById(vehicleId);
		setFieldsInVehicle(updateVehicle, dealerVehicle);
		
		/*******************************************************/
		List<Dealer> dealers = dealerDao.findAll();
		for(Dealer dealer : dealers) {
			Set<Vehicle> vehicles = dealer.getVehicles();
			for(Vehicle vehicle : vehicles) {
				if(vehicle.getVehicleId() == vehicleId) {
					setFieldsInVehicle(vehicle, dealerVehicle);
					vehicleDao.save(vehicle);
				}
			}
		} 
		/********************************************************/
		return new DealerVehicle(updateVehicle);
	}
	
	/*********************************************************************/
	private Vehicle findVehiclerById(Long vehicleId) {
		return vehicleDao.findById(vehicleId)
				.orElseThrow( () -> new NoSuchElementException(
						"Vehicle with ID=" + vehicleId + " was NOT found."));
	}
	
	/*************************************************************************
	 *                        Retrieve a Vehicle by vehicleId
	************************************************************************/
	@Transactional(readOnly = true)
	public DealerVehicle retrieveVehicleById(Long vehicleId) {
		Vehicle vehicle = findVehiclerById(vehicleId);
		return new DealerVehicle(vehicle);
	}

	/*************************************************************************
	 *                        Retrieve all Vehicles 
	************************************************************************/
	@Transactional(readOnly = true)
	public List<DealerVehicle> retrieveAllVehicles() {
		
		List<Vehicle> vehicles = vehicleDao.findAll();
		List<DealerVehicle> result = new LinkedList<>();
		
		for(Vehicle vehicle : vehicles) {
			result.add(new DealerVehicle(vehicle));
		}
		return result;
	}

	/*************************************************************************
	 *                        Delete a Vehicle
	************************************************************************/
	@Transactional(readOnly = false)
	public void deleteVehicleIdByVehicleId(Long vehicleId) {
		Vehicle vehicle = findVehiclerById(vehicleId);
		vehicleDao.delete(vehicle);
		
	}

}
