#!/bin/bash

BASEURL="http://localhost:8080"


# Args:
#   $1 = URL (String), the URL that will be tested to retrieve
#   $2 = HTTP status code (Integer), status code that will be checked against
#        the status code from CURL
function test_get_endpoint () {
    # Generate a tmp file for storing the site content.
    local TEMPFILE
    TEMPFILE=$(mktemp)

    # Access the page and store the content and the HTTP status code
    local HTTP_CODE
    HTTP_CODE=$(curl -s -o "$TEMPFILE" -w "%{http_code}" "$1")
    local STATUS_CODE

    # Check the HTTP status code
    if [[ "$HTTP_CODE" -ne $2 ]] ; then
        cat "$TEMPFILE"
        STATUS_CODE=22
    else
        echo ""
	      STATUS_CODE=0
    fi
    # Remove the tmp file and return the exit status
    rm "$TEMPFILE"
    return $STATUS_CODE

}

function execute_test () {
    echo -n -e "\t$2"

    local OUTPUT
    OUTPUT=$("$@")
    if ! $OUTPUT ; then
        echo " ☐ (failed)"
        echo "$OUTPUT"
        exit 1
    fi
    echo " ☑"
}

echo "Testing views without database entries:"
execute_test test_get_endpoint "$BASEURL" "200"
execute_test test_get_endpoint "$BASEURL/news" "200"
execute_test test_get_endpoint "$BASEURL/finder" "200"
execute_test test_get_endpoint "$BASEURL/forum" "200"


echo "Inserting data into database"
curl -u dummy:ADDB0F5E7826C857D7376D1BD9BC33C0C544790A2EAC96144A8AF22B1298C940 -H "Accept: application/xml" -H "Content-Type: application/xml" "$BASEURL/api/import/articles" --data-binary @doc/examples/articles/article-69.xml
curl -u dummy:ADDB0F5E7826C857D7376D1BD9BC33C0C544790A2EAC96144A8AF22B1298C940 -H "Accept: application/xml" -H "Content-Type: application/xml" "$BASEURL/api/import/articles" --data-binary @doc/examples/articles/article-72.xml

curl -u dummy:ADDB0F5E7826C857D7376D1BD9BC33C0C544790A2EAC96144A8AF22B1298C940 -H "Accept: application/xml" -H "Content-Type: application/xml" "$BASEURL/api/import/news" --data-binary @doc/examples/news/news-1270.xml
curl -u dummy:ADDB0F5E7826C857D7376D1BD9BC33C0C544790A2EAC96144A8AF22B1298C940 -H "Accept: application/xml" -H "Content-Type: application/xml" "$BASEURL/api/import/news" --data-binary @doc/examples/news/news-1760.xml
echo ""


echo "Testing views with database entries:"
execute_test test_get_endpoint "$BASEURL" "200"
execute_test test_get_endpoint "$BASEURL/news" "200"
execute_test test_get_endpoint "$BASEURL/finder" "200"
execute_test test_get_endpoint "$BASEURL/forum" "200"



echo "Tests passed ✓"
exit 0
