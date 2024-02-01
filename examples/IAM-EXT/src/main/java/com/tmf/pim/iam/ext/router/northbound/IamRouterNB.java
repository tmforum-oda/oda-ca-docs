package com.tmf.pim.iam.ext.router.northbound;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.RouterFunctions.Builder;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.tmf.pim.iam.ext.config.IamEndpointExposureConfiguration;
import com.tmf.pim.iam.ext.northbound.IamHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Configuration
public class IamRouterNB {

	private static final String BASE_PATH = "/v1/iam";
	private static final String REALM_USERS="/users";
    private static final String REALM_USERS_USER_ID = "/users/{userId}";
    private static final String REALM_USERS_USER_PASSWORD_RESET = "/users/{userId}/reset-password";
    private static final String REALM_ROLES = "/roles";
	private static final String REALM_ROLES_ROLE_NAME = "/roles/{role-name}";
	private static final String REALM_USERS_ID_ROLE_MAPPINGS_REALM = "/users/{id}/role-mappings/realm";
	private static final String REALM_CLIENTS = "/clients";
	private static final String REALM_CLIENTS_CLIENT_ID = "/clients/{clientId}";
	private static final String REALM_CLIENTS_CLIENT_SECRET = "/clients/{clientId}/client-secret";
	private static final String REALM_CLIENTS_RESOURCE_SERVER = "/clients/{clientId}/authz/resource-server";
	private static final String REALM_CLIENTS_RESOURCE_SERVER_SCOPE = "/clients/{clientId}/authz/resource-server/scope";
	private static final String REALM_CLIENTS_RESOURCE_SERVER_RESOURCE= "/clients/{clientId}/authz/resource-server/resource";
	private static final String REALM_CLIENTS_RESOURCE_SERVER_RESOURCE_ID= "/clients/{clientId}/authz/resource-server/resource/{resourceId}";
	private static final String REALM_CLIENTS_RESOURCE_SERVER_ROLE_POLICY= "/clients/{clientId}/authz/resource-server/policy/role";
	private static final String REALM_CLIENTS_RESOURCE_SERVER_PERMISSION_SCOPE= "/clients/{clientId}/authz/resource-server/permission/scope";
	
	@Autowired
	IamEndpointExposureConfiguration endpointExposureConfiguration;

