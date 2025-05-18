package dealer.customer.controller.model;

import java.util.HashSet;
import java.util.Set;

import dealer.customer.entity.Customer;
import dealer.customer.entity.Dealer;
import dealer.customer.entity.Vehicle;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DealerData {
	
	private long dealerId;
	private String dealerName;
	private String dealerAddress;
	private String dealerCity;
	private String dealerState;
	private String dealerZip;
	private String dealerPhone;
	
	private Set<DealerVehicle> vehicles = new HashSet<>();
	private Set<DealerCustomer> customers = new HashSet<>();

	public DealerData(Dealer dealer) {
		dealerId = dealer.getDealerId();
		dealerName = dealer.getDealerName();
		dealerAddress = dealer.getDealerAddress();
		dealerCity = dealer.getDealerCity();
		dealerState = dealer.getDealerState();
		dealerZip = dealer.getDealerZip();
		dealerPhone = dealer.getDealerPhone();
		
		for(Customer customer : dealer.getCustomers()) {
			this.customers.add(new DealerCustomer(customer)); 
			
		}
		
		for(Vehicle vehicle : dealer.getVehicles()) {
			this.vehicles.add(new DealerVehicle(vehicle)); 
		}
	} //public DealerData(Dealer dealer)
	
	@Data
	@NoArgsConstructor
	public static class DealerVehicle {
		
		private Long vehicleId;
		private String vehicleMake;
		private String vehicleModel;
		private int vehicleYear;
		private String vehicleExtColor;
		private String vehicleIntColor;
		
		public DealerVehicle(Vehicle vehicle){
			
			vehicleId = vehicle.getVehicleId();
			vehicleMake = vehicle.getVehicleMake();
			vehicleModel = vehicle.getVehicleModel();
			vehicleYear = vehicle.getVehicleYear();
			vehicleExtColor = vehicle.getVehicleExtColor();
			vehicleIntColor = vehicle.getVehicleIntColor();
		}
	} //public static class DealerVehicle 
	
	@Data
	@NoArgsConstructor
	public static class DealerCustomer {
		
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
		private String customerPhone;
		
		public DealerCustomer(Customer customer)
		{
			this.customerId = customer.getCustomerId();
			this.customerFirstName = customer.getCustomerFirstName();
			this.customerLastName = customer.getCustomerLastName();
			this.customerEmail = customer.getCustomerEmail();
			this.customerPhone = customer.getCustomerPhone();
					
		}
	} //public static class DealerCustomer 
}
