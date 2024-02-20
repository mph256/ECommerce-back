package com.mph.services.interfaces;

import java.util.List;

import com.mph.entities.Role;

public interface RoleService {

	public Role getRoleById(long roleId);

	public Role getRoleByName(String roleName);

	public List<Role> getRolesByUserUsername(String username);

	public List<Role> getRoles();

}