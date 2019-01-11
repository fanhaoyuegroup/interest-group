package com.fan.design.proxy;


import com.fan.design.proxy.aop.Department;

public class DepartmentServiceImpl implements DepartmentService {
    @Override
    public void insert(Department department) {
        System.out.println("insert:"+department+"...");
    }
}
