package com.tmf.pim.iam.ext.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmf.pim.iam.ext.service.AdminClientService;
import com.tmf.pim.iam.ext.service.IamService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IamServiceImpl implements IamService{

	@Autowired
	AdminClientService adminClientService;
	
	@Override
	public void addRealmRoleToUser(String realm, String id, List<RoleRepresentation> roleRepresentations, String authToken) {
		adminClientService.addRealmRoleToUser(realm, id, roleRepresentations, authToken);
	}

	@Override
	public void createRole(String realm, RoleRepresentation roleRepresentation, String authToken) {
		adminClientService.createRole(realm, roleRepresentation, authToken);
	}

	@Override
	public Mono<Boolean> createUser(String realm, Mono<UserRepresentation> userRepresentation, String authToken) {
		return userRepresentation.flatMap(user -> {return Mono.just(adminClientService.createUser(realm, user, authToken));});
	}

	@Override
	public Mono<Boolean> createUser(String realm, UserRepresentation userRepresentation, String authToken) {
		return Mono.just(adminClientService.createUser(realm, userRepresentation, authToken));
	}

	@Override
	public void deleteRoleByName(String realm, String roleName, String authToken) {
		adminClientService.deleteRoleByName(realm, roleName, authToken);
	}

	@Override
	public void deleteUser(String realm, String userId, String authToken) {
		adminClientService.deleteUser(realm, userId, authToken);
	}

	@Override
	public void deleteUserRealmRoleMapping(String realm, String id, List<RoleRepresentation> roleRepresentations, String authToken) {
		adminClientService.deleteUserRealmRoleMapping(realm, id, roleRepresentations, authToken);
	}

	@Override
	public RoleRepresentation getRoleByName(String realm, String roleName, String authToken) {
		RoleRepresentation roleRepresentation = adminClientService.getRoleByName(realm, roleName, authToken);
		return roleRepresentation;
	}

	@Override
	public void updateUser(String realm, String userId, UserRepresentation userRepresentation, String authToken) {
		adminClientService.updateUser(realm, userId, userRepresentation, authToken);
	}

	@Override
	public void resetUserPassword(String realm, String userId, CredentialRepresentation credentialRepresentation, String authToken) {
		adminClientService.resetUserPassword(realm, userId, credentialRepresentation, authToken);
	}

	@Override
	public void updateRoleByName(String realm, String roleName, RoleRepresentation roleRepresentation, String authToken) {
		adminClientService.updateRoleByName(realm, roleName, roleRepresentation, authToken);
	}

	@Override
	public Flux<RoleRepresentation> getUserRealmRoleMappings(String realm, String id, String authToken) {
		List<org.keycloak.representations.idm.RoleRepresentation> userRealmRoleMappings = adminClientService.getUserRealmRoleMappings(realm, id, authToken);
		return Flux.fromStream(userRealmRoleMappings.stream());
	}

	@Override
	public UserRepresentation getUser(String realm, String userId, String authToken)
	{
		return adminClientService.getUser(realm, userId, authToken);
	}

	@Override
	public Flux<UserRepresentation> findUsers(String realm,String username,
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
            String authToken)
	{
		List<UserRepresentation> users = adminClientService.findUsers(realm, username,firstName,lastName,email,emailVerified,idpAlias,idpUserId,firstResult,maxResults,enabled,briefRepresentation, authToken);
		return Flux.fromStream(users.stream());
	}

	@Override
	public Mono<Boolean> createClient(String realm, ClientRepresentation clientRepresentation, String authToken) {
		return Mono.just(adminClientService.createClient(realm, clientRepresentation, authToken));
	}

	@Override
	public void deleteClient(String realm, String clientId, String authToken)
	{
		adminClientService.deleteClientById(realm, clientId, authToken);
	}
	
	@Override
	public void updateClient(String realm, String clientId, ClientRepresentation clientRepresentation, String authToken) {
		adminClientService.updateClient(realm, clientId, clientRepresentation, authToken);
	}

	@Override
	public Flux<ClientRepresentation> findClients(String realm, String clientId, Boolean viewableOnly, Boolean search, Integer first, Integer max, String authToken) {
		List<ClientRepresentation> clients = adminClientService.findClients(realm, clientId, viewableOnly, search, first, max, authToken);
		return Flux.fromStream(clients.stream());
	}
	
	@Override
	public CredentialRepresentation getClientSecret(String realm, String clientId, String authToken) {
		return adminClientService.getClientSecret(realm, clientId, authToken);
	}
	
	@Override
	public ScopeRepresentation createScope(String realm, String clientId, ScopeRepresentation scopeRepresentation, String authToken) {
		ScopeRepresentation createdScope = adminClientService.createScope(realm, clientId, scopeRepresentation, authToken);
		return createdScope;
	}

	@Override
	public ScopeRepresentation findScopeByName(String realm, String clientId, String scopeName, String authToken) {
		return adminClientService.findScopeByName(realm, clientId, scopeName, authToken);
	}

	@Override
	public ResourceRepresentation createResource(String realm, String clientId, ResourceRepresentation resourceRepresentation, String authToken) {
		return adminClientService.createResource(realm, clientId, resourceRepresentation, authToken);
	}
	
	@Override
	public Flux<ResourceRepresentation> findResourceByName(String realm, String clientId, String resourceName, String authToken) {
		 List<ResourceRepresentation> resources = adminClientService.findResourceByName(realm, clientId, resourceName, authToken);
		 return Flux.fromStream(resources.stream());
	}
	
	@Override
	public void deleteResource(String realm, String clientId, String authToken, String resourceId) {
		adminClientService.deleteResource(realm, clientId, authToken, resourceId);
	}

	@Override
	public void updateResourceServer(String realm, String clientId, String authToken, ResourceServerRepresentation resourceServer) {
		adminClientService.updateResourceServer(realm, clientId, authToken, resourceServer);
	}
	
	@Override
	public RolePolicyRepresentation createRolePolicy(String realm, String clientId, RolePolicyRepresentation rolePolicyRepresentation, String authToken) {
		return adminClientService.createRolePolicy(realm, clientId, rolePolicyRepresentation, authToken);
	}
	
	@Override
	public PolicyRepresentation findPolicyByName(String realm, String clientId, String policyName, String authToken) {
		 return adminClientService.findScopePolicyByName(realm, clientId, policyName, authToken);
	}
	
	@Override
	public ScopePermissionRepresentation createScopePermission(String realm, String clientId, ScopePermissionRepresentation scopePermissionRepresentation, String authToken) {
		return adminClientService.createScopePermission(realm, clientId, scopePermissionRepresentation, authToken);
	}
	
	@Override
	public ScopePermissionRepresentation findScopePermissionByName(String realm, String clientId, String scopePermissionName, String authToken) {
		 return adminClientService.findScopePermissionByName(realm, clientId, scopePermissionName, authToken);
	}
}