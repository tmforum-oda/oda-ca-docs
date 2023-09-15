# 4. Which authentication protocol should be supported

Date: 2013-05-24

## Status

In progress

## Context

The authentication will support one or more open standard authentication protocols. We need to define which we support and which do we focus on in the first sprint. The common protocols are:

**OAuth2 (Open Authorization 2.0):** https://oauth.net/2/ 
OAuth2 is a protocol that allows an application to access specific parts of a user's data without needing their login credentials. Instead, it uses authorization tokens to prove an identity
between consumers and service providers. OAuth2 is commonly used as a way for Internet users to grant websites or applications access to their information on other websites without giving
them the passwords.

**OpenID Connect (OIDC):** https://openid.net/connect/
OpenID Connect is an authentication standard built on top of OAuth2. It allows clients to verify the identity of the user based on the authentication performed by an authorization server,
as well as to obtain basic profile information about the user. It's often used to allow users to log into services using their accounts with well-known providers such as Google or Facebook.

**SAML (Security Assertion Markup Language):** https://www.oasis-open.org/standard/saml/
SAML is a set of XML-based protocol for communicating identity, authentication and authorization data between parties, in particular, between an identity provider and a service provider.
SAML is typically used in enterprise environments for single sign-on (SSO) solutions where a user logs in once and gains access to multiple applications, with the applications trusting the user because they trust
the authenticating authority.


## Decision

The Proposal is to eventually support SAML as well as OpenID Connect/OAuth2. The initial sprint will focus on OpenID Connect/OAuth2. We don't believe we can create a MVP with just OAuth2 as we want to
support Authentication ad well as Authorization.

## Consequences

The Canvas use-cases and BDD features for sprint 3 need to be updated to reference OpenID Connect and OAuth2 protocols
