package com.example.MyStock.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.MyStock.Entity.RiceBags;

public interface RiceBagRepository extends JpaRepository<RiceBags, Integer> {
	
	@Query("from RiceBags where riceBagDelete != '1' or riceBagDelete is null")
	List<RiceBags> findAllRiceBags();
	
	@Query("from RiceBags where riceBagCode like :riceBagCode and riceBagName like :riceBagName and (riceBagDelete != '1' or riceBagDelete is null)")
	List<RiceBags>  findByRiceBagCodeandRiceBagName(@Param("riceBagCode") String riceBagCode , @Param("riceBagName") String riceBagName );
	
	@Query("from RiceBags where riceBagCode like %:riceBagCode% and (riceBagDelete != '1' or riceBagDelete is null)")
	List<RiceBags>  findByRiceBagCode(@Param("riceBagCode") String riceBagCode );
	
	@Query("from RiceBags where riceBagName like %:riceBagName% and (riceBagDelete != '1' or riceBagDelete is null)")
	List<RiceBags>  findByRiceBagName( @Param("riceBagName") String riceBagName );
	
	@Query("select riceBagId , concat(riceBagName,' - ',riceBagCode) from RiceBags where (riceBagDelete != '1' or riceBagDelete is null) and "
			+ "(riceBagStatus != '1' or riceBagStatus is null) ")
	List<Object[]> fetchAllRiceBagNames();
	
	@Query("select riceBagId from RiceBags where (riceBagDelete != '1' or riceBagDelete is null) and "
			+ "(riceBagStatus != '1' or riceBagStatus is null) ")
	List<Integer> getAllRiceBagId();

}
