FROM maven:3-jdk-8 as builder

RUN rm /dev/random && ln -s /dev/urandom /dev/random

WORKDIR /opt/opal
COPY . /opt/opal
RUN mvn package

FROM tomcat:8.5-jre8
LABEL maintainer="Michael Hahn <mhahn.dev@gmail.com>"

RUN rm /dev/random && ln -s /dev/urandom /dev/random

# Apply workaround to enable the execution of 32bit Fortran executables on a 64bit Linux OS: https://stackoverflow.com/questions/14775708/32-bit-fortran-on-64-bit-server
RUN apt-get -y update && \
    apt-get -y install libc6-i386 lib32gfortran3

ENV OPAL_HOME /opalXYZR
ENV OPAL_EXEC ${OPAL_HOME}/bin
ENV OPAL_DATA ${OPAL_HOME}/data
ENV TRADE_URL http://localhost:8081/api

# Create a folder for the executables and data
RUN mkdir -p ${OPAL_EXEC}
RUN mkdir -p ${OPAL_DATA}

# Add the required Opal executable(s) and scripts
ADD opalxyzrarg ${OPAL_EXEC}
WORKDIR ${OPAL_EXEC}
RUN chmod -R a+x *

EXPOSE 8080

COPY --from=builder /opt/opal/target/opalXYZR.war ${CATALINA_HOME}/webapps

WORKDIR ${OPAL_DATA}

CMD ${CATALINA_HOME}/bin/catalina.sh run

#
# Manually build by running:
#
#   docker build -t trade4chor/opalxyzr-service-wrapper .
#
# 
