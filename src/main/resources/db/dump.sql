--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 10.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: cars; Type: SCHEMA; Schema: -; Owner: cars_user
--

CREATE SCHEMA cars;


ALTER SCHEMA cars OWNER TO cars_user;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: car; Type: TABLE; Schema: cars; Owner: cars_user
--

CREATE TABLE cars.car (
    name character varying(255) NOT NULL,
    manufacturername character varying(255) NOT NULL,
    numberofcylinders integer NOT NULL,
    manufacturer integer
);


ALTER TABLE cars.car OWNER TO cars_user;

--
-- Name: manufacturer; Type: TABLE; Schema: cars; Owner: cars_user
--

CREATE TABLE cars.manufacturer (
    name character varying(255) NOT NULL,
    countryoforigin character varying(255) NOT NULL
);


ALTER TABLE cars.manufacturer OWNER TO cars_user;

--
-- Name: trim; Type: TABLE; Schema: cars; Owner: cars_user
--

CREATE TABLE cars."trim" (
    name character varying(255) NOT NULL,
    carname character varying(255) NOT NULL,
    car integer
);


ALTER TABLE cars."trim" OWNER TO cars_user;

--
-- Name: manufacturer_cars; Type: TABLE; Schema: public; Owner: cars_user
--

CREATE TABLE public.manufacturer_cars (
    manufacturer_name character varying(255) NOT NULL,
    cars_name character varying(255) NOT NULL
);


ALTER TABLE public.manufacturer_cars OWNER TO cars_user;

--
-- Data for Name: car; Type: TABLE DATA; Schema: cars; Owner: cars_user
--

COPY cars.car (name, manufacturername, numberofcylinders, manufacturer) FROM stdin;
Focus	Ford	4	\N
Explorer	Ford	6	\N
Accord	Honda	4	\N
Pilot	Honda	6	\N
Camry	Toyota	4	\N
Sequoia	Toyota	8	\N
Integra	Acura	4	\N
\.


--
-- Data for Name: manufacturer; Type: TABLE DATA; Schema: cars; Owner: cars_user
--

COPY cars.manufacturer (name, countryoforigin) FROM stdin;
Ford	USA
Honda	Japan
Toyota	Japan
Acura	Japan
\.


--
-- Data for Name: trim; Type: TABLE DATA; Schema: cars; Owner: cars_user
--

COPY cars."trim" (name, carname, car) FROM stdin;
Focus-Trim1	Focus	\N
Focus-Trim2	Focus	\N
Explorer-Trim1	Explorer	\N
Explorer-Trim2	Explorer	\N
Accord-Trim1	Accord	\N
Accord-Trim2	Accord	\N
Pilot-Trim1	Pilot	\N
Pilot-Trim2	Pilot	\N
Camry-Trim1	Camry	\N
Camry-Trim2	Camry	\N
Sequoia-Trim1	Sequoia	\N
Sequoia-Trim2	Sequoia	\N
Integra-Trim1	Integra	\N
Integra-Trim2	Integra	\N
\.


--
-- Data for Name: manufacturer_cars; Type: TABLE DATA; Schema: public; Owner: cars_user
--

COPY public.manufacturer_cars (manufacturer_name, cars_name) FROM stdin;
\.


--
-- Name: car car_pkey; Type: CONSTRAINT; Schema: cars; Owner: cars_user
--

ALTER TABLE ONLY cars.car
    ADD CONSTRAINT car_pkey PRIMARY KEY (name);


--
-- Name: manufacturer manufacturer_pkey; Type: CONSTRAINT; Schema: cars; Owner: cars_user
--

ALTER TABLE ONLY cars.manufacturer
    ADD CONSTRAINT manufacturer_pkey PRIMARY KEY (name);


--
-- Name: trim trim_pkey; Type: CONSTRAINT; Schema: cars; Owner: cars_user
--

ALTER TABLE ONLY cars."trim"
    ADD CONSTRAINT trim_pkey PRIMARY KEY (name);


--
-- Name: manufacturer_cars uk_nwq7fi2h7waltgm2yen4a8sgm; Type: CONSTRAINT; Schema: public; Owner: cars_user
--

ALTER TABLE ONLY public.manufacturer_cars
    ADD CONSTRAINT uk_nwq7fi2h7waltgm2yen4a8sgm UNIQUE (cars_name);


--
-- Name: car_name_uindex; Type: INDEX; Schema: cars; Owner: cars_user
--

CREATE UNIQUE INDEX car_name_uindex ON cars.car USING btree (name);


--
-- Name: manufacturer_name_uindex; Type: INDEX; Schema: cars; Owner: cars_user
--

CREATE UNIQUE INDEX manufacturer_name_uindex ON cars.manufacturer USING btree (name);


--
-- Name: trim_name_uindex; Type: INDEX; Schema: cars; Owner: cars_user
--

CREATE UNIQUE INDEX trim_name_uindex ON cars."trim" USING btree (name);


--
-- Name: car car_manufacturer_name_fk; Type: FK CONSTRAINT; Schema: cars; Owner: cars_user
--

ALTER TABLE ONLY cars.car
    ADD CONSTRAINT car_manufacturer_name_fk FOREIGN KEY (manufacturername) REFERENCES cars.manufacturer(name);


--
-- Name: trim fkp3prgesdt52dk7t3itbatdrrx; Type: FK CONSTRAINT; Schema: cars; Owner: cars_user
--

ALTER TABLE ONLY cars."trim"
    ADD CONSTRAINT fkp3prgesdt52dk7t3itbatdrrx FOREIGN KEY (carname) REFERENCES cars.car(name);


--
-- Name: car fkrjuahgm7x6bj0d9fy2gbr1b5c; Type: FK CONSTRAINT; Schema: cars; Owner: cars_user
--

ALTER TABLE ONLY cars.car
    ADD CONSTRAINT fkrjuahgm7x6bj0d9fy2gbr1b5c FOREIGN KEY (manufacturername) REFERENCES cars.manufacturer(name);


--
-- Name: trim trim_car_name_fk; Type: FK CONSTRAINT; Schema: cars; Owner: cars_user
--

ALTER TABLE ONLY cars."trim"
    ADD CONSTRAINT trim_car_name_fk FOREIGN KEY (carname) REFERENCES cars.car(name);


--
-- PostgreSQL database dump complete
--

