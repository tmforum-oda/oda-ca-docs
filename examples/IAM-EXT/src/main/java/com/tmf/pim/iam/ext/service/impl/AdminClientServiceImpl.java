package com.tmf.pim.iam.ext.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import com.tmf.pim.iam.ext.config.KeycloakAdminClientConfiguration;
import com.tmf.pim.iam.ext.service.AdminClientService;


@Service
public class AdminClientServiceImpl implements AdminClientService{

	@Autowired
	private KeycloakAdminClientConfiguration adminClientConfiguration;
	
	@Value("${client.ssl.trust-store-password}")
	private String trustStorePassword;
	
	private Keycloak keycloak;
	private KeycloakBuilder keycloakBuilder;
	
	public static final String PROTOCOL_TLS = "TLS";
	public static final String TRUSTSTORE_FILE = "truststore/truststore_oda.jks";
	public static final Logger logger = LoggerFactory.getLogger(AdminClientServiceImpl.class);
	
	
	@PostConstruct
	public void init() {
		ResteasyClientBuilder resteasyClientBuilder = new ResteasyClientBuilder();
		resteasyClientBuilder.sslContext(getSslContext());
		keycloak = KeycloakBuilder
					.builder()
			        .grantType(adminClientConfiguration.getGrantType())
			        .serverUrl(adminClientConfiguration.getUrl())
			        .realm(adminClientConfiguration.getRealm())
			        .clientId(adminClientConfiguration.getClientId())
			        .username(adminClientConfiguration.getUserName())
			        .password(adminClientConfiguration.getPassword())
			        .resteasyClient(resteasyClientBuilder.build())
			        .build();

		keycloakBuilder = KeycloakBuilder
							.builder()
							.serverUrl(adminClientConfiguration.getUrl())
							.realm(adminClientConfiguration.getRealm())
							.clientId(adminClientConfiguration.getClientId())
							.resteasyClient(resteasyClientBuilder.build());
		
	}
	
	private SSLContext getSslContext() {
		SSLContext sslContext = null;
		try {
			KeyStore trustStore = KeyStore.getInstance("JKS");
			InputStream trustStoreStream = getClass().getClassLoader().getResourceAsStream(TRUSTSTORE_FILE);
			trustStore.load(trustStoreStream, trustStorePassword.toCharArray());
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(trustStore);
			sslContext = SSLContext.getInstance(PROTOCOL_TLS);
			sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | KeyManagementException e1) {
			logger.error("error during SSL context creation {}", e1.getMessage());
			throw new RuntimeException(e1);
		}
		
		return sslContext;
	}

	@Override
	public Boolean createUser(String realm, UserRepresentation userRepresentation, String authToken)
	{
		System.out.println("inside createUser in AdminClient");
		Boolean isSuccess = Boolean.FALSE; 
		try (Response createResponse = getKeycloak(authToken).realm(realm).users().create(userRepresentation))
		{
			
			if(Optional.ofNullable(createResponse).isPresent())
			{
				isSuccess = Response.Status.Family.SUCCESSFUL == createResponse.getStatusInfo().getFamily();
			}else
			{throw HttpServerErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "", null, null, null);}
		}
		catch (Exception e)
		{
			throw e;
		}
		
