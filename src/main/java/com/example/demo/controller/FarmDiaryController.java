package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.FarmDiaryDTO;
import com.example.demo.service.FarmDiaryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FarmDiaryController {

	private final FarmDiaryService farmDiaryService;

	/** 메인 페이지 - 일지 목록으로 리다이렉트 **/
	@GetMapping("/")
	public String index() {
		return "redirect:/diary/list";
	}

	@GetMapping("/diary/list")
	public String getList(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
		List<FarmDiaryDTO> diaryList;
		if (keyword != null && !keyword.trim().isEmpty()) {
			diaryList = farmDiaryService.search(keyword.trim());
		} else {
			diaryList = farmDiaryService.getList();
		}
		model.addAttribute("diaryList", diaryList);
		model.addAttribute("keyword", keyword);
		return "diaryList";
	}

	@GetMapping("/diary/add")
	public String addDiary(Model model) {
		model.addAttribute("farmDiaryDTO", new FarmDiaryDTO());
		return "addDiary";
	}

	@PostMapping("/diary/add")
	public String save(FarmDiaryDTO farmDiaryDTO) {
		farmDiaryService.save(farmDiaryDTO);
		return "redirect:/diary/list";
	}
	
	/** 농사일지 상세보기 **/
	@GetMapping("/diary/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		log.info("Controller - 상세보기 요청: id = {}", id);
		FarmDiaryDTO farmDiaryDTO = farmDiaryService.detail(id);
		if (farmDiaryDTO == null) {
			log.warn("Controller - 일지를 찾을 수 없음: id = {}", id);
			return "redirect:/diary/list";
		}
		log.info("Controller - 일지 조회 성공: id = {}, cropName = {}", id, farmDiaryDTO.getCropName());
		model.addAttribute("diaryDetail", farmDiaryDTO);
		return "detailDiary";
	}
	
	/** 농사일지 삭제하기 **/
	@GetMapping("/diary/delete/{id}")
	public String goDelete(@PathVariable("id") Integer id) {
		farmDiaryService.goDelete(id);
		return "redirect:/diary/list";
	}
	
	/** 농사일지 수정화면 호출 **/
	@GetMapping("/diary/update/{id}")
	public String goUpdate(@PathVariable("id") Integer id, Model model) {
		FarmDiaryDTO farmDiaryDTO = farmDiaryService.detail(id);
		model.addAttribute("diaryDetail", farmDiaryDTO);
		return "updateDiary";
	}
	
	/** 농사일지 수정 및 저장 **/
	@PostMapping("/diary/update/{id}")
	public String goUpdate(@PathVariable("id") Integer id, FarmDiaryDTO farmDiaryDTO, Model model) {
		farmDiaryDTO.setDiaryId(id);
		farmDiaryService.goUpdate(farmDiaryDTO);
		FarmDiaryDTO dto = farmDiaryService.detail(id);
		model.addAttribute("diaryDetail", dto);
		return "detailDiary";
	}
}

