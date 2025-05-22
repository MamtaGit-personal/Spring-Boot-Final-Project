package dealer.customer.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dealer.customer.controller.model.DealerData;
import dealer.customer.controller.model.DealerData.DealerCustomer;
import dealer.customer.controller.model.DealerData.DealerVehicle;
import dealer.customer.service.DealerCustomerService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dealership")
@Slf4j
public class DealerCustomerController {
	
	@Autowired
	private DealerCustomerService dealerCustomerService;
	
	/*************************************************************************
	 *                 Dealer Table - Create, Delete, find or Update
	 ************************************************************************/
	
	/**************************************************************************
	 *                   Create a dealer. All the fields of dealer
	 *                   is provided by the user
    **************************************************************************/
	@PostMapping("/dealer")//resource name
	@ResponseStatus(code = HttpStatus.CREATED)
	public DealerData createDealer(@RequestBody DealerData dealerData) {
		log.info("Creating and then saving Dealer data {}", dealerData);
		return dealerCustomerService.saveDealer(dealerData);
	}
	
	/**************************************************************************
	 *                   Update a dealer. All the fields of dealer
	 *                   is provided by the user along with dealer id.
    **************************************************************************/
	@PutMapping("/dealer/{dealerId}")
	public DealerData updateDealerForGivenDealerId(@PathVariable Long dealerId, @RequestBody DealerData dealerData) {
		
		dealerData.setDealerId(dealerId);
		log.info("Updating Dealer data {}", dealerData);
		return dealerCustomerService.updateDealer(dealerData);
	}
	
	/**************************************************************************
	 *                   Find a dealer for a given dealer Id.
    **************************************************************************/
	@GetMapping("/dealer/{dealerId}")
	public DealerData retrieveDealerForGivenDealerId(@PathVariable Long dealerId) {
		
		log.info("Retrieving dealer data for given dealerId = {}", dealerId);
		return dealerCustomerService.retrieveDealerById(dealerId);
	}
	
	/**************************************************************************
	 *                   Find all dealers
    **************************************************************************/
	@GetMapping("/dealer")
	public List<DealerData> findAllDealers() {
		
		log.info("Retrieving all Dealers.");
		return dealerCustomerService.retrieveAllDealers();
	}
	
	/**************************************************************************
	 *                   Delete a dealer for a given dealer id.
    **************************************************************************/
	@DeleteMapping("/dealer/{dealerId}")
	public Map<String, String> deleteDealerForGivenDealerId(@PathVariable Long dealerId) {
		log.info("Deleting a Dealer with ID={}", dealerId);
		dealerCustomerService.deleteDealerById(dealerId);
		
		return Map.of("message", "Deletion of dealer with ID=" + dealerId + " was successful.");
	}
	
	/*************************************************************************
	 *              Customer Table - Create, Delete, find or Update
	*************************************************************************/
	
	/*************************************************************************
	 *                        Create a Customer
	 ************************************************************************/
	
	@PostMapping("/dealer/{dealerId}/customer")//resource name
	@ResponseStatus(code = HttpStatus.CREATED)
	public DealerCustomer createCustomer(@PathVariable Long dealerId,
			@RequestBody DealerCustomer dealerCustomer) {
		log.info("Creating and then saving Customer data {}", dealerCustomer, dealerId);
		return dealerCustomerService.saveCustomer(dealerId, dealerCustomer);
	}
	
	/**************************************************************************
	 *             Update a customer for a given a customer Id
    **************************************************************************/
	@PutMapping("/customer/{customerId}") // "/dealer/customer/{customerId}"???
	public DealerCustomer updateCustomerForGivenCustomerId(@PathVariable Long customerId,
			@RequestBody DealerCustomer dealerCustomer) {
		
		log.info("Updating customer data where customerId= {}", customerId);
		
		dealerCustomer.setCustomerId(customerId);
		return dealerCustomerService.updateCustomerForGivenCustomerId(customerId, dealerCustomer);
	}
	
	/**************************************************************************
	 *              Find a customer for a given customer Id.
    **************************************************************************/
	@GetMapping("/customer/{customerId}")
	public DealerCustomer findCustomerForGivenCustomerId(@PathVariable Long customerId) {
		log.info("Retrieving customer data for given customerId= {}", customerId);
		return dealerCustomerService.retrieveCustomerById(customerId);
	}
	
	/**************************************************************************
	 *              Find all customers
    **************************************************************************/
	@GetMapping("/customer")
	public List<DealerCustomer> findAllCustomers() {
		
		log.info("Retrieving all Customers.");
		return dealerCustomerService.retrieveAllCustomers();
	}
	/**************************************************************************
	 *             Delete a customer for a given customer Id
    **************************************************************************/
	@DeleteMapping("/customer/{customerId}")  // NOT WORKING ?????
	public Map<String, String> deleteCustomerForGivenCustomerId(@PathVariable Long customerId) {
		
		log.info("Deleting customer data where customerId= {}",customerId);
		dealerCustomerService.deleteCustomerByCustomerId(customerId);
		
		return Map.of("message", "Deletion of customer with ID=" + customerId + " was successful.");
	}
	
	/*************************************************************************
	 *              Vehicle Table - Create, Delete, find or Update
	 ************************************************************************/
	
	/*************************************************************************
	 *                        Create a Vehicle
	 ************************************************************************/
	@PostMapping("/dealer/{dealerId}/vehicle")  
	@ResponseStatus(code = HttpStatus.CREATED)
	public DealerVehicle createVehicle(@PathVariable Long dealerId, @RequestBody DealerVehicle dealerVehicle) {
		
		log.info("Creating and then saving Vehicle data {}", dealerVehicle, dealerId);
		return dealerCustomerService.saveVehicle(dealerId, dealerVehicle);
	
	}
	
	/*************************************************************************
	 *                        Update a Vehicle by Id
	************************************************************************/
	@PutMapping("/vehicle/{vehicleId}")
	public DealerVehicle updateVehicleForGivenVehicleId(@PathVariable Long vehicleId, @RequestBody DealerVehicle dealerVehicle) {
		
		dealerVehicle.setVehicleId(vehicleId);
		log.info("Updating Vehicle data {}", dealerVehicle, vehicleId);
		return dealerCustomerService.updateVehicleById(vehicleId, dealerVehicle);
	
	}
	
	/*************************************************************************
	 *                      Retrieve/Find a Vehicle by Id
	************************************************************************/
	@GetMapping("/vehicle/{vehicleId}")
	public DealerVehicle findVehicleForGivenVehicleId(@PathVariable Long vehicleId) {
		
		log.info("Updating Vehicle data {}", vehicleId);
		return dealerCustomerService.retrieveVehicleById(vehicleId);
	
	}
	
	/*************************************************************************
	 *                    Find all Vehicles
	************************************************************************/
	@GetMapping("/vehicle")
	public List<DealerVehicle> findAllVehicles() {
		
		log.info("Retrieving all Vehicles.");
		return dealerCustomerService.retrieveAllVehicles();
	}
	
	/*************************************************************************
	 *                        Delete a Vehicle by Id
	************************************************************************/
	
	@DeleteMapping("/vehicle/{vehicleId}")  // NOT WORKING ?????
	public Map<String, String> deleteVehicleForGivenVehicleId(@PathVariable Long vehicleId) {
		
		log.info("Deleting customer data where customerId= {}", vehicleId);
		dealerCustomerService.deleteVehicleIdByVehicleId(vehicleId);
		
		return Map.of("message", "Deletion of vehicle with ID=" + vehicleId + " was successful.");
	}
}