		return isSuccess;
	}

	public void addRealmRoleToUser(String realm, String id, List<RoleRepresentation> roleRepresentations, String authToken) {
		getKeycloak(authToken).realm(realm).users().get(id).roles().realmLevel().add(roleRepresentations);
	}

	@Override
	public void createRole(String realm, RoleRepresentation roleRepresentation, String authToken) {
		getKeycloak(authToken).realm(realm).roles().create(roleRepresentation);
	}

	@Override
	public void deleteRoleByName(String realm, String roleName, String authToken) {
		getKeycloak(authToken).realm(realm).roles().deleteRole(roleName);
	}

	@Override
	public void deleteUser(String realm, String userId, String authToken) {
		getKeycloak(authToken).realm(realm).users().delete(userId);
	}

	@Override
	public void deleteUserRealmRoleMapping(String realm, String id, List<RoleRepresentation> roleRepresentations, String authToken)
	{
		getKeycloak(authToken).realm(realm).users().get(id).roles().realmLevel().remove(roleRepresentations);
	}

	@Override
	public RoleRepresentation getRoleByName(String realm, String roleName, String authToken) {
		return getKeycloak(authToken).realm(realm).roles().get(roleName).toRepresentation();
	}

	@Override
	public UserRepresentation getUser(String realm, String userId, String authToken) {
		return getKeycloak(authToken).realm(realm).users().get(userId).toRepresentation();
	}

	@Override
	public List<UserRepresentation> findUsers(String realm,String username,
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
            String authToken) {
		return getKeycloak(authToken).realm(realm).users().search(username,firstName,lastName,email,emailVerified,idpAlias,idpUserId,firstResult,maxResults,enabled,briefRepresentation);
	}

	@Override
	public void updateRoleByName(String realm, String roleName, RoleRepresentation roleRepresentation, String authToken) {
		getKeycloak(authToken).realm(realm).roles().get(roleName).update(roleRepresentation);
	}

	@Override
	public void updateUser(String realm, String userId, UserRepresentation userRepresentation, String authToken) {
		getKeycloak(authToken).realm(realm).users().get(userId).update(userRepresentation);
	}

	@Override
	public void resetUserPassword(String realm, String userId, CredentialRepresentation credentialRepresentation, String authToken) {
		getKeycloak(authToken).realm(realm).users().get(userId).resetPassword(credentialRepresentation);
	}

	@Override
	public List<RoleRepresentation> getUserRealmRoleMappings(String realm, String id, String authToken) {
		return getKeycloak(authToken).realm(realm).users().get(id).roles().realmLevel().listAll();
	}

	@Override
	public Boolean createClient(String realm, ClientRepresentation clientRepresentation, String authToken) {
		System.out.println("inside createClient in AdminClient");
		Boolean isSuccess = Boolean.FALSE; 
		try (Response createResponse = getKeycloak(authToken).realm(realm).clients().create(clientRepresentation))
		{
			
			if(Optional.ofNullable(createResponse).isPresent())
			{
				isSuccess = Response.Status.Family.SUCCESSFUL == createResponse.getStatusInfo().getFamily();
			}else
			{throw HttpServerErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "", null, null, null);}
		}
		catch (Exception e)
		{
			throw e;
		}
		
		return isSuccess;
	}

	@Override
	public void deleteClientById(String realm, String clientId, String authToken) {
		getKeycloak(authToken).realm(realm).clients().get(clientId).remove();
	}
	
	@Override
	public void updateClient(String realm, String clientId, ClientRepresentation clientRepresentation, String authToken) {
		getKeycloak(authToken).realm(realm).clients().get(clientId).update(clientRepresentation);
	}

	@Override
	public List<ClientRepresentation> findClients(String realm, String clientId, Boolean viewableOnly, Boolean search, Integer first, Integer max, String authToken) {
		return getKeycloak(authToken).realm(realm).clients().findAll(clientId, viewableOnly, search, first, max);
	}

	@Override
	public CredentialRepresentation getClientSecret(String realm, String clientId, String authToken) {
		return getKeycloak(authToken).realm(realm).clients().get(clientId).getSecret();
	}
	@Override
	public ScopeRepresentation createScope(String realm, String clientId, ScopeRepresentation scopeRepresentation, String authToken) {
		Response response = getKeycloak(authToken).realm(realm).clients().get(clientId).authorization().scopes().create(scopeRepresentation);
		if (Response.Status.Family.SUCCESSFUL == response.getStatusInfo().getFamily()
				&& response.hasEntity()) {
			return response.readEntity(ScopeRepresentation.class);
		}else
		{
			throw HttpServerErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "", null, null, null);
		}
		
	}

	@Override
	public ScopeRepresentation findScopeByName(String realm, String clientId, String scopeName, String authToken) {
		return getKeycloak(authToken).realm(realm).clients().get(clientId).authorization().scopes().findByName(scopeName);
	}

	@Override
	public ResourceRepresentation createResource(String realm, String clientId, ResourceRepresentation resourceRepresentation, String authToken) {
		Response response = getKeycloak(authToken).realm(realm).clients().get(clientId).authorization().resources().create(resourceRepresentation);
		if (Response.Status.Family.SUCCESSFUL == response.getStatusInfo().getFamily()
				&& response.hasEntity()) {
			return response.readEntity(ResourceRepresentation.class);
		}else
		{
			throw HttpServerErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "", null, null, null);
		}
		
	}

	@Override
	public List<ResourceRepresentation> findResourceByName(String realm, String clientId, String resourceName, String authToken) {
		return getKeycloak(authToken).realm(realm).clients().get(clientId).authorization().resources().findByName(resourceName);
	}

	@Override
	public void deleteResource(String realm, String clientId, String authToken, String resourceId) {
		getKeycloak(authToken).realm(realm).clients().get(clientId).authorization().resources().resource(resourceId).remove();
	}

	@Override
	public void updateResourceServer(String realm, String clientId, String authToken, ResourceServerRepresentation resourceServer) {
		getKeycloak(authToken).realm(realm).clients().get(clientId).authorization().update(resourceServer);
	}
	
	@Override
	public RolePolicyRepresentation createRolePolicy(String realm, String clientId, RolePolicyRepresentation rolePolicyRepresentation, String authToken) {
		Response response = getKeycloak(authToken).realm(realm).clients().get(clientId).authorization().policies().role().create(rolePolicyRepresentation);
		if (Response.Status.Family.SUCCESSFUL == response.getStatusInfo().getFamily()
				&& response.hasEntity()) {
			return response.readEntity(RolePolicyRepresentation.class);
		}else
		{
			throw HttpServerErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "", null, null, null);
		}
		
	}
	
	@Override
	public PolicyRepresentation findScopePolicyByName(String realm, String clientId, String policyName, String authToken) {
		return getKeycloak(authToken).realm(realm).clients().get(clientId).authorization().policies().findByName(policyName);
	}
	
	@Override
	public ScopePermissionRepresentation createScopePermission(String realm, String clientId, ScopePermissionRepresentation scopePermissionRepresentation, String authToken) {
		Response response = getKeycloak(authToken).realm(realm).clients().get(clientId).authorization().permissions().scope().create(scopePermissionRepresentation);
		if (Response.Status.Family.SUCCESSFUL == response.getStatusInfo().getFamily()
				&& response.hasEntity()) {
			return response.readEntity(ScopePermissionRepresentation.class);
		}else
		{
			throw HttpServerErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "", null, null, null);
		}
		
	}
	
	@Override
	public ScopePermissionRepresentation findScopePermissionByName(String realm, String clientId, String scopePermissionName, String authToken) {
		return getKeycloak(authToken).realm(realm).clients().get(clientId).authorization().permissions().scope().findByName(scopePermissionName);
	}

	private Keycloak getKeycloak(String authToken) {
		return keycloakBuilder.authorization(authToken).build();
	}

}
