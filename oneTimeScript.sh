#!/bin/bash

sh startEureka.sh
sleep 10s
startPC.sh
startProxy.sh
startAuth.sh
sh startBank1.sh
startBank2.sh
startPCC.sh
startCrypto.sh
startPaypal.sh
