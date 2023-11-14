FROM ubuntu:latest
LABEL authors="Adam"

ENTRYPOINT ["top", "-b"]