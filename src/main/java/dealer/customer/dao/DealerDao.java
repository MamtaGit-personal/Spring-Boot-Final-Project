package dealer.customer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dealer.customer.entity.Dealer;

public interface DealerDao extends JpaRepository<Dealer, Long> {

}
