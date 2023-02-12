package info.batcloud.wxc.core.service.impl;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.dto.EmployeeDTO;
import info.batcloud.wxc.core.entity.Employee;
import info.batcloud.wxc.core.enums.EmployeeStatus;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.repository.EmployeeRepository;
import info.batcloud.wxc.core.service.EmployeeService;
import info.batcloud.wxc.core.service.StoreUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private StoreUserService storeUserService;

    @Override
    public void updateEmployee(Long id, UpdateParam param) {
        Employee one = employeeRepository.findOne(id);
        one.setName(param.getName());
        one.setPhone(param.getPhone());
        employeeRepository.save(one);
    }

    @Override
    public EmployeeDTO findById(Long employeeId) {
        Employee one = employeeRepository.findOne(employeeId);
        return toDTO(one);
    }

    @Override
    //添加新员工
    public EmployeeDTO addEmployee(EmployeeAddParam param) {
        Employee employee = new Employee();
        employee.setCreateTime(new Date());
        employee.setUpdateTime(new Date());
        employee.setName(param.getName());
        employee.setPhone(param.getPhone());
        employee.setStatus(EmployeeStatus.IN_SERVICE);
        employeeRepository.save(employee);
        return toDTO(employee);
    }

    @Override
    @Transactional
    //员工离职
    public void employeeDimission(long employeeId, long handOverEmployeeId) {
        Employee employee = employeeRepository.findOne(employeeId);
        employee.setStatus(EmployeeStatus.DIMISSION);
        employeeRepository.save(employee);
        storeUserService.changeBizManager(employeeId, handOverEmployeeId);
    }

    @Override
    public Paging<EmployeeDTO> search(SearchParam param) {
        Specification<Employee> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (StringUtils.isNotEmpty(param.getName())) {
                expressions.add(cb.like(root.get("name"), "%" + param.getName() + "%"));
            }
            if (StringUtils.isNotEmpty(param.getPhone())) {
                expressions.add(cb.like(root.get("phone"), "%" + param.getPhone() + "%"));
            }
            if (param.getStatus() != null) {
                expressions.add(cb.equal(root.get("status"), param.getStatus()));
            } else {
                expressions.add(cb.notEqual(root.get("status"), EmployeeStatus.DELETED));
            }
            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<Employee> page = employeeRepository.findAll(specification, pageable);
        return PagingHelper.of(page, item -> toDTO(item), param.getPage(), param.getPageSize());
    }

    @Override
    public EmployeeDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, dto);
        return dto;
    }

    @Override
    public void deleteByEmployeeId(long employeeId) {
        Employee employee = employeeRepository.findOne(employeeId);
        Assert.isTrue(employee.getStatus() == EmployeeStatus.DIMISSION, "员工状态不是离职状态，不能删除");
        employee.setStatus(EmployeeStatus.DELETED);
        employeeRepository.save(employee);
    }
}
