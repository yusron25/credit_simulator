#!/bin/bash

if [ -z "$1" ]; then
  echo "Usage: $0 <parameter_file>"
  exit 1
fi

PARAM_FILE="$1"


VEHICLE_TYPE=""
VEHICLE_STATUS=""
PRICE=""
DP=""
TENOR=""


while IFS='=' read -r key value; do
  case "$key" in
    'VEHICLE_TYPE') VEHICLE_TYPE="$value" ;;
    'VEHICLE_STATUS') VEHICLE_STATUS="$value" ;;
    'PRICE') PRICE="$value" ;;
    'DP') DP="$value" ;;
    'TENOR') TENOR="$value" ;;

  esac
done < "$PARAM_FILE"

java -jar credit_simulator-1.jar --vehicle.type="$VEHICLE_TYPE" --vehicle.status="$VEHICLE_STATUS" --vehicle.price="$PRICE" --vehicle.dp="$DP" --vehicle.tenor="$TENOR"