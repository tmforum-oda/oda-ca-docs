#
# COPYRIGHT Ericsson 2021
#
#
#
# The copyright to the computer program(s) herein is the property of
#
# Ericsson Inc. The programs may be used and/or copied only with written
#
# permission from Ericsson Inc. or in accordance with the terms and
#
# conditions stipulated in the agreement/contract under which the
#
# program(s) have been supplied.
#

FROM openjdk:11.0.16-slim
#ARG JAR_FILE
ARG COMMIT=1
ARG BUILD_DATE=2023-04-18
ARG APP_VERSION=0.1
EXPOSE 8080

LABEL \
    org.opencontainers.image.title=pimIamAdapterExt \
    org.opencontainers.image.created=$BUILD_DATE \
    org.opencontainers.image.revision=$COMMIT \
    org.opencontainers.image.vendor=Ericsson \
    org.opencontainers.image.version=$APP_VERSION
#ADD --chown=122937 ${JAR_FILE} pimIamAdapterExt.jar
COPY pim-iam-ext.jar /pimIamAdapterExt.jar
CMD java ${JAVA_OPTS} -jar pimIamAdapterExt.jar -Dserver.ssl.key-store=src/main/resources/keystores/oda_ssl.p12 -Dserver.ssl.key-store-password=oda_ssl