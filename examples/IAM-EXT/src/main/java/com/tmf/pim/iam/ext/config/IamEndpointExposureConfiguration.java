package com.tmf.pim.iam.ext.config;

import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "iam.endpoints")
public class IamEndpointExposureConfiguration {

	private Boolean addRealmRoleToUser;
	private Boolean createRole;
	private Boolean createUser;
	private Boolean getRoleByName;
	private Boolean getUser;
	private Boolean getUserRealmRoleMappings;
	private Boolean updateRoleByName;
	private Boolean updateUser;
	private Boolean deleteRoleByName;
	private Boolean deleteUser;
	private Boolean deleteUserRealmRoleMapping;
	private Boolean defaultCondition;
	private Boolean createClient;
	private Boolean deleteClient;
	private Boolean updateClient;
	private Boolean createScope;
	private Boolean createResource;
	private Boolean createRolePolicy;
	private Boolean createScopePermission;
	private Boolean findUsers;
	private Boolean findClients;
	private Boolean findScopeByName;
	private Boolean findResourceByName;
	private Boolean findPolicyByName;
	private Boolean findScopePermissionByName;
	private Boolean resetUserPassword;
	private Boolean deleteResource;
	private Boolean updateResourceServer;
	private Boolean getClientSecret;
	
	public Boolean isAddRealmRoleToUser() {
		return getCondition(addRealmRoleToUser);
	}
	public void setAddRealmRoleToUser(Boolean addRealmRoleToUser) {
		this.addRealmRoleToUser = addRealmRoleToUser;
	}
	public Boolean isCreateRole() {
		return getCondition(createRole);
	}
	public void setCreateRole(Boolean createRole) {
		this.createRole = createRole;
	}
	public Boolean isCreateUser() {
		return getCondition(createUser);
	}
	public void setCreateUser(Boolean createUser) {
		this.createUser = createUser;
	}
	public Boolean isGetRoleByName() {
		return getCondition(getRoleByName);
	}
	public void setGetRoleByName(Boolean getRoleByName) {
		this.getRoleByName = getRoleByName;
	}
	public Boolean isGetUser() {
		return getCondition(getUser);
	}
	public void setGetUser(Boolean getUser) {
		this.getUser = getUser;
	}
	public Boolean isGetUserRealmRoleMappings() {
		return getCondition(getUserRealmRoleMappings);
	}
	public void setGetUserRealmRoleMappings(Boolean getUserRealmRoleMappings) {
		this.getUserRealmRoleMappings = getUserRealmRoleMappings;
	}
	public Boolean isUpdateRoleByName() {
		return getCondition(updateRoleByName);
	}
	public void setUpdateRoleByName(Boolean updateRoleByName) {
		this.updateRoleByName = updateRoleByName;
	}
	public Boolean isUpdateUser() {
		return getCondition(updateUser);
	}
	public void setUpdateUser(Boolean updateUser) {
		this.updateUser = updateUser;
	}
	public Boolean isDeleteRoleByName() {
		return getCondition(deleteRoleByName);
	}
	public void setDeleteRoleByName(Boolean deleteRoleByName) {
		this.deleteRoleByName = deleteRoleByName;
	}
	public Boolean isDeleteUser() {
		return getCondition(deleteUser);
	}
	public void setDeleteUser(Boolean deleteUser) {
		this.deleteUser = deleteUser;
	}
	public Boolean isDeleteUserRealmRoleMapping() {
		return getCondition(deleteUserRealmRoleMapping);
	}
	public void setDeleteUserRealmRoleMapping(Boolean deleteUserRealmRoleMapping) {
		this.deleteUserRealmRoleMapping = deleteUserRealmRoleMapping;
	}
	public Boolean isDefaultCondition() {
		return defaultCondition;
	}
	public void setDefaultCondition(Boolean defaultCondition) {
		this.defaultCondition = defaultCondition;
	}
	
	private Boolean getCondition(Boolean condition)
	{
		return Optional.ofNullable(condition).orElse(defaultCondition);
	}
	
	public Boolean isCreateClient() {
		return getCondition(createClient);
	}
	public void setCreateClient(Boolean createClient) {
		this.createClient = createClient;
	}
	
	public void setDeleteClient(Boolean deleteClient) {
		this.deleteClient = deleteClient;
	}
	
	public boolean isDeleteClient() {
		return getCondition(deleteClient);
	}

	public void setUpdateClient(Boolean updateClient) {
		this.updateClient = updateClient;
	}
	
	public boolean isUpdateClient() {
		return getCondition(updateClient);
	}

	public void setCreateScope(Boolean createScope) {
		this.createScope = createScope;
	}
	public boolean isCreateScope() {
		return getCondition(createScope);
	}
	
	public void setCreateResource(Boolean createResource) {
		this.createResource = createResource;
	}
	public boolean isCreateResource() {
		return getCondition(createResource);
	}
	
	public void setRolePolicy(Boolean createRolePolicy) {
		this.createRolePolicy = createRolePolicy;
	}
	public boolean isCreateRolePolicy() {
		return getCondition(createRolePolicy);
	}

	public void setCreateScopePermission(Boolean createScopePermission) {
		this.createScopePermission = createScopePermission;
	}

	public boolean isCreateScopePermission() {
		return getCondition(createScopePermission);
	}

	public void setFindUsers(Boolean findUsers) {
		this.findUsers = findUsers;
	}

	public boolean isFindUsers() {
		return getCondition(findUsers);
	}

	public void setFindClients(Boolean findClients) {
		this.findClients = findClients;
	}
	public boolean isFindClients() {
		return getCondition(findClients);
	}

	public void setFindScopeByName(Boolean findScopeByName) {
		this.findScopeByName = findScopeByName;
	}
	
	public boolean isFindScopeByName() {
		return getCondition(findScopeByName);
	}

	public void setFindResourceByName(Boolean findResourceByName) {
		this.findResourceByName = findResourceByName;
	}

	public boolean isFindResourceByName() {
		return getCondition(findResourceByName);
	}

	public void setFindPolicyByName(Boolean findPolicyByName) {
		this.findPolicyByName = findPolicyByName;
	}

	public boolean isFindPolicyByName() {
		return getCondition(findPolicyByName);
	}

	public void setfindScopePermissionByName(Boolean findScopePermissionByName) {
		this.findScopePermissionByName = findScopePermissionByName;
	}

	public boolean isFindScopePermissionByName() {
		return getCondition(findScopePermissionByName);
	}
	
	public void setResetUserPassword(Boolean resetUserPassword) {
		this.resetUserPassword = resetUserPassword;
	}
	
	public boolean isResetUserPassword() {
		return getCondition(resetUserPassword);
	}

	public void setDeleteResource(Boolean deleteResource) {
		this.deleteResource = deleteResource;
	}
	
	public boolean isDeleteResource() {
		return getCondition(deleteResource);
	}

	public void setUpdateResourceServer(Boolean updateResourceServer) {
		this.updateResourceServer = updateResourceServer;
	}
	
	public boolean isUpdateResourceServer() {
		return getCondition(updateResourceServer);
	}

	public void setGetClientSecret(Boolean getClientSecret) {
		this.getClientSecret = getClientSecret;
	}
	
	public boolean isGetClientSecret() {
		return getCondition(getClientSecret);
	}
}
