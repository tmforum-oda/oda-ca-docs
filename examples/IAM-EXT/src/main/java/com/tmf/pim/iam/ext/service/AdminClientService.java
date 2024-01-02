package com.tmf.pim.iam.ext.service;

import java.util.List;

import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.ResourceServerRepresentation;
import org.keycloak.representations.idm.authorization.RolePolicyRepresentation;
import org.keycloak.representations.idm.authorization.ScopePermissionRepresentation;
import org.keycloak.representations.idm.authorization.ScopeRepresentation;


public interface AdminClientService {

	Boolean createUser(String realm, UserRepresentation userRepresentation, String authToken);

	void addRealmRoleToUser(String realm, String id, List<RoleRepresentation> blockFirst, String authToken);

	void createRole(String realm, RoleRepresentation roleRepresentation, String authToken);

	void deleteRoleByName(String realm, String roleName, String authToken);

	void deleteUser(String realm, String userId, String authToken);

	void deleteUserRealmRoleMapping(String realm, String id, List<RoleRepresentation> roleRepresentation, String authToken);

	RoleRepresentation getRoleByName(String realm, String roleName, String authToken);

	UserRepresentation getUser(String realm, String userId, String authToken);

	void updateRoleByName(String realm, String roleName, RoleRepresentation roleRepresentation, String authToken);

	void updateUser(String realm, String userId, UserRepresentation userRepresentation, String authToken);

	List<RoleRepresentation> getUserRealmRoleMappings(String realm, String id, String authToken);

	Boolean createClient(String realm, ClientRepresentation clientRepresentation, String authToken);

	void deleteClientById(String realm, String clientId, String authToken);

	void updateClient(String realm, String clientId, ClientRepresentation clientRepresentation, String authToken);

	ScopeRepresentation createScope(String realm, String clientId, ScopeRepresentation scopeRepresentation, String authToken);

	ResourceRepresentation createResource(String realm, String clientId, ResourceRepresentation resourceRepresentation, String authToken);

	RolePolicyRepresentation createRolePolicy(String realm, String clientId, RolePolicyRepresentation rolePolicyRepresentation, String authToken);

	ScopePermissionRepresentation createScopePermission(String realm, String clientId, ScopePermissionRepresentation scopePermissionRepresentation, String authToken);

	List<UserRepresentation> findUsers(String realm,
			String username, 
			String firstName, 
			String lastName, 
			String email,
			Boolean emailVerified, 
			String idpAlias, 
			String idpUserId, 
			Integer firstResult, 
			Integer maxResults,
			Boolean enabled, 
			Boolean briefRepresentation,
			String authToken);

	List<ClientRepresentation> findClients(String realm,
			String clientId,
			Boolean viewableOnly, 
			Boolean search, 
			Integer first,
			Integer max,
			String authToken);

	ScopeRepresentation findScopeByName(String realm, String clientId, String scopeName, String authToken);

	List<ResourceRepresentation> findResourceByName(String realm, String clientId, String resourceName, String authToken);

	PolicyRepresentation findScopePolicyByName(String realm, String clientId, String policyName, String authToken);

	ScopePermissionRepresentation findScopePermissionByName(String realm, String clientId, String permissionName, String authToken);

	void resetUserPassword(String realm, String userId, CredentialRepresentation credentialRepresentation, String authToken);

	void deleteResource(String realm, String clientId, String authToken, String resourceId);

	void updateResourceServer(String realm, String clientId, String authToken, ResourceServerRepresentation resourceServer);

	CredentialRepresentation getClientSecret(String realm, String clientId, String authToken);
}
