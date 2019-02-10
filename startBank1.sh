#!/bin/bash

cd $(pwd)/Workspace/InteliJ/Master/SEP/server/bank-service
mvn spring-boot:run -Pbank1 -DprofileIdEnabled=true