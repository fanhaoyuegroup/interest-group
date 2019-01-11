package com.fan.design.proxy;


import com.fan.design.proxy.aop.Department;
import com.fan.design.proxy.aop.Interceptor;
import com.fan.design.proxy.aop.LoggerInterceptor;
import com.fan.design.proxy.aop.TransactionInterceptor;

public class Test {
    public static void main(String[] args) {
        DepartmentService departmentService = new DepartmentServiceImpl();
        Interceptor loggerInterceptor = new LoggerInterceptor();
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        departmentService  = (DepartmentService)loggerInterceptor.register(departmentService);
        departmentService = (DepartmentService)transactionInterceptor.register(departmentService);
        Department department = new Department();
        department.setDepartmentName("软件学院");
        department.setDepartmentId("001");
        departmentService.insert(department);
    }
}
