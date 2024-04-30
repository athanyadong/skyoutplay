package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annoutation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    Employee getByUsername(String username);

    /**
     * 插入员工数据
     * @param employee
     */

    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 根据主键动态修改
     * @param build
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee build);

    Employee getById(Long id);
}
