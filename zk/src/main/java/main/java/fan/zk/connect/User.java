/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.connect;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * User
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-27
 */
@Data
@AllArgsConstructor
public class User{

    private Long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        User user = (User)o;
        return this.id.equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
