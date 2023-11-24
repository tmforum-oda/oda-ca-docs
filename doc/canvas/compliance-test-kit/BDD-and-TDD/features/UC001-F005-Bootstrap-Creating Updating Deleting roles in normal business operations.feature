# The 'business stakeholders' for the canvas Behaviour Driven Design are the Engineering teams
# from the Vendor of a component, from a Systems Integrator who may be integrating and deploying 
# a component, or a Service Provider's Operations team who may be supporting a component.

@UC001         # tagged as use case 1
@UC001-F005    # tagged as use feature 4 within use case 1
Feature: UC001-F005-Bootstrap - Creating Updating Deleting roles in normal business operations.

Background: Installing an example package
    Given An example package 'productcatalog-v1beta2' has been installed

Scenario: Creating a role in Component
    Given a 'MVNO' permission specification set 'does not' exist in the Component
    When A business admin 'creates' a 'MVNO' permission specification set with description 'MVNO-test' in the Component
    Then I should see the 'new' 'MVNO' role for the 'productcatalog' component with description 'MVNO-test' in the identity platform

Scenario: Updating a role in Component
    Given a 'MVNO' permission specification set 'does' exist with description 'MVNO-test' in the Component
    When A business admin 'updates' a 'MVNO' permission specification set with description 'MVNO-test 2' in the Component
    Then I should see the 'updated' 'MVNO' role for the 'productcatalog' component with description 'MVNO-test 2' in the identity platform

Scenario: Deleting a role in Component
    Given a 'MVNO' permission specification set 'does' exist in the Component
    When A business admin 'deletes' a 'MVNO' permission specification set in the Component
    Then I should not see the 'MVNO' role for the 'productcatalog' component in the identity platform


