#!/bin/bash
for i in $(ls /tmp/export/article-*.xml)
do
	curl -u dummy:ADDB0F5E7826C857D7376D1BD9BC33C0C544790A2EAC96144A8AF22B1298C940 -X POST -H "Accept: application/xml" -H "Content-Type: application/xml" http://localhost:8080/api/import/articles --data-binary @$i
	echo $i
done
