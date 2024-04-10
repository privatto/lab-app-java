ARG ARG_BUILD_DIR="/tmp/springboot"

#########
# Build
#########
FROM maven:3-openjdk-17 AS build

ARG ARG_BUILD_DIR
ENV BUILD_DIR=${ARG_BUILD_DIR}

WORKDIR ${BUILD_DIR}

# Copy Files
COPY .mvn/ .mvn/
COPY src/ src/
COPY ./pom.xml .
COPY ./mvnw .

# Permition
USER 0
RUN chown -R 185:0 "${BUILD_DIR}" && chmod -R g=u "${BUILD_DIR}"

# Marven Package
USER 185
RUN mvn clean package

########
# Test
########
FROM build AS test

ARG ARG_BUILD_DIR
ENV BUILD_DIR=${ARG_BUILD_DIR}
WORKDIR ${BUILD_DIR}

RUN mvn test


#########
# Runner
#########
#FROM eclipse-temurin:17-alpine
FROM eclipse-temurin:17

ARG ARG_BUILD_DIR
ENV BUILD_DIR=${ARG_BUILD_DIR}
ENV SPRINGBOOT_DIR=/opt/springboot

WORKDIR ${SPRINGBOOT_DIR}

COPY --from=build ${BUILD_DIR}/target/*.jar ${SPRINGBOOT_DIR}/app.jar

USER 185
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]