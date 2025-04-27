package com.oraz.saken.demo.repository;

import com.oraz.saken.demo.entity.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {
    @Query("""
            SELECT s FROM ServiceItem s
            WHERE (:query IS NULL OR :query = '' 
              OR LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%'))
              OR LOWER(s.description) LIKE LOWER(CONCAT('%', :query, '%')))
            """)
    List<ServiceItem> findByQuery(@Param("query") String query);
}
