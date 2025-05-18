package dealer.customer.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Dealer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long dealerId;
	
	private String dealerName;
	private String dealerAddress;
	private String dealerCity;
	private String dealerState;
	private String dealerZip;
	private String dealerPhone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL, orphanRemoval = true) // dealer is from the Vehicle class
	private Set<Vehicle> vehicles = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany (cascade = CascadeType.PERSIST)
	@JoinTable(name = "dealer_customer",
		joinColumns = @JoinColumn(name = "dealer_id"),
		inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<Customer>();
}
