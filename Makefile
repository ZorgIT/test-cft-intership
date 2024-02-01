.DEFAULT_GOAL := build-run
.PHONY: build

fast-start:
	./gradlew wrapper --gradle-version 8.5 clean build install

clean:
	./gradlew clean

setup:
	./gradlew wrapper --gradle-version 8.5

build:
	./gradlew clean build

install:
	./gradlew clean install

run-dist:
	./build/install/java-package/bin/java-package

run:
	./gradlew run

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain

check-deps:
	./gradlew dependencyUpdates -Drevision=release


build-run: build run