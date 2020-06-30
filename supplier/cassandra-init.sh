
CQL="DROP keyspace nineleaps;

CREATE KEYSPACE nineleaps
WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};

CREATE TABLE nineleaps.supplier (
    id int,
    email text,
    name text
	PRIMARY KEY(id)
);

CREATE TABLE nineleaps.product (
    id int,
    supplier_id int,
    description text,
    name text,
    price double,
    PRIMARY KEY (id, supplier_id)
);

CREATE TYPE nineleaps.address (
    houseno text,
    street text,
    city text,
    state text,
    country text,
    pincode int
);

CREATE TYPE nineleapsespringboot.item (
    product_id int,
    quantity int,
    price double
);

CREATE TABLE nineleaps.orders (
    id int PRIMARY KEY,
    customer_address address,
    customer_email text,
    customer_name text,
    date timestamp,
    items item,
    total int
);"

until echo $CQL | cqlsh; do
  echo "cqlsh: Cassandra is unavailable to initialize - will retry later"
  sleep 2
done &

exec /docker-entrypoint.sh "$@"
