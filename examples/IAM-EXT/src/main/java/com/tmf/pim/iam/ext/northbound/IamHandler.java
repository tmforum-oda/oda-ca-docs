package com.tmf.pim.iam.ext.northbound;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.tmf.pim.iam.ext.exception.ApplicationErrorHandler;
import com.tmf.pim.iam.ext.exception.TMF637ApiException;
import com.tmf.pim.iam.ext.service.IamService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class IamHandler {

    public static final Logger IAM_Logger = LoggerFactory.getLogger(IamHandler.class);
    
    public static final String PATH_VARIABLE_USER_ID = "userId";
    public static final String PATH_VARIABLE_ID = "id";
    public static final String PATH_VARIABLE_REALM = "realm";
    public static final String PATH_VARIABLE_ROLE_NAME = "role-name";
    public static final String PATH_VARIABLE_CLIENT_ID = "clientId";
    public static final String PATH_VARIABLE_RESOURCE_ID = "resourceId";

	private static final String QPARAM_USER_NAME = "username";
	private static final String QPARAM_FIRST_NAME = "firstname";
	private static final String QPARAM_LAST_NAME = "lastName";
	private static final String QPARAM_EMAIL = "email";
	private static final String QPARAM_EMAIL_VERIFIED = "emailVerified";
	private static final String QPARAM_IDP_ALIAS = "idpAlias";
	private static final String QPARAM_IDP_USER_ID = "idpUserId";
	private static final String QPARAM_FIRST_RESULT = "first";
	private static final String QPARAM_MAX_RESULTS = "max";
	private static final String QPARAM_ENABLED = "enabled";
	private static final String QPARAM_BRIEF_REPRESENTATION = "briefRepresentation";

	private static final String QPARAM_CLIENT_ID = "clientId";

	private static final String QPARAM_VIEWABLE_ONLY = "viewableOnly";

	private static final String QPARAM_SEARCH = "search";
	private static final String QPARAM_OBJECT_NAME= "name";
    
    @Value("${iam.realm}")
    private String realm;
    
    @Autowired
    private IamService iamService;
    
    @Autowired
    private ApplicationErrorHandler applicationErrorHandler;

    public Mono<ServerResponse> addRealmRoleToUser(ServerRequest request)
    {
    	String id = request.pathVariable(PATH_VARIABLE_ID);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }

    	IAM_Logger.debug("Adding Realm Role to user : {} in realm: {}", id, realm);
    	
		Flux<RoleRepresentation> roleRepresentations = request.bodyToFlux(RoleRepresentation.class);
		
        Mono<ServerResponse> serverResponseMono = null;
        try
        {
        	serverResponseMono =  roleRepresentations.collectList().flatMap(roleReps ->
        							{
        								iamService.addRealmRoleToUser(realm, id, roleReps, authToken);
        								return  ServerResponse.status(HttpStatus.CREATED).build(); 
        							});
        }
        catch (TMF637ApiException e)
        {
        	serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
        }
        
        return serverResponseMono;
    }

    public Mono<ServerResponse> createRole(ServerRequest request)
    {
    	IAM_Logger.debug("Create Realm Role in realm: {}", realm);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
		Mono<RoleRepresentation> roleRepresentation = request.bodyToMono(RoleRepresentation.class);
        Mono<ServerResponse> serverResponseMono = null;
        try
        {
        	serverResponseMono = roleRepresentation.flatMap(role ->
        						{
        							iamService.createRole(realm, role, authToken);
        							return ServerResponse.status(HttpStatus.CREATED).build(); 
        						});
        }
        catch (TMF637ApiException e)
        {
        	serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
        }
        
        return serverResponseMono;
    }

    public Mono<ServerResponse> createUser(ServerRequest request)
    {
    	IAM_Logger.info("Create User in realm: {}", realm);
    	IAM_Logger.info("request: {}", request.toString());
    	
//		  Mono<UserRepresentation> body = request.body(BodyExtractors.toMono(UserRepresentation.class));
		  Mono<UserRepresentation> body1 = request.bodyToMono(UserRepresentation.class);
	    String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
//		  Mono<UserRepresentation> flatMap = body.flatMap(user -> Mono.just(user));
    	
		/*
		 * Mono<UserRepresentation> userRepresentation =
		 * request.bodyToMono(UserRepresentation.class);
		 * System.out.println(userRepresentation.hasElement());
		 */
//    	body.log().subscribe(System.out::println,
//				(e) -> System.err.println(e),
//				() -> System.out.println("Completed!"));
		/*
		 * body1.log().subscribe(System.out::println, (e) -> System.err.println(e), ()
		 * -> System.out.println("Completed!"));
		 */

        Mono<ServerResponse> serverResponseMono = null;
        try
        {
        	serverResponseMono = body1.flatMap(user -> {iamService.createUser(realm, user, authToken); return ServerResponse.status(HttpStatus.CREATED)
                        .build();});
        	
//        	serverResponseMono = ServerResponse.status(HttpStatus.CREATED).build(); 
        }
        catch (TMF637ApiException e)
        {
        	serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
        }
        
        return serverResponseMono;
    }

    public Mono<ServerResponse> deleteRoleByName(ServerRequest request)
    {
        String roleName = request.pathVariable(PATH_VARIABLE_ROLE_NAME);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Delete role {} in realm: {}", roleName, realm);
    	
        Mono<ServerResponse> serverResponseMono = null;
        try
        {
        	iamService.deleteRoleByName(realm, roleName, authToken);
        	serverResponseMono = ServerResponse.status(HttpStatus.NO_CONTENT).build(); 
        }
        catch (TMF637ApiException e)
        {
        	serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
        }
        
        return serverResponseMono;
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request)
    {
        String userId = request.pathVariable(PATH_VARIABLE_USER_ID);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Delete user {} in realm: {}", userId, realm);
    	
        Mono<ServerResponse> serverResponseMono = null;
        try
        {
        	iamService.deleteUser(realm, userId, authToken);
        	serverResponseMono = ServerResponse.status(HttpStatus.NO_CONTENT).build(); 
        }
        catch (TMF637ApiException e)
        {
        	serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
        }
        
        return serverResponseMono;
    }

    public Mono<ServerResponse> deleteUserRealmRoleMapping(ServerRequest request)
    {
        String id = request.pathVariable(PATH_VARIABLE_ID);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Delete role mappings of user {} in realm: {}", id, realm);
    	
    	Flux<RoleRepresentation> roleRepresentations = request.bodyToFlux(RoleRepresentation.class);
        Mono<ServerResponse> serverResponseMono = null;
        try
        {
        	serverResponseMono =  roleRepresentations
        			.collectList()
        			.flatMap(roleReps ->
					{
						iamService.deleteUserRealmRoleMapping(realm, id, roleReps, authToken);
						return  ServerResponse.status(HttpStatus.NO_CONTENT).build(); 
					});
}
        catch (TMF637ApiException e)
        {
        	serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
        }
        
        return serverResponseMono;
    }

    public Mono<ServerResponse> getRoleByName(ServerRequest request)
    {
        String roleName = request.pathVariable(PATH_VARIABLE_ROLE_NAME);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Get role {} in realm: {}", roleName, realm);
    	
        Mono<ServerResponse> serverResponseMono = null;
        try
        {
            Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        	Mono<RoleRepresentation> roleByName = Mono.just(iamService.getRoleByName(realm, roleName, authToken));
        	serverResponseMono = roleByName
                    			.flatMap(role ->
                    				ServerResponse.ok()
                    				.contentType(APPLICATION_JSON)
                    				.body(fromValue(role)))
                    			.switchIfEmpty(notFound); 
        }
        catch (TMF637ApiException e)
        {
        	serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
        }
        
        return serverResponseMono;
    }

    public Mono<ServerResponse> getUser(ServerRequest request)
    {
        String userId = request.pathVariable(PATH_VARIABLE_USER_ID);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        IAM_Logger.debug("Get User with id: {} in Realm: {}", userId, realm);

        try{
            Mono<ServerResponse> notFound = ServerResponse.notFound().build();

            Mono<UserRepresentation> userMono = Mono.just(iamService.getUser(realm, userId, authToken));

            return userMono
                    .flatMap(user ->
                            ServerResponse.ok()
                                    .contentType(APPLICATION_JSON)
                                    .body(fromValue(user)))
                    .switchIfEmpty(notFound);
        }catch (TMF637ApiException e){
            IAM_Logger.error(" Exception handled for Get User with id: {} in Realm: {} ", userId, realm);
            return applicationErrorHandler.handleDownStreamError(e);
        }
    }

    public Mono<ServerResponse> findUsers(ServerRequest request)
    {
    	MultiValueMap<String, String> queryParams = request.queryParams();
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Find Users with Qparams {} in Realm: {}", request.queryParams(), realm);
    	try{
    		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    		
    		Flux<UserRepresentation> users = iamService.findUsers(realm,
    				queryParams.getFirst(QPARAM_USER_NAME),
    				queryParams.getFirst(QPARAM_FIRST_NAME),
    				queryParams.getFirst(QPARAM_LAST_NAME),
    				queryParams.getFirst(QPARAM_EMAIL),
    				queryParams.getFirst(QPARAM_EMAIL_VERIFIED) != null ?
    					Boolean.valueOf(queryParams.getFirst(QPARAM_EMAIL_VERIFIED)) : null 
    				,
    				queryParams.getFirst(QPARAM_IDP_ALIAS),
    				queryParams.getFirst(QPARAM_IDP_USER_ID),
    				queryParams.getFirst(QPARAM_FIRST_RESULT) != null ? 
    				Integer.valueOf(queryParams.getFirst(QPARAM_FIRST_RESULT)) : 0,
    				queryParams.getFirst(QPARAM_MAX_RESULTS) != null ?
    						Integer.valueOf(queryParams.getFirst(QPARAM_MAX_RESULTS)) : null,
    				queryParams.getFirst(QPARAM_ENABLED) != null ?
    						Boolean.valueOf(queryParams.getFirst(QPARAM_ENABLED)) : null
    				,
    				queryParams.getFirst(QPARAM_BRIEF_REPRESENTATION)  != null ?
    				Boolean.valueOf(queryParams.getFirst(QPARAM_BRIEF_REPRESENTATION)) : null,
    				authToken
   				);
    		
    		return  ServerResponse
    				.ok()
    				.contentType(APPLICATION_JSON)
    				.body(users, UserRepresentation.class)
    				.switchIfEmpty(notFound);
    	}catch (TMF637ApiException e){
    		IAM_Logger.error(" Exception handled for Find Users with Qparams {} in Realm: {}", request.queryParams(), realm);
    		return applicationErrorHandler.handleDownStreamError(e);
    	}
    }
    
    public Mono<ServerResponse> getUserRealmRoleMappings(ServerRequest request)
    {
        String userId = request.pathVariable(PATH_VARIABLE_ID);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        IAM_Logger.debug("Get User's role mappings with user id: {} in Realm: {}", userId, realm);
        try{
            Mono<ServerResponse> notFound = ServerResponse.notFound().build();

            Flux<RoleRepresentation> roleMappings = iamService.getUserRealmRoleMappings(realm, userId, authToken);

            return  ServerResponse
            			.ok()
                        .contentType(APPLICATION_JSON)
                        .body(roleMappings, RoleRepresentation.class)
                        .switchIfEmpty(notFound);
        }catch (TMF637ApiException e){
            IAM_Logger.error(" Exception handled for Get User with id: {} in Realm: {} ", userId, realm);
            return applicationErrorHandler.handleDownStreamError(e);
        }
    }

    public Mono<ServerResponse> updateRoleByName(ServerRequest request)
    {
        String roleName = request.pathVariable(PATH_VARIABLE_ROLE_NAME);
        String authToken = extractAuthToken(request);
        
    	
    	IAM_Logger.debug("Update Realm Role {} in realm: {}", roleName, realm);
    	
		Mono<RoleRepresentation> roleRepresentation = request.bodyToMono(RoleRepresentation.class);
		
        Mono<ServerResponse> serverResponseMono = null;
        try
        {
        	serverResponseMono = roleRepresentation.flatMap(role ->
        								{
        									iamService.updateRoleByName(realm, roleName, role, authToken);
        									return ServerResponse.status(HttpStatus.OK).build(); 
        								});
        }
        catch (TMF637ApiException e)
        {
        	serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
        }
        
        return serverResponseMono;
    }

    public Mono<ServerResponse> updateUser(ServerRequest request)
    {
        String userId = request.pathVariable(PATH_VARIABLE_USER_ID);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Update user with id {} in realm: {}", userId, realm);
    	
		Mono<UserRepresentation> userRepresentation = request.bodyToMono(UserRepresentation.class);
		
        Mono<ServerResponse> serverResponseMono = null;
        try
        {
        	serverResponseMono = userRepresentation.flatMap(user -> 
        						{
        							iamService.updateUser(realm, userId, user, authToken);
        							return ServerResponse.status(HttpStatus.OK).build(); 
        						});
        }
        catch (TMF637ApiException e)
        {
        	serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
        }
        
        return serverResponseMono;
    }

    public Mono<ServerResponse> resetUserPassword(ServerRequest request)
    {
    	String userId = request.pathVariable(PATH_VARIABLE_USER_ID);
    	String authToken = extractAuthToken(request);
    	if (authToken == null || authToken.isBlank())
    	{
    		return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    	IAM_Logger.debug("Update user password with id {} in realm: {}", userId, realm);
    	
    	Mono<CredentialRepresentation> credentialRepresentation = request.bodyToMono(CredentialRepresentation.class);
    	
    	Mono<ServerResponse> serverResponseMono = null;
    	try
    	{
    		serverResponseMono = credentialRepresentation.flatMap(credential -> 
    		{
    			iamService.resetUserPassword(realm, userId, credential, authToken);
    			return ServerResponse.status(HttpStatus.NO_CONTENT).build(); 
    		});
    	}
    	catch (TMF637ApiException e)
    	{
    		serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
    	}
    	
    	return serverResponseMono;
    }

    public Mono<ServerResponse> createClient(ServerRequest request)
    {
    	IAM_Logger.debug("Create Client in realm: {}", realm);
    	
		Mono<ClientRepresentation> clientRepresentation = request.bodyToMono(ClientRepresentation.class);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Mono<ServerResponse> serverResponseMono = null;
        try
        {
        	serverResponseMono = clientRepresentation.flatMap(client ->
        						{
        							iamService.createClient(realm, client, authToken);
        							return ServerResponse.status(HttpStatus.CREATED).build(); 
        						});
        }
        catch (TMF637ApiException e)
        {
        	serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
        }
        
        return serverResponseMono;
    }

    public Mono<ServerResponse> deleteClient(ServerRequest request)
    {
        String clientId = request.pathVariable(PATH_VARIABLE_CLIENT_ID);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Delete client {} in realm: {}", clientId, realm);
    	
        Mono<ServerResponse> serverResponseMono = null;
        try
        {
        	iamService.deleteClient(realm, clientId, authToken);
        	serverResponseMono = ServerResponse.status(HttpStatus.NO_CONTENT).build(); 
        }
        catch (TMF637ApiException e)
        {
        	serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
        }
        
        return serverResponseMono;
    }
    
    public Mono<ServerResponse> updateClient(ServerRequest request)
    {
        String clientId = request.pathVariable(PATH_VARIABLE_CLIENT_ID);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Update client with id {} in realm: {}", clientId, realm);
    	
		Mono<ClientRepresentation> clientRepresentation = request.bodyToMono(ClientRepresentation.class);
		
        Mono<ServerResponse> serverResponseMono = null;
        try
        {
        	serverResponseMono = clientRepresentation.flatMap(client -> 
        						{
        							iamService.updateClient(realm, clientId, client, authToken);
        							return ServerResponse.status(HttpStatus.OK).build(); 
        						});
        }
        catch (TMF637ApiException e)
        {
        	serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
        }
        
        return serverResponseMono;
    }

    public Mono<ServerResponse> findClients(ServerRequest request)
    {
    	MultiValueMap<String, String> queryParams = request.queryParams();
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Find client with Qparams : {} in realm: {}", queryParams, realm);
    	
    	try{
    		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    		
    		Flux<ClientRepresentation> clients = iamService.findClients(realm, 
    				queryParams.getFirst(QPARAM_CLIENT_ID),
    				queryParams.getFirst(QPARAM_VIEWABLE_ONLY) != null ?
        					Boolean.valueOf(queryParams.getFirst(QPARAM_VIEWABLE_ONLY)) : null,
        			queryParams.getFirst(QPARAM_SEARCH) != null ?
                					Boolean.valueOf(queryParams.getFirst(QPARAM_SEARCH)) : null,
    				queryParams.getFirst(QPARAM_FIRST_RESULT) != null ? 
    				Integer.valueOf(queryParams.getFirst(QPARAM_FIRST_RESULT)) : 0,
    				queryParams.getFirst(QPARAM_MAX_RESULTS) != null ?
    						Integer.valueOf(queryParams.getFirst(QPARAM_MAX_RESULTS)) : null,
    				authToken		
    				);
    		
    		return  ServerResponse
    				.ok()
    				.contentType(APPLICATION_JSON)
    				.body(clients, ClientRepresentation.class)
    				.switchIfEmpty(notFound);
    	}catch (TMF637ApiException e){
    		IAM_Logger.error(" Exception handled for Find client with Qparams : {} in realm: {}", request.queryParams(), realm);
    		return applicationErrorHandler.handleDownStreamError(e);
    	}
    	
    }

    public Mono<ServerResponse> getClientSecret(ServerRequest request)
    {
        String clientId = request.pathVariable(PATH_VARIABLE_CLIENT_ID);
    	String authToken = extractAuthToken(request);
    	if (authToken == null || authToken.isBlank())
    	{
    		return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    	IAM_Logger.debug("Get client secret with client id: {} in realm: {}", clientId, realm);
    	
    	try{
    		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    		
    		Mono<CredentialRepresentation> credentialRepresentation = Mono.just(iamService.getClientSecret(realm, clientId, authToken));
    		
            return credentialRepresentation
                    .flatMap(credential ->
                            ServerResponse.ok()
                                    .contentType(APPLICATION_JSON)
                                    .body(fromValue(credential)))
                    .switchIfEmpty(notFound);
    	}catch (TMF637ApiException e){
    		IAM_Logger.error(" Exception handled for Get client secret with client id: {} in realm: {}", clientId, realm);
    		return applicationErrorHandler.handleDownStreamError(e);
    	}
    	
    }
    
    public Mono<ServerResponse> createScope(ServerRequest request)
    {
        String clientId = request.pathVariable(PATH_VARIABLE_CLIENT_ID);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        IAM_Logger.debug("Create Scope in Client: {}", clientId );
        try{
            Mono<ServerResponse> internalServerError = ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            
    		Mono<ScopeRepresentation> scopeRepresentation = request.bodyToMono(ScopeRepresentation.class);

    		return scopeRepresentation.flatMap(scope -> 
				{
					ScopeRepresentation createScope = iamService.createScope(realm, clientId, scope, authToken);
					return ServerResponse.status(HttpStatus.CREATED).contentType(APPLICATION_JSON)
							.body(fromValue(createScope));
				 
				})
    			.switchIfEmpty(internalServerError);
    		

        }catch (TMF637ApiException e){
            IAM_Logger.error(" Exception handled for Get User with id: {} in Realm: {} ", clientId, realm);
            return applicationErrorHandler.handleDownStreamError(e);
        }
    }

    public Mono<ServerResponse> findScopeByName(ServerRequest request)
    {
    	String clientId = request.pathVariable(PATH_VARIABLE_CLIENT_ID);
    	String scopeName = request.queryParam(QPARAM_OBJECT_NAME).orElse(null);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Find Scope by Name {} in Client: {}", scopeName, clientId);
    	Mono<ServerResponse> serverResponseMono = null;
    	try{
            Mono<ServerResponse> notFoundResponse= ServerResponse.status(HttpStatus.NOT_FOUND).build();

            ScopeRepresentation scopeRepresentation = iamService.findScopeByName(realm, clientId, scopeName, authToken);
            Mono<ScopeRepresentation> scopeByName = Mono.justOrEmpty(scopeRepresentation);
        	serverResponseMono = scopeByName
                    			.flatMap(scope ->
                    				ServerResponse.ok()
                    				.contentType(APPLICATION_JSON)
                    				.body(fromValue(scope)))
                    			.switchIfEmpty(notFoundResponse); 
    	}catch (TMF637ApiException e){
    		IAM_Logger.error(" Exception handled for Get User with id: {} in Realm: {} ", clientId, realm);
    		serverResponseMono =  applicationErrorHandler.handleDownStreamError(e);
    	}
    	return serverResponseMono;
    }

    public Mono<ServerResponse> createResource(ServerRequest request)
    {
    	String clientId = request.pathVariable(PATH_VARIABLE_CLIENT_ID);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Create Resource in Client: {}", clientId );
    	try{
    		Mono<ServerResponse> internalServerError = ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    		
    		Mono<ResourceRepresentation> resourceRepresentation = request.bodyToMono(ResourceRepresentation.class);
    		
    		return resourceRepresentation.flatMap(resource -> 
    		{
    			ResourceRepresentation createResource = iamService.createResource(realm, clientId, resource, authToken);
    			return ServerResponse.status(HttpStatus.CREATED).contentType(APPLICATION_JSON)
    					.body(fromValue(createResource));
    			
    		})
    		.switchIfEmpty(internalServerError);
    		
    		
    	}catch (TMF637ApiException e){
    		IAM_Logger.error(" Exception handled for create resource in clien: {} in Realm: {} ", clientId, realm);
    		return applicationErrorHandler.handleDownStreamError(e);
    	}
    }
    
    public Mono<ServerResponse> findResourceByName(ServerRequest request)
    {
    	String clientId = request.pathVariable(PATH_VARIABLE_CLIENT_ID);
    	String resourceName = request.queryParam(QPARAM_OBJECT_NAME).orElse(null);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Find Resource by Name {} in Client: {}", resourceName, clientId);
    	Mono<ServerResponse> serverResponseMono = null;
    	try{
            Mono<ServerResponse> notFoundResponse= ServerResponse.status(HttpStatus.NOT_FOUND).build();

            Flux<ResourceRepresentation> resourceRepresentations = iamService.findResourceByName(realm, clientId, resourceName, authToken);
        	serverResponseMono = ServerResponse
    				.ok()
    				.contentType(APPLICATION_JSON)
    				.body(resourceRepresentations, ResourceRepresentation.class)
    				.switchIfEmpty(notFoundResponse);
    	}catch (TMF637ApiException e){
    		IAM_Logger.error(" Exception handled for Find Resource by Name {} in Client: {}", resourceName, clientId);
    		serverResponseMono =  applicationErrorHandler.handleDownStreamError(e);
    	}
    	return serverResponseMono;
    }
    
    public Mono<ServerResponse> deleteResource(ServerRequest request)
    {
    	String clientId = request.pathVariable(PATH_VARIABLE_CLIENT_ID);
    	String resourceId = request.pathVariable(PATH_VARIABLE_RESOURCE_ID);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Delete Resource {} in Client: {}", resourceId, clientId);

    	Mono<ServerResponse> serverResponseMono = null;
        try
        {
        	iamService.deleteResource(realm, clientId, authToken, resourceId);
        	serverResponseMono = ServerResponse.status(HttpStatus.NO_CONTENT).build(); 
        }
        catch (TMF637ApiException e)
        {
        	IAM_Logger.error(" Exception handled for Delete Resource {} in Client: {} ", resourceId, clientId);
        	serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
        }
        
        return serverResponseMono;
    }

    public Mono<ServerResponse> updateResourceServer(ServerRequest request)
    {
    	String clientId = request.pathVariable(PATH_VARIABLE_CLIENT_ID);
    	String authToken = extractAuthToken(request);
    	if (authToken == null || authToken.isBlank())
    	{
    		return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    	IAM_Logger.debug("Update Resource Server in Client: {}", clientId);
    	Mono<ResourceServerRepresentation> resourceServerRepresentation = request.bodyToMono(ResourceServerRepresentation.class);
    	
    	Mono<ServerResponse> serverResponseMono = null;
    	try
    	{
        	serverResponseMono = resourceServerRepresentation.flatMap(resourceServer ->
			{
				iamService.updateResourceServer(realm, clientId, authToken, resourceServer);
				return ServerResponse.status(HttpStatus.NO_CONTENT).build(); 
			});
    	}
    	catch (TMF637ApiException e)
    	{
    		IAM_Logger.error(" Exception handled for Update Resource Server in Client: {}", clientId);
    		serverResponseMono = applicationErrorHandler.handleDownStreamError(e);
    	}
    	
    	return serverResponseMono;
    }

    public Mono<ServerResponse> createRolePolicy(ServerRequest request)
    {
    	String clientId = request.pathVariable(PATH_VARIABLE_CLIENT_ID);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Create Role Policy in Client: {}", clientId );
    	try{
    		Mono<ServerResponse> internalServerError = ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    		
    		Mono<RolePolicyRepresentation> rolePolicyRepresentation = request.bodyToMono(RolePolicyRepresentation.class);
    		
    		return rolePolicyRepresentation.flatMap(rolePolicy -> 
    		{
    			RolePolicyRepresentation createRolePolicy = iamService.createRolePolicy(realm, clientId, rolePolicy, authToken);
    			return ServerResponse
    					.status(HttpStatus.CREATED)
    					.contentType(APPLICATION_JSON)
    					.body(fromValue(createRolePolicy));
    		})
 			.switchIfEmpty(internalServerError);
    	}catch (TMF637ApiException e){
    		IAM_Logger.error(" Exception handled for create role policy in clien: {} in Realm: {} ", clientId, realm);
    		return applicationErrorHandler.handleDownStreamError(e);
    	}
    }
    
    public Mono<ServerResponse> findPolicyByName(ServerRequest request)
    {
    	String clientId = request.pathVariable(PATH_VARIABLE_CLIENT_ID);
    	String policyName = request.queryParam(QPARAM_OBJECT_NAME).orElse(null);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Find Policy by Name {} in Client: {}", policyName, clientId);
    	Mono<ServerResponse> serverResponseMono = null;
    	try{
            Mono<ServerResponse> notFoundResponse= ServerResponse.status(HttpStatus.NOT_FOUND).build();

            PolicyRepresentation policyRepresentation = iamService.findPolicyByName(realm, clientId, policyName, authToken);
            Mono<PolicyRepresentation> policyByName = Mono.justOrEmpty(policyRepresentation);
        	serverResponseMono = policyByName
                    			.flatMap(policy ->
                    				ServerResponse.ok()
                    				.contentType(APPLICATION_JSON)
                    				.body(fromValue(policy)))
                    			.switchIfEmpty(notFoundResponse); 
    	}catch (TMF637ApiException e){
    		IAM_Logger.error(" Exception handled for Find Policy by Name {} in Client: {}", policyName, clientId);
    		serverResponseMono =  applicationErrorHandler.handleDownStreamError(e);
    	}
    	return serverResponseMono;
    }
    
    public Mono<ServerResponse> createScopePermission(ServerRequest request)
    {
    	String clientId = request.pathVariable(PATH_VARIABLE_CLIENT_ID);
        String authToken = extractAuthToken(request);
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
        
    	
    	IAM_Logger.debug("Create Scope Permission in Client: {}", clientId );
    	try{
    		Mono<ServerResponse> internalServerError = ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    		
    		Mono<ScopePermissionRepresentation> scopePermissionRepresentation = request.bodyToMono(ScopePermissionRepresentation.class);
    		
    		return scopePermissionRepresentation.flatMap(scopePermission -> 
    		{
    			ScopePermissionRepresentation createdScopePermission = iamService.createScopePermission(realm, clientId, scopePermission, authToken);
    			return ServerResponse
    					.status(HttpStatus.CREATED)
    					.contentType(APPLICATION_JSON)
    					.body(fromValue(createdScopePermission));
    		})
    				.switchIfEmpty(internalServerError);
    	}catch (TMF637ApiException e){
    		IAM_Logger.error(" Exception handled for create scope permissionin clien: {} in Realm: {} ", clientId, realm);
    		return applicationErrorHandler.handleDownStreamError(e);
    	}
    }
    
    public Mono<ServerResponse> findScopePermissionByName(ServerRequest request)
    {
    	String clientId = request.pathVariable(PATH_VARIABLE_CLIENT_ID);
    	String scopePermissionName = request.queryParam(QPARAM_OBJECT_NAME).orElse(null);
    	String authToken = extractAuthToken(request);
    	
        if (authToken == null || authToken.isBlank())
        {
        	return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	IAM_Logger.debug("Find Scope Permisson by Name {} in Client: {}", scopePermissionName, clientId);
    	Mono<ServerResponse> serverResponseMono = null;
    	try{
            Mono<ServerResponse> notFoundResponse= ServerResponse.status(HttpStatus.NOT_FOUND).build();

            ScopePermissionRepresentation scopePermissionRepresentation = iamService.findScopePermissionByName(realm, clientId, scopePermissionName, authToken);
            Mono<ScopePermissionRepresentation> permissionByName = Mono.justOrEmpty(scopePermissionRepresentation);
        	serverResponseMono = permissionByName
                    			.flatMap(permission ->
                    				ServerResponse.ok()
                    				.contentType(APPLICATION_JSON)
                    				.body(fromValue(permission)))
                    			.switchIfEmpty(notFoundResponse); 
    	}catch (TMF637ApiException e){
    		IAM_Logger.error(" Exception handled for Find Scope Permisson by Name {} in Client: {}", scopePermissionName, clientId);
    		serverResponseMono =  applicationErrorHandler.handleDownStreamError(e);
    	}
    	return serverResponseMono;
    }
    
	private String extractAuthToken(ServerRequest request) {
		List<String> authHeader = request.headers().header(HttpHeaders.AUTHORIZATION);
        String authToken = (authHeader != null & !authHeader.isEmpty()) 
        	? authHeader.get(0).startsWith("Bearer") 
        			? authHeader.get(0).split(" ")[1] 
        			: null
        	: null;
		return authToken;
	}
}
