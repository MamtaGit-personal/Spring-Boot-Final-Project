package dealer.customer.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vehicleId;
		
	private String vehicleMake;
	private String vehicleModel;
	private int vehicleYear;
	private String vehicleExtColor;
	private String vehicleIntColor;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)  //java field name, vehicles in the Dealer class
	@JoinColumn(name = "dealer_id", nullable = false)
	private Dealer dealer;
}
