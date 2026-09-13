package com.wastesegregation.repository;

import com.wastesegregation.model.WasteQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteQueryRepository extends JpaRepository<WasteQuery, Long> {
}
