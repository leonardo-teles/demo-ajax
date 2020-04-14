package com.mballem.demoajax.service;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.mballem.demoajax.repository.PromocaoRepository;

public class PromocaoDataTablesService {

	private String[] cols = {
		"id", "titulo", "site", "linkPromocao", "descricao", "linkImagem",
		"preco", "likes", "dtCadastro", "categoria"
	};
	
	public Map<String, Object> execute(PromocaoRepository repository, HttpServletRequest request) {
		
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		int draw = Integer.parseInt(request.getParameter("draw"));
		
		int current = currentPage(start, length);
		
		String column = columnName(request);
		Direction direction = orderBy(request);
		
		Pageable pageable = PageRequest.of(current, length, direction, column);
		
		Map<String, Object> json = new LinkedHashMap<>();
		json.put("draw", draw);
		json.put("recordsTotal", 0);
		json.put("recordsFiltered", 0);
		json.put("data", null);
		
		return json;
	}

	private Direction orderBy(HttpServletRequest request) {
		String order = request.getParameter("order[0][dir]");
		Direction sort = Direction.ASC;
		if (order.equalsIgnoreCase("desc")) {
			sort = Direction.DESC;
		}
		
		return sort;
	}

	private String columnName(HttpServletRequest request) {
		int iCol = Integer.parseInt(request.getParameter("order[0][column]"));
		
		return cols[iCol];
	}

	private int currentPage(int start, int length) {
		return start / length;
	}
}
