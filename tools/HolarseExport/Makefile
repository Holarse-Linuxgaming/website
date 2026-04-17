build: setup
	mvn clean package

setup:
	mkdir -p /tmp/export

run: setup
	java -jar target/HolarseExport-1.0-SNAPSHOT-jar-with-dependencies.jar

.PHONY: clean
	$(RM) -rf target
	$(RM) -rf /tmp/export
