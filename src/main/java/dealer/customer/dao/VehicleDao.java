package dealer.customer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dealer.customer.entity.Vehicle;

public interface VehicleDao extends JpaRepository<Vehicle, Long> {

}
