package com.coder.springjwt.services.adminServices.catalogCbiService;

import com.coder.springjwt.payload.adminPayloads.catalogPaylods.CatalogPayloadInvestigation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface CatalogCbiService  {
//    ResponseEntity<?> getCatalogInProgressListService(int page , int size);

//    ResponseEntity<?> catalogInvestigationService(Long catalogId, CatalogPayloadInvestigation catalogInvestigationPayload);

    ResponseEntity<?> getCatalogMasters();

//    ResponseEntity<?> searchCatalogByDateService(int page, int size, LocalDate startDate, LocalDate adjustedEndDate);
}