	@Bean
    @RouterOperations(
            {
                    @RouterOperation(
                            method = RequestMethod.POST,
                            path = "/iam/users/{id}/role-mappings/realm",
                            operation =
                            @Operation(
                                    description = "Add realm-level role mappings to the user",
                                    operationId = "addRealmRoleToUser",
                                    tags = "iam | Role Mapper",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "201",
                                                    description = "success"
                                                    ),
                                            @ApiResponse(responseCode = "500", description = "Errors while calling downstream component")
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "realm",required = true),
                                            @Parameter(in = ParameterIn.PATH, name = "id",required = true)
                                            }
                            )),
                    @RouterOperation(
                            method = RequestMethod.POST,
                            path = "/iam/roles",
                            operation =
                            @Operation(
                                    description = "Create a new role for the realm or client",
                                    operationId = "createRole",
                                    tags = "iam | roles",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "201",
                                                    description = "success"),
                                            @ApiResponse(responseCode = "500", description = "Errors while calling downstream component")
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "realm",required = true)
                                            },
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = RoleRepresentation.class)))
                                    )),
                    @RouterOperation(
                    		method = RequestMethod.POST,
                    		path = "/iam/users",
                    		operation =
                    		@Operation(
                    				description = "create a new user on identity provider",
                    				operationId = "createUser",
                    				tags = "iam | users",
                    				responses = {
                    						@ApiResponse(
                    								responseCode = "201",
                    								description = "success"),
                    						@ApiResponse(responseCode = "500", description = "Errors while calling downstream component")
                    				},
                    				parameters = {
                    						@Parameter(in = ParameterIn.PATH, name = "realm",required = true)
                    				}
                    				)),
                    @RouterOperation(
                            method = RequestMethod.DELETE,
                            path = "/iam/roles/{role-name}",
                            operation =
                            @Operation(
                                    description = "Delete a role by name",
                                    operationId = "deleteRoleByName",
                                    tags = "iam | roles",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "204",
                                                    description = "Success"
                                                    ),
                                            @ApiResponse(responseCode = "404", description = "Role not found"),
                                            @ApiResponse(responseCode = "500", description = "Errors while calling downstream component")
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "realm"),
                                            @Parameter(in = ParameterIn.PATH, name = "role-name")}
                            )),
                    @RouterOperation(
                    		method = RequestMethod.DELETE,
                    		path = "/iam/users/{userId}",
                    		operation =
                    		@Operation(
                    				description = "Delete the user",
                    				operationId = "deleteUser",
                    				tags = "iam | users",
                    				responses = {
                    						@ApiResponse(
                    								responseCode = "204",
                    								description = "Success"
                    								),
                    						@ApiResponse(responseCode = "404", description = "User not found"),
                    						@ApiResponse(responseCode = "500", description = "Errors while calling downstream component")
                    				},
                    				parameters = {
                    						@Parameter(in = ParameterIn.PATH, name = "realm"),
                    						@Parameter(in = ParameterIn.PATH, name = "userId")}
                    				)),
                    @RouterOperation(
                    		method = RequestMethod.DELETE,
                    		path = "/iam/users/{id}/role-mappings/realm",
                    		operation =
                    		@Operation(
                    				description = "Delete realm-level role mappings",
                    				operationId = "deleteUserRealmRoleMapping",
                    				tags = "iam | Role Mapper",
                    				responses = {
                    						@ApiResponse(
                    								responseCode = "204",
                    								description = "Success"
                    								),
                    						@ApiResponse(responseCode = "404", description = "Resource not found"),
                    						@ApiResponse(responseCode = "500", description = "Errors while calling downstream component")
                    				},
                    				parameters = {
                    						@Parameter(in = ParameterIn.PATH, name = "realm"),
                    						@Parameter(in = ParameterIn.PATH, name = "id")}
                    				)),
                    @RouterOperation(
                            method = RequestMethod.GET,
                            path = "/iam/roles/{role-name}",
                            operation =
                            @Operation(
                                    description = "Get a role by name",
                                    operationId = "getRoleByName",
                                    tags = "iam | roles",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "success",
                                                    content = {
                                                            @Content(
                                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                                    schema = @Schema(implementation = RoleRepresentation.class))
                                                    }),
                                            @ApiResponse(responseCode = "404", description = "Resource not found"),
                                            @ApiResponse(responseCode = "500", description = "Errors while calling downstream component")
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "realm"),
                                            @Parameter(in = ParameterIn.PATH, name = "role-name")}
                            )),
                    @RouterOperation(
                    		method = RequestMethod.GET,
                    		path = "/iam/users/{userId}",
                    		operation =
                    		@Operation(
                    				description = "Get representation of the user",
                    				operationId = "getUser",
                    				tags = "iam | users",
                    				responses = {
                    						@ApiResponse(
                    								responseCode = "200",
                    								description = "success",
                    								content = {
                    										@Content(
                    												mediaType = MediaType.APPLICATION_JSON_VALUE,
                    												schema = @Schema(implementation = UserRepresentation.class))
                    								}),
                    						@ApiResponse(responseCode = "404", description = "Resource not found"),
                    						@ApiResponse(responseCode = "500", description = "Errors while calling downstream component")
                    				},
                    				parameters = {
                    						@Parameter(in = ParameterIn.PATH, name = "realm"),
                    						@Parameter(in = ParameterIn.PATH, name = "userId")}
                    				)),
                    @RouterOperation(
                    		method = RequestMethod.GET,
                    		path = "/iam/users/{id}/role-mappings/realm",
                    		operation =
                    		@Operation(
                    				description = "Get realm-level role mappings",
                    				operationId = "getUserRealmRoleMappings",
                    				tags = "iam | Role Mapper",
                    				responses = {
                    						@ApiResponse(
                    								responseCode = "200",
                    								description = "success",
                    								content = {
                    										@Content(
                    												mediaType = MediaType.APPLICATION_JSON_VALUE,
                    												array = @ArraySchema(schema = @Schema(implementation = RoleRepresentation.class)))
                    								}),
                    						@ApiResponse(responseCode = "404", description = "Resource not found"),
                    						@ApiResponse(responseCode = "500", description = "Errors while calling downstream component")
                    				},
                    				parameters = {
                    						@Parameter(in = ParameterIn.PATH, name = "realm"),
                    						@Parameter(in = ParameterIn.PATH, name = "id")}
                    				)),
                    @RouterOperation(
                    		method = RequestMethod.PUT,
                    		path = "/iam/roles/{role-name}",
                    		operation =
                    		@Operation(
                    				description = "Update a role by name",
                    				operationId = "updateRoleByName",
                    				tags = "iam | roles",
                    				responses = {
                    						@ApiResponse(
                    								responseCode = "200",
                    								description = "success"),
                    						@ApiResponse(responseCode = "404", description = "Resource not found"),
                    						@ApiResponse(responseCode = "500", description = "Errors while calling downstream component")
                    				},
                    				parameters = {
                    						@Parameter(in = ParameterIn.PATH, name = "realm"),
                    						@Parameter(in = ParameterIn.PATH, name = "role-name")}
                    				)),
                    @RouterOperation(
                    		method = RequestMethod.PUT,
                    		path = "/iam/users/{userId}",
                    		operation =
                    		@Operation(
                    				description = "Update the user",
                    				operationId = "updateUser",
                    				tags = "iam | users",
                    				responses = {
                    						@ApiResponse(
                    								responseCode = "200",
                    								description = "success"),
                    						@ApiResponse(responseCode = "404", description = "Resource not found"),
                    						@ApiResponse(responseCode = "500", description = "Errors while calling downstream component")
                    				},
                    				parameters = {
                    						@Parameter(in = ParameterIn.PATH, name = "realm"),
                    						@Parameter(in = ParameterIn.PATH, name = "userId")}
                    				))
            })
    RouterFunction<ServerResponse> routeIamRequests(IamHandler handler) {
        return route()
                .path(BASE_PATH,
                        builder ->{ 
                				builder
                                .nest(accept(APPLICATION_JSON).or(contentType(APPLICATION_JSON)).or(accept(TEXT_EVENT_STREAM)),
                                        nestedBuilder -> 
                                				{
                                    				new ConditionalBuilder(nestedBuilder)
                                            				.addWithCondition(HttpMethod.POST, REALM_USERS_ID_ROLE_MAPPINGS_REALM, handler::addRealmRoleToUser, endpointExposureConfiguration.isAddRealmRoleToUser())
                                            				.addWithCondition(HttpMethod.POST, REALM_ROLES, handler::createRole, endpointExposureConfiguration.isCreateRole())
                                            				.addWithCondition(HttpMethod.POST, REALM_USERS, handler::createUser, endpointExposureConfiguration.isCreateUser())
                                            				.addWithCondition(HttpMethod.GET, REALM_USERS, handler::findUsers, endpointExposureConfiguration.isFindUsers())
                                            				.addWithCondition(HttpMethod.GET, REALM_ROLES_ROLE_NAME, handler::getRoleByName, endpointExposureConfiguration.isGetRoleByName())
                                            				.addWithCondition(HttpMethod.GET, REALM_USERS_USER_ID, handler::getUser, endpointExposureConfiguration.isGetUser())
                                            				.addWithCondition(HttpMethod.GET, REALM_USERS_ID_ROLE_MAPPINGS_REALM, handler::getUserRealmRoleMappings, endpointExposureConfiguration.isGetUserRealmRoleMappings())
                                                    		.addWithCondition(HttpMethod.PUT, REALM_ROLES_ROLE_NAME, handler::updateRoleByName, endpointExposureConfiguration.isUpdateRoleByName())
                                                    		.addWithCondition(HttpMethod.PUT, REALM_USERS_USER_ID, handler::updateUser, endpointExposureConfiguration.isUpdateUser())
                                                    		.addWithCondition(HttpMethod.PUT, REALM_USERS_USER_PASSWORD_RESET, handler::resetUserPassword, endpointExposureConfiguration.isResetUserPassword())
                                                    		.addWithCondition(HttpMethod.POST, REALM_CLIENTS, handler::createClient, endpointExposureConfiguration.isCreateClient())
                                                    		.addWithCondition(HttpMethod.PUT, REALM_CLIENTS_CLIENT_ID, handler::updateClient, endpointExposureConfiguration.isUpdateClient())
                                                    		.addWithCondition(HttpMethod.GET, REALM_CLIENTS_CLIENT_SECRET, handler::getClientSecret, endpointExposureConfiguration.isGetClientSecret())
                                                    		.addWithCondition(HttpMethod.GET, REALM_CLIENTS, handler::findClients, endpointExposureConfiguration.isFindClients())
                                                    		.addWithCondition(HttpMethod.PUT, REALM_CLIENTS_RESOURCE_SERVER, handler::updateResourceServer, endpointExposureConfiguration.isUpdateResourceServer())
                                                    		.addWithCondition(HttpMethod.POST, REALM_CLIENTS_RESOURCE_SERVER_SCOPE, handler::createScope, endpointExposureConfiguration.isCreateScope())
                                                    		.addWithCondition(HttpMethod.GET, REALM_CLIENTS_RESOURCE_SERVER_SCOPE, handler::findScopeByName, endpointExposureConfiguration.isFindScopeByName())
                                                    		.addWithCondition(HttpMethod.POST, REALM_CLIENTS_RESOURCE_SERVER_RESOURCE, handler::createResource, endpointExposureConfiguration.isCreateResource())
                                                    		.addWithCondition(HttpMethod.GET, REALM_CLIENTS_RESOURCE_SERVER_RESOURCE, handler::findResourceByName, endpointExposureConfiguration.isFindResourceByName())
                                                    		.addWithCondition(HttpMethod.DELETE, REALM_CLIENTS_RESOURCE_SERVER_RESOURCE_ID, handler::deleteResource, endpointExposureConfiguration.isDeleteResource())
                                                    		.addWithCondition(HttpMethod.POST, REALM_CLIENTS_RESOURCE_SERVER_ROLE_POLICY, handler::createRolePolicy, endpointExposureConfiguration.isCreateRolePolicy())
                                                    		.addWithCondition(HttpMethod.GET, REALM_CLIENTS_RESOURCE_SERVER_ROLE_POLICY, handler::findPolicyByName, endpointExposureConfiguration.isFindPolicyByName())
                                                    		.addWithCondition(HttpMethod.POST, REALM_CLIENTS_RESOURCE_SERVER_PERMISSION_SCOPE, handler::createScopePermission, endpointExposureConfiguration.isCreateScopePermission())
                                                    		.addWithCondition(HttpMethod.GET, REALM_CLIENTS_RESOURCE_SERVER_PERMISSION_SCOPE, handler::findScopePermissionByName, endpointExposureConfiguration.isFindScopePermissionByName())
                                                    		;
                                				}
                                );
                        		new ConditionalBuilder(builder)
                        				.addWithCondition(HttpMethod.DELETE, REALM_ROLES_ROLE_NAME, handler::deleteRoleByName, endpointExposureConfiguration.isDeleteRoleByName())
                        				.addWithCondition(HttpMethod.DELETE, REALM_USERS_USER_ID, handler::deleteUser, endpointExposureConfiguration.isDeleteUser())
                        				.addWithCondition(HttpMethod.DELETE, REALM_USERS_ID_ROLE_MAPPINGS_REALM, handler::deleteUserRealmRoleMapping, endpointExposureConfiguration.isDeleteUserRealmRoleMapping())
                        				.addWithCondition(HttpMethod.DELETE, REALM_CLIENTS_CLIENT_ID, handler::deleteClient, endpointExposureConfiguration.isDeleteClient());
					}
                ).build();
    }
	
	private class ConditionalBuilder {
		private Builder builderInstance;

		public ConditionalBuilder(Builder builder) {
			builderInstance = builder;
		}

		private ConditionalBuilder addWithCondition(HttpMethod method, String pattern,
				HandlerFunction<ServerResponse> handlerFunction, boolean enabled) {
			if (enabled) {
				builderInstance.add(RouterFunctions
						.route(RequestPredicates.method(method).and(RequestPredicates.path(pattern)), handlerFunction));
			}
			return this;
		}
	}
}
