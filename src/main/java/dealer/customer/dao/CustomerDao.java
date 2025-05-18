package dealer.customer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dealer.customer.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
