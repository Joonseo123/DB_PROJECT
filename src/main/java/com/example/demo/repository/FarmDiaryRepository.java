package com.example.demo.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import com.example.demo.dto.FarmDiaryDTO;

@Repository
@RequiredArgsConstructor
public class FarmDiaryRepository {
	
	private final SqlSessionTemplate sql;
	
	public List<FarmDiaryDTO> getList() {
		return sql.selectList("FarmDiary.getList");
	}
	
	public List<FarmDiaryDTO> search(String keyword) {
		return sql.selectList("FarmDiary.search", keyword);
	}

	public void save(FarmDiaryDTO farmDiaryDTO) {
		sql.insert("FarmDiary.save", farmDiaryDTO);
	}
	
	public FarmDiaryDTO detail(Integer id) {
		return sql.selectOne("FarmDiary.detail", id);
	}
	
	public int goDelete(Integer id) {
		return sql.delete("FarmDiary.goDelete", id);
	}

	public void goUpdate(FarmDiaryDTO farmDiaryDTO) {
		sql.update("FarmDiary.goUpdate", farmDiaryDTO);
	}
}

