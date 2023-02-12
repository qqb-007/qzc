package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.service.EmployeeService;
import info.batcloud.wxc.core.service.FoodService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Inject
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public Object info(@PathVariable long id) {
        return employeeService.findById(id);
    }

    @PutMapping("/{id}")
    @Permission(value = ManagerPermissions.EMPLOYEE_MANAGE)
    public Object update(@PathVariable long id, EmployeeService.UpdateParam param) {
        employeeService.updateEmployee(id,param);
        return true;
    }

    @PostMapping()
    @Permission(ManagerPermissions.EMPLOYEE_MANAGE)
    public Object add(EmployeeService.EmployeeAddParam param) {
        return employeeService.addEmployee(param);
    }

    @GetMapping("/search")
    public Object search(EmployeeService.SearchParam param) {
        return employeeService.search(param);
    }

    @PutMapping("/dimission/{employeeId}")
    @Permission(ManagerPermissions.EMPLOYEE_MANAGE)
    public Object dimission(@PathVariable long employeeId, @RequestParam long handOverEmployeeId) {
        employeeService.employeeDimission(employeeId, handOverEmployeeId);
        return true;
    }


    @DeleteMapping("/{employeeId}")
    public Object delete(@PathVariable long employeeId) {
        employeeService.deleteByEmployeeId(employeeId);
        return true;
    }

}
