# CTK Troubleshooting Guide

The Compliance Test Kit will perform static and dynamic tests of the component against the ODA Component standard. Static tests are just testing the components YAML definition; The dynamic tests will connect to the running component in an ODA Canvas and perform tests via APIs.

The Compliance Test consists of two levels: The level 1 is testing against the underlying design standards that apply to all components; The level 2 tests are testing the functionality of a specific component type against the `Golden Component` for that type.

See the video below for an introduction to the CTK.

 [![Compliance Test Kit](https://img.youtube.com/vi/RyTGzc4pv64/0.jpg)](https://www.youtube.com/watch?v=RyTGzc4pv64)


For a newly developed component, it is good practice to execute the L1 static test against the component definition before attempting to deploy the component. The L1 static test will highlight if there are any missing or invalid parts to the component defintion (that would cause the deployment to fail anyway).