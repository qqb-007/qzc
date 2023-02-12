package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.dto.EmployeeDTO;
import info.batcloud.wxc.core.entity.Employee;
import info.batcloud.wxc.core.enums.EmployeeStatus;

public interface EmployeeService {

    void updateEmployee(Long id, UpdateParam param);

    EmployeeDTO findById(Long employeeId);

    EmployeeDTO addEmployee(EmployeeAddParam param);

    /**
     * 离职
     *
     * @param employeeId         离职人员id
     * @param handOverEmployeeId 交接人员id
     */
    void employeeDimission(long employeeId, long handOverEmployeeId);

    Paging<EmployeeDTO> search(SearchParam param);

    EmployeeDTO toDTO(Employee employee);

    void deleteByEmployeeId(long employeeId);

    class SearchParam extends PagingParam {
        private String name;
        private String phone;
        private EmployeeStatus status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public EmployeeStatus getStatus() {
            return status;
        }

        public void setStatus(EmployeeStatus status) {
            this.status = status;
        }
    }

    class EmployeeAddParam {
        private String name;

        private String phone;

        private EmployeeStatus status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public EmployeeStatus getStatus() {
            return status;
        }

        public void setStatus(EmployeeStatus status) {
            this.status = status;
        }
    }

    class UpdateParam {
        private String name;
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

}
