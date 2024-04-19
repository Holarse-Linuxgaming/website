BASE_FILES=$(find doc/db2/01_schema/ -type f | sort)
echo $BASE_FILES

PG_PASSWORD=geheim

for i in $BASE_FILES; do
    echo "Running $i"
    docker-compose -f doc/docker-compose.yml exec -T db psql -v ON_ERROR_STOP=1 -U holarse -d holarse < $i
    rc=$?
    if [ ! "$rc" -eq 0 ]; then
      exit 1
    fi
done

DATA_FILES=$(find doc/db2/02_data/ -type f | sort)

for i in $DATA_FILES; do
    echo "Running $i"
    docker-compose -f doc/docker-compose.yml exec -T db psql -v ON_ERROR_STOP=1 -U holarse -d holarse < $i
    rc=$?
    if [ ! "$rc" -eq 0 ]; then
      exit 1
    fi
done