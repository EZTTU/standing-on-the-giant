package org.cuit.echo.seon.commonauth;

import java.util.Set;

/**
 * @author SeonExlike
 * @date 2023/2/1
 */
public abstract class Authorizer {

    /**
     * 获取角色信息
     *
     * @return 角色列表
     */
    public Set<String> getRoles() {
        return null;
    }

    /**
     * 获取权限信息
     *
     * @return 权限列表
     */
    public Set<String> getPermissions() {
        return null;
    }
}
