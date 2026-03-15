# OpenFaaS Serverless Data Processing Pipeline

A serverless data processing pipeline for NYC taxi trip data, 
built using OpenFaaS as the function execution platform.

## Overview

This repository contains the orchestration layer of the pipeline. 
It defines and deploys the preprocessing function responsible for 
ingesting raw NYC taxi CSV data, parsing it into structured Fare 
objects, and serving it as JSON to downstream functions via the 
OpenFaaS gateway.

The full pipeline is composed of independently deployed 
function-based services across multiple repositories:

- **[OpenFaas](https://github.com/elenidriva/OpenFaas)** — 
  preprocessing & orchestration (this repo)
- **[alpha-mapper](https://github.com/elenidriva/alpha-mapper)** — 
  geospatial quadrant classification of pickup locations
- **[beta-mapper](https://github.com/elenidriva/beta-mapper)** — 
  trip filtering based on haversine distance, duration, 
  and passenger count
- **[gamma-mapper](https://github.com/elenidriva/gamma-mapper)** — 
  additional trip filtering layer

## Architecture

Each function is an independent OpenFaaS service deployed as a 
Docker container, defined via a stack YAML file. Functions 
communicate through the OpenFaaS gateway running locally.
```
CSV Data → Preprocess Function → Alpha Mapper → Beta/Gamma Mappers
```

## Tech Stack

Java, OpenFaaS, Docker

## Dataset

NYC Taxi Trip Duration dataset. Each record contains pickup/dropoff 
coordinates, timestamps, passenger count, and trip duration.
