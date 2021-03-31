package com.bihanitech.shikshyaprasasak.model.responseModel;

import com.bihanitech.shikshyaprasasak.model.EmployeeGenderWise;
import com.google.gson.annotations.Expose;

public class EmployeeGenderWiseResponse {
    @Expose
    private EmployeeGenderWise employeeGenderWise;

    public EmployeeGenderWiseResponse() {
    }

    public EmployeeGenderWise getEmployeeGenderWise() {
        return employeeGenderWise;
    }

    public void setEmployeeGenderWise(EmployeeGenderWise employeeGenderWise) {
        this.employeeGenderWise = employeeGenderWise;
    }
}
