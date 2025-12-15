package com.example.demo.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FarmDiaryDTO {
	
	private int diaryId;
	private LocalDate diaryDate;
	private String cropName;
	private String workContent;
	private String weather;
	private Integer temperature;
	private Integer humidity;
	private String notes;
}

