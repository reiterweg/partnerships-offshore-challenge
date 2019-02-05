INSERT INTO VENUES (id, name, city, state) VALUES (nextval('venues_id_seq'), 'Wrigley Field', 'Chicago', 'IL');
INSERT INTO EVENTS (id, name, date, venue_id) VALUES (nextval('events_id_seq'), 'Chicago White Sox vs. Chicago Cubs', now(), 1);
