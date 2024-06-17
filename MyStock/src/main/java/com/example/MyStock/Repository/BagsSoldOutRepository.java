package com.example.MyStock.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.MyStock.Entity.BagsSoldOut;

public interface BagsSoldOutRepository extends JpaRepository<BagsSoldOut, Integer> {

	@Query("FROM BagsSoldOut WHERE riceBags.riceBagId = :riceBagId " + "AND (:fromDate IS NULL OR date >= :fromDate) "
			+ "AND (:toDate IS NULL OR date <= :toDate)")
	List<BagsSoldOut> getRiceBagsSoldOut(@Param("riceBagId") int riceBagId, @Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate);

	@Query("select sum(riceSoldQuantity),sum(totalSoldPrice),sum(ourTotalSoldPrice) FROM BagsSoldOut WHERE riceBags.riceBagId = :riceBagId "
			+ "AND (:fromDate IS NULL OR date >= :fromDate) " + "AND (:toDate IS NULL OR date <= :toDate)")
	List<Object[]> getTotalQuantityAndPrice(@Param("riceBagId") int riceBagId, @Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate);

	@Query("SELECT riceBags, date, sum(riceSoldQuantity) , sum(totalSoldPrice) , sum(ourTotalSoldPrice) , sum(amtProfit) FROM BagsSoldOut WHERE "
			+ "(:fromDate IS NULL OR date >= :fromDate) " + "AND (:toDate IS NULL OR date <= :toDate)"
			+ "group by riceBags.riceBagId")
	List<Object[]> getAllRiceBagsSoldOut(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	

}
