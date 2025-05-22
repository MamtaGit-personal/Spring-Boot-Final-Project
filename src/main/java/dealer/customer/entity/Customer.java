package dealer.customer.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name ="customer") //added on 5/21
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	private String customerPhone;
	
	// 
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany (mappedBy = "customers", cascade = CascadeType.PERSIST)
	private Set<Dealer> dealers = new HashSet<Dealer>();
}
