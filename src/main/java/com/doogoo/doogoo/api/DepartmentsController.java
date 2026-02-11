package com.doogoo.doogoo.api;

import com.doogoo.doogoo.dto.DepartmentItemDto;
import com.doogoo.doogoo.dto.DepartmentsResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DepartmentsController {

    @GetMapping("/departments")
    public DepartmentsResponse getDepartments() {
        List<DepartmentItemDto> departments = List.of(
                new DepartmentItemDto("1", "컴퓨터공학과"),
                new DepartmentItemDto("2", "소프트웨어학과"),
                new DepartmentItemDto("3", "전자정보공학과"),
                new DepartmentItemDto("0", "전체")
        );
        return new DepartmentsResponse(departments);
    }
}
