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
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "dealer")
public class Dealer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long dealerId;
	
	@EqualsAndHashCode.Exclude
	private String dealerName;
	
	@EqualsAndHashCode.Exclude
	private String dealerAddress;
	
	@EqualsAndHashCode.Exclude
	private String dealerCity;
	
	@EqualsAndHashCode.Exclude
	private String dealerState;
	
	@EqualsAndHashCode.Exclude
	private String dealerZip;
	
	@EqualsAndHashCode.Exclude
	private String dealerPhone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL, orphanRemoval = true) //java field name dealer is from the Vehicle class
	private Set<Vehicle> vehicles = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // changed on 5/21
	@JoinTable(name = "dealer_customer",
		joinColumns = @JoinColumn(name = "dealer_id"),
		inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<Customer>();
	
	/****************         added on 5/21      *************************/
	
	public void removeCustomer(Customer customer){
		this.customers.remove(customer);
		customer.getDealers().remove(this);
	}
	
	/*********************************************************************/
	
}
