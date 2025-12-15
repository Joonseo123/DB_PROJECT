package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.FarmDiaryDTO;
import com.example.demo.repository.FarmDiaryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FarmDiaryService {
	
	private final FarmDiaryRepository farmDiaryRepository;
	
	/** 농사일지 목록 불러오기 **/
	public List<FarmDiaryDTO> getList() {
		return farmDiaryRepository.getList();
	}
	
	/** 농사일지 검색하기 **/
	public List<FarmDiaryDTO> search(String keyword) {
		return farmDiaryRepository.search(keyword);
	}
	
	/** 농사일지 추가하기 **/
	public void save(FarmDiaryDTO farmDiaryDTO) {
		farmDiaryRepository.save(farmDiaryDTO);
	}
	
	/** 농사일지 상세보기 **/
	public FarmDiaryDTO detail(Integer id) {
		log.info("Service - 일지 상세 조회: diaryId = {}", id);
		FarmDiaryDTO result = farmDiaryRepository.detail(id);
		if (result != null) {
			log.info("Service - 일지 조회 성공: diaryId = {}, cropName = {}", id, result.getCropName());
		} else {
			log.warn("Service - 일지를 찾을 수 없음: diaryId = {}", id);
		}
		return result;
	}
	
	/** 농사일지 삭제하기 **/
	@Transactional
	public void goDelete(Integer id) {
		log.info("일지 삭제 시도: diaryId = {}", id);
		int deletedRows = farmDiaryRepository.goDelete(id);
		log.info("삭제된 행 수: {}", deletedRows);
		if (deletedRows == 0) {
			log.warn("삭제할 일지가 없습니다: diaryId = {}", id);
		}
	}
	
	/** 농사일지 수정하기 **/
	@Transactional
	public void goUpdate(FarmDiaryDTO farmDiaryDTO) {
		log.info("Service - 일지 수정: diaryId = {}", farmDiaryDTO.getDiaryId());
		farmDiaryRepository.goUpdate(farmDiaryDTO);
		log.info("Service - 일지 수정 완료: diaryId = {}", farmDiaryDTO.getDiaryId());
	}
}

