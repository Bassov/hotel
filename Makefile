init_db:
	psql < src/main/resources/db/setup.sql
	psql -d hotel -U hotel_adm < src/main/resources/db/data.sql