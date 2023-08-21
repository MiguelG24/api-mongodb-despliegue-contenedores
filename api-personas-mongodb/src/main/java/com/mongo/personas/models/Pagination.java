package com.mongo.personas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {

	private Long totalItems;
	
	private Integer totalPages;
	
	private Integer currentPages;
	
	private Integer previosPage;
	
	private Integer nextPage;
}
